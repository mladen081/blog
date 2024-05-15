package u.blog.service;

import u.blog.dto.CommentDto;

public interface CommentService {
    void createComment(String postUrl, CommentDto commentDto);
}
