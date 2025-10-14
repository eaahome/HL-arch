package name.erzin.learn.hl.api;

import jakarta.servlet.http.HttpServletRequest;
import name.erzin.learn.hl.model.*;
import name.erzin.learn.hl.repository.PostRepo;
import name.erzin.learn.hl.security.SecurityProvider;
import name.erzin.learn.hl.service.CachedFeedService;
import name.erzin.learn.hl.service.DatabaseFeedService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class PostApiImplementation implements PostApiDelegate {
    ModelMapper modelMapper = new ModelMapper();
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private SecurityProvider securityProvider;
    @Autowired
    private PostRepo postRepo;
    @Autowired
    CachedFeedService feedService;

    @Override
    public ResponseEntity<List<Post>> postFeedGet(BigDecimal offset, BigDecimal limit) {
        String userId = securityProvider.extractLoginFromRequest(request);

        List<Post> postsDTO = feedService.getPosts(userId, offset.intValue(), limit.intValue());

        return new ResponseEntity<>(postsDTO, HttpStatusCode.valueOf(200));
    }

    @Override
    public ResponseEntity<String> postCreatePost(PostCreatePostRequest postCreatePostRequest) {
        String authorId = securityProvider.extractLoginFromRequest(request);
        String postId = UUID.randomUUID().toString();

        Post post = new Post();
        post.setId(postId);
        post.setAuthorUserId(authorId);
        post.setText(postCreatePostRequest.getText());

        insertPost(post);
        feedService.onPostAdded(authorId, post);

        return new ResponseEntity<>(postId, HttpStatusCode.valueOf(200));
    }

    private void insertPost (Post post) {
        postRepo.insertPost(post.getId(), post.getAuthorUserId(), post.getText());
    }

    @Override
    public ResponseEntity<Void> postDeleteIdPut(String postId) {
        postRepo.deletePost(postId);

        String authorId = securityProvider.extractLoginFromRequest(request);
        feedService.onPostDeleted (authorId, postId);

        return new ResponseEntity<>(null, HttpStatusCode.valueOf(200));
    }

    @Override
    public ResponseEntity<Post> postGetIdGet(String id) {
        Optional<name.erzin.learn.hl.entity.Post> post = postRepo.findById(id);

        if (post.isPresent()) {
            // Just map Entity to DTO
            Post postDTO = modelMapper.map(post.get(), Post.class);
            return new ResponseEntity<>(postDTO, HttpStatusCode.valueOf(200));
        } else {
            return new ResponseEntity<>(null, HttpStatusCode.valueOf(404));
        }
    }
}
