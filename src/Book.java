public class Book {
    private int id;
    private String title;
    private String author;
    public boolean checkedOut;

    public Book(int id, String title, String author) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.checkedOut = false;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public boolean isCheckedOut() {
        return checkedOut;
    }

    public void checkOut() {
        checkedOut = true;
    }

    public void checkIn() {
        checkedOut = false;
    }

    // toString() method for easy printing
    @Override
    public String toString() {
        return "[" + id + "] " + title + " by " + author;
    }
}
