package dev.ias.springtextboard.controller;

import dev.ias.springtextboard.model.Entry;
import dev.ias.springtextboard.service.EntryService;
import dev.ias.springtextboard.service.RequestLoggingService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
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
    private final RequestLoggingService loggingService;

    @PostMapping
    ResponseEntity<Entry> createEntry(@RequestBody Entry entry, HttpServletRequest request) {
        Entry createdEntry = service.createEntry(entry);
        loggingService.save(HttpMethod.POST, "/api/v1/entry", request, createdEntry);
        return ResponseEntity.status(CREATED).body(createdEntry);
    }

    @GetMapping
    List<Entry> getAllEntries(HttpServletRequest request) {
        loggingService.save(HttpMethod.GET, "/api/v1/entry", request);
        return service.getEntries();
    }

    @GetMapping("/search/title")
    List<Entry> searchByTitle(@RequestParam String title, HttpServletRequest request) {
        loggingService.save(HttpMethod.GET, "/api/v1/entry/search/title", request, "title=" + title);
        return service.findByTitle(title);
    }

    @GetMapping("/search/title/contains")
    List<Entry> searchByTitleContaining(@RequestParam String title, HttpServletRequest request) {
        loggingService.save(HttpMethod.GET, "/api/v1/entry/search/title/contains", request, "title=" + title);
        return service.findByTitleContainingIgnoreCase(title);
    }

    @GetMapping("/search/author")
    List<Entry> searchByAuthor(@RequestParam String author, HttpServletRequest request) {
        loggingService.save(HttpMethod.GET, "/api/v1/entry/search/author", request, "author=" + author);
        return service.findByAuthor(author);
    }

    @GetMapping("/search/author/contains")
    List<Entry> searchByAuthorContaining(@RequestParam String author, HttpServletRequest request) {
        loggingService.save(HttpMethod.GET, "/api/v1/entry/search/author/contains", request, "author=" + author);
        return service.findByAuthorContainsIgnoreCase(author);
    }
}
