package name.erzin.learn.hl.repository;

import jakarta.transaction.Transactional;
import name.erzin.learn.hl.entity.Post;
import name.erzin.learn.hl.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.ArrayList;
import java.util.Optional;

public interface PostRepo extends JpaRepository<Post, String> {
    @Query(value = "SELECT * FROM \"post\" where id = :id", nativeQuery = true)
    Optional<Post> findById (String id);

    @Query(value = "SELECT post.* FROM post "
            + " join friend on post.author_user_id = friend.friend_id "
            + " where friend.user_id = :userId "
            + " order by post.created_at desc "
            + " limit :limit offset :offset",
            nativeQuery = true)
    ArrayList<Post> feed (String userId, int offset, int limit);

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO \"post\" (id, author_user_id, \"text\") VALUES (:id, :authorUserId, :text)",
            nativeQuery = true)
    void insertPost (String id, String authorUserId, String text);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM \"post\" where id = :id",
            nativeQuery = true)
    void deletePost (String id);
}
