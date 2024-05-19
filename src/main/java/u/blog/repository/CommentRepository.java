package u.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import u.blog.entity.Comment;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    @Query(value = "select c.* from comments c inner join posts p on c.post_id = p.id where p.created_by =:userId", nativeQuery = true)
    List<Comment> findCommentsByPost(Long userId);
}
