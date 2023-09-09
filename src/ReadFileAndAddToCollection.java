import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;
import java.io.IOException;
import java.util.*;

public class ReadFileAndAddToCollection {
    /*
   Name: Chris Wattles
   Course: CEN-3024C
   Date: 9/9/2023
   Class: ReadFileAndAddToCollection
   Description: This class creates a ReadFileAndAddToCollection class. The ReadFileAndAddToCollection objects can use the filePath variable with
                BufferedReader and FileReader from Java.io to read in lines from the text file which contains the collection of books.
    */
    private String filePath;

    public ReadFileAndAddToCollection(String filePath) {
        this.filePath = filePath;
    }
        public List<String> readLines(){
        /*
        Name: readLines();
        Description: Creates an Array List, then using the pathing to the file listed above and the BufferedReader method from Java.io
                     reads in each line as a string. If the line does not equal null, the line gets added to the Array List.
        Arguments: None
        Return value: Returns an Array List called "lines"
         */
        List<String> lines = new ArrayList<>();
        File file = new File("C:\\Users\\Mystery\\IdeaProjects\\Library Management System\\Books.txt");

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String s;
            while ((s = reader.readLine()) != null) {
                lines.add(s);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return lines;
    }
}

