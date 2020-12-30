package dtos;

import java.util.Date;
import java.util.Objects;

public class Opinion {
    //Variables which model book table
    private int id;
    private int userId;
    private int bookId;
    private Date date;
    private int rating;
    private String comment;

    public Opinion(int id, int userId, int bookId, Date date, int rating, String comment) {
        this.id = id;
        this.userId = userId;
        this.bookId = bookId;
        this.date = date;
        this.rating = rating;
        this.comment = comment;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "Opinion{" +
                "id=" + id +
                ", userId=" + userId +
                ", bookId=" + bookId +
                ", date=" + date +
                ", rating=" + rating +
                ", comment='" + comment + '\'' +
                '}';
    }
}
