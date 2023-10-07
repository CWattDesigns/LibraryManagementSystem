import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class LibraryTest {
    private Library library;

    @Before
    public void setUp() {
        library = new Library();
    }

    @Test
    public void testAddBook() {
        System.out.println("Running testAddBook...");
        Book book = new Book(1, "Sample Book", "Sample Author");
        library.addBook(book);
        assertTrue(library.getBooks().contains(book));
        System.out.println("testAddBook passed!\n");
    }

    @Test
    public void testRemoveBookById() {
        System.out.println("Running testRemoveBookByID...");
        Book book = new Book(1, "Sample Book", "Sample Author");
        library.addBook(book);
        library.removeBookById(1);
        assertFalse(library.getBooks().contains(book));
        System.out.println("testRemoveBookByID passed!\n");
    }

    @Test
    public void testRemoveBookByTitle() {
        System.out.println("Running testRemoveBookByTitle...");
        Book book = new Book(1, "Sample Book", "Sample Author");
        library.addBook(book);
        library.removeBookByTitle("Sample Book");
        assertFalse(library.getBooks().contains(book));
        System.out.println("testRemoveBookByTitle passed!\n");
    }

    @Test
    public void testCheckOutBook() {
        System.out.println("Running testCheckOutBook...");
        Book book = new Book(1, "Sample Book", "Sample Author");
        library.addBook(book);
        assertFalse(book.isCheckedOut());
        library.checkOutBook("Sample Book");
        assertTrue(book.isCheckedOut());
        System.out.println("testCheckOutBook passed!\n");
    }

    @Test
    public void testCheckInBook() {
        System.out.println("Running testCheckInBook...");
        Book book = new Book(1, "Sample Book", "Sample Author");
        library.addBook(book);
        library.checkOutBook("Sample Book");
        assertTrue(book.isCheckedOut());
        assertTrue(library.checkInBook("Sample Book"));
        assertFalse(book.isCheckedOut());
        System.out.println("testCheckInBook passed!\n");
    }
}