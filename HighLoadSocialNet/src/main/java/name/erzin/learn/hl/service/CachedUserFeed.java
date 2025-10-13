package name.erzin.learn.hl.service;

import lombok.Getter;
import name.erzin.learn.hl.model.Post;

import java.util.ArrayList;
import java.util.List;

@Getter
public class CachedUserFeed {
    private List<Post> posts;
    private final int FEED_MAX_SIZE = 1000;

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
            posts.remove(posts.size() - 1); // Remove last
        }
    }

    public void deletePost(String postId) {
        int i = 0;
        for (Post post : posts) {
            if (post.getId().equals(postId)) {
                posts.remove(i);
                return;
            }
            i++;
        }
    }
}
