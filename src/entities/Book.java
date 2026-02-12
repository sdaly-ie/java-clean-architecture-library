package entities;

public class Book {

    /* Allowed states */
    public enum Status { AVAILABLE, BORROWED }

    /* Fields for data */
    private final String id;
    private final String title;
    private final String isbn;
    private final String author;
    private int quantity; // copies available

    /* Mutable fields that change over time */
    private Status status;

    /* Constructor */
    public Book(String id, String title, String isbn, String author, int quantity) {
        if (id == null || id.isBlank()) throw new IllegalArgumentException("id required");
        if (title == null || title.isBlank()) throw new IllegalArgumentException("title required");
        if (isbn == null || isbn.isBlank()) throw new IllegalArgumentException("isbn required");
        if (author == null || author.isBlank()) throw new IllegalArgumentException("author required");
        if (quantity < 0) throw new IllegalArgumentException("quantity must be >= 0");

        this.id = id.trim();
        this.title = title.trim();
        this.isbn = isbn.trim();
        this.author = author.trim();
        this.quantity = quantity;

        // If there are no copies of the book, then treat as not available
        this.status = (this.quantity > 0) ? Status.AVAILABLE : Status.BORROWED;
    }

    /* Getters */
    public String getId() { return id; }
    public String getTitle() { return title; }
    public String getIsbn() { return isbn; }
    public String getAuthor() { return author; }
    public int getQuantity() { return quantity; }
    public Status getStatus() { return status; }

    /* No copies are available can't borrow */
    public void borrow() {
        if (quantity <= 0) throw new IllegalStateException("no copies available");
        quantity--;
        status = (quantity > 0) ? Status.AVAILABLE : Status.BORROWED;
    }

    /* Returning book increases number of available copies */
    public void returnBook() {
        quantity++;
        status = Status.AVAILABLE;
    }
}

