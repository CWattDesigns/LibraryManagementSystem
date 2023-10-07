import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/*
   Name: Chris Wattles
   Course: CEN-3024C
   Date: 10/5/2023
   Class: WriteToTextFile
   Description: The WriteToTextFile class takes a filePath variable and uses a list of books (with BufferedWriter and FileWriter to
                "writeLines" into the document specified on the filePath.
    */

public class WriteToTextFile {
    private String filePath;

    /*
    Name: WriteToTextFile
    Purpose: Establishes a filePath to a provided file or created file
    Arguments: filePath
    Returns: a file pathing
     */
    public WriteToTextFile(String filePath) {
        this.filePath = filePath;
    }

    /*
    Name: writeLines
    Purpose: Writes lines to the text file. These lines include a book ID, book title, book author, and book checkout status
    Arguments: List of books, a string for the filePath
    Returns: lines written into the file provided/created
     */
    public void writeLines(List<Book> books, String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (Book book : books) {
                String checkedOutStatus = book.isCheckedOut() ? "true" : "false";
                String bookInfo = book.getId() + ": " + book.getTitle() + ", " + book.getAuthor() + ", Due Back:" + book.getDueDate();;
                writer.write(bookInfo);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}