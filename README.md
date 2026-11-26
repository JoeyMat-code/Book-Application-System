# Book Application System

## Project Description
A Java console application for managing a mixed catalog of **PrintedBook** and **AudioBook** items. Users can load and save book records via `books_data.txt`, add new books with input validation against invalid characters or negative numbers (while supporting zero values), view all stored books and the last 3 added printed books or audiobooks, search entries, check genre counts, and compute total cost along with average page count and average duration.

## How to Run the Program

### From IntelliJ IDEA
1. Open the `BookApplicationSystem` project in IntelliJ IDEA.
2. Make sure `books_data.txt` is present in the project root directory.
3. Run `BookApplication.main()`.

### From Command Prompt / Terminal (in the project root)
```bash
javac -d out src/*.java
java -cp out BookApplication
```

## Design Explanation
- **Abstraction:** `Book` (interface) defines the book contract and helper methods, while `BookAbstract` declares `abstract void computeCost()` so each book type defines its own pricing formula.
- **Inheritance:** `BookAbstract` implements `Book`, and `PrintedBook` and `AudioBook` extend `BookAbstract` to reuse shared fields and behavior while adding type-specific state (`numPages` for printed books and `duration` in minutes for audiobooks).
- **Encapsulation:** Subclass fields (`numPages`, `duration`) and sliding-window tracking lists (`lastThreePrinted`, `lastThreeAudio`) are kept `private` with controlled accessors and helper methods.
- **Polymorphism:** Books are managed as `Book` references in a list, allowing `computeCost()` (\$0.50 per page for printed books; \$0.25 per minute for audiobooks), `toString()`, and interface methods to dispatch dynamically at runtime based on each object's actual subclass.