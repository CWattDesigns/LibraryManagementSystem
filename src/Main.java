import java.util.*;
import java.io.*;

public class Main {
    /*
    Name: Chris Wattles
    Course: CEN-3024C
    Date: 9/9/2023
    Class: Main
    Description: This class is the main class where the program runs. It prompts the user for a file name. If the file is not found, the program creates one.
                 Users are prompted to choose from a handful of different options:
                 1: Add a book
                 2: Remove a book by ID
                 3: Remove a book by Title
                 4: Check out a book
                 5: Check in a book
                 6: View entire collection
                 7: Save and exit
                 These options allow the user to create a working Library Management System. It takes the file name and creates filepathing. Then it prompts
                 the user for the above options on a re-occurring loop to design a library system.
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Library library = new Library();

        // Ask the user for a file name
        System.out.print("Enter file name: ");
        String fileName = scanner.nextLine();

        // Constructs the file path
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

        // Sets up the read and write functions for the file
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
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.println("Enter book title:");
                    String title = scanner.nextLine();
                    System.out.println("Enter author:");
                    String author = scanner.nextLine();
                    Book newBook = new Book(library.getBooks().size() + 1, title, author); //Resizes the list
                    books.add(newBook); //Creates a new book object
                    library.addBook(newBook); //Adds the new book object to the library
                    System.out.println("Book added successfully: " + newBook);
                    break;
                case 2:
                    System.out.println("Enter book ID to remove:");
                    int bookId = scanner.nextInt();
                    library.removeBookById(bookId);
                    System.out.println("Book removed successfully!");
                    // Update the books list after removing the book
                    books = library.getBooks();
                    // Print the book updated books list
                    for (Book book : books) {
                        System.out.println(book);
                    }
                    break;
                case 3:
                    System.out.println("Enter book title to remove:");
                    String titleToRemove = scanner.nextLine();
                    library.removeBookByTitle(titleToRemove);
                    System.out.println("Book removed successfully!");
                    books = library.getBooks(); //Updates the books list
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
                    //The following logic checks if the book is checked out already and prints a message based on that
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