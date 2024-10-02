package pl.tomaszosuch.security.book;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class BookServiceTest {

    @InjectMocks
    private BookService bookService;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private BookMapper bookMapper;

    @Test
    public void testGetAllBooks() {
        // Given
        Book book1 = new Book(1L, "Author1", "Title1", "ISBN1", LocalDateTime.of(2021, 1, 1, 0, 0), LocalDateTime.of(2021, 1, 1, 0, 0), 1, 1);
        Book book2 = new Book(2L, "Author2", "Title2", "ISBN2", LocalDateTime.of(2021, 1, 1, 0, 0), LocalDateTime.of(2021, 1, 1, 0, 0), 1, 1);
        BookDto bookDto1 = new BookDto("Author1", "Title1", "ISBN1", LocalDateTime.of(2021, 1, 1, 0, 0), LocalDateTime.of(2021, 1, 1, 0, 0), 1, 1);
        BookDto bookDto2 = new BookDto("Author2", "Title2", "ISBN2", LocalDateTime.of(2021, 1, 1, 0, 0), LocalDateTime.of(2021, 1, 1, 0, 0), 1, 1);
        List<BookDto> bookDtos = List.of(bookDto1, bookDto2);
        List<Book> books = List.of(book1, book2);
        when(bookRepository.findAll()).thenReturn(books);      
        when(bookMapper.booksToBooksDTO(books)).thenReturn(bookDtos); 
        // When
        List<BookDto> returnBooks = bookService.getAllBooks();
        // Then
        assertEquals(2, returnBooks.size());
    }

    @Test
    public void testGetBookById() {
        //Given
        Long id = 1L;
        Book book = new Book(id, "Author1", "Title1", "ISBN1", LocalDateTime.of(2021, 1, 1, 0, 0), LocalDateTime.of(2021, 1, 1, 0, 0), 1, 1);
        BookDto bookDto = new BookDto("Author1", "Title1", "ISBN1", LocalDateTime.of(2021, 1, 1, 0, 0), LocalDateTime.of(2021, 1, 1, 0, 0), 1, 1);
        when(bookRepository.findById(id)).thenReturn(Optional.of(book));
        when(bookMapper.bookToBookDTO(book)).thenReturn(bookDto);
        //When
        BookDto returnBook = bookService.getBookById(id);
        //Then
        assertEquals(bookDto, returnBook);
    }

    @Test
    public void testCreateBook() {
        //Given
        Book book = new Book(1L, "Author1", "Title1", "ISBN1", LocalDateTime.of(2021, 1, 1, 0, 0), LocalDateTime.of(2021, 1, 1, 0, 0), 1, 1);
        BookDto bookDto = new BookDto("Author1", "Title1", "ISBN1", LocalDateTime.of(2021, 1, 1, 0, 0), LocalDateTime.of(2021, 1, 1, 0, 0), 1, 1);
        when(bookMapper.bookDTOToBook(bookDto)).thenReturn(book);
        when(bookRepository.save(book)).thenReturn(book);
        when(bookMapper.bookToBookDTO(book)).thenReturn(bookDto);
        //When
        BookDto returnBook = bookService.createBook(bookDto);
        //Then
        assertEquals(bookDto, returnBook);
    }

    @Test
    public void testUpdateBook() {
        // Given
        Long id = 1L;
        Book book = new Book(id, "Author1", "Title1", "ISBN1", LocalDateTime.of(2021, 1, 1, 0, 0), LocalDateTime.of(2021, 1, 1, 0, 0), 1, 1);
        BookDto bookDto = new BookDto("Author2", "Title2", "ISBN2", LocalDateTime.of(2021, 1, 1, 0, 0), LocalDateTime.of(2021, 1, 1, 0, 0), 1, 1);
        when(bookRepository.findById(id)).thenReturn(Optional.of(book));
        when(bookMapper.bookDTOToBook(bookDto)).thenReturn(book);
        when(bookRepository.save(book)).thenReturn(book);
        when(bookMapper.bookToBookDTO(book)).thenReturn(bookDto);
        // When
        BookDto returnBook = bookService.updateBook(id, bookDto);
        // Then
        assertEquals(bookDto, returnBook);
    }

    @Test
    public void testDeleteBook() {
        // Given
        Long id = 1L;
        Book book = new Book(id, "Author1", "Title1", "ISBN1", LocalDateTime.of(2021, 1, 1, 0, 0), LocalDateTime.of(2021, 1, 1, 0, 0), 1, 1);
        BookDto bookDto = new BookDto("Author1", "Title1", "ISBN1", LocalDateTime.of(2021, 1, 1, 0, 0), LocalDateTime.of(2021, 1, 1, 0, 0), 1, 1);
        when(bookRepository.findById(id)).thenReturn(Optional.of(book));
        doNothing().when(bookRepository).deleteById(id);
        // When
        bookService.deleteBook(id);
        // Then
        verify(bookRepository, times(1)).deleteById(id);
        assertEquals(0, bookRepository.findAll().size());
    }

}
