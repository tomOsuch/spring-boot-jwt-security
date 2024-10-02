package pl.tomaszosuch.security.book;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookDto {

    private String author;

    private String title;

    private String isbn;

    private LocalDateTime createDate;

    private LocalDateTime lastModified;

    private Integer createdBy;

    private Integer lastModifiedBy;

}
