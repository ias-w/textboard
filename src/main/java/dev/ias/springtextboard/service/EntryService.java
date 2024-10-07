package dev.ias.springtextboard.service;

import dev.ias.springtextboard.model.Entry;
import dev.ias.springtextboard.repository.EntryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EntryService {
    private final EntryRepository repository;

    public Entry createEntry(Entry entry) {
        entry.setId(null);
        return repository.save(entry);
    }

    public List<Entry> getEntries() {
        return repository.findAll();
    }

    public List<Entry> findByTitle(String title) {
        return repository.findByTitle(title);
    }

    public List<Entry> findByTitleContainingIgnoreCase(String title) {
        return repository.findByTitleContainingIgnoreCase(title);
    }

    public List<Entry> findByAuthor(String author) {
        return repository.findByAuthor(author);
    }

    public List<Entry> findByAuthorContainsIgnoreCase(String author) {
        return repository.findByAuthorContainsIgnoreCase(author);
    }
}
