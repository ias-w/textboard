package dev.ias.springtextboard.model.requestlog;

import dev.ias.springtextboard.model.Entry;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString(callSuper = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@SuperBuilder
public class CreationRequestLog extends RequestLog {
    @OneToOne
    private Entry entry;
}
