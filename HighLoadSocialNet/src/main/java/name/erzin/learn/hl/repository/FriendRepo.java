package name.erzin.learn.hl.repository;

import jakarta.transaction.Transactional;
import name.erzin.learn.hl.entity.Friend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface FriendRepo  extends JpaRepository<Friend, String> {
    @Transactional
    @Modifying
    @Query(value = "INSERT INTO \"friend\" (user_id, friend_id) VALUES (:userId, :friendId)",
            nativeQuery = true)
    void insertFriend (String userId, String friendId);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM \"friend\" where user_id = :userId and friend_id = :friendId",
            nativeQuery = true)
    void deleteFriend (String userId, String friendId);
}
