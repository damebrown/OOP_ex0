//This class represents a library, which hold a collection of books. Patrons can register at the library to be able
// to check out books, if a copy of the requested book is available.
class Library {
    final int maxBookCapacity;
    final int maxBorrowedBooks;
    final int maxPatronCapacity;
    Book books[];
    Book borrowed[];
    Patron patronMembers[];

    /**---constructor function**/

    //constructor function- creates a new library with the given characteristics
    Library(int inputmaxBookCapacity,
            int inputmaxBorrowedBooks,
            int inputmaxPatronCapacity) {
        maxBookCapacity = inputmaxBookCapacity;
        maxBorrowedBooks = inputmaxBorrowedBooks;
        maxPatronCapacity = inputmaxPatronCapacity;
        books = new Book[maxBookCapacity];
        borrowed = new Book[maxBookCapacity];
        patronMembers = new Patron[maxPatronCapacity];
    }

    /**----=  Instance Methods  =-----**/

    // Adds the given book to this library, if there is place
    // available, and it isn't already in the library.
    // returns the new/existing book's Id if successful, and -1 else.
    int addBookToLibrary(Book book) {
        //initialize return value-
        int index = 0;
        //check if the book is already in library:
        //initializing iteration over array of books-
        for (int i = 0; i < maxBookCapacity; i++) {
            index = i;
            if (books[i] != null) {
                //if book exists in library return id
                if (books[i] == book) {
                    return i;
                    //if the library is full and the book's not in if- return -1
                } else if (i == maxBookCapacity - 1) {
                    return -1;
                }
                //if the library is vacant and the book's not in it- add the book
            } else {
                books[i] = book;
                return i;
            }
        } return index;
    }

    //an aid function for the getPatronId function. checks if the patron is registered
    boolean isPatronRegistered(Patron patron) {
        //checking if patron is registered-
        //iterating over list of registered patrons
        for (int i = 0; i < maxPatronCapacity; i++) {
            if (patronMembers[i] == patron) {
                //if its a match- return true
                return true;
            }
        //else- if the patron's not registered, return false
        } return false;
    }

    //and aid function for the getBookId function: checks if the book exists in the library
    boolean doesBookExists(Book book) {
        //iterating over the array of existing books
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
        //checks the bookId and patronId are valid-
        if ((isPatronIdValid(patronId)) && (isBookIdValid(bookId))) {
            //assigning the patron and book to known variables
            Book checkedBook = books[bookId];
            Patron checkingPatron = patronMembers[patronId];
            //assigning a new variable that counts the number of borrowed books by the asking patron
            int borrowedQuantity = 0;
            //iterating over existing books
            for (int i = 0; i < maxBookCapacity; i++) {
                Book curBook = books[i];
                if (curBook.borrower == patronId) {
                    //checking the patron didn't borrow the max amount
                    borrowedQuantity++;
                    //checks if the patron has borrowed the max amount allowed
                    if (borrowedQuantity >= maxBorrowedBooks) {
                        return false;
                    }
                }
            }
            //checking the book isn't borrowed
            if (checkedBook.getCurrentBorrowerId() != -1) {
                return false;
            }
            //checking the patron will enjoy the book
            if (checkingPatron.willEnjoyBook(checkedBook)) {
                checkedBook.borrower = patronId;
                borrowed[bookId] = checkedBook;
                return true;
            }
        } return false;
    }

    //Returns the non-negative id number of the given book if he is owned by this library, -1 otherwise.
    int getBookId(Book book) {
        //checks the book exists in the library-
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
        //checks if patron is registered
        if (isPatronRegistered(patron)) {
            //if he is- starts iterating over registered patrons to get id
            for (int i = 0; i < maxPatronCapacity; i++) {
                //if its a match- return id
                if (patronMembers[i] == patron) {
                    return i;
                }
            }
        }
        //if the patron's not registered- return -1
        return -1;
    }

    //Returns true if the book with the given id is available, false otherwise.
    boolean isBookAvailable(int bookId) {
        //checks the bookId is valid and that it is borrowed- returns false if it is
        if ((!isBookIdValid(bookId)) || (borrowed[bookId] == books[bookId])){
            return false;
            //if it exists and not borrowed- returns true
        } return true;
    }


    //Returns true if the given number is an id of some book in the library, false otherwise.
    boolean isBookIdValid(int bookId) {
        //makes sure the id number is in range
        if ((bookId > maxBookCapacity-1) || (bookId < 0)) {
            //if its not- returns false
            return false;
            //if it is and the book exists in library- returns true
        } else if (books[bookId] != null) {
                return true;
            //if it is and the book doesn't exist- returns false
        } return false;
    }

    //Returns true if the given number is an id of a patron in the library, false otherwise.
    boolean isPatronIdValid(int patronId) {
        //makes sure the id number is in range
        if (patronId > maxPatronCapacity-1){
            //if its not- returns false
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
        //makes sure the id is valid-
        if (isBookIdValid(bookId)){
            //if it does- returns the book
            Book returnedBook = books[bookId];
            returnedBook.returnBook();
            borrowed[bookId] = null;
        }
    }

    //Suggest the patron with the given id the book he will enjoy the most,
    // out of all available books he will enjoy, if any such exist.
    Book suggestBookToPatron(int patronId) {
        //makes sure the patron id is valid
        if (isPatronIdValid(patronId)) {
            //if it is- initializing grading variables
            int curScore, maxScore = 0;
            Book bestBook = null;
            Patron askingPatron = patronMembers[patronId];
            //iterating over existing books
            for (int i = 0; i < maxBookCapacity; i++) {
                Book curBook = books[i];
                //makes sure the book is available
                if (isBookAvailable(i)) {
                    //makes sure the patron will enjoy book
                    if (askingPatron.willEnjoyBook(books[i])) {
                        //if he will- assiging grade
                        curScore = askingPatron.getBookScore(books[i]);
                        //checks if the new grade is the highest so far and changing assignment accordingly
                        if (curScore > maxScore) {
                            maxScore = curScore;
                            bestBook = curBook;
                        }
                    }
                }
            }
            //returns the book with highest score, if there is one
            return bestBook;
        } else {
            //returns null if none of the books is suitable
            return null;
        }
    }
}
