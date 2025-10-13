package name.erzin.learn.hl.feed;

import name.erzin.learn.hl.model.Post;

import java.util.List;

public class CachedFeed {
    /**
     * Adds new post to all feeds for all friends of author
     * @param authorUserId
     * @param post
     */
    void onPostAdded (String authorUserId, Post post) {

    }

    /**
     * Delete post from all feeds for all friends of author
     * @param postId
     */
    void onPostDeleted (String postId) {

    }

    /**
     * Creates new feed from given list for given user
     * @param userId
     * @param posts
     */
    void initFromList (String userId, List<Post> posts) {

    }

    /**
     * Return cached feed for given user or null
     * @param userId
     * @return cached feed for given user or null
     */
    List<Post> get (String userId) {
        return null;
    }
}
