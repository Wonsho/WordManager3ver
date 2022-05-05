package com.wons.wordmanager3ver.gamefragment;

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
                this.gamePic = R.drawable.image;
                break;

            }
            case MAKE_WORD_BY_SPELLING: {
                this.gameCode = enumGame.gameCodeInt;
                this.gameName = "스펠링 조합하기";
                this.gameInfo = "스펠링 조합하기입니다\n컴퓨터가 고른 단어의 스펠링을 조합해보세요";
                this.gamePic = R.drawable.makeword;
                break;
                //todo recycler view 로 선택 단어 카드 만듦 --> 원래 스펠링을 뒤죽박죽 섞음
                // 선택 화면을 누르면 값이 들어가는 뷰에 넣어줌
                // 값이 들어가는 뷰는 빈칸으로 미리 표기해줌 (띄어쓰기 때문 )*/
            }

            case FOUR_CARD: {
                this.gameCode = enumGame.gameCodeInt;
                this.gameName = "사지선다";
                this.gameInfo = "사지선다입니다\n4개의 카드중 맞는것을 골라주세요";
                this.gamePic = R.drawable.four;
                break;
                //TODO  총 단어중에 내가 한 단어 표시 ex) 2/32
                // 틀렸던 단어를 저장해서 사지선다 형태로 맞는 정답, 내가 선택한 카드를 보여주기
                // 백버튼 -> 내가 햇던것을 뷰 보여주기
                // 햇던것이 없으면 finish()*/
            }

//            case WRITE_WORD: {
//                this.gameCode = enumGame.gameCodeInt;
//                this.gameName = "받아쓰기";
//                this.gameInfo = "받아쓰기입니다\n소리를 듣고 단어를 써보세요";
//                this.gamePic = R.drawable.pixabay;
//                break;
//                //todo  총 단어중에 내가 한 단어 표시 ex) 2/32
//                // 받아쓰기 처음에 단어를 TTS 해주고 스피커 버튼을 달아 TTS 를 계속 실행 가능
//                // 백버튼 --> 햇던것 보여주기
//                // 없으면 finish*/
//            }

            case PUT_SPELL_AT_BLANK: {
                this.gameCode = enumGame.gameCodeInt;
                this.gameName = "빈칸 맞추기";
                this.gameInfo = "빈칸 맞추기입니다\n컴퓨터가 고른 단어의 빈칸을 채워보세요";
                this.gamePic = R.drawable.putblank;
                //todo 뜻을 보여줌
                // 단어의 30%을 빈칸으로 설정
                // (recycler view horizontal view 로 뿌려줌)
                // 빈칸이 있는 뷰 그리고 선택하는 뷰
                // --> 선택하는 뷰는 선택된 단어의 빈칸에 맞는 스펠링 과 그 스펠링 수의 *2배의 랜덤한 스펠링을 가짐
                // 빈칸이 있는 뷰는 선택하면 없어지고 다시 재 설정 가능 or 되돌리기 버튼 넣기*/
                break;
            }

            case OX_QUIZ: {
                this.gameCode = enumGame.gameCodeInt;
                this.gameName = "O X 퀴즈";
                this.gameInfo = "O X 퀴즈입니다\n맞으면 O 틀리면 X 를 눌러주세요";
                this.gamePic = R.drawable.ox;

                //todo 랜덤으로 틀린 답을 넣을지 맞는 답을 넣을지 1/3 의 확률로 실행
                // 이것도 수량 표기  ex 1/65*
                // 나중에 틀린것과 맞는것 리스트로 뿌려줌 /
                break;
            }



        }
    }

}
