/**
 * Abstract class implementing the Book interface.
 * Demonstrate Abstraction and Inheritance: provides common attributes and
 * leaves specific cost calculation to subclasses via the abstract computeCost method.
 */

public abstract class BookAbstract implements Book {

    // ENCAPSULATION: Fields are protected so external classes cannot directly alter object state/
    protected String title;
    protected String author;
    protected String genre;
    protected double cost;
    protected String bookType;

    public BookAbstract(String title, String author, String genre, double cost, String bookType) {
        this.title = title;
        this.author= author;
        this.genre = genre;
        this.cost = cost;
        this.bookType = bookType;
    }

    // ENCAPSULATION: Control public getters expose private fields safely.
    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getAuthor() {
        return author;
    }

    @Override
    public String getGenre() {
        return genre;
    }

    @Override
    public double getCost() {
        return cost;
    }

    @Override
    public String getBookType() {
        return bookType;
    }


     //ABSTRACTION: Forces each subclass to provide its own cost calculation rule.
    public abstract void computeCost();

    //POLYMORPHISM: Ovverides Object.toString() so polymorphic Book references print formatted details.
    @Override
    public String toString(){
        return String.format("Type: %s | Title: %s | Author: %s | Genre: %s | Cost: $%.2f",
                bookType, title, author, genre, cost);
    }
}
