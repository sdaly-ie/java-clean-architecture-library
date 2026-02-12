package adapters;

import entities.Book;
import usecases.*;

import java.util.List;

public class LibraryController {

    private final AddBookUseCase addBook;
    private final RegisterMemberUseCase registerMember;
    private final BorrowBookUseCase borrowBook;
    private final ReturnBookUseCase returnBook;
    private final ListBooksUseCase listBooks;

    public LibraryController(
            AddBookUseCase addBook,
            RegisterMemberUseCase registerMember,
            BorrowBookUseCase borrowBook,
            ReturnBookUseCase returnBook,
            ListBooksUseCase listBooks
    ) {
        this.addBook = addBook;
        this.registerMember = registerMember;
        this.borrowBook = borrowBook;
        this.returnBook = returnBook;
        this.listBooks = listBooks;
    }

    private String shortId(String id) {
        if (id == null) return "";
        String trimmed = id.trim();
        return trimmed.length() <= 8 ? trimmed : trimmed.substring(0, 8);
    }

    public String addBook(String title, String isbn, String author, int quantity) {
        try {
            String bookId = addBook.execute(title, isbn, author, quantity);
            return "Book added. ID=" + shortId(bookId);
        } catch (RuntimeException ex) {
            return "Add book failed: " + ex.getMessage();
        }
    }

    public String registerMember(String name) {
        try {
            String memberId = registerMember.execute(name);
            return "Member registered. ID=" + shortId(memberId);
        } catch (RuntimeException ex) {
            return "Register member failed: " + ex.getMessage();
        }
    }

    public String borrowBook(String bookId, String memberId) {
        try {
            String recordId = borrowBook.execute(bookId, memberId);
            return "Borrowed successfully. Record ID=" + shortId(recordId);
        } catch (RuntimeException ex) {
            return "Borrow failed: " + ex.getMessage();
        }
    }

    public String returnBook(String bookId, String memberId) {
        try {
            returnBook.execute(bookId, memberId);
            return "Returned successfully.";
        } catch (RuntimeException ex) {
            return "Return failed: " + ex.getMessage();
        }
    }

    public List<Book> listAvailableBooks() {
        return listBooks.execute();
    }
}
