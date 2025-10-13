package name.erzin.learn.hl.api;

import jakarta.servlet.http.HttpServletRequest;
import name.erzin.learn.hl.repository.FriendRepo;
import name.erzin.learn.hl.security.SecurityProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class FriendApiImplementation implements FriendApiDelegate {
    @Autowired
    private FriendRepo friendRepo;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private SecurityProvider securityProvider;

    @Override
    public ResponseEntity<Void> friendDeleteUserIdPut(String friendId) {
        String userId = securityProvider.extractLoginFromRequest(request);

        friendRepo.deleteFriend(userId, friendId);
        return new ResponseEntity<>(null, HttpStatusCode.valueOf(200));
    }

    @Override
    public ResponseEntity<Void> friendSetUserIdPut(String friendId) {
        String userId = securityProvider.extractLoginFromRequest(request);

        friendRepo.insertFriend(userId, friendId);
        return new ResponseEntity<>(null, HttpStatusCode.valueOf(200));
    }
}
