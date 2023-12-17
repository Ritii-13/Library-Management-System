package org.example;

import org.example.Book;
import org.example.Member;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.PriorityQueue;
import java.util.ArrayList;

public class Librarian{
    private ArrayList<Book> books;
    private int nextBookID = 1;
    private int nextMemberId = 1;
    private PriorityQueue<Member> members;
    private Member loggedInMember;
    private Map<Integer, Date> bookDueDates;

    public Librarian() {
        this.books = new ArrayList<>();
//        this.nextBookID = 1;
        this.members = new PriorityQueue<>(new Comparator<Member>() {
            @Override
            public int compare(Member member1, Member member2) {
                return member1.getPhoneNumber().compareTo(member2.getPhoneNumber());
            }
        });
        this.bookDueDates = new HashMap<>();
    }

    public void addBook(String title, String author, int totalCopies, int nextBookID) {
        int bookId = this.nextBookID;
        int count = bookId;
        while (count < totalCopies + bookId) {
            System.out.println(count);
            Book newBook = new Book(title, author, totalCopies, count);
            books.add(newBook);
            this.nextBookID++;
            bookId = nextBookID;
            count++;
        }
        System.out.println("Books added successfully");
    }

    public int getNextBookID() {
        return nextBookID;
    }

    public void removeBook(int bookId) {
        Book bookToRemove = null;

        for (Book book : books) {
            if (book.getBookID() == bookId) {
                bookToRemove = book;
                break;
            }
        }

        if (bookToRemove != null) {
            books.remove(bookToRemove);
            System.out.println("Book with ID " + bookId + " removed successfully.");
        } else {
            System.out.println("Book with ID " + bookId + " not found in the library.");
        }
    }

    public void registerMember(String phoneNumber, String name, int age) {
        boolean memberExists = false;

        for (Member existingMember : members) {
            if (existingMember.getPhoneNumber().equals(phoneNumber)) {
                // Member with the same phone number already exists
                System.out.println("Member with phone number " + phoneNumber + " already exists.");
                memberExists = true;
                break; // No need to continue checking
            }
        }

        if (!memberExists) {
            // Create a new member and add them to the library
            int memberId = nextMemberId;
            nextMemberId++;
            Member newMember = new Member(phoneNumber, name, age);
            members.add(newMember);
            System.out.println("Member registered successfully with phone number: " + phoneNumber + " and member ID: " + memberId);
        }
    }


    public void removeMember(String phoneNumber) {
        boolean removed = false;
        for (Member member : members) {
            if (member.getPhoneNumber().equals(phoneNumber)) {
                members.remove(member);
                removed = true;
                System.out.println("Member with phone number " + phoneNumber + " removed from the library.");
                break;
            }
        }
        if (!removed) {
            System.out.println("Member with phone number " + phoneNumber + " not found in the library.");
        }
    }

    public Member getLoggedInMember(Member member) {
        return loginMember(member.getPhoneNumber());
    }

    public Member loginMember(String phoneNumber) {
        for (Member member: members) {
            if (member.getPhoneNumber().equals(phoneNumber)) {
                return member;
            }
        }
        return null;
    }


