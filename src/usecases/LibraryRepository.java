package usecases;

import entities.Book;
import entities.BorrowRecord;
import entities.Member;

import java.util.List;

public interface LibraryRepository {

    // Books
    void addBook(Book book);
    Book findBookById(String bookId);
    Book findBookByIsbn(String isbn);
    List<Book> getAllBooks();

    // Members
    void addMember(Member member);
    Member findMemberById(String memberId);

    // Borrow records
    void addBorrowRecord(BorrowRecord record);
    BorrowRecord findOpenBorrowRecordByBookId(String bookId);
    BorrowRecord findOpenBorrowRecordByBookIdAndMemberId(String bookId, String memberId);

    // Extra feature: borrowing limit
    int countOpenBorrowRecordsForMember(String memberId);
}
