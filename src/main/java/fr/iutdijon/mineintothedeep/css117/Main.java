package fr.iutdijon.mineintothedeep.css117;

import fr.iutdijon.mineintothedeep.css117.ai.MineIntoTheDeepAI;
import fr.iutdijon.mineintothedeep.css117.ai.SampleMineIntoTheDeepAI;
import fr.iutdijon.mineintothedeep.css117.client.IMineIntoTheDeepClient;
import fr.iutdijon.mineintothedeep.css117.client.MineIntoTheDeepClient;

import java.io.IOException;
import java.net.InetSocketAddress;

public class Main {
    public static void main(String[] args) {
        // Team name
        String teamName = "CSS117";

        // AI to use
        MineIntoTheDeepAI aiToUse = new SampleMineIntoTheDeepAI();

        // Where to connect
        InetSocketAddress address = new InetSocketAddress("localhost", 1234);

        try {
            IMineIntoTheDeepClient client = new MineIntoTheDeepClient(teamName, address, aiToUse);
            client.connect();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}