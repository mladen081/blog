package u.blog.controller;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import u.blog.dto.CommentDto;
import u.blog.dto.PostDto;
import u.blog.service.CommentService;
import u.blog.service.PostService;
import u.blog.util.ROLE;
import u.blog.util.SecurityUtils;

import java.util.List;

@Controller
public class PostController {
    private final PostService postService;
    private final CommentService commentService;

    public PostController(PostService postService, CommentService commentService) {
        this.postService = postService;
        this.commentService = commentService;
    }

    @GetMapping("/admin/posts")
    public String posts(Model model) {
        String role = SecurityUtils.getRole();
        List<PostDto> posts = null;
        if (ROLE.ROLE_ADMIN.name().equals(role)) {
            posts = postService.findAllPosts();
        } else {
            posts = postService.findPostsByUser();
        }
        model.addAttribute("posts", posts);
        return "admin/posts";
    }

    @GetMapping("/admin/posts/comments")
    public String postComments(Model model) {
        String role = SecurityUtils.getRole();
        List<CommentDto> comments = null;
        if (ROLE.ROLE_ADMIN.name().equals(role)) {
            comments = commentService.findAllComments();
        } else {
            comments = commentService.findCommentsByPost();
        }
        model.addAttribute("comments", comments);
        return "admin/comments";
    }

    @GetMapping("/admin/posts/comments/{commentId}")
    public String deleteComments(@PathVariable("commentId") Long commentId, Model model) {
        commentService.deleteComment(commentId);
        return "redirect:/admin/posts/comments";
    }

    @GetMapping("/admin/posts/newpost")
    public String newPostForm(Model model) {
        PostDto postDto = new PostDto();
        model.addAttribute("post", postDto);
        return "admin/create_post";
    }

    @PostMapping("/admin/posts")
    public String createPost(@Valid @ModelAttribute("post") PostDto postDto,
                             BindingResult bindingResult,
                             Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("post", postDto);
            return  "admin/create_post";
        }
        postDto.setUrl(getUrl(postDto.getTitle()));
        postService.createPost(postDto);
        return "redirect:/admin/posts";
    }

    @GetMapping("/admin/posts/{postId}/edit")
    public String editPost(@PathVariable("postId") Long postId, Model model) {
        PostDto postDto = postService.findPostById(postId);
        model.addAttribute("post", postDto);
        return "admin/edit_post";
    }

    @PostMapping("/admin/posts/{postId}")
    public String updatePost(@PathVariable("postId") Long postId,
                             @Valid @ModelAttribute("post") PostDto post,
                             BindingResult bindingResult,
                             Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("post", post);
            return "admin/edit_post";
        }
        post.setId(postId);
        postService.updatePost(post);
        return "redirect:/admin/posts";
    }

    @GetMapping("/admin/posts/{postId}/delete")
    public String deletePost(@PathVariable("postId") Long postId) {
        postService.deletePost(postId);
        return "redirect:/admin/posts";
    }


    @GetMapping("/admin/posts/{postUrl}/view")
    public String viewPost(@PathVariable("postUrl") String postUrl, Model model) {
        PostDto postDto = postService.findPostByUrl(postUrl);
        model.addAttribute("post", postDto);
        return "admin/view_post";
    }

    // localhost:8080/admin/posts/search?query=something
    @GetMapping("/admin/posts/search")
    public String searchPosts(@RequestParam(value = "query") String query, Model model) {
        List<PostDto> posts = postService.searchPosts(query);
        model.addAttribute("posts", posts);
        return "admin/posts";
    }

    private static String getUrl(String postTitle) {
        // oops-concepts-explained-in-java
        String title = postTitle.trim().toLowerCase();
        String url = title.replaceAll("\\s+", "-");
        url = url.replaceAll("[^A-Za-z0-9]", "-");
        return url;
    }
}
