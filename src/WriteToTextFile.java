import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class WriteToTextFile {
    /*
    Name: Chris Wattles
    Course: CEN-3024C
    Date: 9/9/2023
    Class: WriteToTextFile
    Description: This class creates a WriteToTextFile class. The WriteToTextFile objects can use the filePath variable with
                 BufferedWriter and FileWriter from Java.io to write lines to the text file which contains the collection of books.
     */
    private String filePath;

    public WriteToTextFile(String filePath) {
        this.filePath = filePath;
    }

    public void writeLines(List<String> lines){
        /*
        Name: writeLines();
        Description: Uses BufferedWriter from Java.io to write a new line into the text file
        Arguments: The Array List
        Return value: Does not return anything
         */
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (String line : lines){
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