    public void issueBook(int bookID, Member member) {
        if (getLoggedInMember(member) == null) {
            System.out.println("Please log in as a member to issue a book.");
            return;
        }

        // Check if the member owes a penalty amount
        if (getLoggedInMember(member).getBalance() > 0) {
            System.out.println("Please clear your penalty balance before issuing a new book.");
            return;
        }

        Book selectedBook = null;
        for (Book book : books) {
            if (book.getBookID() == bookID) {
                selectedBook = book;
                break;
            }
        }

        if (selectedBook == null) {
            System.out.println("Book with ID " + bookID + " not found in the library.");
            return;
        }

        if ((getLoggedInMember(member).getBorrowedBooks().size() >= 2)) {
            System.out.println("You have reached the maximum limit of borrowed books.");
            return;
        }

        if (selectedBook.getAvailableCopies() > 0) {
            selectedBook.decreaseAvailableCopies();
            (getLoggedInMember(member)).addBorrowedBook(selectedBook);

            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.SECOND, 10);
            Date dueDate = calendar.getTime();
            bookDueDates.put(bookID, dueDate);
            System.out.println("Book with ID" + bookID + " issued successfully.");
        }else {
            System.out.println("No available copies of the book with ID " + bookID + ".");
        }
    }

    public void returnBook(int bookID, Member member) {
        if (getLoggedInMember(member) == null) {
            System.out.println("Please log in as a member to return a book.");
            return;
        }

        Book returnedBook = null;
        for (Book book : getLoggedInMember(member).getBorrowedBooks()) {
            if (book.getBookID() == bookID) {
                returnedBook = book;
                break;
            }
        }

        if (returnedBook == null) {
            System.out.println("Book with ID " + bookID + " not found in your borrowed books.");
            return;
        }

        double penaltyAmount = calculateFine(member);
        getLoggedInMember(member).updateBalance(penaltyAmount);
        getLoggedInMember(member).setBalance(getLoggedInMember(member).getBalance() + penaltyAmount);
        returnedBook.increaseAvailableCopies();
        getLoggedInMember(member).removeBorrowedBook(returnedBook);

        //bookDueDates.remove(getLoggedInMember(member));

        System.out.println("Book with ID " + bookID + " returned successfully.");
        System.out.println("A penalty of " + penaltyAmount + " rupees has been charged");
    }

    public void listBooks() {
        System.out.println("--------------------------------");
        System.out.println("BOOKS AVAILABLE IN THE LIBRARY");
        System.out.println("--------------------------------");

        if (books.isEmpty()) {
            System.out.println("Sorry, no books available.");
            return;
        }

        for (Book book : books) {
            if (book.getTotalCopies() > 0) {
                System.out.println("--------------------------------");
                System.out.println("Book ID: " + book.getBookID());
                System.out.println("Title: " + book.getTitle());
                System.out.println("Author: " + book.getAuthor());
                System.out.println("--------------------------------");
            }
        }
    }

    public void getMemberBooks(Member member) {

        System.out.println("--------------------------------");
        System.out.println("BOOKS YOU HAVE BORROWED");
        System.out.println("--------------------------------");

        ArrayList<Book> memberBooks = new ArrayList<>();
        memberBooks =  member.getBorrowedBooks();

        if (memberBooks.isEmpty()) {
            System.out.println("You have not borrowed any books.");
            return;
        }

        for (Book book : memberBooks) {
            System.out.println("--------------------------------");
            System.out.println("Book ID: " + book.getBookID());
            System.out.println("Title: " + book.getTitle());
            System.out.println("Author: " + book.getAuthor());
            System.out.println("--------------------------------");
        }
    }

    public void listMembers() {
        System.out.println("--------------------------------");
        System.out.println("REGISTERED MEMBERS IN THE LIBRARY");
        System.out.println("--------------------------------");

        if (members.isEmpty()) {
            System.out.println("No members registered.");
            return;
        }

        PriorityQueue<Member> tempMembers = new PriorityQueue<>(members);
        ArrayList<Book> memberBooks = new ArrayList<>();

        while (!tempMembers.isEmpty()) {
            Member member = tempMembers.poll();
            memberBooks =  member.getBorrowedBooks();
            System.out.println("--------------------------------");
            System.out.println("Phone Number: " + member.getPhoneNumber());
            System.out.println("Name: " + member.getName());
            System.out.println("Age: " + member.getAge());
            System.out.println("Penalty Balance: " + member.getBalance() + " rupees");
            System.out.println("Borrowed books:");
            for (Book book : memberBooks) {
                System.out.println("Book ID: " + book.getBookID());
                System.out.println("Title: " + book.getTitle());
                System.out.println("Author: " + book.getAuthor());
            }
            System.out.println("--------------------------------");
        }
    }

//    public void calculateFineForBook(int bookID, Member member) {
//        // Check if a member is logged in
//        if (getLoggedInMember(member) == null) {
//            System.out.println("Please log in as a member to calculate the fine.");
//            return;
//        }
//
//        // Find the book with the specified bookID
//        Book returnedBook = null;
//        for (Book book : getLoggedInMember(member).getBorrowedBooks()) {
//            if (book.getBookID() == bookID) {
//                returnedBook = book;
//                break;
//            }
//        }
//
//        if (returnedBook == null) {
//            System.out.println("Book with ID " + bookID + " not found in your borrowed books.");
//            return;
//        }
//
//        // Calculate the fine amount using the calculateFine method
//        double fineAmount = calculateFine(loggedInMember);
//
//        System.out.println("Fine for Book with ID " + bookID + ": " + fineAmount + " rupees.");
//    }
    public double calculateFine(Member member){
        if (getLoggedInMember(member) == null) {
            System.out.println("Please log in as a member to calculate the fine.");
            return 0; // Return 0 if no member is logged in
        }

        // Check if the member has borrowed any books
        if (member.getBorrowedBooks().isEmpty()) {
            System.out.println("You don't have any borrowed books.");
            return 0; // Return 0 if the member has no borrowed books
        }

        double totalFine = 0;

        // Get the current date and time
        Date currentDate = new Date();

        // Iterate through the member's borrowed books to calculate fines
        for (Book book : member.getBorrowedBooks()) {
            Date dueDate = bookDueDates.get(book.getBookID());

            // Calculate the time difference in seconds
            long timeDifferenceInSeconds = (currentDate.getTime() - dueDate.getTime()) / 1000;

            // Calculate the fine in rupees (assuming 1 second = 1 day)
            double fineAmount = timeDifferenceInSeconds * 3;

            if (fineAmount > 0) {
                System.out.println("Fine for Book ID " + book.getBookID() + ": " + fineAmount + " rupees.");
            }

            // Add the fine for this book to the total fine
            totalFine += fineAmount;
        }

        return totalFine;
    }


    public void payFine(Member member) {
        if (getLoggedInMember(member) != null) {

            double penaltyAmount = getLoggedInMember(member).getBalance();
            if (penaltyAmount > 0) {
                getLoggedInMember(member).clearFine();
                System.out.println("Fine of " + penaltyAmount + " rupees has been successfully paid.");

                for (Book book : getLoggedInMember(member).getBorrowedBooks()) {
                    book.resetFine(); // Assuming you have a method to reset the fine for a book
                }
            } else {
                System.out.println("No fines due");
            }
        } else {
            System.out.println("No member is currently logged in.");
        }
    }
}

