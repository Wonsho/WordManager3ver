package com.wons.wordmanager3ver.gamefragment;

public enum EnumGame {

    HANGMAN_GAME(0),
    MAKE_WORD_BY_SPELLING(1),
    PUT_SPELL_AT_BLANK(2),
    FOUR_CARD(3),
    WRITE_WORD(4),
    OX_QUIZ(5);
    public int gameCodeInt;
    EnumGame(int gameCode) {
        this.gameCodeInt = gameCode;
    }
}
