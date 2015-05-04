package com.cbthinkx.puzzler.CoreService.Enum;

public enum PuzzleSkill {
    BABY(144), CHILD(108), ADULT(72);
    private  final int val;
    PuzzleSkill (int i) {
        this.val = i;
    }
    public int getVal() {
        return val;
    }
    public static PuzzleSkill valueOf(int x){
        for (PuzzleSkill y : PuzzleSkill.values()) {
            if (y.getVal() == x){
                return y;
            }
        }
        return null;
    }
    public static PuzzleSkill valueOfIndex(int x) {
        if (x == 1) {
            return BABY;
        }
        if (x == 2) {
            return CHILD;
        }
        if (x == 3) {
            return ADULT;
        }
        return null;
    }

}
