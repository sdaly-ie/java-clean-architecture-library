package usecases;

import entities.Member;

import java.util.UUID;

public class RegisterMemberUseCase {

    private LibraryRepository repo;

    public RegisterMemberUseCase(LibraryRepository repo) {
        if (repo == null) throw new IllegalArgumentException("repo required");
        this.repo = repo;
    }

    public String execute(String name) {
        if (name == null || name.isBlank()) throw new IllegalArgumentException("name required");

        Member member = new Member(UUID.randomUUID().toString(), name);
        repo.addMember(member);

        return member.getId();
    }
}
