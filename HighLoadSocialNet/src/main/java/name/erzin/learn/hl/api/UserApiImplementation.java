package name.erzin.learn.hl.api;

import name.erzin.learn.hl.entity.Account;
import name.erzin.learn.hl.model.User;
import name.erzin.learn.hl.model.UserRegisterPost200Response;
import name.erzin.learn.hl.model.UserRegisterPostRequest;
import name.erzin.learn.hl.repository.AccountRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class UserApiImplementation implements UserApiDelegate {
    @Autowired
    AccountRepo accountRepo;

    @Override
    public ResponseEntity<UserRegisterPost200Response> userRegisterPost(UserRegisterPostRequest userRegisterPostRequest) {
        return UserApiDelegate.super.userRegisterPost(userRegisterPostRequest);
    }

    @Override
    public ResponseEntity<List<User>> userSearchGet(String firstName, String lastName) {
        ArrayList<Account> test = accountRepo.findAll();
        User u1 = new User();
        u1.setFirstName(test.get(0).getLogin());
        u1.setSecondName(test.get(0).getPassword());
        return new ResponseEntity<>(Arrays.asList(u1), HttpStatusCode.valueOf(200));
    }
}
