package u.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import u.blog.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
}
