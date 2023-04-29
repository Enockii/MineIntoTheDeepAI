package fr.iutdijon.mineintothedeep.css117.ai;

import fr.iutdijon.mineintothedeep.css117.player.IMineIntoTheDeepPlayer;

public interface MineIntoTheDeepAI {
    /**
     * Triggered each time the server ask you to play
     */
    void play(IMineIntoTheDeepPlayer player);
}
