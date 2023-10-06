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
    private List<Book> books;

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
    public void addBook(Book book) {
        books.add(book);
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
            if (book.getTitle().equalsIgnoreCase(title) && !book.isCheckedOut()) {
                book.checkOut();
                System.out.println("Book checked out successfully!");
                return;
            }
        }
        System.out.println("Book not available for checkout or does not exist.");
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
                return true;
            }
        }
        return false;
    }
    public List<Book> getBooks() {
        return books;
    }
}


