package name.erzin.learn.hl.service;

import lombok.extern.java.Log;
import name.erzin.learn.hl.entity.Friend;
import name.erzin.learn.hl.model.Post;
import name.erzin.learn.hl.repository.FriendRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Log
@Service
public class CachedFeedService {
    private HashMap<String, CachedUserFeed> userFeeds = new HashMap();

    @Autowired
    private DatabaseFeedService databaseFeedService;
    @Autowired
    private FriendRepo friendsRepo;

    /**
     * Adds new post to all feeds for all friends of author
     * @param authorUserId
     * @param post
     */
    public void onPostAdded (String authorUserId, Post post) {
        ArrayList<Friend> friends = friendsRepo.findUsersForFriend(authorUserId);
        if (friends == null) {
            return;
        }
        for (Friend friend : friends) {
            addPostToFeed(friend.getUserId(), post);
        }
    }

    /**
     * Delete post from all feeds for all friends of author
     * @param postId
     */
    public void onPostDeleted (String authorUserId, String postId) {
        ArrayList<Friend> friends = friendsRepo.findUsersForFriend(authorUserId);
        if (friends == null) {
            return;
        }
        for (Friend friend : friends) {
            deletePostFromFeed(friend.getUserId(), postId);
        }
    }

    private void addPostToFeed (String userId, Post post) {
        if (userFeeds.containsKey(userId)) {
            log.info("Add post " + post.getId() + " to feed for user: " + userId);
            userFeeds.get(userId).addPost(post);
        } else {
            // We not need to add new post to cache when it not have requested - we will initialize it from DB later
            log.info("Do not add post " + post.getId() + " to feed for user: " + userId + " - it has not cache yet");

            //userFeeds.put(userId, new CachedUserFeed(post)); // Need init from DB, not only from last Post
        }
    }

    private void deletePostFromFeed (String userId, String postId) {
        // Exit when no cached feed
        if (!userFeeds.containsKey(userId)) {
            return;
        }

        log.info("Delete post " + postId + " from feed for user: " + userId);
        userFeeds.get(userId).deletePost(postId);
    }

    /**
     * Creates new feed from given list for given user
     * @param userId
     * @param posts
     */
    private void initFromList (String userId, List<Post> posts) {
        CachedUserFeed userFeed = new CachedUserFeed(posts);
        userFeeds.put (userId, userFeed);
    }

    /**
     * Return cached feed for given user or null
     * @param userId
     * @return cached feed for given user or null
     */
    private List<Post> getCachedPosts(String userId) {
        if (userFeeds.containsKey(userId)) {
            log.info("Found cached feed for user: " + userId);
            return userFeeds.get(userId).getPosts();
        }

        log.info("NOT found cached feed for user: " + userId);
        return null;
    }

    public List<Post> getPosts(String userId, int offset, int limit) {
        List<Post> posts = getCachedPosts(userId);
        if (posts == null) {
            log.info("Loading posts from DB for user: " + userId);
            posts = databaseFeedService.getPosts(userId, 0, CachedUserFeed.FEED_MAX_SIZE);
            initFromList(userId, posts);
        }

        return posts.subList (offset, offset + limit);
    }
}
