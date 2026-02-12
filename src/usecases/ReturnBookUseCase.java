package usecases;

import entities.Book;
import entities.BorrowRecord;
import entities.Member;

public class ReturnBookUseCase {

    private final LibraryRepository repo;

    public ReturnBookUseCase(LibraryRepository repo) {
        if (repo == null) throw new IllegalArgumentException("repo required");
        this.repo = repo;
    }

    public void execute(String bookId, String memberId) {
        if (bookId == null || bookId.isBlank()) throw new IllegalArgumentException("bookId required");
        if (memberId == null || memberId.isBlank()) throw new IllegalArgumentException("memberId required");

        Book book = repo.findBookById(bookId.trim());
        if (book == null) throw new IllegalArgumentException("Book not found");

        Member member = repo.findMemberById(memberId.trim());
        if (member == null) throw new IllegalArgumentException("Member not found");

        BorrowRecord record = repo.findOpenBorrowRecordByBookIdAndMemberId(book.getId(), member.getId());
        if (record == null) throw new IllegalStateException("No open borrow record for this book + member");

        record.close();
        book.returnBook();
    }
}
