package pl.tomaszosuch.security.book;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BookMapper {

    BookMapper INSTANCE = Mappers.getMapper(BookMapper.class);

    BookDto bookToBookDTO(Book book);

    Book bookDTOToBook(BookDto bookDto);

    List<BookDto> booksToBooksDTO(List<Book> books);

    List<Book> booksDTOToBooks(List<BookDto> bookDtos);
}
