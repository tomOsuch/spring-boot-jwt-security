package pl.tomaszosuch.security.book;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import pl.tomaszosuch.security.config.JwtService;

@SpringJUnitConfig
@WebMvcTest(BookController.class)
public class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    @MockBean
    private JwtService jwtService;

    @BeforeEach
    void setUp() {
        when(jwtService.isTokenValid("valid-token", null)).thenReturn(true);
    }

    @Test
    @WithMockUser //Używane do symulowania uwierzytelnionego użytkownika.
    public void testGetAllBooks() throws Exception {
        // Given
        List<BookDto> books = List.of(new BookDto("Author1", "Title1", "ISBN1", LocalDateTime.of(2021, 1, 1, 0, 0), LocalDateTime.of(2021, 1, 1, 0, 0), 1, 1));
        when(bookService.getAllBooks()).thenReturn(books);
        // When & Then
        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/v1/book/all")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer token"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].author").value("Author1"))
                .andExpect(jsonPath("$[0].title").value("Title1"))
                .andExpect(jsonPath("$[0].isbn").value("ISBN1"))
                .andExpect(jsonPath("$[0].createDate").value("2021-01-01T00:00:00"))
                .andExpect(jsonPath("$[0].lastModified").value("2021-01-01T00:00:00"))
                .andExpect(jsonPath("$[0].createdBy").value(1))
                .andExpect(jsonPath("$[0].lastModifiedBy").value(1));


    }

    @Test
    @WithMockUser
    public void testGetBookById() throws Exception {
        // Given
        BookDto book = new BookDto("Author1", "Title1", "ISBN1", LocalDateTime.of(2021, 1, 1, 0, 0), LocalDateTime.of(2021, 1, 1, 0, 0), 1, 1);
        when(bookService.getBookById(1L)).thenReturn(book);
        // When & Then
        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/v1/book/1")
                    .contentType(MediaType.APPLICATION_JSON)
                    .header("Authorization", "Bearer token"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.author").value("Author1"))
                .andExpect(jsonPath("$.title").value("Title1"))
                .andExpect(jsonPath("$.isbn").value("ISBN1"))
                .andExpect(jsonPath("$.createDate").value("2021-01-01T00:00:00"))
                .andExpect(jsonPath("$.lastModified").value("2021-01-01T00:00:00"))
                .andExpect(jsonPath("$.createdBy").value(1))
                .andExpect(jsonPath("$.lastModifiedBy").value(1));
    }

    @Test
    public void testCreateBook() throws Exception {
        // Given
        BookDto book = new BookDto("Author1", "Title1", "ISBN1", LocalDateTime.of(2021, 1, 1, 0, 0), LocalDateTime.of(2021, 1, 1, 0, 0), 1, 1);
        when(bookService.createBook(book)).thenReturn(book);
        // When & Then
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/book/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"author\":\"Author1\",\"title\":\"Title1\",\"isbn\":\"ISBN1\",\"createDate\":\"2021-01-01T00:00:00\",\"lastModified\":\"2021-01-01T00:00:00\",\"createdBy\":1,\"lastModifiedBy\":1}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.author").value("Author1"))
                .andExpect(jsonPath("$.title").value("Title1"))
                .andExpect(jsonPath("$.isbn").value("ISBN1"))
                .andExpect(jsonPath("$.createDate").value("2021-01-01T00:00:00"))
                .andExpect(jsonPath("$.lastModified").value("2021-01-01T00:00:00"))
                .andExpect(jsonPath("$.createdBy").value(1))
                .andExpect(jsonPath("$.lastModifiedBy").value(1));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testDeleteBook() throws Exception {
        // Given
        BookDto bookDto = new BookDto("Author1", "Title1", "ISBN1", LocalDateTime.of(2021, 1, 1, 0, 0), LocalDateTime.of(2021, 1, 1, 0, 0), 1, 1);
        when(bookService.getBookById(anyLong())).thenReturn(bookDto);
        // When & Then
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/api/v1/book/delete/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer valid-token")) // Use the valid token
                .andExpect(status().isOk());
    }
}
