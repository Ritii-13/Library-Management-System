# LIBRARY MANAGEMENT SYSTEM

In this project I have implemented a library management system using object oriented programming in java.
There are four classes:
    
1. Book.java
2. Member.java
3. Librarian.java
4. Main.java

## Important Assumptions:
1. In the `addBook()` function if the input is 2 copies of "Harry Potter" then both copies will be assigned unique book IDs.
2. In the `removeBook()` function since each copy has a unique ID, only 1 copy of a particular book will be removed at a time depending on the bookID entered. This book might have multiple copies with unique bookIDs but removal of book depends not on the name or author but solely the unique ID of the book.
3. In the `payFine()` function, say a member has borrowed 2 books, fine won't be displayed unless he returns atleast 1 one of them. In addition, when a member goes to payFine(), all the fine that is due is cleared and if a book is yet to be returned then its fine initialises from 0.

## Book.java 

1. **Instance Variables:**
    - `bookID`: An integer representing the unique identifier for the book.
    - `title`: A string representing the title of the book.
    - `author`: A string representing the author of the book.
    - `totalCopies`: An integer indicating the total number of copies of this book available.
    - `availableCopies`: An integer indicating the number of copies of this book currently available in the library.
    - `books`: An ArrayList of Book objects, which is currently unused in this class.

2. **Constructor:**
    - The class has a constructor that takes parameters for the book's title, author, total copies, and book ID. It initializes the instance variables with these values and also sets the available copies to be equal to the total copies. It initializes the `books` ArrayList, but it seems to be unused in this context.

3. **Getters:**
    - The class provides getter methods for retrieving the book's ID, title, author, total copies, and available copies.

4. **Methods for Managing Copies:**
    - `decreaseAvailableCopies()`: This method decreases the available copies by one if there are available copies to decrement.
    - `increaseAvailableCopies()`: This method increases the available copies by one if the total available copies haven't reached the maximum limit.

## Member.java


1. **Instance Variables:**
    - `phoneNumber`: A string representing the member's phone number.
    - `name`: A string representing the member's name.
    - `age`: An integer indicating the member's age.
    - `balance`: A double representing the member's balance (possibly fines or fees owed).
    - `borrowedBooks`: An ArrayList of Book objects representing books currently borrowed by the member.
    - `members`: A PriorityQueue of Member objects, which is currently unused in this class.

2. **Constructor:**
    - The class has a constructor that takes parameters for phone number, name, and age, initializing the instance variables accordingly. It also initializes the `borrowedBooks` ArrayList and the unused `members` PriorityQueue.

3. **Methods:**
    - `clearFine()`: Clears any fines (sets balance to 0) if the member has a positive balance.
    - Getter methods are provided for retrieving member information, such as phone number, name, age, balance, and borrowed books.
    - `setBalance(double balance)`: Sets the member's balance to a specified amount.
    - `addBorrowedBook(Book book)`: Adds a book to the list of books borrowed by the member.
    - `removeBorrowedBook(Book book)`: Removes a book from the list of books borrowed by the member.
    - `updateBalance(int amount)`: Updates the member's balance by adding a specified amount.

## Librarian.java


1. **Instance Variables:**
    - `books`: An ArrayList of `Book` objects, representing the collection of books available in the library.
    - `nextBookID`: An integer that serves as the identifier for the next book to be added to the library, ensuring each book has a unique ID.
    - `nextMemberId`: An integer that tracks the next available member ID.
    - `members`: A PriorityQueue of `Member` objects, organized by member phone numbers for efficient retrieval.
    - `loggedInMember`: A reference to the currently logged-in member, allowing for member-specific operations.
    - `bookDueDates`: A map that associates each member with their respective book due dates for fine calculation.

2. **Constructor:**
    - The class constructor initializes the `books`, `members`, and `bookDueDates` data structures. It also sets the initial values for `nextBookID` and `nextMemberId`.

3. **Book Management:**
    - `addBook()`: Allows the librarian to add books to the library, specifying details such as title, author, and total copies.
    - `removeBook()`: Enables the removal of a book from the library by its unique ID.
    - `listBooks()`: Displays a list of available books in the library.

4. **Member Management:**
    - `registerMember()`: Allows the librarian to register new library members by providing their phone number, name, and age.
    - `removeMember()`: Permits the removal of a member from the library based on their phone number.
    - `listMembers()`: Lists all registered members along with their contact information and penalty balances.

5. **Member Login:**
    - `loginMember()`: Allows a member to log in by providing their phone number and sets the `loggedInMember` reference.

6. **Book Transactions:**
    - `issueBook()`: Facilitates the issuance of books to members, taking into account factors such as availability, borrowing limits, and fines.
    - `returnBook()`: Handles book returns, calculates fines if applicable, and updates available copies.

7. **Fine Calculation:**
    - `calculateFine()`: Calculates the fine amount for a member based on due dates and return times.

8. **Additional Features:**
    - `clearFine()`: Allows members to clear their fines.
    - `getMemberBooks()`: Lists the books currently borrowed by a member.
    - `calculateFineForBook()`: Calculates the fine for a specific book for the logged-in member.
    - `payFine()`: Allows members to pay fines to clear their balances.

## Main.java


1. **Librarian and Member Interaction:**
    - The code presents users with the option to assume one of two roles: librarian or member.
    - The user can exit the system if they choose not to continue.

2. **Librarian Menu:**
    - For librarians, a comprehensive menu is available, enabling them to perform various administrative tasks within the library:
        - `Register a Member`: Librarians can register new library members by providing their name, age, and phone number.
        - `Remove a Member`: Librarians can remove a member from the library by specifying their phone number.
        - `Add a Book`: Books can be added to the library by specifying the title, author, and the total number of copies.
        - `Remove a Book`: Librarians can remove a book from the library by entering its unique book ID.
        - `View all Members`: This option displays a list of all registered members, including their contact information and any fines they need to pay.
        - `View all Books`: Librarians can view a list of all available books in the library.

3. **Member Menu:**
    - For library members, a menu provides options for managing their borrowing activities:
        - `List Available Books`: Members can see a list of books currently available for borrowing.
        - `List My Books`: This option displays the books currently borrowed by the logged-in member.
        - `Issue Book`: Members can borrow a book by specifying the book ID.
        - `Return Book`: Books can be returned by entering the book ID.
        - `Pay Fine`: While the placeholder exists, it seems that the implementation for paying fines is not yet completed.

4. **Input Handling:**
    - The code efficiently manages user input, ensuring it is validated and correctly interpreted. It handles exceptions gracefully, informing users of invalid inputs and requesting correction.

5. **Integration with Librarian and Member Classes:**
    - The code integrates with other classes like `Librarian`, `Member`, and potentially `Book` (not shown) to execute library management operations. It relies on these classes to store data and perform operations.

6. **Exit and Flow Control:**
    - The code provides an option to exit the system when users are done using it. It manages the flow of the program by looping through menus until the user chooses to exit.

In summary, this code lays the groundwork for a functional library management system. Librarians can administer the library's resources, while members can borrow and return books. 