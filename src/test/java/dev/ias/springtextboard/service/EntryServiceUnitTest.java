package dev.ias.springtextboard.service;

import dev.ias.springtextboard.model.Entry;
import dev.ias.springtextboard.repository.EntryRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.Iterator;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

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

        assertThat(createdEntry).isNotNull();
        assertThat(entry1.getTitle()).isEqualTo(createdEntry.getTitle());
        assertThat(entry1.getText()).isEqualTo(createdEntry.getText());
        assertThat(entry1.getAuthor()).isEqualTo(createdEntry.getAuthor());
        assertThat(entry1.getCreationDate()).isEqualTo(createdEntry.getCreationDate());
    }

    @Test
    @Order(2)
    void shouldGetAllEntries() {
        given(entryRepository.findAll())
                .willReturn(List.of(entry1, entry2));

        Iterable<Entry> allEntries = entryService.getEntries();

        assertThat(allEntries).isNotNull();
        Iterator<Entry> iterator = allEntries.iterator();
        assertThat(iterator.hasNext()).isTrue();
        assertThat(iterator.next()).isEqualTo(entry1);
        assertThat(iterator.hasNext()).isTrue();
        assertThat(iterator.next()).isEqualTo(entry2);
    }
}
