package org.example;

import org.example.Book;
import org.example.Member;
import org.example.Librarian;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static boolean containsOnlyAlphabets(String input) {
        // Define a regular expression pattern to match only alphabets
        Pattern pattern = Pattern.compile("^[a-zA-Z ]+$");

        // Create a Matcher to match the input against the pattern
        Matcher matcher = pattern.matcher(input);

        // Check if the input contains only alphabets
        return matcher.matches();
    }

    public static boolean containsExactly10Digits(String input) {
        // Define a regular expression pattern to match exactly 10 digits
        Pattern pattern = Pattern.compile("^\\d{10}$");

        // Create a Matcher to match the input against the pattern
        Matcher matcher = pattern.matcher(input);

        // Check if the input contains exactly 10 digits
        return matcher.matches();
    }

    public static void main(String[] args) throws IOException {
        Librarian librarian = new Librarian();
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        Member loggedInMember = null;
        boolean isRunning = true;
        while (isRunning) {
            System.out.println("Initializing Library Portal...");
            System.out.println("WELCOME!");
            System.out.println("--------------------------------");
            System.out.println("1. Enter as a librarian");
            System.out.println("2. Enter as a member");
            System.out.println("3. Exit");
            System.out.println("--------------------------------");
            System.out.print("Enter your choice: ");
            try {
                int choice = Integer.parseInt(reader.readLine());

                switch (choice) {
                    case 1:
                        //librarian
                        librarianMenu(librarian, reader);
                        break;
                    case 2:
                        //member
                        System.out.print("Enter member name: ");
                        String memberName = reader.readLine();
                        System.out.print("Enter member phone number: ");
                        String memberPhoneNumber = reader.readLine();

                        loggedInMember = librarian.loginMember(memberPhoneNumber);
                        if (loggedInMember != null) {
                            System.out.println("Welcome " + memberName + "!");
                            memberMenu(librarian, loggedInMember, reader);
                        }else {
                            System.out.println("Member not found. Please register or check your phone number.");
                        }
                        break;
                    case 3:
                        //exit
                        isRunning = false;
                        break;
                    default:
                        System.out.println("Invalid choice, Please try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input, Please enter a valid number.");
            }
        }
        reader.close();
        System.out.println("Thanks for visiting!");
    }

    private static void librarianMenu(Librarian librarian, BufferedReader reader) throws IOException {
        boolean isLibrarianMenuRunning = true;
        while (isLibrarianMenuRunning) {
            // Display librarian menu options
            System.out.println("--------------------------------");
            System.out.println("LIBRARIAN MENU");
            System.out.println("--------------------------------");
            System.out.println("1. Register a member");
            System.out.println("2. Remove a member");
            System.out.println("3. Add a book");
            System.out.println("4. Remove a book");
            System.out.println("5. View all members along with their books and fines to be paid");
            System.out.println("6. View all books");
            System.out.println("7. Back");
            System.out.println("--------------------------------");
            System.out.print("Enter your choice: ");

            try {
                int librarianChoice = Integer.parseInt(reader.readLine());

                switch (librarianChoice) {
                    case 1:
                        // Register a member
                        System.out.print("Enter member name: ");
                        String memberName = reader.readLine();
                        if (!containsOnlyAlphabets(memberName)) {
                            System.out.println("Enter a valid name");
                            break;
                        }
                        System.out.print("Enter member age: ");
                        int memberAge = Integer.parseInt(reader.readLine());
                        System.out.print("Enter member phone number: ");
                        String memberPhoneNumber = reader.readLine();
                        if (!containsExactly10Digits(memberPhoneNumber)) {
                            System.out.println("Enter a valid phone number");
                            break;
                        }

                        librarian.registerMember(memberPhoneNumber, memberName, memberAge);
                        break;
                    case 2:
                        // Remove a member
                        System.out.print("Enter member phone number you want to remove: ");
                        String phoneNumberToRemove = reader.readLine();
                        librarian.removeMember(phoneNumberToRemove);
                        break;
                    case 3:
                        // Add a book
                        System.out.print("Enter book title: ");
                        String bookTitle = reader.readLine();
                        System.out.print("Enter book author: ");
                        String bookAuthor = reader.readLine();
                        System.out.print("Enter total copies of the book: ");
                        int totalCopies = Integer.parseInt(reader.readLine());

                        librarian.addBook(bookTitle, bookAuthor, totalCopies, librarian.getNextBookID());
                        break;
                    case 4:
                        // Remove a book
                        librarian.listBooks();
                        System.out.print("Enter book ID you wish to remove: ");
                        int bookIdToRemove = Integer.parseInt(reader.readLine());
                        librarian.removeBook(bookIdToRemove);
                        break;
                    case 5:
                        // View all members along with their books and fines
                        librarian.listMembers();
                        break;
                    case 6:
                        // View all books
                        librarian.listBooks();
                        break;
                    case 7:
                        // Back to the main menu
                        isLibrarianMenuRunning = false;
                        break;
                    default:
                        System.out.println("Invalid choice, Please try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input, Please enter a valid number.");
            }
        }
    }

    private static void memberMenu (Librarian librarian, Member member, BufferedReader reader) throws IOException {
        boolean isMemberMenuRunning = true;
        while (isMemberMenuRunning) {
            // Display member menu options
            System.out.println("--------------------------------");
            System.out.println("MEMBER MENU");
            System.out.println("--------------------------------");
            System.out.println("1. List Available Books");
            System.out.println("2. List My Books");
            System.out.println("3. Issue Book");
            System.out.println("4. Return Book");
            System.out.println("5. Pay Fine");
            System.out.println("6. Back");
            System.out.println("--------------------------------");
            System.out.print("Enter your choice: ");

            try {
                int memberChoice = Integer.parseInt(reader.readLine());

                switch (memberChoice) {
                    case 1:
                        // List Available Books
                        librarian.listBooks();
                        break;
                    case 2:
                        // List My Books
                        librarian.getMemberBooks(member);
                        break;
                    case 3:
                        // Issue Book
                        librarian.listBooks();
                        System.out.print("Enter book ID to issue: ");
                        int issueBookId = Integer.parseInt(reader.readLine());
                        librarian.issueBook(issueBookId, member);
                        break;
                    case 4:
                        // Return Book
                        librarian.getMemberBooks(member);
                        System.out.print("Enter book ID to return: ");
                        int returnBookId = Integer.parseInt(reader.readLine());
                        librarian.returnBook(returnBookId, member);
                        break;
                    case 5:
                        // Pay Fine
                        librarian.payFine(member);
                        break;
                    case 6:
                        // Back to the main menu
                        isMemberMenuRunning = false;
                        break;
                    default:
                        System.out.println("Invalid choice, Please try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input, Please enter a valid number.");
            }
        }
    }
}