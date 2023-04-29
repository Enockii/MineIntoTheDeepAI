package fr.iutdijon.mineintothedeep.css117.client;

import fr.iutdijon.mineintothedeep.css117.map.MineIntoTheDeepMap;
import fr.iutdijon.mineintothedeep.css117.MineIntoTheDeepScores;
import fr.iutdijon.mineintothedeep.css117.ai.MineIntoTheDeepAI;
import fr.iutdijon.mineintothedeep.css117.message.*;
import fr.iutdijon.mineintothedeep.css117.player.IMineIntoTheDeepPlayer;
import fr.iutdijon.mineintothedeep.css117.player.PickaxeUpgrade;

import java.awt.*;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class MineIntoTheDeepClient implements IMineIntoTheDeepClient {

    //#region Connection
    private final InetSocketAddress address;
    private Socket clientSocket;
    private BufferedInputStream inputStream;
    private BufferedOutputStream outputStream;
    //#endregion

    //#region Information
    private final String teamName;
    private Integer teamNumber;
    private Integer currentTurnNumber;
    //#endregion

    private final MineIntoTheDeepAI mineIntoTheDeepClientAI;
    private final IMineIntoTheDeepPlayer player;

    public MineIntoTheDeepClient(String teamName, InetSocketAddress address, MineIntoTheDeepAI mineIntoTheDeepClientAI) {
        if (teamName == null)
            throw new IllegalArgumentException("The team name cannot be null");

        if (address == null)
            throw new IllegalArgumentException("The address cannot be null");

        if (mineIntoTheDeepClientAI == null)
            throw new IllegalArgumentException("The AI cannot be null");

        this.teamName = teamName;
        this.address = address;
        this.mineIntoTheDeepClientAI = mineIntoTheDeepClientAI;
        this.player = new MineIntoTheDeepPlayer();
    }

    private String readNextMessage() throws IOException {
        byte[] buffer = new byte[1024];
        int read = this.inputStream.read(buffer);
        if (read == -1)
            throw new RuntimeException("The connection has been lost");

        return new String(buffer, 0, read).replace("\n", "").replace("\r", "");
    }

    @Override
    public void connect() throws IOException {
        if (this.clientSocket != null && this.clientSocket.isConnected())
            throw new IllegalArgumentException("The connection is already established");

        this.clientSocket = new Socket();
        this.clientSocket.connect(this.address, 5000);

        this.inputStream = new BufferedInputStream(this.clientSocket.getInputStream());
        this.outputStream = new BufferedOutputStream(this.clientSocket.getOutputStream());

        while (true) {
            try {
                String[] messages = MineIntoTheDeepClient.this.readNextMessage().split("\\|");
                if (messages.length == 0)
                    throw new IllegalStateException("The server has sent an empty message");

                if (messages[0].equals(MineIntoTheDeepMessages.SERVER_CONNECTED))
                    this.teamNumber = this.sendMessage(new MineIntoTheDeepPresentationMessage(this.teamName));

                else if (messages[0].equals(MineIntoTheDeepMessages.SERVER_MY_TURN)) {
                    if (messages.length < 2)
                        throw new IllegalStateException("The server did not sent the turn number");

                    this.currentTurnNumber = Integer.parseInt(messages[1]);
                    try {
                        this.mineIntoTheDeepClientAI.play(MineIntoTheDeepClient.this.player);
                    } catch (Exception e) {
                        System.err.println("An error occurred while playing the turn");
                        e.printStackTrace();
                    }
                }

                else
                    throw new IllegalStateException("The server has sent an unknown message: " + messages[0]);
            } catch (IOException e) {
                break;
            }
        }
        throw new RuntimeException("The connection has been lost");
    }

    @Override
    public <T> T sendMessage(MineIntoTheDeepMessage<T> message) {
        if (message == null)
            throw new IllegalArgumentException("The message cannot be null");

        try {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(message.getMessage());

            if (message.getParameters() != null)
                for (Object argument : message.getParameters())
                    stringBuilder.append("|").append(argument);

            this.outputStream.write((stringBuilder.toString() + "\n").getBytes());
            this.outputStream.flush();

            if (!message.waitingForResponse())
                return null;

            String rawResponse = this.readNextMessage();
            try {
                MineIntoTheDeepResponse<T> response = message.parse(rawResponse.split("\\|"));

                if (response == null)
                    throw new RuntimeException("The server has sent an unknown message: " + rawResponse);

                if (!response.isSuccess())
                    throw new RuntimeException("The server has sent an error message: " + response.getErrorMessage());

                return response.getResponse();
            }
            catch (Exception e) {
                System.err.println("An error occurred while parsing the response: " + rawResponse);
                throw new RuntimeException("An error occurred while parsing the response", e);
            }
        } catch (IOException e) {
            throw new RuntimeException("The connection has been lost");
        }
    }

    @Override
    public boolean isConnected() {
        return this.clientSocket != null && this.clientSocket.isConnected();
    }

    @Override
    public String getTeamName() {
        return teamName;
    }

    @Override
    public Integer getTeamNumber() {
        return teamNumber;
    }

    @Override
    public IMineIntoTheDeepPlayer getPlayer() {
        return player;
    }

    public class MineIntoTheDeepPlayer implements IMineIntoTheDeepPlayer {
        private final Map<Integer, PickaxeUpgrade> currentPickaxeUpgrades;
        private final Map<Integer, Point> dwarfPositions;

        private MineIntoTheDeepPlayer() {
            this.currentPickaxeUpgrades = new HashMap<>();
            this.dwarfPositions = new HashMap<>();
        }

        @Override
        public int getMyPlayerId() {
            return MineIntoTheDeepClient.this.teamNumber;
        }

        @Override
        public int getTurnNumber() {
            return MineIntoTheDeepClient.this.currentTurnNumber;
        }


        @Override
        public PickaxeUpgrade getPickaxeUpgrade(int dwarfId) {
            return this.currentPickaxeUpgrades.getOrDefault(dwarfId, PickaxeUpgrade.WOODEN);
        }

        @Override
        public Point getDwarfPosition(int dwarfId) {
            return this.dwarfPositions.get(dwarfId);
        }


        @Override
        public void endOfTurn() {
            MineIntoTheDeepClient.this.sendMessage(new MineIntoTheDeepEndTurnMessage());
        }

        @Override
        public MineIntoTheDeepMap getMap() {
            return MineIntoTheDeepClient.this.sendMessage(new MineIntoTheDeepMapMessage());
        }

        @Override
        public void moveDwarf(int dwarfId, int dx, int dy) {
            MineIntoTheDeepClient.this.sendMessage(new MineIntoTheDeepMoveDwarfMessage(dwarfId, dx, dy));
            this.dwarfPositions.put(dwarfId, new Point(dx, dy));
        }

        @Override
        public void removeDwarf(int dwarfId) {
            MineIntoTheDeepClient.this.sendMessage(new MineIntoTheDeepRemoveDwarfMessage(dwarfId));
            this.dwarfPositions.remove(dwarfId);
        }

        @Override
        public void hireDwarf() {
            MineIntoTheDeepClient.this.sendMessage(new MineIntoTheDeepHireDwarfMessage());
        }

        @Override
        public void upgradePickaxe(int dwarfId) {
            PickaxeUpgrade currentPickaxeUpgrade = this.getPickaxeUpgrade(dwarfId);
            if (currentPickaxeUpgrade.getNextUpgrade() == null)
                throw new IllegalStateException("The pickaxe cannot be upgraded anymore");

            MineIntoTheDeepClient.this.sendMessage(new MineIntoTheDeepUpgradePickaxeMessage(dwarfId));
            this.currentPickaxeUpgrades.put(dwarfId, currentPickaxeUpgrade.getNextUpgrade());
        }

        @Override
        public void sabotage(int playerId) {
            MineIntoTheDeepClient.this.sendMessage(new MineIntoTheDeepSabotageMessage(playerId));
        }

        @Override
        public MineIntoTheDeepScores getScores() {
            return MineIntoTheDeepClient.this.sendMessage(new MineIntoTheDeepScoresMessage());
        }

        @Override
        public MineIntoTheDeepSonarMessage.MineIntoTheDeepSonarResponse sonar(int x, int y) {
            return MineIntoTheDeepClient.this.sendMessage(new MineIntoTheDeepSonarMessage(x, y));
        }
    }
}
