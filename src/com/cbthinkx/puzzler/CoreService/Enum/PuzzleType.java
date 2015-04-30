package com.cbthinkx.puzzler.CoreService.Enum;

public enum PuzzleType {
    ONESIDED(1), TWOSIDED(2);
    private  final int val;
    PuzzleType (int i) {
        this.val = i;
    }
    public int getVal() {
        return val;
    }
    public static PuzzleType valueOf(int x){
        for (PuzzleType y : PuzzleType.values()) {
            if (y.getVal() == x){
                return y;
            }
        }
        return null;
    }
}
