import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class LibraryManagementGUI extends JFrame{
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
    private Set<String> uniqueBarcodes;
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/library_db"; //Replace with database and verify the port info.
    private static final String JDBC_USER = "user"; //Update this with your instance username
    private static final String JDBC_PASSWORD = "password"; //Update this with your instance password

    private Connection connection;

    /*
    Name: Chris Wattles
    Course: CEN-3024C
    Date: 11/12/2023
    Class: LibraryManagementGUI
    Description: This is a GUI built in Intellij which performs the operations of the Library Management System through a user-friendly interface.
                 Users can select from a number of options which include:
                 1. Adding multiple books at a time
                 2. Removing books by ID (barcode)
                 3. Removing books by Title
                 4. Checking a book out
                 5. Checking a book in
                 6. Printing the current collection of books
                 7. Exiting the GUI
     */
    public LibraryManagementGUI() {
        setContentPane(mainPanel);
        setTitle("Library Management System");
        setSize(610, 360);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);

        try {
            connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error connecting to the database: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }

        jpBookAdd.setLayout(new BorderLayout());
        textArea1.setBorder(new JTextField().getBorder());

        uniqueBarcodes = new HashSet<>();

        btnAddBooks.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String booksInput = textArea1.getText().trim();
                String[] booksArray = booksInput.split("\\n");

                for (String book : booksArray) {
                    String trimmedBook = book.trim();
                    String[] parts = trimmedBook.split(":", -1);

                    if (parts.length >= 2) {
                        String barcode = parts[0].trim();
                        if (!uniqueBarcodes.contains(barcode)) {
                            uniqueBarcodes.add(barcode);

                            // Insert the book into the database
                            String insertQuery = "INSERT INTO books (barcode, title, status) VALUES (?, ?, ?)";
                            try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
                                preparedStatement.setString(1, barcode);
                                preparedStatement.setString(2, parts[1].trim());  // Use index 1 for title
                                preparedStatement.setString(3, "available");
                                preparedStatement.executeUpdate();
                            } catch (SQLException ex) {
                                ex.printStackTrace();
                                JOptionPane.showMessageDialog(null, "Error inserting the book into the database: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "Duplicate barcode found: " + barcode, "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Invalid book entry format: " + trimmedBook, "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }

                textArea1.setText("");
            }
        });

        btnBarCodeRemove.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String barcode = txtBarcodeRemoval.getText().trim();
                // Execute SQL to delete the book by barcode
                String deleteQuery = "DELETE FROM books WHERE barcode = ?";
                try (PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {
                    preparedStatement.setString(1, barcode);
                    int rowsAffected = preparedStatement.executeUpdate();
                    if (rowsAffected > 0) {
                        JOptionPane.showMessageDialog(null, "Book with barcode " + barcode + " removed successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(null, "Book with barcode " + barcode + " not found!", "Not Found", JOptionPane.WARNING_MESSAGE);
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error deleting the book: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        btnRemoveByTitle.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String title = txtTitleRemoval.getText().trim();
                // Execute SQL to delete the book by title
                String deleteQuery = "DELETE FROM books WHERE title = ?";
                try (PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {
                    preparedStatement.setString(1, title);
                    int rowsAffected = preparedStatement.executeUpdate();
                    if (rowsAffected > 0) {
                        JOptionPane.showMessageDialog(null, "Book with title " + title + " removed successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(null, "Book with title " + title + " not found!", "Not Found", JOptionPane.WARNING_MESSAGE);
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error deleting the book: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });


        //CheckOut button allows a user to check out a book. When a book is checked out, it's status is changed from "available" to "checked out"
        btnCheckOut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String title = txtBookCheckout.getText().trim();
                // Execute SQL to update the book status to "checked out" and set the due date
                String updateQuery = "UPDATE books SET status = 'checked out', due_date = ? WHERE title = ? AND status = 'available'";
                try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
                    // Set the due date
                    preparedStatement.setDate(1, new java.sql.Date(System.currentTimeMillis() + 28L * 24 * 60 * 60 * 1000)); // 4 weeks (28 days)
                    preparedStatement.setString(2, title);
                    int rowsAffected = preparedStatement.executeUpdate();
                    if (rowsAffected > 0) {
                        JOptionPane.showMessageDialog(null, title + " checked out successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(null, title + " not found or is already checked out!", "Not Found", JOptionPane.WARNING_MESSAGE);
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error updating book status: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });


        //Checkin button allows a user to check a book back in. Doing so changes the status of the book from "checked out" to "available" and sets the due date back to "null"
        btnCheckin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String title = txtCheckin.getText().trim();
                // Execute SQL to update the book status to "available" and set the due date to null
                String updateQuery = "UPDATE books SET status = 'available', due_date = NULL WHERE title = ? AND status = 'checked out'";
                try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
                    preparedStatement.setString(1, title);
                    int rowsAffected = preparedStatement.executeUpdate();
                    if (rowsAffected > 0) {
                        JOptionPane.showMessageDialog(null, title + " checked in successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(null, title + " was not found or is not checked out!", "Not Found", JOptionPane.WARNING_MESSAGE);
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error updating book status: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });


        //Prints the current collection of books and their statuses
        btnPrint.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try (Statement statement = connection.createStatement()) {
                    ResultSet resultSet = statement.executeQuery("SELECT * FROM books");
                    StringBuilder collection = new StringBuilder();
                    while (resultSet.next()) {
                        collection.append(resultSet.getString("barcode"))
                                .append(": ")
                                .append(resultSet.getString("title"))
                                .append(", ")
                                .append(resultSet.getString("author"))
                                .append(": ")
                                .append(resultSet.getString("status"))
                                .append(", ")
                                .append(resultSet.getString("due_date"))
                                .append("\n");
                    }

                    JTextArea textArea = new JTextArea(collection.toString());
                    textArea.setEditable(false);
                    JScrollPane scrollPane = new JScrollPane(textArea);
                    JOptionPane.showMessageDialog(null, scrollPane, "Library Collection", JOptionPane.PLAIN_MESSAGE);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error querying the database: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        //Allows the user to exit the GUI
        btnExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int result = JOptionPane.showConfirmDialog(null, "Are you sure you want to exit?", "Confirm Exit", JOptionPane.YES_NO_OPTION);
                if (result == JOptionPane.YES_OPTION){
                    // Close the database connection before exiting
                    try {
                        if (connection != null) {
                            connection.close();
                        }
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                    System.exit(0);
                }
            }
        });
    }

    public static void main(String[] args){
        LibraryManagementGUI myLibraryGUI = new LibraryManagementGUI();
    }
}
