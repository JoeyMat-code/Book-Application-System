import java.io.*;
import java.util.*;

/**
 * ---BOOK APPLICATION SYSTEM PROJECT---
 * This is my project for tracking books I read, calulating costs,
 * search and sort them, check stats, and save/load data from a file I used 4 main OOP concepts:
 *
 * 1. ABSTRACTION:
 *      - I made a 'Book' interface and a 'BookAbstract' class.
 *      -They help hide complex stuff and show only what is needed.
 *      For example the cost calculation method (computeCost()) is left
 *      blank in the abstract class so the child classes can writhe their
 *      own rules for it.
 *
 * 2. INHERITANCE:
 *      - My 'BookAbstract' takes things from the 'Book' interface.
 *      -Then, 'PrintedBook' and 'Audiobook' both inherit the same basic things
 *      like title, author, genre, cost from 'BookAbstract' so I dont have to
 *      write the same code twice.
 *
 * 3. POLYMORPHISM:
 *      - Both 'PrintedBook' and 'AudioBook' override the 'computeCost()' method
 *      differently (Printed books cost $0.50 per page, but audionbook cost
 *      $0.25 per minute).
 *      -When the program runs, it handles different book type together in
 *      one list smoothly.
 *
 * 4. ENCAPSULATION:
 *      - I keep my fields protected or private inside the classes so other
 *      parts of the program cannot change them directly. Users can only
 *      view or change them safely using getter methods.
 *
 */

public class BookApplication {
    private static final List<Book> bookList = new ArrayList<>();
    private static final Scanner scanner = new Scanner(System.in);
    private static final String FILE_NAME = "books_data.txt";

    public static void main(String [] args){
        //Automatically load saved data on program startup
        loadFromFile();

        boolean running = true;
        while (running) {
            printMenu();
            String choice = scanner.nextLine().trim();

            switch (choice){
                case "1":
                    addBookInteractive();
                    break;
                case "2":
                    searchBooksInteractive();
                    break;
                case "3":
                    sortBooksInteractive();
                    break;
                case "4":
                    displayStatisticsInteractive();
                    break;
                case "5":
                    displayAllBooks();
                    break;
                case "6":
                    saveToFile();
                    break;
                case "7":
                    loadFromFile();
                    break;
                case "8":
                    saveToFile();
                    System.out.println("Data saved successfully. Exiting application. Goodbye!");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option. Please enter a number between 1 and 8.");
            }
        }
    }

    private static void printMenu() {
        System.out.println("\n============================================");
        System.out.println("         BOOK APPLICATION SYSTEM MENU         ");
        System.out.println("==============================================");
        System.out.println("1. Add a Book (Printed or Audio)");
        System.out.println("2. Search Books (Title, Author, Genre, Type)");
        System.out.println("3. Sort Books");
        System.out.println("4. Display Statistics & Last-3 Tracking");
        System.out.println("5. Display All Books");
        System.out.println("6. Save Data to File");
        System.out.println("7. Load Data to File");
        System.out.println("8. Exit");
        System.out.print("Enter your choice (1-8): ");
    }

