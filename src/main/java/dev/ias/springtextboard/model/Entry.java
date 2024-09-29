package dev.ias.springtextboard.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Entity
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
public class Entry {
    @Id
    @GeneratedValue
    private Long id;
    private String title;
    private String text;
    private String author;
    private Instant creationDate;

    public Entry(String title, String text, String author, Instant creationDate) {
        this(null, title, text, author, creationDate);
    }
}
