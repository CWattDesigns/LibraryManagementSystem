/*
   Name: Chris Wattles
   Course: CEN-3024C
   Date: 10/5/2023
   Class: Book
   Description: The Book class allows for book objects to be created and added to the library. It keeps track of various
                aspects of each book object such as the ID, the title, the author, and whether or not the book is checked out.
 */

public class Book {
    private int id;
    private String title;
    private String author;
    public boolean checkedOut;

    /*
    Name: Book
    Purpose: Creates a book object
    Arguments: id, title, author
    Returns: A book object
    */
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

    //Used to verify if a book is checked out
    public boolean isCheckedOut() {
        return checkedOut;
    }

    //Changes the value of the book object's checkout parameter
    public void checkOut() {
        checkedOut = true;
    }

    //Changes the value of the book object's checkout parameter
    public void checkIn() {
        checkedOut = false;
    }

    // toString() method for easy printing
    @Override
    public String toString() {
        return "[" + id + "] " + title + " by " + author;
    }
}
