package name.erzin.learn.hl.api;

import jakarta.servlet.http.HttpServletRequest;
import name.erzin.learn.hl.model.DialogMessage;
import name.erzin.learn.hl.model.DialogUserIdSendPostRequest;
import name.erzin.learn.hl.repository.DialogRepo;
import name.erzin.learn.hl.security.SecurityProvider;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class DialogApiImplementation implements DialogApiDelegate {
    @Autowired
    DialogRepo dialogRepo;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private SecurityProvider securityProvider;

    private ModelMapper initMapper() {
        ModelMapper modelMapper = new ModelMapper();
        TypeMap<name.erzin.learn.hl.entity.DialogMessage, DialogMessage> propertyMapper =
                modelMapper.createTypeMap(name.erzin.learn.hl.entity.DialogMessage.class, DialogMessage.class);
        propertyMapper.addMapping(name.erzin.learn.hl.entity.DialogMessage::getSrcUserId, DialogMessage::setFrom);
        propertyMapper.addMapping(name.erzin.learn.hl.entity.DialogMessage::getDstUserId, DialogMessage::setTo);
        
        return modelMapper;
    }

    @Override
    public ResponseEntity<List<DialogMessage>> dialogUserIdListGet(String dstUserId) {
        String srcUserId = securityProvider.extractLoginFromRequest(request);
        ArrayList<name.erzin.learn.hl.entity.DialogMessage> dialogMessages = dialogRepo.list(srcUserId, dstUserId);
        List<DialogMessage> dialogDTO = new ArrayList<>();

        ModelMapper modelMapper = initMapper();
        for (name.erzin.learn.hl.entity.DialogMessage dialogMessage : dialogMessages) {
            DialogMessage dialogMessageDTO = modelMapper.map(dialogMessage, DialogMessage.class);
            dialogDTO.add(dialogMessageDTO);
        }

        return new ResponseEntity<>(dialogDTO, HttpStatusCode.valueOf(200));
    }

    @Override
    public ResponseEntity<Void> dialogUserIdSendPost(String dstUserId, DialogUserIdSendPostRequest dialogUserIdSendPostRequest) {
        String srcUserId = securityProvider.extractLoginFromRequest(request);

        name.erzin.learn.hl.entity.DialogMessage dialogMessage = new name.erzin.learn.hl.entity.DialogMessage();
        dialogMessage.setSrcUserId(srcUserId);
        dialogMessage.setDstUserId(dstUserId);
        dialogMessage.setText(dialogUserIdSendPostRequest.getText());

        insertDialogMessage(dialogMessage);

        return new ResponseEntity<>(null, HttpStatusCode.valueOf(200));
    }

    private String calculateDistributionKey (name.erzin.learn.hl.entity.DialogMessage message) {
        String distKey;

        String[] users = { message.getSrcUserId(), message.getDstUserId() };
        Arrays.sort(users);
        distKey = users[0] + "_" + users[1];

        return distKey;
    }

    private void insertDialogMessage (name.erzin.learn.hl.entity.DialogMessage message) {
        String distKey = calculateDistributionKey(message);
        dialogRepo.insertDialogMessage(message.getSrcUserId(), message.getDstUserId(), message.getText(), distKey);
    }
}
