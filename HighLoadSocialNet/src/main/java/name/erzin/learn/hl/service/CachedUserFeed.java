package name.erzin.learn.hl.service;

import lombok.Getter;
import lombok.extern.java.Log;
import name.erzin.learn.hl.model.Post;

import java.util.ArrayList;
import java.util.List;

@Log
@Getter
public class CachedUserFeed {
    private List<Post> posts;
    public static final int FEED_MAX_SIZE = 1000;

    public CachedUserFeed(Post post) {
        posts = new ArrayList<>();
        posts.add(post);
    }

    public CachedUserFeed(List<Post> posts) {
        this.posts = posts;
    }

    public void addPost (Post post) {
        posts.add(0, post); // Latest post must be first in list
        if (posts.size() > FEED_MAX_SIZE) {
            log.info ("Cache too large, remove last element");
            posts.remove(posts.size() - 1); // Remove last
        }
        log.info("Cache size after adding: " + posts.size());
    }

    public void deletePost(String postId) {
        int i = 0;
        for (Post post : posts) {
            if (post.getId().equals(postId)) {
                posts.remove(i);
                log.info("Remove Post " + postId + " from cache, cache size: " + posts.size());
                return;
            }
            i++;
        }
    }
}
