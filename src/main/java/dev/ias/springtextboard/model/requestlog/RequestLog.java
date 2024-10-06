package dev.ias.springtextboard.model.requestlog;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Getter
@Setter
@NoArgsConstructor
@ToString
@SuperBuilder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class RequestLog {
    @Id
    @GeneratedValue
    private Long id;
    private String remoteAddress;
    private String userAgent;
    private String method;
    private String path;
    @CreationTimestamp
    private Instant createdAt;
}
