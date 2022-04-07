package com.wons.wordmanager3ver.fragmenthome.value;

import com.wons.wordmanager3ver.R;

public class GameValue {
    public int gameCode;
    public String gameName;
    public String gameInfo;
    public int gamePic;

    public GameValue(EnumGame game) {
        init(game);
    }

    private void init(EnumGame enumGame) {
        switch (enumGame) {
            case HANGMAN_GAME: {
                this.gameCode = enumGame.gameCodeInt;
                this.gameName = "행맨게임";
                this.gameInfo = "행맨게임 입니다\n컴퓨터가 고른 단어를 맟춰보세요.";
                this.gamePic = R.mipmap.ic_launcher_round;
            }
        }
    }

}
