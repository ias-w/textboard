package dev.ias.springtextboard.controller;

import dev.ias.springtextboard.model.Entry;
import dev.ias.springtextboard.service.EntryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/entry")
public class EntryController {
    private final EntryService service;

    @PostMapping
    ResponseEntity<Entry> createEntry(@RequestBody Entry entry) {
        return ResponseEntity.status(CREATED).body(service.createEntry(entry));
    }

    @GetMapping
    List<Entry> getAllEntries() {
        return service.getEntries();
    }

    @GetMapping("/search/title")
    List<Entry> searchByTitle(@RequestParam String title) {
        return service.findByTitle(title);
    }

    @GetMapping("/search/title/contains")
    List<Entry> searchByTitleContaining(@RequestParam String title) {
        return service.findByTitleContainingIgnoreCase(title);
    }

    @GetMapping("/search/author")
    List<Entry> searchByAuthor(@RequestParam String author) {
        return service.findByAuthor(author);
    }

    @GetMapping("/search/author/contains")
    List<Entry> searchByAuthorContaining(@RequestParam String author) {
        return service.findByAuthorContainsIgnoreCase(author);
    }
}
