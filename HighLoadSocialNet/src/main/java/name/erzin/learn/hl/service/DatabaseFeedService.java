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
public class FeedService implements DatabaseFeedService {
    @Autowired
    private PostRepo postRepo;
    ModelMapper modelMapper = new ModelMapper();

    @Override
    public List<Post> getPosts(BigDecimal offset, BigDecimal limit, String userId) {
        ArrayList<name.erzin.learn.hl.entity.Post> posts = postRepo.feed(userId, offset.intValue(), limit.intValue());
        List<Post> postsDTO = new ArrayList<>();

        for (name.erzin.learn.hl.entity.Post post : posts) {
            Post postDTO = modelMapper.map(post, Post.class);
            postsDTO.add(postDTO);
        }
        return postsDTO;
    }
}
