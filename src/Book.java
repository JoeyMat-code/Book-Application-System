import java.util.List;
import java.util.Map;

/**
 * Interface representing the Book contract.
 * Demonstrates Abstraction by defining high-level operations without implementation details.
 */

// ABSTRACTION: Defines the public contract for any Book without exposing how fields or costs are implemented.
public interface Book {

    // ABSTRACTION: Declares core read operations that implementing/extending classes ,ust provide.
    String getTitle();
    String getAuthor();
    String getGenre();
    double getCost();
    String getBookType();

     //Default method to display details of all books stored in the application.

    default void displayAllBooksDetails(List<Book> books){
        if (books == null || books.isEmpty()){
            System.out.println("No books stored in the application.");
            return;
        }
        System.out.println("\n--- All Books Details ---");
        // POLYMORPHISM: Loops over Book references and calls each object's overridden toString() at runtime.
        for (Book b : books){
            System.out.println(b);
        }
    }

     //Returns the number of books in each genre.
    static Map<String, Integer> numberBooksPerGenre(List<Book> books){
        Map<String, Integer> genreCount = new java.util.HashMap<>();
        // POLYMORPHISM: Calls getGere() through the Book interface regardless of concrete subclass.
        for (Book b : books){
            genreCount.put(b.getGenre(), genreCount.getOrDefault(b.getGenre(), 0) + 1);
        }
        return genreCount;
    }

     //Return the total computed cost of all stored books.
    static double getTotalCost(List<Book> books){
        double total = 0.0;
        // POLYMORPHISM: Sums getCost() across mixed PrintedBook and AudioBook instances via Book references.
        for (Book b : books){
            total += b.getCost();
        }
        return total;
    }
}

