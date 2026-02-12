package frameworks;

import entities.Book;
import entities.BorrowRecord;
import entities.Member;
import usecases.LibraryRepository;

import java.util.ArrayList;
import java.util.List;

public class InMemoryLibraryRepository implements LibraryRepository {

    private final List<Book> books = new ArrayList<>();
    private final List<Member> members = new ArrayList<>();
    private final List<BorrowRecord> borrowRecords = new ArrayList<>();

    private String cleanId(String raw) {
        if (raw == null) return null;
        String s = raw.trim();
        if (s.startsWith("ID=") || s.startsWith("id=")) {
            s = s.substring(3).trim();
        }
        return s;
    }

    private boolean idMatches(String fullId, String userInput) {
        if (fullId == null || userInput == null) return false;
        String input = cleanId(userInput);
        if (input == null || input.isBlank()) return false;

        return fullId.equals(input) || fullId.startsWith(input);
    }

    // -------- Books --------

    @Override
    public void addBook(Book book) {
        if (book == null) throw new IllegalArgumentException("book required");
        books.add(book);
    }

    @Override
    public Book findBookById(String bookId) {
        if (bookId == null) return null;

        for (Book b : books) {
            if (idMatches(b.getId(), bookId)) return b;
        }
        return null;
    }

    @Override
    public Book findBookByIsbn(String isbn) {
        if (isbn == null) return null;
        String needle = isbn.trim();

        for (Book b : books) {
            if (b.getIsbn().equals(needle)) return b;
        }
        return null;
    }

    @Override
    public List<Book> getAllBooks() {
        return books;
    }

    // -------- Members --------

    @Override
    public void addMember(Member member) {
        if (member == null) throw new IllegalArgumentException("member required");
        members.add(member);
    }

    @Override
    public Member findMemberById(String memberId) {
        if (memberId == null) return null;

        for (Member m : members) {
            if (idMatches(m.getId(), memberId)) return m;
        }
        return null;
    }

    // -------- Borrow Records --------

    @Override
    public void addBorrowRecord(BorrowRecord record) {
        if (record == null) throw new IllegalArgumentException("record required");
        borrowRecords.add(record);
    }

    @Override
    public BorrowRecord findOpenBorrowRecordByBookId(String bookId) {
        if (bookId == null) return null;
        String id = cleanId(bookId);

        for (BorrowRecord r : borrowRecords) {
            if (r.isOpen() && r.getBookId().equals(id)) {
                return r;
            }
        }
        return null;
    }

    @Override
    public BorrowRecord findOpenBorrowRecordByBookIdAndMemberId(String bookId, String memberId) {
        if (bookId == null || memberId == null) return null;

        String bId = cleanId(bookId);
        String mId = cleanId(memberId);

        for (BorrowRecord r : borrowRecords) {
            if (r.isOpen()
                    && r.getBookId().equals(bId)
                    && r.getMemberId().equals(mId)) {
                return r;
            }
        }
        return null;
    }

    @Override
    public int countOpenBorrowRecordsForMember(String memberId) {
        if (memberId == null) return 0;
        String id = cleanId(memberId);

        int count = 0;
        for (BorrowRecord r : borrowRecords) {
            if (r.getMemberId().equals(id) && r.isOpen()) count++;
        }
        return count;
    }
}

