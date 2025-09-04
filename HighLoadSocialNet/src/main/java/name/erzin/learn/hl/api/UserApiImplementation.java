package name.erzin.learn.hl.api;

import name.erzin.learn.hl.entity.Test;
import name.erzin.learn.hl.model.User;
import name.erzin.learn.hl.repository.TestRepo;
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
    TestRepo testRepo;

    @Override
    public ResponseEntity<List<User>> userSearchGet(String firstName, String lastName) {
        ArrayList<Test> test = testRepo.findAll();
        User u1 = new User();
        u1.setFirstName(test.get(0).getFirstName());
        u1.setSecondName(test.get(0).getLastName());
        return new ResponseEntity<>(Arrays.asList(u1), HttpStatusCode.valueOf(200));
    }
}
