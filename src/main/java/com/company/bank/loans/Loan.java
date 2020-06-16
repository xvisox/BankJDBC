package com.company.bank.loans;

public class Loan {
    private String name;
    private String surname;
    private long pesel;
    private int amount;
    private boolean isAccepted;
    private boolean isPaid;

    public Loan() {
    }

    public Loan(String name, String surname, long pesel, int amount, boolean isAccepted, boolean isPaid) {
        this.name = name;
        this.surname = surname;
        this.pesel = pesel;
        this.amount = amount;
        this.isAccepted = isAccepted;
        this.isPaid = isPaid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public long getPesel() {
        return pesel;
    }

    public void setPesel(long pesel) {
        this.pesel = pesel;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public boolean isAccepted() {
        return isAccepted;
    }

    public void setAccepted(boolean accepted) {
        isAccepted = accepted;
    }

    public boolean isPaid() {
        return isPaid;
    }

    public void setPaid(boolean paid) {
        isPaid = paid;
    }

    @Override
    public String toString() {
        return name + "," + surname + "," + pesel + "," + amount + "," + isAccepted + "," + isPaid;
    }
}
