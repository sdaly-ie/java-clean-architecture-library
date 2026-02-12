package entities;

public class BorrowRecord {

    private String id;
    private String bookId;
    private String memberId;
    private boolean open = true;

    public BorrowRecord(String id, String bookId, String memberId) {
        if (id == null || id.isBlank()) throw new IllegalArgumentException("id required");
        if (bookId == null || bookId.isBlank()) throw new IllegalArgumentException("bookId required");
        if (memberId == null || memberId.isBlank()) throw new IllegalArgumentException("memberId required");

        this.id = id.trim();
        this.bookId = bookId.trim();
        this.memberId = memberId.trim();
    }

    public String getId() { return id; }
    public String getBookId() { return bookId; }
    public String getMemberId() { return memberId; }
    public boolean isOpen() { return open; }

    public void close() {
        if (!open) throw new IllegalStateException("borrow record already closed");
        open = false;
    }
}
