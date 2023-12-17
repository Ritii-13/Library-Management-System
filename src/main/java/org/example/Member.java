package org.example;

import org.example.Book;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

public class Member{
    private String phoneNumber;
    private String name;
    private int age;
    private double balance;
    private ArrayList<Book> borrowedBooks;
    private PriorityQueue<Member> members;

    public Member(String phoneNumber, String name, int age) {
        this.phoneNumber = phoneNumber;
        this.name = name;
        this.age = age;
        this.balance = 0;
        this.borrowedBooks = new ArrayList<>();
        this.members = new PriorityQueue<>();
    }

    //getters
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public ArrayList<Book> getBorrowedBooks() {
        return borrowedBooks;
    }

    public void addBorrowedBook(Book book) {
        borrowedBooks.add(book);
    }

    public void removeBorrowedBook(Book book) {
        borrowedBooks.remove(book);
    }

    public void updateBalance(double amount) {
        balance += amount;
    }

    public void clearFine() {
        balance = 0.0; // Set the balance to zero to clear the fine
    }
}

