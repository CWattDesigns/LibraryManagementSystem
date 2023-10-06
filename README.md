# LibraryManagementSystem

This project creates a library management system.

Users are first prompted to add a file. In the event that the file does not exist or cannot be found, the program will create one for the user. From there the program generates a filepath to that file.
Once the filing has been dealt with, the user is then prompted with a menu of options...
    1. Add a book
    2. Remove a book by ID (barcode)
    3. Remove a book by title
    4. Checkout a book
    5. Checkin a book
    6. Print the current book collection to the screen
    7. Save and Exit (which writes updates to the file)

There are several classes here which work together to get the Library System functioning. These classes include:
Main -- where the core functionality of this library system lives
Book -- where book objects are created
Library -- where book objects are added to an ArrayList
WriteToTextFile -- where the write functionality is contained
ReadAndAddToCollection -- where the file read functionality is contained
