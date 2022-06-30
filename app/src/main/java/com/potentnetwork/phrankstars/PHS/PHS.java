package com.potentnetwork.phrankstars.PHS;


import java.io.Serializable;
import java.util.Comparator;

public class PHS implements Serializable {
    String staff_name1, staff_position1, staff_phoneNumber1, staffSalary1, staffBonus1, staffDeduction1,
            staffSavings1, staffSavingsPerMonth1, staffLoan1, staffLoanPayPerMonth1, staff_bankAccountName1,
            staff_accountNumber1, staff_bankName1, staff_commentary1, createdDate,teacherImageUri,staff_salary_increment1,
            id,trackingDate,remainingLoan,loanPaid;
    double payable;

    public PHS(String staff_name1, String staff_position1, String staff_phoneNumber1,
               String staffSalary1, String staffBonus1, String staffDeduction1, String staffSavings1,
               String staffSavingsPerMonth1, String staffLoan1, String staffLoanPayPerMonth1, String staff_bankAccountName1,
               String staff_accountNumber1, String staff_bankName1, String staff_commentary1, String createdDate,
               String teacherImageUri, String staff_salary_increment1, String id, double payable,String trackingDate,String remainingLoan,String loanPaid) {
        this.staff_name1 = staff_name1;
        this.staff_position1 = staff_position1;
        this.staff_phoneNumber1 = staff_phoneNumber1;
        this.staffSalary1 = staffSalary1;
        this.staffBonus1 = staffBonus1;
        this.staffDeduction1 = staffDeduction1;
        this.staffSavings1 = staffSavings1;
        this.staffSavingsPerMonth1 = staffSavingsPerMonth1;
        this.staffLoan1 = staffLoan1;
        this.staffLoanPayPerMonth1 = staffLoanPayPerMonth1;
        this.staff_bankAccountName1 = staff_bankAccountName1;
        this.staff_accountNumber1 = staff_accountNumber1;
        this.staff_bankName1 = staff_bankName1;
        this.staff_commentary1 = staff_commentary1;
        this.createdDate = createdDate;
        this.teacherImageUri = teacherImageUri;
        this.staff_salary_increment1 = staff_salary_increment1;
        this.id = id;
        this.payable = payable;
        this.trackingDate = trackingDate;
        this.remainingLoan = remainingLoan;
        this.loanPaid = loanPaid;
    }

    public static Comparator<PHS> staffName = new Comparator<PHS>() {
        @Override
        public int compare(PHS s1, PHS s2) {
            return s1.getStaff_name1().compareTo(s2.getStaff_name1());
        }
    };

    public PHS(){

    }

    public String getStaff_name1() {
        return staff_name1;
    }

    public void setStaff_name1(String staff_name1) {
        this.staff_name1 = staff_name1;
    }

    public String getStaff_position1() {
        return staff_position1;
    }

    public void setStaff_position1(String staff_position1) {
        this.staff_position1 = staff_position1;
    }

    public String getStaff_phoneNumber1() {
        return staff_phoneNumber1;
    }

    public void setStaff_phoneNumber1(String staff_phoneNumber1) {
        this.staff_phoneNumber1 = staff_phoneNumber1;
    }

    public String getStaffSalary1() {
        return staffSalary1;
    }

    public void setStaffSalary1(String staffSalary1) {
        this.staffSalary1 = staffSalary1;
    }

    public String getStaffBonus1() {
        return staffBonus1;
    }

    public void setStaffBonus1(String staffBonus1) {
        this.staffBonus1 = staffBonus1;
    }

    public String getStaffDeduction1() {
        return staffDeduction1;
    }

    public void setStaffDeduction1(String staffDeduction1) {
        this.staffDeduction1 = staffDeduction1;
    }

    public String getStaffSavings1() {
        return staffSavings1;
    }

    public void setStaffSavings1(String staffSavings1) {
        this.staffSavings1 = staffSavings1;
    }

    public String getStaffSavingsPerMonth1() {
        return staffSavingsPerMonth1;
    }

    public void setStaffSavingsPerMonth1(String staffSavingsPerMonth1) {
        this.staffSavingsPerMonth1 = staffSavingsPerMonth1;
    }

    public String getStaffLoan1() {
        return staffLoan1;
    }

    public void setStaffLoan1(String staffLoan1) {
        this.staffLoan1 = staffLoan1;
    }

    public String getStaffLoanPayPerMonth1() {
        return staffLoanPayPerMonth1;
    }

    public void setStaffLoanPayPerMonth1(String staffLoanPayPerMonth1) {
        this.staffLoanPayPerMonth1 = staffLoanPayPerMonth1;
    }

    public String getStaff_bankAccountName1() {
        return staff_bankAccountName1;
    }

    public void setStaff_bankAccountName1(String staff_bankAccountName1) {
        this.staff_bankAccountName1 = staff_bankAccountName1;
    }

    public String getStaff_accountNumber1() {
        return staff_accountNumber1;
    }

    public void setStaff_accountNumber1(String staff_accountNumber1) {
        this.staff_accountNumber1 = staff_accountNumber1;
    }

    public String getStaff_bankName1() {
        return staff_bankName1;
    }

    public void setStaff_bankName1(String staff_bankName1) {
        this.staff_bankName1 = staff_bankName1;
    }

    public String getStaff_commentary1() {
        return staff_commentary1;
    }

    public void setStaff_commentary1(String staff_commentary1) {
        this.staff_commentary1 = staff_commentary1;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getTeacherImageUri() {
        return teacherImageUri;
    }

    public void setTeacherImageUri(String teacherImageUri) {
        this.teacherImageUri = teacherImageUri;
    }

    public String getStaff_salary_increment1() {
        return staff_salary_increment1;
    }

    public void setStaff_salary_increment1(String staff_salary_increment1) {
        this.staff_salary_increment1 = staff_salary_increment1;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getPayable() {
        return payable;
    }

    public void setPayable(double payable) {
        this.payable = payable;
    }

    public String getTrackingDate() {
        return trackingDate;
    }

    public void setTrackingDate(String trackingDate) {
        this.trackingDate = trackingDate;
    }

    public String getRemainingLoan() {
        return remainingLoan;
    }

    public void setRemainingLoan(String remainingLoan) {
        this.remainingLoan = remainingLoan;
    }

    public String getLoanPaid() {
        return loanPaid;
    }

    public void setLoanPaid(String loanPaid) {
        this.loanPaid = loanPaid;
    }
}

