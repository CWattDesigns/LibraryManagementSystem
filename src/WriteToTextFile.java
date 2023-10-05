import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class WriteToTextFile {
    private String filePath;

    public WriteToTextFile(String filePath) {
        this.filePath = filePath;
    }

    public void writeLines(List<Book> books, String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (Book book : books) {
                String checkedOutStatus = book.isCheckedOut() ? "true" : "false";
                String bookInfo = book.getId() + ": " + book.getTitle() + ", " + book.getAuthor() + ", " + book.isCheckedOut();
                writer.write(bookInfo);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

