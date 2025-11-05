package name.erzin.learn.hl.service;

import name.erzin.learn.hl.model.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Primary
public class CombinedFeedService implements FeedService{
    @Autowired
    DatabaseFeedService databaseFeedService;

    @Autowired
    RabbitFeedService rabbitFeedService;

    @Override
    public void onPostAdded(String authorUserId, Post post) {
        databaseFeedService.onPostAdded(authorUserId, post);
        rabbitFeedService.onPostAdded(authorUserId, post);
    }

    @Override
    public void onPostDeleted(String authorUserId, String postId) {
        databaseFeedService.onPostDeleted(authorUserId, postId);
        rabbitFeedService.onPostDeleted(authorUserId, postId);
    }

    @Override
    public List<Post> getPosts(String userId, int offset, int limit) {
        return databaseFeedService.getPosts(userId, offset, limit);
    }
}
