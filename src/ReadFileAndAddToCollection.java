import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;
import java.io.IOException;
import java.util.*;

/*
   Name: Chris Wattles
   Course: CEN-3024C
   Date: 10/5/2023
   Class: ReadFileAndAddToCollection
   Description: The ReadFileAndAddToCollection objects can use the filePath variable with
                BufferedReader and FileReader from Java.io to read in lines from the text file which contains the collection of books.
    */

public class ReadFileAndAddToCollection {

    private String filePath;

    public ReadFileAndAddToCollection(String filePath) {
        this.filePath = filePath;
    }
    public List<Book> readLines() {
        /*
        Name: readLines();
        Description: Creates an Array List, then using the pathing to the file listed above and the BufferedReader method from Java.io
                     reads in each line as a string. If the line does not equal null, the line gets added to the Array List.
        Arguments: None
        Return value: Returns an Array List called "lines"
         */
        List<Book> books = new ArrayList<>(); // Create a new ArrayList<Book> to store books
        File file = new File(this.filePath);

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                //Splits the string by ":" which allows the program to separate the id from the rest of the string
                String[] parts = line.split(":");
                if (parts.length >= 2) {
                    try {
                        int id = Integer.parseInt(parts[0].trim());
                        String[] bookInfo = parts[1].split(","); // Split book title and book author by ","
                        if (bookInfo.length >= 2) {
                            String title = bookInfo[0].trim();
                            String author = bookInfo[1].trim();
                            Book book = new Book(id, title, author);
                            // Add the parsed book to the list
                            books.add(book);
                        } else {
                            System.out.println("Invalid format for line: " + line);
                        }
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
}