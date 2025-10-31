package name.erzin.learn.hl.service;

import name.erzin.learn.hl.model.Post;

import java.util.List;

public interface FeedService {
    void onPostAdded(String authorUserId, Post post);

    void onPostDeleted(String authorUserId, String postId);

    List<Post> getPosts(String userId, int offset, int limit);
}
