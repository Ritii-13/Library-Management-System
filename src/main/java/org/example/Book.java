package org.example;

import java.util.ArrayList;
public class Book {
    private int bookID;
    private double fine;
    private String title;
    private String author;
    private int totalCopies;
    private int availableCopies;

    private ArrayList<Book> books;

    public Book(String title, String author, int totalCopies, int bookID) {
        this.bookID = bookID;
        this.title = title;
        this.author = author;
        this.totalCopies = totalCopies;
        this.availableCopies = 1;
        this.books = new ArrayList<>();
    }

    //getters

    public int getBookID() {
        return bookID;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public int getTotalCopies() {
        return totalCopies;
    }

    public int getAvailableCopies() {
        return availableCopies;
    }

    public void decreaseAvailableCopies() {
        if (availableCopies > 0) {
            availableCopies --;
        }
    }

    public void increaseAvailableCopies() {
        if (availableCopies < totalCopies) {
            availableCopies ++;
        }
    }

    public void resetFine() {
        this.fine = 0.0;
    }
}

