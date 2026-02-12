package frameworks;

import adapters.LibraryController;
import entities.Book;

import java.util.List;
import java.util.Scanner;

public class ConsoleMenu {

    private final LibraryController controller;
    private final Scanner scanner = new Scanner(System.in);

    public ConsoleMenu(LibraryController controller) {
        this.controller = controller;
    }

    public void run() {
        boolean running = true;

        while (running) {
            printMenu();
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1" -> addBookFlow();
                case "2" -> registerMemberFlow();
                case "3" -> borrowBookFlow();
                case "4" -> returnBookFlow();
                case "5" -> listBooksFlow();
                case "0" -> running = false;
                default -> System.out.println("Invalid option. Try again.");
            }
        }

        System.out.println("Goodbye.");
    }

    private void printMenu() {
        System.out.println("\n=== Library Menu ===");
        System.out.println("1) Add book");
        System.out.println("2) Register member");
        System.out.println("3) Borrow book (limit: 10 active borrows per member)");
        System.out.println("4) Return book");
        System.out.println("5) View available books");
        System.out.println("0) Exit");
        System.out.print("Choose: ");
    }

    private void addBookFlow() {
        System.out.print("Title: ");
        String title = scanner.nextLine();

        System.out.print("ISBN: ");
        String isbn = scanner.nextLine();

        System.out.print("Author: ");
        String author = scanner.nextLine();

        int quantity;
        while (true) {
            System.out.print("Quantity: ");
            String qtyInput = scanner.nextLine().trim();

            qtyInput = qtyInput.replace("Quantity:", "")
                    .replace("quantity:", "")
                    .replace("qty=", "")
                    .trim();

            try {
                quantity = Integer.parseInt(qtyInput);
                break;
            } catch (NumberFormatException ex) {
                System.out.println("Please enter a whole number (e.g., 1, 2, 3).");
            }
        }

        System.out.println(controller.addBook(title, isbn, author, quantity));
    }

    private void registerMemberFlow() {
        System.out.print("Member name: ");
        String name = scanner.nextLine();

        System.out.println(controller.registerMember(name));
    }

    private void borrowBookFlow() {
        System.out.print("Book ID: ");
        String bookId = scanner.nextLine();

        System.out.print("Member ID: ");
        String memberId = scanner.nextLine();

        System.out.println(controller.borrowBook(bookId, memberId));
    }

    private void returnBookFlow() {
        System.out.print("Book ID: ");
        String bookId = scanner.nextLine();

        System.out.print("Member ID: ");
        String memberId = scanner.nextLine();

        System.out.println(controller.returnBook(bookId, memberId));
    }

    private void listBooksFlow() {
        List<Book> available = controller.listAvailableBooks();

        if (available.isEmpty()) {
            System.out.println("No available books.");
            return;
        }

        System.out.println("\nAvailable books:");
        for (Book b : available) {
            System.out.println("- " + b.getId()
                    + " | " + b.getTitle()
                    + " | " + b.getAuthor()
                    + " | " + b.getIsbn()
                    + " | Qty: " + b.getQuantity());
        }
    }
}
