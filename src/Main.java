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

        ReadFileAndAddToCollection fileReader = new ReadFileAndAddToCollection(fileName);
        WriteToTextFile fileWriter = new WriteToTextFile(fileName);

        // Reading existing books from the file and adding them to the library
        List<String> lines = fileReader.readLines();
        for (String line : lines) {
            String[] parts = line.split(",");
            int id = Integer.parseInt(parts[0]);
            String title = parts[1];
            String author = parts[2];
            Book book = new Book(id, title, author);
            library.addBook(book);
        }

        while (true) {
            System.out.println("\nMenu:");
            System.out.println("1: Add a book");
            System.out.println("2: Remove a book by ID");
            System.out.println("3: View entire collection");
            System.out.println("4: Save and exit");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline character left by nextInt()

            switch (choice) {
                case 1:
                    System.out.println("Enter book title:");
                    String title = scanner.nextLine();
                    System.out.println("Enter author:");
                    String author = scanner.nextLine();
                    Book newBook = new Book(library.getBooks().size() + 1, title, author);
                    library.addBook(newBook);
                    System.out.println("Book added successfully!");
                    break;
                case 2:
                    System.out.println("Enter book ID to remove:");
                    int bookId = scanner.nextInt();
                    library.removeBookById(bookId);
                    System.out.println("Book removed successfully!");
                    break;
                case 3:
                    System.out.println("Current Collection:");
                    List<Book> books = library.getBooks();
                    for (Book book : books) {
                        System.out.println(book);
                    }
                    break;
                case 4:
                    // Save books to the file before exiting
                    fileWriter.writeLines(library.getBooks(), filePath);
                    System.out.println("Library saved. Exiting...");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    public List<Book> readLines() {
        List<Book> books = new ArrayList<>();
        File file = new File("Books.txt");

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(","); // Split by comma
                if (parts.length >= 3) {
                    try {
                        int id = Integer.parseInt(parts[0]);
                        String title = parts[1];
                        String author = parts[2];
                        Book book = new Book(id, title, author);
                        books.add(book);
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid format for line: " + line);
                    }
                } else {
                    System.out.println("Invalid format for line: " + line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return books;
    }

    public void writeLines(List<Book> books, String fileName) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (Book book : books) {
                writer.write(book.getId() + " " + book.getTitle());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}