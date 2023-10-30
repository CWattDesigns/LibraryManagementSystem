import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.*;

public class LibraryManagementGUI extends JFrame{
    private JLabel lbFileName;
    private JTextField tfFileName;
    private JButton btnSubmit;
    private JPanel mainPanel;
    private JButton btnExit;
    private JLabel listOfBooksToAdd;
    private JButton btnAddBooks;
    private JTextArea textArea1;
    private JPanel jpBookAdd;
    private JButton btnPrint;
    private JLabel lbBarCodeRemove;
    private JTextField txtBarcodeRemoval;
    private JButton btnBarCodeRemove;
    private JLabel lbRemoveByTitle;
    private JTextField txtTitleRemoval;
    private JButton btnRemoveByTitle;
    private JLabel lbCheckout;
    private JTextField txtBookCheckout;
    private JButton btnCheckOut;
    private JLabel lbCheckin;
    private JTextField txtCheckin;
    private JButton btnCheckin;
    private List<String> booksToAdd;
    private File file;
    private Set<String> uniqueBarcodes;
    private List<String> validBooks;

    /*
    Name: Chris Wattles
    Course: CEN-3024C
    Date: 10/29/2023
    Class: Main
    Description: This is a GUI built in Intellij which performs the operations of the Library Management System through a user-friendly interface.
                 Users can select from a number of options which include:
                 1. Selecting which file will be used to store the books
                 2. Adding multiple books at a time
                 3. Removing books by ID (barcode)
                 4. Removing books by Title
                 5. Checking a book out
                 6. Checking a book in
                 7. Printing the current collection of books
                 8. Exiting the GUI
     */
    public LibraryManagementGUI(){
        setContentPane(mainPanel);
        setTitle("Library Management System");
        setSize(610, 360);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        file = null;

        jpBookAdd.setLayout(new BorderLayout());
        (textArea1).setBorder(new JTextField().getBorder());

        booksToAdd = new ArrayList<>();

        uniqueBarcodes = new HashSet<>();
        validBooks = new ArrayList<>();

        //Submit button checks for a valid filename using user input
        btnSubmit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (file == null){
                    String fileName = tfFileName.getText().trim();
                    file = new File(fileName);
                }

                if (file.exists()){
                    try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                        // Read the file content if needed
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(null, "Error reading the file: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "File not found!", "Error", JOptionPane.ERROR_MESSAGE);
                    file = null;
                }
            }
        });

        //AddBooks button allows users to add multiple books at a time. When pressed this button checks to make sure no duplicate IDs were entered, then writes to the file
        btnAddBooks.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String booksInput = textArea1.getText().trim();

                String[] booksArray = booksInput.split("\\n");
                validBooks.clear();

                // Add non-empty and unique books to the validBooks list
                for (String book : booksArray) {
                    String trimmedBook = book.trim();

                    // Split the book entry into barcode and book information
                    String[] parts = trimmedBook.split(":");
                    if (parts.length >= 2) {
                        String barcode = parts[0].trim();

                        // Check if the barcode is unique
                        if (!uniqueBarcodes.contains(barcode)) {
                            uniqueBarcodes.add(barcode);
                            validBooks.add(trimmedBook);
                        } else {
                            JOptionPane.showMessageDialog(null, "Duplicate barcode found: " + barcode, "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Invalid book entry format: " + trimmedBook, "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }

                textArea1.setText("");

                // Append the valid books to the file
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
                    for (String book : validBooks) {
                        writer.write(book + ": available");
                        writer.newLine();
                    }
                } catch (IOException x) {
                    JOptionPane.showMessageDialog(null, "Error writing to the file: " + x.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        btnBarCodeRemove.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String barcode = txtBarcodeRemoval.getText().trim();
                List<String> updatedBooks= new ArrayList<>();

                try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                    String line;
                    boolean bookFound = false;

                    while ((line = reader.readLine()) != null) {
                        // Split the line into barcode and book information
                        String[] parts = line.split(":");
                        if (parts.length >= 2) {
                            String bookBarcode = parts[0].trim();

                            if (bookBarcode.equals(barcode)) {
                                bookFound = true;
                                continue;
                            }
                        }

                        updatedBooks.add(line);
                    }

                    // Update the file with the books that were not removed
                    try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                        for (String book : updatedBooks) {
                            writer.write(book);
                            writer.newLine();
                        }
                    }

                    if (bookFound) {
                        JOptionPane.showMessageDialog(null, "Book with barcode " + barcode + " removed successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(null, "Book with barcode " + barcode + " not found!", "Not Found", JOptionPane.WARNING_MESSAGE);
                    }
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "Error reading/writing the file: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        //RemoveByTitle takes user input titles and removes those books if they are found in the Library Management System
        btnRemoveByTitle.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String title = txtTitleRemoval.getText().trim();
                List<String> updatedBooks= new ArrayList<>();

                try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                    String line;
                    boolean bookFound = false;

                    while ((line = reader.readLine()) != null) {
                        // Split the line by ":" and ","
                        String[] parts = line.split(":|\\,");
                        if (parts.length >= 2) {
                            String bookTitle = parts[1].trim();

                            if (bookTitle.equals(title)) {
                                bookFound = true;
                                continue;
                            }
                        }
                        updatedBooks.add(line);
                    }

                    try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                        for (String book : updatedBooks) {
                            writer.write(book);
                            writer.newLine();
                        }
                    }

                    if (bookFound) {
                        JOptionPane.showMessageDialog(null, "Book with title " + title + " removed successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(null, "Book with title " + title + " not found!", "Not Found", JOptionPane.WARNING_MESSAGE);
                    }
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "Error reading/writing the file: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        //CheckOut button allows a user to check out a book. When a book is checked out, it's status is changed from "available" to "checked out"
        btnCheckOut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String title = txtBookCheckout.getText().trim();
                boolean bookFound = false;
                List<String> updatedBooks = new ArrayList<>();

                try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        //Split the line by ":" and ","
                        String[] parts = line.split(":|\\,");
                        if (parts.length >= 4) {
                            String barcode = parts[0].trim();
                            String bookTitle = parts[1].trim();
                            String bookAuthor = parts[2].trim();
                            String bookStatus = parts[3].trim();

                            //Alters the status to checked out
                            if (bookTitle.equalsIgnoreCase(title) && bookStatus.toLowerCase().contains("available")) {
                                bookFound = true;
                                updatedBooks.add(barcode + ": " + bookTitle + ", " + bookAuthor + ": checked out");
                            } else {
                                updatedBooks.add(line);
                            }
                        }
                    }
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "Error reading the file: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (bookFound) {
                    try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                        for (String book : updatedBooks) {
                            writer.write(book);
                            writer.newLine();
                        }
                        JOptionPane.showMessageDialog(null, title + " checked out successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(null, "Error writing to the file: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, title + " not found or is already checked out!", "Not Found", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        //Checkin button allows a user to check a book back in. Doing so changes the status of the book from "checked out" to "available"
        btnCheckin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String title = txtCheckin.getText().trim();
                boolean bookFound = false;
                List<String> updatedBooks = new ArrayList<>();

                try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        //Split the line by ":" and ","
                        String[] parts = line.split(":|\\,");
                        if (parts.length >= 4) {
                            String barcode = parts[0].trim();
                            String bookTitle = parts[1].trim();
                            String bookAuthor = parts[2].trim();
                            String bookStatus = parts[3].trim();

                            //Alters the status to available
                            if (bookTitle.equalsIgnoreCase(title) && bookStatus.toLowerCase().contains("checked out")) {
                                bookFound = true;
                                updatedBooks.add(barcode + ": " + bookTitle + ", " + bookAuthor + ": available");
                            } else {
                                updatedBooks.add(line);
                            }
                        }
                    }
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "Error reading the file: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (bookFound) {
                    try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                        for (String book : updatedBooks) {
                            writer.write(book);
                            writer.newLine();
                        }
                        JOptionPane.showMessageDialog(null, title + " checked in successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(null, "Error writing to the file: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, title + " was not found or is not checked out!", "Not Found", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        //Prints the current collection of books and their statuses
        btnPrint.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                    StringBuilder collection = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        collection.append(line).append("\n");
                    }

                    JTextArea textArea = new JTextArea(collection.toString());
                    textArea.setEditable(false);
                    JScrollPane scrollPane = new JScrollPane(textArea);
                    JOptionPane.showMessageDialog(null, scrollPane, "Library Collection", JOptionPane.PLAIN_MESSAGE);
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "Error reading the file: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        //Allows the user to exit the GUI
        btnExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int result = JOptionPane.showConfirmDialog(null, "Are you sure you want to exit?", "Confirm Exit", JOptionPane.YES_NO_OPTION);
                if (result == JOptionPane.YES_OPTION){
                    System.exit(0);
                }
            }
        });
    }

    //Main function to run the LibraryManagementGUI
    public static void main(String[] args){
        LibraryManagementGUI myLibraryGUI = new LibraryManagementGUI();
    }
}
