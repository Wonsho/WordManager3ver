package com.wons.wordmanager3ver.datavalues;

public enum EnumListGrade {
    MASTER(4),
    GOLD(3),
    SILVER(2),
    BRONZE(1),
    NON(0);
    public int gradeInt;
    EnumListGrade(int grade) {
        this.gradeInt = grade;
    }
}
