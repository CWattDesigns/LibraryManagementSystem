import java.util.*;

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
        //Sets the file pathing for the text file
        String filePath = "C:\\Users\\Mystery\\IdeaProjects\\Library Management System\\Books.txt";
        ReadFileAndAddToCollection fileReader = new ReadFileAndAddToCollection(filePath);
        WriteToTextFile fileWriter = new WriteToTextFile(filePath);

        List<String> s = fileReader.readLines();
        /*
        Name: readLines();
        Description: Creates an Array List, then using the pathing to the file listed above and the BufferedReader and FileReader methods
        from Java.io to read in each line as a string. If the line does not equal null, the line gets added to the Array List.
        Arguments: None
        Return value: Returns an Array List called "lines"
         */

        //Next section prompts the user for input and provides functionality to the choices listed below
        Scanner userInput = new Scanner(System.in);
        System.out.println("Hello, please enter the number of your selection -->\n 1: Add a book\n 2: Remove a book by ID\n 3: View entire collection");
        Integer choice = Integer.valueOf(userInput.nextLine());
        if (choice == 1){
            System.out.println("What would you like to add?");
            Scanner bookAdd = new Scanner(System.in);
            String bookToBeAdded = bookAdd.nextLine();
            s.add(bookToBeAdded);
        } else if (choice == 2) {
            System.out.println("What is the ID of the book you would like to remove?");
            Scanner removeBook = new Scanner(System.in);
            String bookID = removeBook.nextLine();
            for (int i = 0; i < s.size(); i++){
                String line = s.get(i);
                int lineIndex = line.indexOf(".");
                if (lineIndex != -1){
                    String id = line.substring(0, lineIndex);
                    if (bookID.equals(id)){
                        s.remove(i);
                        break;
                    }
                }
            }
        } else if (choice == 3) {
            for(String line : s) {
                System.out.println(line);
            }
        } else {
            System.out.println("Looks like it's the end of the road, Batman!");
        }

        fileWriter.writeLines(s);
        /*
        Name: writeLines();
        Description: Uses BufferedWriter and FileWriter from Java.io to write a new line into the text file
        Arguments: The Array List
        Return value: Does not return anything
         */
    }
}