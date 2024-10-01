package dev.ias.springtextboard.service;

import dev.ias.springtextboard.model.Entry;
import dev.ias.springtextboard.repository.EntryRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class EntryServiceUnitTest {
    @Mock
    private EntryRepository entryRepository;

    @InjectMocks
    private EntryService entryService;

    private Entry entry1;
    private Entry entry2;

    @BeforeEach
    void setUp() {
        entry1 = Entry.builder()
                .id(1L)
                .title("title1")
                .text("text1")
                .author("author1")
                .creationDate(Instant.now())
                .build();
        entry2 = Entry.builder()
                .id(2L)
                .title("title2")
                .text("text2")
                .author("author2")
                .creationDate(Instant.now())
                .build();
    }

    @Test
    @Order(1)
    void shouldCreateAnEntry() {
        given(entryRepository.save(entry1))
                .willReturn(entry1);

        Entry createdEntry = entryService.createEntry(entry1);

        then(entryRepository).should().save(entry1);
        assertThat(entry1).usingRecursiveComparison().isEqualTo(createdEntry);
    }

    @Test
    @Order(2)
    void shouldGetAllEntries() {
        given(entryRepository.findAll())
                .willReturn(List.of(entry1, entry2));

        List<Entry> allEntries = entryService.getEntries();

        then(entryRepository).should().findAll();
        assertThat(allEntries).usingRecursiveComparison().isEqualTo(List.of(entry1, entry2));
    }

    @Test
    @Order(3)
    void shouldFindByTitle() {
        given(entryRepository.findByTitle("title1"))
                .willReturn(List.of(entry1));

        List<Entry> foundEntries = entryService.findByTitle("title1");

        then(entryRepository).should().findByTitle("title1");
        assertThat(foundEntries).containsExactly(entry1);
    }

    @Test
    @Order(4)
    void shouldFindByTitleContainingIgnoreCase() {
        given(entryRepository.findByTitleContainingIgnoreCase("title"))
                .willReturn(List.of(entry1, entry2));

        List<Entry> foundEntries = entryService.findByTitleContainingIgnoreCase("title");

        then(entryRepository).should().findByTitleContainingIgnoreCase("title");
        assertThat(foundEntries).containsExactly(entry1, entry2);
    }

    @Test
    @Order(5)
    void shouldFindByAuthor() {
        given(entryRepository.findByAuthor("author1"))
                .willReturn(List.of(entry1));

        List<Entry> foundEntries = entryService.findByAuthor("author1");

        then(entryRepository).should().findByAuthor("author1");
        assertThat(foundEntries).containsExactly(entry1);
    }

    @Test
    @Order(6)
    void shouldFindByAuthorContainsIgnoreCase() {
        given(entryRepository.findByAuthorContainsIgnoreCase("author"))
                .willReturn(List.of(entry1, entry2));

        List<Entry> foundEntries = entryService.findByAuthorContainsIgnoreCase("author");

        then(entryRepository).should().findByAuthorContainsIgnoreCase("author");
        assertThat(foundEntries).containsExactly(entry1, entry2);
    }
}
