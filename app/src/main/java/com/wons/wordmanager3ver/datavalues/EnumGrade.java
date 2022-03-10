package com.wons.wordmanager3ver.datavalues;

public enum EnumGrade {

    A_PLUS("A+"),
    A("A"),
    B_PLUS("B+"),
    B("B"),
    C_PLUS("C+"),
    C("C"),
    D_PLUS("D+"),
    D("D"),
    F("F");

    public String grade;

    EnumGrade(String grade) {
        this.grade = grade;
    }
}
