import java.util.*;
import java.io.*;

public class Main {
    /*
    Name: Chris Wattles
    Course: CEN-3024C
    Date: 9/9/2023
    Class: Main
    Description: This class is the main class where the program runs. It sets up a menu for the user to choose what they
                 would like to do (Add, Remove, or View the book list) and then performs the core functionality of each.
                 This program is intended to create a Library Management System. This system manages the library books
                 from a text file and updates it as needed.
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Library library = new Library();

        // Ask the user for a file name
        System.out.print("Enter file name: ");
        String fileName = scanner.nextLine();

        // Construct the file path
        String filePath = System.getProperty("user.dir") + File.separator + fileName;

        // Check if the file exists, if not, create a new file
        File file = new File(filePath);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                System.out.println("Error creating the file.");
                System.exit(1);
            }
        }

        ReadFileAndAddToCollection fileReader = new ReadFileAndAddToCollection(fileName);
        WriteToTextFile fileWriter = new WriteToTextFile(fileName);

        // Reading existing books from the file and adding them to the library
        List<Book> books = fileReader.readLines(); // Change from List<String> to List<Book>
        for (Book book : books) {
            library.addBook(book);
        }

        while (true) {
            System.out.println("\nMenu:");
            System.out.println("1: Add a book");
            System.out.println("2: Remove a book by ID");
            System.out.println("3: Remove a book by Title");
            System.out.println("4: Check out a book");
            System.out.println("5: Check in a book");
            System.out.println("6: View entire collection");
            System.out.println("7: Save and exit");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline character left by nextInt()

            switch (choice) {
                case 1:
                    System.out.println("Enter book title:");
                    String title = scanner.nextLine();
                    System.out.println("Enter author:");
                    String author = scanner.nextLine();
                    Book newBook = new Book(library.getBooks().size() + 1, title, author);
                    books.add(newBook);  // Update the books list
                    library.addBook(newBook);  // Update the library object
                    System.out.println("Book added successfully: " + newBook);
                    break;
                case 2:
                    System.out.println("Enter book ID to remove:");
                    int bookId = scanner.nextInt();
                    library.removeBookById(bookId);
                    System.out.println("Book removed successfully!");
                    // Update the books list after removing the book
                    books = library.getBooks();
                    break;
                case 3:
                    System.out.println("Enter book title to remove:");
                    String titleToRemove = scanner.nextLine();
                    library.removeBookByTitle(titleToRemove);
                    System.out.println("Book removed successfully!");
                    books = library.getBooks();
                    for (Book book : books) {
                        System.out.println(book);
                    }
                    break;
                case 4:
                    System.out.println("Enter book title to check out:");
                    String titleToCheckOut = scanner.nextLine();
                    library.checkOutBook(titleToCheckOut);
                    System.out.println("Current Collection:");
                    for (Book book : books) {
                        System.out.println(book);
                    }
                    break;
                case 5:
                    System.out.println("Enter book title to check in:");
                    String titleToCheckIn = scanner.nextLine();
                    boolean checkedIn = library.checkInBook(titleToCheckIn);
                    if (!checkedIn) {
                        System.out.println("Book checked in successfully!");
                    } else {
                        System.out.println("Book is not checked out or does not exist.");
                    }
                    for (Book book : books) {
                        System.out.println(book);
                    }
                    break;
                case 6:
                    System.out.println("Printing the current collection...");
                    System.out.println("Current Collection:");
                    for (Book book : books) {
                        System.out.println(book);
                    }
                    break;
                case 7:
                    // Save books to the file before exiting
                    fileWriter.writeLines(books, filePath);
                    System.out.println("Library saved. Exiting...");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

}