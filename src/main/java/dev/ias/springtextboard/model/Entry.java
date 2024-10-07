package dev.ias.springtextboard.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;

@Entity
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
public class Entry {
    @Id
    @GeneratedValue
    private Long id;
    private String title;
    @Column(length = 1023)
    private String text;
    private String author;
    @CreationTimestamp
    private Instant creationDate;

    public Entry(String title, String text, String author, Instant creationDate) {
        this(null, title, text, author, creationDate);
    }
}
