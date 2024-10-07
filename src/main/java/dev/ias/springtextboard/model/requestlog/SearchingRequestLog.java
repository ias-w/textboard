package dev.ias.springtextboard.model.requestlog;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString(callSuper = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@SuperBuilder
public class SearchingRequestLog extends RequestLog {
    @Column(length = 1023)
    private String query;
}
