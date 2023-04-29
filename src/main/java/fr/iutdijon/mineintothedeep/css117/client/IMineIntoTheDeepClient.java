package fr.iutdijon.mineintothedeep.css117.client;

import fr.iutdijon.mineintothedeep.css117.message.MineIntoTheDeepMessage;
import fr.iutdijon.mineintothedeep.css117.player.IMineIntoTheDeepPlayer;

import java.io.IOException;

public interface IMineIntoTheDeepClient {
    //#region Connection
    void connect() throws IOException;
    //#endregion

    //#region Message
    <T> T sendMessage(MineIntoTheDeepMessage<T> message);
    //#endregion

    //#region State
    boolean isConnected();
    String getTeamName();
    Integer getTeamNumber();
    //#endregion

    IMineIntoTheDeepPlayer getPlayer();
}
