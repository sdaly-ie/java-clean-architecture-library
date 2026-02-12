package main;

import adapters.LibraryController;
import frameworks.ConsoleMenu;
import frameworks.InMemoryLibraryRepository;

import usecases.AddBookUseCase;
import usecases.RegisterMemberUseCase;
import usecases.BorrowBookUseCase;
import usecases.ReturnBookUseCase;
import usecases.ListBooksUseCase;

public class Main {

    public static void main(String[] args) {

        InMemoryLibraryRepository repo = new InMemoryLibraryRepository();

        AddBookUseCase addBook = new AddBookUseCase(repo);
        RegisterMemberUseCase registerMember = new RegisterMemberUseCase(repo);
        BorrowBookUseCase borrowBook = new BorrowBookUseCase(repo);
        ReturnBookUseCase returnBook = new ReturnBookUseCase(repo);
        ListBooksUseCase listBooks = new ListBooksUseCase(repo);

        LibraryController controller = new LibraryController(
                addBook, registerMember, borrowBook, returnBook, listBooks
        );

        ConsoleMenu menu = new ConsoleMenu(controller);
        menu.run();
    }
}
