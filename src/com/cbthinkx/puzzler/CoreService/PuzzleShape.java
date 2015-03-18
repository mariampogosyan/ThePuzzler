package com.cbthinkx.puzzler.CoreService;

public enum PuzzleShape {
    SQUARE(1), ELLIPSE(2), HEART(3), NGON(4);
    private  final int val;
    PuzzleShape (int i) {
        this.val = i;
    }
    public int getVal() {
        return val;
    }
    public static PuzzleShape valueOf(int x){
        for (PuzzleShape y : PuzzleShape.values()) {
            if (y.getVal() == x){
                return y;
            }
        }
        return null;
    }
}
