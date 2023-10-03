import java.util.ArrayList;
import java.util.List;

public class Library {
    private List<Book> books;

    public Library() {
        this.books = new ArrayList<>();
    }

    public void addBook(Book book) {
        books.add(book);
    }

    public void removeBookById(int id) {
        books.removeIf(book -> book.getId() == id);
    }

    public List<Book> getBooks() {
        return books;
    }
}


