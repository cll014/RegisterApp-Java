package edu.uark.registerapp.commands.activeUsers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.uark.registerapp.commands.VoidCommandInterface;
import edu.uark.registerapp.models.entities.ActiveUserEntity;
import edu.uark.regiserapp.models.repositories.ActiveUserRepository;

//define class
@Service
public class ActiveUserDeleteCommand implements VoidCommandInterface{
    //properties
    private String sessionKey;
    private activeUser activeUser;

    //getter
    public String getSessionKey(){
        return this.sessionKey;
    }
    public ActiveUserDeleteCommand setSessionKey(final String sessionKey){
        this.sessionKey = sessionKey;
        return this;
    }

    //Functionality
    @Transactional
    @Override
    public void execute(){
        //validate the incoming employee request object
        this.validateProperties();
        //query the activeUser table for a record with the provided sessionKey
        final Optional<ActiveUserEntity> activeUserEntity = this.activeUserRepository.findBySessionKey(
            this.sessionKey);
        //delete activeUser record
        //use the existing ActiveUserRepository.delete()
        if(activeUserEntity.isPresent()){
            this.activeUserRepository.delete(activeUserEntity.get());
        }
    }
    @Override
    private void validateProperties(){
        if (StringUtils.isBlank(this.activeUser.getFirstName())){
            throw new UnprocessableEntityException("First Name");
        }
        if (StringUtils.isBlank(this.activeUser.getLastName())){
            throw new UnprocessableEntityException("Last Name");
        }
    }

    @Autowired
    private ActiveUserRepository activeUserRepository;

}