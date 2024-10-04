package dev.ias.springtextboard.repository;

import dev.ias.springtextboard.model.Entry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EntryRepository extends JpaRepository<Entry, Long> {
    List<Entry> findByTitle(String title);
    List<Entry> findByTitleContainingIgnoreCase(String title);
    List<Entry> findByAuthor(String author);
    List<Entry> findByAuthorContainsIgnoreCase(String author);
}
