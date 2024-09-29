package dev.ias.springtextboard.service;

import dev.ias.springtextboard.model.Entry;
import dev.ias.springtextboard.repository.EntryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EntryService {
    private final EntryRepository repository;

    public Entry createEntry(Entry entry) {
        return repository.save(entry);
    }

    public Iterable<Entry> getEntries() {
        return repository.findAll();
    }
}
