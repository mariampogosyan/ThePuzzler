package com.cbthinkx.puzzler.CoreService;

public enum PuzzleSkill {
    BABY(1), CHILD(2), ADULT(3);
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

}
