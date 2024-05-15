package u.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import u.blog.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
