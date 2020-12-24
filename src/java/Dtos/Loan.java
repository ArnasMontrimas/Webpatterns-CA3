
package Dtos;

import java.util.Date;
import java.util.Objects;

public class Loan {
    
    private int loanID;
    private User loanUserID;
    private Book loanBookID;
    private Date loanStarted;
    private Date loanEnds;
    private Date loanReturned;
    private double fees;

    public Loan(int loanID, User loanUserID, Book loanBookID, Date loanStarted, Date loanEnds, Date loanReturned, double fees) {
        this.loanID = loanID;
        this.loanUserID = loanUserID;
        this.loanBookID = loanBookID;
        this.loanStarted = loanStarted;
        this.loanEnds = loanEnds;
        this.loanReturned = loanReturned;
        this.fees = fees;
    }

    public Loan(User loanUserID, Book loanBookID, Date loanStarted, Date loanEnds, Date loanReturned, double fees) {
        this.loanUserID = loanUserID;
        this.loanBookID = loanBookID;
        this.loanStarted = loanStarted;
        this.loanEnds = loanEnds;
        this.loanReturned = loanReturned;
        this.fees = fees;
    }

    public int getLoanID() {
        return loanID;
    }

    public void setLoanID(int loanID) {
        this.loanID = loanID;
    }

    public User getLoanUserID() {
        return loanUserID;
    }

    public void setLoanUserID(User loanUserID) {
        this.loanUserID = loanUserID;
    }

    public Book getLoanBookID() {
        return loanBookID;
    }

    public void setLoanBookID(Book loanBookID) {
        this.loanBookID = loanBookID;
    }

    public Date getLoanStarted() {
        return loanStarted;
    }

    public void setLoanStarted(Date loanStarted) {
        this.loanStarted = loanStarted;
    }

    public Date getLoanEnds() {
        return loanEnds;
    }

    public void setLoanEnds(Date loanEnds) {
        this.loanEnds = loanEnds;
    }

    public Date getLoanReturned() {
        return loanReturned;
    }

    public void setLoanReturned(Date loanReturned) {
        this.loanReturned = loanReturned;
    }

    public double getFees() {
        return fees;
    }

    public void setFees(double fees) {
        this.fees = fees;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Loan loan = (Loan) o;
        return loanID == loan.loanID;
    }

    @Override
    public int hashCode() {
        return Objects.hash(loanID);
    }

    @Override
    public String toString() {
        return "Loan{" +
                "loanID=" + loanID +
                ", loanUserID=" + loanUserID +
                ", loanBookID=" + loanBookID +
                ", loanStarted=" + loanStarted +
                ", loanEnds=" + loanEnds +
                ", loanReturned=" + loanReturned +
                ", fees=" + fees +
                '}';
    } 
}
