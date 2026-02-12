package usecases;

import entities.Book;
import entities.BorrowRecord;
import entities.Member;

import java.util.UUID;

public class BorrowBookUseCase {

    private final LibraryRepository repo;
    private static final int MAX_BORROWS_PER_MEMBER = 10;

    public BorrowBookUseCase(LibraryRepository repo) {
        if (repo == null) throw new IllegalArgumentException("repo required");
        this.repo = repo;
    }

    public String execute(String bookId, String memberId) {
        if (bookId == null || bookId.isBlank()) throw new IllegalArgumentException("bookId required");
        if (memberId == null || memberId.isBlank()) throw new IllegalArgumentException("memberId required");

        Book book = repo.findBookById(bookId.trim());
        if (book == null) throw new IllegalArgumentException("Book not found");

        Member member = repo.findMemberById(memberId.trim());
        if (member == null) throw new IllegalArgumentException("Member not found");

        int openBorrows = repo.countOpenBorrowRecordsForMember(member.getId());
        if (openBorrows >= MAX_BORROWS_PER_MEMBER) {
            throw new IllegalStateException("Borrow limit reached (max " + MAX_BORROWS_PER_MEMBER + " active borrows)");
        }

        if (book.getQuantity() <= 0) {
            throw new IllegalStateException("No copies available");
        }

        book.borrow();

        BorrowRecord record = new BorrowRecord(
                UUID.randomUUID().toString(),
                book.getId(),
                member.getId()
        );
        repo.addBorrowRecord(record);

        return record.getId();
    }
}
