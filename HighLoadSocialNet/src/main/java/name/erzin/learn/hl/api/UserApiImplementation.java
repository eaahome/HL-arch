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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class UserApiImplementation implements UserApiDelegate {
    ModelMapper modelMapper = new ModelMapper();
    @Autowired
    UserRepo userRepo;

    @Override
    public ResponseEntity<UserRegisterPost200Response> userRegisterPost(UserRegisterPostRequest userRegisterPostRequest) {
        // Convert request to Entity
        name.erzin.learn.hl.entity.User user = modelMapper.map(userRegisterPostRequest,
                name.erzin.learn.hl.entity.User.class);

        user.setId(UUID.randomUUID().toString());
        insertUserNative(user);

        UserRegisterPost200Response response = new UserRegisterPost200Response();
        response.setUserId(user.getId());
        return new ResponseEntity<>(response, HttpStatusCode.valueOf(200));

        /*
        JUST HEADER EXPERIMENT

        // Set auth header
        // create a new headers object.
        HttpHeaders headers = new HttpHeaders();
        //add all existing headers in new headers
        headers.addAll(responseEntity.getHeaders());
        //add the new required header
        headers.add("X-TOKEN", "TEST TOKEN HERE");

        responseEntity = ResponseEntity
                .status(HttpStatusCode.valueOf(200))
                .headers(headers)
                .body(response);
         */

    }

    /**
     * Insert user into DB using Hibernate entity
     */
    @Deprecated
    private void insertUser (name.erzin.learn.hl.entity.User user) {
        // This method at first do SELECT and then INSERT
        userRepo.save(user);
    }

    /**
     * Insert user into DB using native SQL
    */
    private void insertUserNative (name.erzin.learn.hl.entity.User user) {
        userRepo.insertUser(user.getId(), user.getFirstName(), user.getSecondName(), user.getBirthdate().toString(),
                user.getSex(), user.getBiography(), user.getCity());
    }

    @Override
    public ResponseEntity<User> userGetIdGet(String id) {
        Optional<name.erzin.learn.hl.entity.User> user = userRepo.findById(id);

        if (user.isPresent()) {
            // Just map Entity to DTO
            User userDTO = modelMapper.map(user.get(), User.class);
            return new ResponseEntity<>(userDTO, HttpStatusCode.valueOf(200));
        } else {
            return new ResponseEntity<>(null, HttpStatusCode.valueOf(404));
        }
    }

    @Override
    public ResponseEntity<List<User>> userSearchGet(String firstName, String lastName) {
        ArrayList<name.erzin.learn.hl.entity.User> users = userRepo.findByFirstNameAndSecondName(firstName, lastName);
        List<User> usersDTO = new ArrayList<>();

        for (name.erzin.learn.hl.entity.User user : users) {
            User userDTO = modelMapper.map(user, User.class);
            usersDTO.add(userDTO);
        }

        return new ResponseEntity<>(usersDTO, HttpStatusCode.valueOf(200));
    }
}
