package pl.tomaszosuch.security.book;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    public BookDto createBook(BookDto bookDto) {
        Book book = bookMapper.bookDTOToBook(bookDto);
        Book savedBook = bookRepository.save(book);
        return bookMapper.bookToBookDTO(savedBook);
    }

    public BookDto getBookById(Long id) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new BookNotFoundException("Book not found"));
        return bookMapper.bookToBookDTO(book);
    }

    public List<BookDto> getAllBooks() {
        return Optional.of(bookRepository.findAll())
                .map(bookMapper::booksToBooksDTO)
                .orElseThrow(() -> new IllegalArgumentException("Books not found"));
    }

    public BookDto updateBook(Long id, BookDto bookDto) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new BookNotFoundException("Book not found"));
        book.setAuthor(bookDto.getAuthor());
        book.setTitle(bookDto.getTitle());
        book.setIsbn(bookDto.getIsbn());
        Book updatedBook = bookRepository.save(book);
        return bookMapper.bookToBookDTO(updatedBook);
    }

    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }
}
