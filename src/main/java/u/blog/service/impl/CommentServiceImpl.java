package u.blog.service.impl;

import org.springframework.stereotype.Service;
import u.blog.dto.CommentDto;
import u.blog.entity.Comment;
import u.blog.entity.Post;
import u.blog.mapper.CommentMapper;
import u.blog.repository.CommentRepository;
import u.blog.repository.PostRepository;
import u.blog.service.CommentService;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    public CommentServiceImpl(CommentRepository commentRepository, PostRepository postRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
    }

    @Override
    public void createComment(String postUrl, CommentDto commentDto) {

        Post post = postRepository.findByUrl(postUrl).get();
        Comment comment = CommentMapper.mapToComment(commentDto);
        comment.setPost(post);
        commentRepository.save(comment);
    }
}
