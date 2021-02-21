//This class represents a library patron that has a name and assigns
// values to different literary aspects of books
class Patron {
    final String patronFirstName;
    final String patronLastName;
    final int comicTendency;
    final int dramaticTendency;
    final int educationalTendency;
    final int patronEnjoymentThreshold;

    /**---constructor function**/

    //constructor function- creates a new patron with the given characteristics
    Patron(String inputPatronFirstName,
                  String inputPatronLastName,
                  int inputComicTendency,
                  int inputDramaticTendency,
                  int inputEducationalTendency,
                  int inputPatronEnjoymentThreshold){
        patronFirstName = inputPatronFirstName;
        patronLastName = inputPatronLastName;
        comicTendency = inputComicTendency;
        dramaticTendency = inputDramaticTendency;
        educationalTendency = inputEducationalTendency;
        patronEnjoymentThreshold = inputPatronEnjoymentThreshold;
    }

    /**----=  Instance Methods  =-----**/

    //gets as input a book to asses. returns the literary value this patron assigns to the given book.
    int getBookScore(Book book){
        return comicTendency*book.getBookComicValue()+dramaticTendency*book.getBookDramaticValue()+
                educationalTendency*book.getBookEducationalValue();
    }

    //gets as input a book to asses. returns true of this patron will enjoy
    // the given book, false otherwise.
    boolean willEnjoyBook(Book book){
        //if enjoyment threshold is smaller than the book's score- return true
        if (patronEnjoymentThreshold < getBookScore(book)) {
            return true;
            //if enjoyment threshold is bigger than the book's score- return false
        } else {
            return false;
        }
    }

    //Returns a string representation of the patron,
    // which is a sequence of its first and last name.
    String stringRepresentation(){
        return this.patronFirstName+" "+this.patronLastName;
    }
}
