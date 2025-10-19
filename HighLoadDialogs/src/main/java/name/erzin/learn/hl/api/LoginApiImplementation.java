package name.erzin.learn.hl.api;

import name.erzin.learn.hl.model.LoginPost200Response;
import name.erzin.learn.hl.model.LoginPostRequest;
import name.erzin.learn.hl.security.SecurityProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.security.NoSuchAlgorithmException;

@Component
public class LoginApiImplementation implements LoginApiDelegate {
    @Autowired
    SecurityProvider securityProvider;

    public LoginApiImplementation() throws NoSuchAlgorithmException {
    }

    @Override
    public ResponseEntity<LoginPost200Response> loginPost(LoginPostRequest loginPostRequest) {
        LoginPost200Response response = new LoginPost200Response();

        if (securityProvider.isValidUserPassword(loginPostRequest.getId(), loginPostRequest.getPassword())) {
            response.setToken(securityProvider.createJwt(loginPostRequest.getId()));
            return new ResponseEntity<>(response, HttpStatusCode.valueOf(200));
        }

        return new ResponseEntity<>(response, HttpStatusCode.valueOf(403));
    }
}
