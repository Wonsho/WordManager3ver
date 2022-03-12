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

    EnumGrade() {}
    EnumGrade(String grade) {
        this.grade = grade;
    }

    public String getGradeToString(int gradeInt) {
        StringBuilder grade = new StringBuilder();
        if (gradeInt < 10) {
            grade.append(EnumGrade.F.grade);
        } else if (gradeInt >= 10 && gradeInt < 20) {
            grade.append(EnumGrade.D.grade);
        } else if (gradeInt >= 20 && gradeInt < 25) {
            grade.append(EnumGrade.D_PLUS.grade);
        } else if (gradeInt >= 25 && gradeInt < 35) {
            grade.append(EnumGrade.C.grade);
        } else if (gradeInt >= 35 && gradeInt < 40) {
            grade.append(EnumGrade.C_PLUS.grade);
        } else if (gradeInt >= 40 && gradeInt < 60) {
            grade.append(EnumGrade.B.grade);
        } else if (gradeInt >= 60 && gradeInt < 70) {
            grade.append(EnumGrade.B_PLUS.grade);
        } else if (gradeInt >= 70 && gradeInt < 85) {
            grade.append(EnumGrade.A.grade);
        } else {
            grade.append(EnumGrade.A_PLUS.grade);
        }
        return grade.toString();
    }
}
