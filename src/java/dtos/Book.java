package dtos;

import java.util.Objects;

public class Book {
    //Variables which model book table
    private int bookID;
    private String imagePath;
    private String bookName;
    private String bookISBN;
    private String bookDescription;
    private String author;
    private String publisher;
    private int quantityInStock;
    private String genre;


    public Book(int bookID, String imagePath, String bookName, String bookISBN, String bookDescription, String author, String publisher, int quantityInStock, String genre) {
        this.bookID = bookID;
        this.imagePath = imagePath;
        this.bookName = bookName;
        this.bookISBN = bookISBN;
        this.bookDescription = bookDescription;
        this.author = author;
        this.publisher = publisher;
        this.quantityInStock = quantityInStock;
        this.genre = genre;
    }


    public int getBookID() {
        return bookID;
    }

    public void setBookID(int bookID) {
        this.bookID = bookID;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getBookISBN() {
        return bookISBN;
    }

    public void setBookISBN(String bookISBN) {
        this.bookISBN = bookISBN;
    }

    public String getBookDescription() {
        return bookDescription;
    }

    public void setBookDescription(String bookDescription) {
        this.bookDescription = bookDescription;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public int getQuantityInStock() {
        return quantityInStock;
    }

    public void setQuantityInStock(int quantityInStock) {
        this.quantityInStock = quantityInStock;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return bookISBN.equals(book.bookISBN);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bookISBN);
    }

    @Override
    public String toString() {
        return "Book{" +
                "bookID=" + bookID +
                ", imagePath='" + imagePath + '\'' +
                ", bookName='" + bookName + '\'' +
                ", bookISBN='" + bookISBN + '\'' +
                ", bookDescription='" + bookDescription + '\'' +
                ", author='" + author + '\'' +
                ", publisher='" + publisher + '\'' +
                ", quantityInStock=" + quantityInStock +
                ", genre='" + genre + '\'' +
                '}';
    }
}