    private static void addBookInteractive() {
        String typeChoice;
        while (true) {
            System.out.println("Enter Book Type (1 for PrintedBook, 2 for AudioBook): ");
            typeChoice = scanner.nextLine().trim();
            if (typeChoice.equals("1") || typeChoice.equals("2")) {
                break;
            }
            System.out.print("Invalid book type choice. Please enter 1 or 2.");
        }
        System.out.print("Enter Title: ");
        String title = scanner.nextLine().trim();

        System.out.print("Enter Author: ");
        String author = scanner.nextLine().trim();

        System.out.print("Enter Genre: ");
        String genre = scanner.nextLine().trim();

        if (typeChoice.equals("1")) {
            int pages = -1;
            while (pages < 0) {
                System.out.print("Enter Number of Pages (0 or more): ");
                try {
                    int parsed = Integer.parseInt(scanner.nextLine().trim());
                    if (parsed >= 0) {
                        pages = parsed;
                    } else {
                        System.out.println("Number of pages cannot be negative. Please enter 0 or more.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid page number entered. Book not added.");
                }
            }
            // Here is Polymorphism & Inheritance creating a PrintedBook object
            PrintedBook pb = new PrintedBook(title, author, genre, pages);
            bookList.add(pb);
            System.out.println("Printed Book added successfully! Cost computed: $" + String.format("%.2f", pb.getCost()));

        } else {
            int duration = -1;
            while (duration < 0) {
                System.out.print("Enter Duration in minutes: ");
                try {
                    int parsed = Integer.parseInt(scanner.nextLine().trim());
                    if (parsed >= 0) {
                        duration = parsed;
                    } else {
                        System.out.println(" Duration cannot be negative. Please enter 0 or more.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid duration entered. Book not added.");
                }
                // Creating an AudioBook object
                AudioBook ab = new AudioBook(title, author, genre, duration);
                bookList.add(ab);
                System.out.println("AudioBook added successfully! Cost computed: $" + String.format("%.2f", ab.getCost()));
            }
        }
    }

    private static void searchBooksInteractive() {
        System.out.println("\n--- Search Books ---");
        System.out.println("1. Search by Title (partial match allowed)");
        System.out.println("2. Search by Author");
        System.out.println("3. Search by Genre");
        System.out.println("4. Search by Book Type (PrintedBook / AudioBook)");

        String choice="";
        while (true) {

            System.out.print("Choose search criteria (1-4): ");
            choice = scanner.nextLine().trim();

            if (choice.equals("1") || choice.equals("2") || choice.equals("3") || choice.equals("4")) {
                break;
            }
            System.out.println("Invalid search option. Please enter a number from 1 to 4.");
        }

        System.out.print("Enter search query: ");
        String query = scanner.nextLine().trim().toLowerCase();

        List<Book> results = new ArrayList<>();
        for (Book b : bookList) {
            switch (choice) {
                case "1":
                    if (b.getTitle().toLowerCase().contains(query)) results.add(b);
                    break;
                case "2":
                    if (b.getAuthor().toLowerCase().contains(query)) results.add(b);
                    break;
                case "3":
                    if (b.getGenre().toLowerCase().contains(query)) results.add(b);
                    break;
                case "4":
                    if (b.getBookType().toLowerCase().contains(query)) results.add(b);
                    break;
            }
        }
        System.out.println("\n--- Search Results (" + results.size() + " found) ---");
        if (results.isEmpty()){
            System.out.println("No matching books found.");
        } else {
            for (Book b : results){
                System.out.println(b);
            }
        }
    }

    private static void sortBooksInteractive(){
        System.out.println("\n--- Sort Books ---");
        System.out.println("1. Sort by Title");
        System.out.println("2. Sort by Cost");
        System.out.print("Choose sort option (1-2): ");
        String choice = scanner.nextLine().trim();

        if (choice.equals("1")){
            bookList.sort(Comparator.comparing(Book::getTitle, String.CASE_INSENSITIVE_ORDER));
            System.out.println("Books sorted by Title successfully.");
        } else if (choice.equals("2")){
            bookList.sort(Comparator.comparingDouble(Book::getCost));
            System.out.println("Books sorted by Cost successfully.");
        } else {
            System.out.println("Invalid sort choice.");
            return;
        }
        displayAllBooks();
    }

    private static void displayStatisticsInteractive() {
        System.out.println("\n========================================");
        System.out.println("          APPLICATION STATISTICS          ");
        System.out.println("==========================================");
        System.out.println("Total Books Stored: " + bookList.size());
        System.out.println("Total Computed Cost: $" + String.format("%.2f", Book.getTotalCost(bookList)));
        System.out.println("Average Pages (Printed Books): " + String.format("%.1f",PrintedBook.computeAveragePages(bookList)));
        System.out.println("Average Duration (AudioBooks): " + String.format("%.1f mins", AudioBook.computeAverageDuration(bookList)));

        System.out.println("\n--- Books Per Genre ---");
        Map<String, Integer> genreCounts = Book.numberBooksPerGenre(bookList);
        if (genreCounts.isEmpty()){
            System.out.println("No genres recorded.");
        } else {
            for (Map.Entry<String, Integer> entry : genreCounts.entrySet()){
                System.out.println("- " + entry.getKey() + ": " + entry.getValue() + " book(s)");
            }
        }

        //Display Last 3 tracking functionality
        PrintedBook.displayLastThreePrintedDetails();
        AudioBook.displayLastThreeAudioDetails();
    }

    private static void displayAllBooks(){
        if(bookList.isEmpty()){
            System.out.println("\nNo books in the application.");
            return;
        }
        // Using interface default method here
        bookList.get(0).displayAllBooksDetails(bookList);
    }

    public static void saveToFile(){
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_NAME))){
            for (Book b : bookList){
                if (b instanceof PrintedBook){
                    PrintedBook pb = (PrintedBook) b;
                    writer.println("PrintedBook," +pb.getTitle() + "," + pb.getAuthor() + "," + pb.getGenre() + "," + pb.getNumPages());
                } else if (b instanceof AudioBook) {
                    AudioBook ab = (AudioBook) b;
                    writer.println("AudioBook," +ab.getTitle() + "," + ab.getAuthor() + "," + ab.getGenre() + "," + ab.getDuration());
                    }
                }
            System.out.println("Data successfully saved to " + FILE_NAME);
            }catch (IOException e){
            System.out.println("Error saving file: " + e.getMessage());
        }
    }

    public static void loadFromFile(){
        File file = new File(FILE_NAME);
        if (!file.exists()){
            System.out.println("File not found: " + FILE_NAME);
            return;
        }

        bookList.clear();
        int loadedCount = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))){
            String line;
            while ((line = reader.readLine()) != null){
                line = line.replace("\uFEFF", "").trim();

                if (line.isEmpty() || line.startsWith("Title,Author,")){
                    continue;
                }

                String[] parts = line.split(",");

                if (parts.length == 5){
                    String type = parts[0].trim();
                    String title = parts[1].trim();
                    String author = parts[2].trim();
                    String genre = parts[3].trim();
                    int metric = Integer.parseInt(parts[4].trim());

                    if (type.equalsIgnoreCase("PrintedBook") || type.equalsIgnoreCase("Printed")){
                        bookList.add(new PrintedBook(title, author, genre, metric));
                        loadedCount++;
                    } else if (type.equalsIgnoreCase("AudioBook") || type.equalsIgnoreCase("Audio")){
                        bookList.add(new AudioBook(title, author, genre, metric));
                        loadedCount++;
                    }
                }
                else if (parts.length >= 6){
                    String title = parts[0].trim();
                    String author = parts[1].trim();
                    String genre = parts[2].trim();
                    String type = parts[3].trim();
                    String pagesStr = parts[4].trim();
                    String durationStr = parts[5].trim();


                    if (type.equalsIgnoreCase("Printed") || type.equalsIgnoreCase("PrintedBook")){
                        int pages = pagesStr.equalsIgnoreCase("null") ? 0 : Integer.parseInt(pagesStr);
                        bookList.add(new PrintedBook(title, author, genre, pages));
                        loadedCount++;
                    } else if (type.equalsIgnoreCase("Audio") || type.equalsIgnoreCase("AudioBook")){
                        int duration = durationStr.equalsIgnoreCase("null") ? 0 : Integer.parseInt(durationStr);
                        bookList.add(new AudioBook(title, author, genre, duration));
                        loadedCount++;
                    }
                }
            }
            System.out.println("Loaded " + loadedCount + " book(s) from " + FILE_NAME);
        } catch (IOException | NumberFormatException e){
            System.out.println("Error loading file: " + e.getMessage());
        }
    }
}
