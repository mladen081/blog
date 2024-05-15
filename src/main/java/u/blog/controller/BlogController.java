package u.blog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import u.blog.dto.CommentDto;
import u.blog.dto.PostDto;
import u.blog.service.PostService;

import java.util.List;

@Controller
public class BlogController {
    private final PostService postService;

    public BlogController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/")
    public String viewBlogPosts(Model model) {
        List<PostDto> postsResponse = postService.findAllPosts();
        model.addAttribute("postsResponse", postsResponse);
        return "blog/view_posts";
    }

    @GetMapping("/post/{postUrl}")
    public String showPost(@PathVariable("postUrl") String postUrl, Model model) {
        PostDto post = postService.findPostByUrl(postUrl);
        CommentDto commentDto = new CommentDto();
        model.addAttribute("post", post);
        model.addAttribute("comment", commentDto);
        return "blog/blog_post";
    }

    // http://localhost:8080/page/search?query=something
    @GetMapping("/page/search")
    public String searchPosts(@RequestParam(value = "query") String query, Model model) {
        List<PostDto> postsResponse = postService.searchPosts(query);
        model.addAttribute("postsResponse", postsResponse);
        return "blog/view_posts";
    }
}
