package pl.tomaszosuch.security.book;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class BookMapperTest {

    private BookMapper bookMapper;

    @BeforeEach
    public void setUp() {
        bookMapper = Mappers.getMapper(BookMapper.class);
    }

    @Test
    public void testMapBookToBookDTO() {
        // Given
        Book book = new Book("Author1", "Title1", "ISBN1", LocalDateTime.of(2021, 1, 1, 0, 0), LocalDateTime.of(2021, 1, 1, 0, 0), 1, 1);
        // When
        BookDto bookFromMapper = bookMapper.bookToBookDTO(book);
        // Then
        assertEquals(bookFromMapper.getAuthor(), book.getAuthor());
        assertEquals(bookFromMapper.getTitle(), book.getTitle());
        assertEquals(bookFromMapper.getIsbn(), book.getIsbn());
    }

    @Test
    public void testMapBookDtoToBook() {
        // Given
        BookDto bookDto = new BookDto("Author1", "Title1", "ISBN1", LocalDateTime.of(2021, 1, 1, 0, 0), LocalDateTime.of(2021, 1, 1, 0, 0), 1, 1);
        // When
        Book bookFromMapper = bookMapper.bookDTOToBook(bookDto);
        // Then
        assertEquals(bookFromMapper.getAuthor(), bookDto.getAuthor());
        assertEquals(bookFromMapper.getTitle(), bookDto.getTitle());
        assertEquals(bookFromMapper.getIsbn(), bookDto.getIsbn());

    }

    @Test
    public void testMapBookListToBookDtoList() {
        // Given
        Book book1 = new Book("Author1", "Title1", "ISBN1", LocalDateTime.of(2021, 1, 1, 0, 0), LocalDateTime.of(2021, 1, 1, 0, 0), 1, 1);
        Book book2 = new Book("Author2", "Title2", "ISBN2", LocalDateTime.of(2021, 1, 1, 0, 0), LocalDateTime.of(2021, 1, 1, 0, 0), 1, 1);
        List<Book> bookList = List.of(book1, book2);
        // When
        List<BookDto> bookDtoList = bookMapper.booksToBooksDTO(bookList);
        // Then
        assertEquals(bookDtoList.size(), 2);
        assertEquals(bookDtoList.get(0).getAuthor(), bookList.get(0).getAuthor());
        assertEquals(bookDtoList.get(1).getAuthor(), bookList.get(1).getAuthor());
    }

    @Test
    public void testMapBookDtoListToBookList() {
        // Given
        BookDto bookDto1 = new BookDto("Author1", "Title1", "ISBN1", LocalDateTime.of(2021, 1, 1, 0, 0), LocalDateTime.of(2021, 1, 1, 0, 0), 1, 1);
        BookDto bookDto2 = new BookDto("Author2", "Title2", "ISBN2", LocalDateTime.of(2021, 1, 1, 0, 0), LocalDateTime.of(2021, 1, 1, 0, 0), 1, 1);
        List<BookDto> bookDtoList = List.of(bookDto1, bookDto2);
        // When
        List<Book> bookList = bookMapper.booksDTOToBooks(bookDtoList);
        // Then
        assertEquals(bookList.size(), 2);
        assertEquals(bookList.get(0).getAuthor(), bookDtoList.get(0).getAuthor());
        assertEquals(bookList.get(1).getAuthor(), bookDtoList.get(1).getAuthor());
    }
}
