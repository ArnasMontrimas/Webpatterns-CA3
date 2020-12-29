
package Dtos;

import java.time.Duration;
import java.util.Date;
import java.util.Objects;

public class Loan {
    // Fees cost for each day late after return date
    private static double feesPerLateDay = 5.5;
    
    private int loanID;
    private int loanUserID;
    private int loanBook;
    private Date loanStarted;
    private Date loanEnds;
    private Date loanReturned;
    private double feesPaid;

    public Loan(int loanID, int loanUserID, int loanBook, Date loanStarted, Date loanEnds, Date loanReturned, double feesPaid) {
        this.loanID = loanID;
        this.loanUserID = loanUserID;
        this.loanBook = loanBook;
        this.loanStarted = loanStarted;
        this.loanEnds = loanEnds;
        this.loanReturned = loanReturned;
        this.feesPaid = feesPaid;
    }

    public Loan(int loanUserID, int loanBook, Date loanStarted, Date loanEnds, Date loanReturned, double feesPaid) {
        this.loanUserID = loanUserID;
        this.loanBook = loanBook;
        this.loanStarted = loanStarted;
        this.loanEnds = loanEnds;
        this.loanReturned = loanReturned;
        this.feesPaid = feesPaid;
    }

    public int getLoanID() {
        return loanID;
    }

    public void setLoanID(int loanID) {
        this.loanID = loanID;
    }

    public int getLoanUserID() {
        return loanUserID;
    }

    public void setLoanUserID(int loanUserID) {
        this.loanUserID = loanUserID;
    }

    public int getLoanBook() {
        return loanBook;
    }

    public void setLoanBook(int loanBook) {
        this.loanBook = loanBook;
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

    public double getFeesPaid() {
        return feesPaid;
    }

    public void setFeesPaid(double feesPaid) {
        this.feesPaid = feesPaid;
    }

    /**
     * Calculate fees that have to be paid at the moment of the call
     * @return fees to be paid if returned now, compared to when it should have been returned
     */
    public double calculateFees() {
      int daysLate = (int) (((new Date()).getTime() - this.loanEnds.getTime()) / (1000 * 60 * 60 * 24));
      return (new Date()).compareTo(this.loanEnds) > 0
        // Fees rounded to 2 decimals
        ? Math.floor((daysLate * feesPerLateDay) * 100)  / 100
        : 0;
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
                ", loanBookID=" + loanBook +
                ", loanStarted=" + loanStarted +
                ", loanEnds=" + loanEnds +
                ", loanReturned=" + loanReturned +
                ", feesPaid=" + feesPaid +
                '}';
    } 
}
