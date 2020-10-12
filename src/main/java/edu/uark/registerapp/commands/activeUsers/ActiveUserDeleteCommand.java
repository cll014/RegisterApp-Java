package edu.uark.registerapp.commands.activeUsers;

import java.util.Optional;

import edu.uark.registerapp.commands.exceptions.UnprocessableEntityException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.uark.registerapp.commands.VoidCommandInterface;
import edu.uark.registerapp.models.entities.ActiveUserEntity;
import edu.uark.registerapp.models.repositories.ActiveUserRepository;

//define class
@Service
public class ActiveUserDeleteCommand implements VoidCommandInterface{
    //properties
    private String sessionKey;

    // Getter
    public String getSessionKey(){
        return this.sessionKey;
    }
    // Setter
    public ActiveUserDeleteCommand setSessionKey(final String sessionKey){
        this.sessionKey = sessionKey;
        return this;
    }

    //Functionality
    @Transactional
    @Override
    public void execute(){
        final Optional<ActiveUserEntity> activeUserEntity = this.activeUserRepository.findBySessionKey(
                this.sessionKey);
        //query the activeUser table for a record with the provided sessionKey
        //delete activeUser record
        //use the existing ActiveUserRepository.delete()
        if(activeUserEntity.isPresent()){
            this.activeUserRepository.delete(activeUserEntity.get());
        }

    }

    @Autowired
    private ActiveUserRepository activeUserRepository;

}
