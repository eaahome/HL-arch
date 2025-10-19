package name.erzin.learn.hl.repository;

import jakarta.transaction.Transactional;
import name.erzin.learn.hl.entity.DialogMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.ArrayList;

public interface DialogRepo extends JpaRepository<DialogMessage, String> {
    @Query(value = "SELECT * FROM dialog "
            + " where (src_user_id = :srcUserId and dst_user_id = :dstUserId) "
            + " or    (src_user_id = :dstUserId and dst_user_id = :srcUserId) "
            + " order by created_at desc ",
            nativeQuery = true)
    ArrayList<DialogMessage> list (String srcUserId, String dstUserId);

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO dialog (src_user_id, dst_user_id, \"text\", dist_key) "
            + " VALUES (:srcUserId, :dstUserId, :text, :distKey)",
            nativeQuery = true)
    void insertDialogMessage (String srcUserId, String dstUserId, String text, String distKey);
}
