package dev.ias.springtextboard.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.ias.springtextboard.model.Entry;
import dev.ias.springtextboard.service.EntryService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.Instant;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EntryController.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class EntryControllerUnitTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private EntryService entryService;
    @Autowired
    private ObjectMapper objectMapper;
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
    void shouldCreateEntry() throws Exception {
        given(entryService.createEntry(entry1))
                .willReturn(entry1);

        ResultActions response = mockMvc.perform(post("/api/v1/entry")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(entry1)));

        response.andExpectAll(
                status().is2xxSuccessful(),
                jsonPath("$.title", is(entry1.getTitle())),
                jsonPath("$.text", is(entry1.getText())),
                jsonPath("$.author", is(entry1.getAuthor())),
                jsonPath("$.creationDate", is(entry1.getCreationDate().toString()))
        );
    }

    @Test
    @Order(2)
    void shouldGetAllEntries() throws Exception {
        List<Entry> entries = List.of(entry1, entry2);
        given(entryService.getEntries())
                .willReturn(entries);

        ResultActions response = mockMvc.perform(get("/api/v1/entry"));

        response.andExpectAll(
                status().is2xxSuccessful(),
                jsonPath("$.size()", is(entries.size())),

                jsonPath("$[0].id", is(entry1.getId().intValue())),
                jsonPath("$[0].title", is(entry1.getTitle())),
                jsonPath("$[0].text", is(entry1.getText())),
                jsonPath("$[0].author", is(entry1.getAuthor())),
                jsonPath("$[0].creationDate", is(entry1.getCreationDate().toString())),

                jsonPath("$[1].id", is(entry2.getId().intValue())),
                jsonPath("$[1].title", is(entry2.getTitle())),
                jsonPath("$[1].text", is(entry2.getText())),
                jsonPath("$[1].author", is(entry2.getAuthor())),
                jsonPath("$[1].creationDate", is(entry2.getCreationDate().toString()))
        );
    }
}
