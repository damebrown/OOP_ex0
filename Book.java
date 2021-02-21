//This class represents a book, which has a title, author,
// year of publication and different literary aspects
public class Book {
    /** The title of this book. */
    final String bookTitle;

    /** The name of the author of this book. */
    final String bookAuthor;

    /** The year this book was published. */
    final int bookYearOfPublication;

    /** The comic value of this book. */
    int bookComicValue;

    /** The dramatic value of this book. */
    int bookDramaticValue;

    /** The educational value of this book. */
    int bookEducationalValue;

    /** The id of the current borrowe of this book. */
    int borrower = -1;

    //constructor function: creates a new book with the given characteristic
    Book(String inputbookTitle,
                String inputbookAuthor,
                int inputbookYearOfPublication,
                int inputbookComicValue,
                int inputbookDramaticValue,
                int inputbookEducationalValue) {
        bookTitle = inputbookTitle;
        bookAuthor = inputbookAuthor;
        bookYearOfPublication = inputbookYearOfPublication;
        bookComicValue = inputbookComicValue;
        bookDramaticValue = inputbookDramaticValue;
        bookEducationalValue = inputbookEducationalValue;
        borrower = -1;
    }

    //getter function for the book's comic value
    int getBookComicValue(){
        return bookComicValue;
    }

    //getter function for the book's dramatic value
    int getBookDramaticValue(){
        return bookDramaticValue;
    }

    //getter function for the book's educational value
    int getBookEducationalValue(){
        return bookEducationalValue;
    }

    //getter function for the book's borrower's id- returns the borrower's id
    int getCurrentBorrowerId() {
        return borrower;
    }

    //setter function for the book's borrower's id- sets the input as the book's borrower's id
    void setBorrowerId(int borrowerId) {
        if (borrower != -1){
            borrower = borrowerId;
        }
    }

    //getter function for the literary value- returns the sum of its comic,
    //dramatic and educational value.
    int getLiteraryValue() {
        return bookComicValue + bookDramaticValue + bookEducationalValue;
    }

    //returnBook func- marks the book as returned
    void returnBook() {
        setBorrowerId(-1);
    }

    //string representation- returns the String representation of this book
    String stringRepresentation(){
        return "["+bookTitle+","+bookAuthor+","+bookYearOfPublication+","+getLiteraryValue()+"]";
    }

}
