package com.cbthinkx.puzzler.CoreService;

public enum PieceShape {
    SQUARE(1), JIGSAW(2);
    private  final int val;
    PieceShape (int i) {
        this.val = i;
    }
    public int getVal() {
        return val;
    }
    public static PieceShape valueOf(int x){
        for (PieceShape y : PieceShape.values()) {
            if (y.getVal() == x){
                return y;
            }
        }
        return null;
    }

}
