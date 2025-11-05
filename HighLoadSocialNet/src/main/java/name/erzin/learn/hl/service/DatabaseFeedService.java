package name.erzin.learn.hl.service;

import name.erzin.learn.hl.model.Post;
import name.erzin.learn.hl.repository.PostRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class DatabaseFeedService implements FeedService {
    @Autowired
    private PostRepo postRepo;
    ModelMapper modelMapper = new ModelMapper();

    @Override
    public void onPostAdded(String authorUserId, Post post) {

    }

    @Override
    public void onPostDeleted(String authorUserId, String postId) {

    }

    public List<Post> getPosts(String userId, int offset, int limit) {
        ArrayList<name.erzin.learn.hl.entity.Post> posts = postRepo.feed(userId, offset, limit);
        List<Post> postsDTO = new ArrayList<>();

        for (name.erzin.learn.hl.entity.Post post : posts) {
            Post postDTO = modelMapper.map(post, Post.class);
            postsDTO.add(postDTO);
        }
        return postsDTO;
    }
}
