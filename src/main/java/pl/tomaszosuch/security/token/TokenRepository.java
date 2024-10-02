package pl.tomaszosuch.security.token;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.tomaszosuch.security.user.User;

import java.util.List;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Long> {
    Optional<Token> findByToken(String token);
    Optional<Token> findByUser(User user);
    void deleteByToken(String token);

    @Query(value = "SELECT t FROM Token t INNER JOIN User u ON t.user.id = u.id WHERE u.id =: id  AND (t.expired = false AND t.revoked = false)")
    List<Token> findAllValidTokenByUser(Long id);
}
