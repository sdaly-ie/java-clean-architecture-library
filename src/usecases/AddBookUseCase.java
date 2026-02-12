package usecases;

import entities.Book;

import java.util.UUID;

public class AddBookUseCase {

    private static final int MAX_BOOKS = 10;
    private final LibraryRepository repo;

    public AddBookUseCase(LibraryRepository repo) {
        if (repo == null) throw new IllegalArgumentException("repo required");
        this.repo = repo;
    }

    public String execute(String title, String isbn, String author, int quantity) {
        if (title == null || title.isBlank()) throw new IllegalArgumentException("title required");
        if (isbn == null || isbn.isBlank()) throw new IllegalArgumentException("isbn required");
        if (author == null || author.isBlank()) throw new IllegalArgumentException("author required");
        if (quantity <= 0) throw new IllegalArgumentException("quantity must be >= 1");

        if (repo.getAllBooks().size() >= MAX_BOOKS) {
            throw new IllegalStateException("Library is full (max " + MAX_BOOKS + " books)");
        }

        if (repo.findBookByIsbn(isbn.trim()) != null) {
            throw new IllegalStateException("ISBN already exists");
        }

        Book book = new Book(UUID.randomUUID().toString(), title, isbn, author, quantity);
        repo.addBook(book);

        return book.getId();
    }
}
