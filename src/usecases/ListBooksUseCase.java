package usecases;

import entities.Book;

import java.util.ArrayList;
import java.util.List;

public class ListBooksUseCase {

    private LibraryRepository repo;

    public ListBooksUseCase(LibraryRepository repo) {
        if (repo == null) throw new IllegalArgumentException("repo required");
        this.repo = repo;
    }

    public List<Book> execute() {
        List<Book> available = new ArrayList<>();
        for (Book b : repo.getAllBooks()) {
            if (b.getStatus() == Book.Status.AVAILABLE) {
                available.add(b);
            }
        }
        return available;
    }
}
