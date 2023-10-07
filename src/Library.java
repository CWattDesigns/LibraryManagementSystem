import java.util.ArrayList;
import java.util.List;

/*
   Name: Chris Wattles
   Course: CEN-3024C
   Date: 10/5/2023
   Class: Library
   Description: The Library class creates and stores a virtual library... that is to say that it creates an ArrayList of book objects and
                allows for functions to be performed on each book in the library. These functions include:
                1. Adding a book
                2. Removing a book by ID
                3. Removing a book by title
                4. Checking out a book by title
                5. Checking in a book by title
    */
public class Library {
    private static List<Book> books;

    /*
    Name: Library
    Purpose: Creates an ArrayList of Book objects, effectively creating a digital library
    Arguments: books
    Returns: An ArrayList
     */
    public Library() {
        this.books = new ArrayList<>();
    }

    //Used to add book objects
    public static short addBook(Book book) {
        books.add(book);
        return 0;
    }

    //Removes books by specified id
    public void removeBookById(int id) {
        books.removeIf(book -> book.getId() == id);
    }

    //Removes books by title
    public void removeBookByTitle(String title) {
        books.removeIf(book -> book.getTitle().equalsIgnoreCase(title));
    }

    /*
    Name: checkOutBook
    Purpose: Checks if the book entered is checked out... if it isn't, then it checks out the book
    Arguments: title
    Returns: book checkout status
     */
    public void checkOutBook(String title) {
        for (Book book : books) {
            if (book.getTitle().equalsIgnoreCase(title)) {
                if (book.isCheckedOut()) {
                    System.out.println("Book is already checked out and not available for checkout.");
                    return; // Return immediately if the book is already checked out
                }

                book.checkOut();
                System.out.println("Book checked out successfully! Due Date: " + book.getDueDate());
                return;
            }
        }

        System.out.println("Book not found in the library.");
    }

    /*
    Name: checkInBook
    Purpose: Checks if the book entered is checked out... if it is, then it checks in the book
    Arguments: title
    Returns: True or false based on book checkin status
     */
    public boolean checkInBook(String title) {
        for (Book book : books) {
            if (book.getTitle().equalsIgnoreCase(title) && book.isCheckedOut()) {
                book.checkIn();
                System.out.println("Book checked in successfully! Due Date: " + book.getDueDate());
                return true;
            }
        }
        return false;
    }
    public List<Book> getBooks() {
        return books;
    }
}
