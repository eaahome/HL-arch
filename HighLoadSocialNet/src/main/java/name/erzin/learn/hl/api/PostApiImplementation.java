package name.erzin.learn.hl.api;

import jakarta.servlet.http.HttpServletRequest;
import name.erzin.learn.hl.model.*;
import name.erzin.learn.hl.repository.PostRepo;
import name.erzin.learn.hl.repository.UserRepo;
import name.erzin.learn.hl.security.SecurityProvider;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.NativeWebRequest;

import java.math.BigDecimal;
import java.util.ArrayList;
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

    @Override
    public ResponseEntity<List<Post>> postFeedGet(BigDecimal offset, BigDecimal limit) {
        String userId = securityProvider.extractLoginFromRequest(request);

        ArrayList<name.erzin.learn.hl.entity.Post> posts = postRepo.feed(userId, offset.intValue(), limit.intValue());
        List<Post> postsDTO = new ArrayList<>();

        for (name.erzin.learn.hl.entity.Post post : posts) {
            Post postDTO = modelMapper.map(post, Post.class);
            postsDTO.add(postDTO);
        }

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

        return new ResponseEntity<>(postId, HttpStatusCode.valueOf(200));
    }

    private void insertPost (Post post) {
        postRepo.insertPost(post.getId(), post.getAuthorUserId(), post.getText());
    }

    @Override
    public ResponseEntity<Void> postDeleteIdPut(String id) {
        postRepo.deletePost(id);
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
