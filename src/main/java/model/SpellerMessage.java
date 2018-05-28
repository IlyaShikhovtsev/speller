package model;

public class SpellerMessage {
    private int code;
    private int pos;
    private int row;
    private int col;
    private int len;
    private String word;
    private String[] s;

    public int getCode() {
        return code;
    }

    public int getPos() {
        return pos;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public int getLen() {
        return len;
    }

    public String getWord() {
        return word;
    }

    public String[] getS() {
        return s;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public void setLen(int len) {
        this.len = len;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public void setS(String[] s) {
        this.s = s;
    }
}
