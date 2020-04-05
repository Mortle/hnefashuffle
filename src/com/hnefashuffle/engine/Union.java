package com.hnefashuffle.engine;

import com.hnefashuffle.engine.player.AttackerPlayer;
import com.hnefashuffle.engine.player.DefenderPlayer;
import com.hnefashuffle.engine.player.Player;

public enum Union {
    ATTACKER {
        @Override
        public Player choosePlayer(AttackerPlayer attackersPlayer, DefenderPlayer defendersPlayer) {
            return defendersPlayer;
        }

        @Override
        public String toString() {
            return "Attacker";
        }
    },
    DEFENDER {
        @Override
        public Player choosePlayer(AttackerPlayer attackersPlayer, DefenderPlayer defendersPlayer) {
            return attackersPlayer;
        }

        @Override
        public String toString() {
            return "Defender";
        }
    };

    public abstract Player choosePlayer(AttackerPlayer attackersPlayer, DefenderPlayer defendersPlayer);
}
