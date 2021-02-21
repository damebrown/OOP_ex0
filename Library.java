public class Library {
    //This class represents a library, which hold a collection of books. Patrons can register at the library to be able
    // to check out books, if a copy of the requested book is available.
    final int maxBookCapacity;
    final int maxBorrowedBooks;
    final int maxPatronCapacity;
    Book[] books;
    Book[] borrowed;
    Patron[] patronMembers;


    //constructor function- creates a new library with the given characteristics
    Library(int inputmaxBookCapacity,
            int inputmaxBorrowedBooks,
            int inputmaxPatronCapacity) {
        maxBookCapacity = inputmaxBookCapacity;
        maxBorrowedBooks = inputmaxBorrowedBooks;
        maxPatronCapacity = inputmaxPatronCapacity;
        Book[] books = new Book[maxBookCapacity];
        Book[] borrowed = new Book[maxBookCapacity];
        Patron[] patronMembers = new Patron[maxPatronCapacity];
    }

    // Adds the given book to this library, if there is place
    // available, and it isn't already in the library.
    // returns the new/existing book's Id if successful, and -1 else.
    int addBookToLibrary(Book book) {
        //initialize book counting
        int numOfBooks = 0; int index = 0;
        //check if in library:
        for (int i = 0; i < maxBookCapacity; i++) {
            index = i;
            if (books[i] != null) {
                //if book exists- count the book
                numOfBooks += 1;
                //if so- return id
                if (books[i] == book) {
                    return i;
                } else if (i == maxBookCapacity - 1) {
                    return -1;
                }
                //if not- add the book
            } else {
                books[numOfBooks] = new Book(book.bookTitle,
                        book.bookAuthor,
                        book.bookYearOfPublication,
                        book.bookComicValue,
                        book.bookDramaticValue,
                        book.bookEducationalValue);
                return i;
            }
        }
    return index;}

    boolean isPatronRegistered(Patron patron) {
        for (int i = 0; i < maxPatronCapacity; i++) {
            if (patronMembers[i] == patron) {
                //if its a match- return true
                return true;
            }
        }//else- if the patron's not registered, return false
        return false;
    }

// else if ((i == maxPatronCapacity - 1) || (patronMembers[i] == null)) {

    boolean doesBookExists(Book book) {
        for (int i = 0; i < maxBookCapacity; i++) {
            if (books[i] == book) {
                //if its a match- return true
                return true;
            }
        }//if the book does not exist- return false
        return false;
    }

    // Marks the book with the given id number as borrowed by the patron with the given patron id,
    // if this book is available, the given patron isn't already borrowing the maximal number of
    // books allowed, and if the patron will enjoy this book.
    boolean borrowBook(int bookId, int patronId) {
        if ((isPatronIdValid(patronId)) && (isBookIdValid(bookId))) {
            Book checkedBook = books[bookId];
            Patron checkingPatron = patronMembers[patronId];
            int borrowedQuantity = 0;
            for (int i = 0; i < maxBookCapacity; i++) {
                //iterating over existing books
                Book curBook = books[i];
                if (curBook.borrower == patronId) {
                    //checking the patron didn't borrow the max amount
                    borrowedQuantity++;
                    if (borrowedQuantity >= maxBorrowedBooks) {
                        return false;
                    }
                }
            }
            //checking the book isn't borrowed
            if (checkedBook.getCurrentBorrowerId() != -1) {
                return false;
            }
            if (checkingPatron.willEnjoyBook(checkedBook)) {
                //checking the patron will enjoy the book
                checkedBook.borrower = patronId;
                borrowed[bookId] = checkedBook;
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    //Returns the non-negative id number of the given book if he is owned by this library, -1 otherwise.
    int getBookId(Book book) {
        if (doesBookExists(book)) {
            // if book exists- find it and return id
            for (int i = 0; i < maxBookCapacity; i++) {
                if (books[i] == book) {
                    //if its a match- return id
                    return i;
                }
            }
        }
        // if it does not exist- return -1
        return -1;
    }

    //Returns the non-negative id number of the given patron if he is registered to this library, -1 otherwise.
    int getPatronId(Patron patron) {
        if (isPatronRegistered(patron)) {
            //checks if patron is registered
            for (int i = 0; i < maxPatronCapacity; i++) {
                //if it does- it finds the patron and returns the id
                if (patronMembers[i] == patron) {
                    //if its a match- return id
                    return i;
                }
            }
        }
        //if the patron's not registered- return -1
        return -1;
    }

    //Returns true if the book with the given id is available, false otherwise.
    boolean isBookAvailable(int bookId) {
        if (isBookIdValid(bookId)) {
            if (borrowed[bookId] == books[bookId]) {
                return false;
            }
        } return true;
    }

    //Returns true if the given number is an id of some book in the library, false otherwise.
    boolean isBookIdValid(int bookId) {
        if ((bookId > this.maxBookCapacity-1) || (bookId < 0)){
            return false;
        } else if (books[bookId]== (null)){
            return false;
        } else {
            return true;
        }
    }

    //Returns true if the given number is an id of a patron in the library, false otherwise.
    boolean isPatronIdValid(int patronId) {
        if (patronId > maxPatronCapacity-1){
            return false;
        } else if (patronMembers[patronId] == null){
            return false;
        } return true;
    }

    //Registers the given Patron to this library, if there is a spot available.
    int registerPatronToLibrary(Patron patron) {
        //check if already registered:
        for (int i = 0; i < maxPatronCapacity; i++) {
            if (patronMembers[i] == patron) {
                //if patron is registered- return id
                return i;
            } else if (patronMembers[i] == null) {
                //if patron is not registered- register patron
                patronMembers[i] = patron;
                return i;
            }
        }
        // if there's no place for new registrations- return -1
        return -1;
    }

    //returns the given book
    void returnBook(int bookId) {
        if (isBookIdValid(bookId)){
            Book returnedBook = books[bookId];
            returnedBook.returnBook();
            borrowed[bookId] = null;
        }
    }

    //Suggest the patron with the given id the book he will enjoy the most,
    // out of all available books he will enjoy, if any such exist.
    Book suggestBookToPatron(int patronId) {
        if (isPatronIdValid(patronId)) {
            int curScore, maxScore = 0;
            Book bestBook = null;
            Patron askingPatron = patronMembers[patronId];
            for (int i = 0; i < maxBookCapacity; i++) {
                Book curBook = books[i];
                if (isBookAvailable(i)) {
                    if (askingPatron.willEnjoyBook(books[i])) {
                        curScore = askingPatron.getBookScore(books[i]);
                        if (curScore > maxScore) {
                            maxScore = curScore;
                            bestBook = curBook;
                        }
                    }
                }
            }
            return bestBook;
        } else {
            return null;
        }
    }
}
