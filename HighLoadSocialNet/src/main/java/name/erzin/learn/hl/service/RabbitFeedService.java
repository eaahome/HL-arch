package name.erzin.learn.hl.service;

import lombok.SneakyThrows;
import name.erzin.learn.hl.entity.Friend;
import name.erzin.learn.hl.model.Post;
import name.erzin.learn.hl.repository.FriendRepo;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import java.util.ArrayList;
import java.util.List;

@Service
public class RabbitFeedService implements FeedService {
    @Autowired
    private FriendRepo friendsRepo;

    @Autowired
    RabbitTemplate rabbitTemplate;

    final ObjectWriter jsonWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();

    @Override
    public void onPostAdded(String authorUserId, Post post) {
        ArrayList<Friend> friends = friendsRepo.findUsersForFriend(authorUserId);
        if (friends == null) {
            return;
        }
        for (Friend friend : friends) {
            pushToRabbit(friend.getUserId(), post);
        }
    }

    @SneakyThrows
    private void pushToRabbit (String userId, Post post) {
        String postJson = jsonWriter.writeValueAsString(post);
        rabbitTemplate.convertAndSend (userId, postJson);
    }

    @Override
    public void onPostDeleted(String authorUserId, String postId) {
    }

    @Override
    public List<Post> getPosts(String userId, int offset, int limit) {
        throw new NotImplementedException();
    }
}
