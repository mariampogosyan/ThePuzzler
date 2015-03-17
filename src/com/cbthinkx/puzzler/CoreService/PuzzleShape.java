package com.cbthinkx.puzzler.CoreService;

public enum PuzzleShape {
    HEART(1), ELLIPSE(2), NGON(3), SQUARE(4);
    private  final int val;
    PuzzleShape (int i) {
        this.val = i;
    }
    public int getVal() {
        return val;
    }
    public PuzzleShape valueOf(int x){
        for (PuzzleShape y : PuzzleShape.values()) {
            if (y.getVal() == x){
                return y;
            }
        }
        return null;
    }
}
