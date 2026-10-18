package dev.akhileshaher.backend.service;

import dev.akhileshaher.backend.entity.Book;
import dev.akhileshaher.backend.entity.IssueRecord;
import dev.akhileshaher.backend.entity.User;
import dev.akhileshaher.backend.repository.BookRepository;
import dev.akhileshaher.backend.repository.IssueRecordRepository;
import dev.akhileshaher.backend.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDate;

@Service
public class IssueRecordService {

    private final IssueRecordRepository issueRecordRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    public IssueRecordService(
            IssueRecordRepository issueRecordRepository,
            BookRepository bookRepository,
            UserRepository userRepository
    ) {
        this.issueRecordRepository = issueRecordRepository;
        this.bookRepository= bookRepository;
        this.userRepository = userRepository;
    }

    public IssueRecord issueTheBook(Long bookId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Book not found"));

        if(book.getQuantity() <= 0 || !book.getIsAvailable()) {
            throw new RuntimeException("Book is not available");
        }

        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        IssueRecord issueRecord = new IssueRecord();
        issueRecord.setIssueDate(LocalDate.now());
        issueRecord.setDueDate(LocalDate.now().plusDays(14));
        issueRecord.setIsReturned(false);
        issueRecord.setBook(book);
        issueRecord.setUser(user);

        book.setQuantity(book.getQuantity() - 1);

        if(book.getQuantity() == 0) {
            book.setIsAvailable(false);
        }
        bookRepository.save(book);
        return issueRecordRepository.save(issueRecord);
    }

    public IssueRecord returnTheBook(Long issueRecordId) {
        IssueRecord issueRecord = issueRecordRepository.findById(issueRecordId)
                .orElseThrow(() -> new RuntimeException("Issue Record Not Found"));

        if(issueRecord.getIsReturned()) {
            throw new RuntimeException("Book is already returned");
        }

        Book book = issueRecord.getBook();
        book.setQuantity(book.getQuantity() + 1);
        book.setIsAvailable(true);
        bookRepository.save(book);

        issueRecord.setReturnDate(LocalDate.now());
        issueRecord.setIsReturned(true);

        return issueRecordRepository.save(issueRecord);
    }
}
