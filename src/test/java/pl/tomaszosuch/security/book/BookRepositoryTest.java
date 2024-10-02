package pl.tomaszosuch.security.book;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class BookRepositoryTest {

    @Mock
    private BookRepository bookRepository;

    public BookRepositoryTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testBookFindAll() {
        // Given
        List<Book> books = Stream.of(new Book(1L, "Author1", "Title1", "ISBN1", LocalDateTime.of(2021, 1, 1, 0, 0), LocalDateTime.of(2021, 1, 1, 0, 0), 1, 1),
                new Book(2L, "Author2", "Title2", "ISBN2", LocalDateTime.of(2021, 1, 1, 0, 0), LocalDateTime.of(2021, 1, 1, 0, 0), 1, 1),
                new Book(3L, "Author3", "Title3", "ISBN3", LocalDateTime.of(2021, 1, 1, 0, 0), LocalDateTime.of(2021, 1, 1, 0, 0), 1, 1))
                .collect(Collectors.toList());

        when(bookRepository.findAll()).thenReturn(books);
        // When
        List<Book> booksFromRepository = bookRepository.findAll();
        // Then
        Assertions.assertEquals(books.size(), booksFromRepository.size());
    }

    @Test
    public void testBookFindById() {
        // Given
        Book book = new Book(1L, "Author1", "Title1", "ISBN1", LocalDateTime.of(2021, 1, 1, 0, 0), LocalDateTime.of(2021, 1, 1, 0, 0), 1, 1);
        when(bookRepository.findById(1L)).thenReturn(java.util.Optional.of(book));
        // When
        Book bookFromRepository = bookRepository.findById(1L).get();
        // Then
        Assertions.assertEquals(book.getId(), bookFromRepository.getId());
    }

    @Test
    public void testBookSave() {
        // Given
        Book book = new Book(1L, "Author1", "Title1", "ISBN1", LocalDateTime.of(2021, 1, 1, 0, 0), LocalDateTime.of(2021, 1, 1, 0, 0), 1, 1);
        when(bookRepository.save(book)).thenReturn(book);
        // When
        Book bookFromRepository = bookRepository.save(book);
        // Then
        Assertions.assertEquals(book.getId(), bookFromRepository.getId());
        Assertions.assertEquals(book.getAuthor(), bookFromRepository.getAuthor());
        Assertions.assertEquals(book.getTitle(), bookFromRepository.getTitle());
        Assertions.assertEquals(book.getIsbn(), bookFromRepository.getIsbn());
    }

    @Test
    public void testBookDelete() {
        // Given
        Book book = new Book(1L, "Author1", "Title1", "ISBN1", LocalDateTime.of(2021, 1, 1, 0, 0), LocalDateTime.of(2021, 1, 1, 0, 0), 1, 1);
        bookRepository.deleteById(1L);
        // When
        bookRepository.deleteById(1L);
        // Then
        Assertions.assertEquals(0, bookRepository.findAll().size());
    }
}
