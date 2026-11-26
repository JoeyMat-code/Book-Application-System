import java.util.ArrayList;
import java.util.List;

/**
 * AudioBook subclass inheriting from BookAbstract.
 * Stores duration in minutes, compute cost (half the cost of a single page per minute = $0.25),
 * and tracks the last 3 audiobooks.
 */

// INHERITANCE: AudioBook extends BookAbstract to inherit shared book attributes and methods.
public class AudioBook extends BookAbstract {

    // ENCAPSULATION: Duration (in minutes) and recent-audiobook list are private to protect internal state.
    private int duration; // in minutes
    private static final List<AudioBook> lastThreeAudio = new ArrayList<>();

    public AudioBook(String title, String author, String genre, int duration) {
        // INHERITANCE: Calls superclass constructor to initialize common BookAbstract fields.
        super(title, author, genre, 0.0, "AudioBook");
        this.duration = duration;
        // POLYMORPHISM / ABSTRACTION: Invokes AudioBook's specific implementation of computeCost().
        computeCost();
        updateLastThree(this);
    }

    // POLYMOPHISM: Overrides abstract method computeCost() from BookAbstract with audiobook pricing.
    @Override
    public void computeCost() {
        //Each minute cost half the cost of a single page ($0.50 / 2 = $0.25)
        this.cost = this.duration * 0.25;
    }

    // ENCAPSULATION: Controlled public getter provides read access to private duration.
    public int getDuration() {
        return duration;
    }

    public static double computeAverageDuration(List<Book> books) {
        int totalDuration = 0;
        int count = 0;
        for (Book b : books) {
            // POLYMORPHISM: Iterates over polymorphic Book references and checks runtime type.
            if (b instanceof AudioBook){
                totalDuration += ((AudioBook) b).getDuration();
                count++;
            }
        }
        return count == 0 ? 0.0 : (double) totalDuration / count;
    }

    // ENCAPSULATION: Helper method keeps sliding-window history logic private to AudioBook.
    private static void updateLastThree(AudioBook book) {
       lastThreeAudio.add(book);
       if (lastThreeAudio.size() > 3){
           lastThreeAudio.remove(0); //Keep only the last 3 added
       }
    }

    public static List<AudioBook> getLastThreeAudio() {
        return lastThreeAudio;
    }

    public static void displayLastThreeAudioDetails(){
        System.out.println("\n--- Last 3 AudioBooks Added ---");
        if (lastThreeAudio.isEmpty()) {
            System.out.println("No audiobooks added yet.");
            return;
        }
        for (AudioBook ab : lastThreeAudio) {
            // POLYMORPHISM: Calls overridden toString() on AudioBook.
            System.out.println(ab + " | Duration: " + ab.getDuration() + " mins");
        }
    }

    // POLYMORPHISM: Extends BookAbstract.toString() to append duration when printed as a Book.
    @Override
    public String toString() {
        return super.toString() + String.format(" | Duration: %d mins", duration);
    }
}
