package dev.ias.springtextboard.controller;

import dev.ias.springtextboard.model.Entry;
import dev.ias.springtextboard.service.EntryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class EntryController {
    private final EntryService service;

    @PostMapping("/api/v1/entry")
    Entry createEntry(@RequestBody Entry entry) {
        return service.createEntry(entry);
    }

    @GetMapping("/api/v1/entry")
    Iterable<Entry> getAllEntries() {
        return service.getEntries();
    }
}
