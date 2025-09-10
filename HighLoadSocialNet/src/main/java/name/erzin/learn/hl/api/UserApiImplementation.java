package name.erzin.learn.hl.api;

import name.erzin.learn.hl.model.User;
import name.erzin.learn.hl.model.UserRegisterPost200Response;
import name.erzin.learn.hl.model.UserRegisterPostRequest;
import name.erzin.learn.hl.repository.UserRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class UserApiImplementation implements UserApiDelegate {
    @Autowired
    UserRepo userRepo;

    @Override
    public ResponseEntity<UserRegisterPost200Response> userRegisterPost(UserRegisterPostRequest userRegisterPostRequest) {
        return UserApiDelegate.super.userRegisterPost(userRegisterPostRequest);
    }

    @Override
    public ResponseEntity<User> userGetIdGet(String id) {
        name.erzin.learn.hl.entity.User user = userRepo.findById(id);

        if (user != null) {
            // Just map Entity to DTO
            ModelMapper mm = new ModelMapper();
            User userDTO = mm.map(user, User.class);

            return new ResponseEntity<>(userDTO, HttpStatusCode.valueOf(200));
        } else {
            return new ResponseEntity<>(null, HttpStatusCode.valueOf(404));
        }
    }
}
