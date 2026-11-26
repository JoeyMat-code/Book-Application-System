import java.util.ArrayList;
import java.util.List;

/**
 * PrintedBook subclass inheriting from BookAbstract.
 * Stores page count, compute cost ($0.50 per page), and tracks the last 3 printed books.
 */

// INHERITANCE: PrintedBook extends BookAbstract to reuse common book fields and behavior.
public class PrintedBook extends BookAbstract {

    //ENCAPSULATION: Page count and recent-book tracking list are private to protect internal state.
    private int numPages;
    private static final List<PrintedBook> lastThreePrinted = new ArrayList();

    public PrintedBook(String title, String author, String genre, int numPages){
        // INHERITANCE: Calls superclass constructor to initialize shared BookAbstract attributes.
        super(title, author, genre, 0.0, "PrintedBook");
        this.numPages = numPages;
        // POLYMORPHISM / ABSTRACTION: Invokes PrintedBook's specific implementation of computeCost().
        computeCost();
        updateLastThree(this);
    }

    // POLYMORPHISM: Overrides abstract method computeCost() from BookAbvstract with printed-book pricing.
    @Override
    public void computeCost(){
        //Cost of a single page is $0.50
        this.cost = this.numPages * 0.50;
    }

    // ENCAPSULATION: Controlled public getter provides read access to private numPages.
    public int getNumPages(){
        return numPages;
    }

    public static double computeAveragePages(List<Book> books){
        int totalPages = 0;
        int count = 0;
        for (Book b : books){
            // POLYMORPHISM: Iterates over polymirphic Book references and checks runtime type.
            if (b instanceof PrintedBook){
                totalPages += ((PrintedBook) b).getNumPages();
                count++;
            }
        }
        return count == 0 ? 0.0 : (double) totalPages / count;
    }

    // ENCAPSULATION: Helper method keeps sliding-window hsitory logic private to PritnedBook.
    private static void updateLastThree(PrintedBook book) {
        lastThreePrinted.add(book);
        if (lastThreePrinted.size() > 3) {
            lastThreePrinted.remove(0); //Keep only the last 3 added
        }
    }

    public static List<PrintedBook> getLastThreePrinted() {
        return lastThreePrinted;
    }

    public static void displayLastThreePrintedDetails() {
        System.out.println("\n--- Last 3 Printed Books Added ---");
        if (lastThreePrinted.isEmpty()){
            System.out.println("No printed books added yet.");
            return;
        }
        for (PrintedBook pb : lastThreePrinted) {
            // POLYMORPHISM: Calls overridden toString() on PrintedBook.
            System.out.println(pb + " | Pages: " + pb.getNumPages());
        }
    }

    // POLYMORPHISM: Extends BookAbstract.toString() to append page count when printed as a Book.
    @Override
    public String toString(){
        return super.toString() + String.format(" | Pages: %d", numPages);
    }
}