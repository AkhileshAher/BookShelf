package dev.akhileshaher.backend.repository;

import dev.akhileshaher.backend.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}
