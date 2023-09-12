package com.login.loginjava.domain.service;

import com.login.loginjava.common.exception.ApplicationException;
import com.login.loginjava.domain.model.SessionsModel;
import com.login.loginjava.persistence.crud.SessionRepository;
import com.login.loginjava.persistence.mapper.SessionMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class SessionService {

    private final SessionRepository sessionRepository;

    public SessionService(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    public List<SessionsModel> getAll(){
        return sessionRepository
                .findAll()
                .stream()
                .map(SessionMapper.INSTANCE::tosessionModel)
                .toList();
    }

    public SessionsModel getId(Integer idSession) throws ApplicationException {
        return sessionRepository
                .findById(idSession)
                .map(SessionMapper.INSTANCE::tosessionModel)
                .orElseThrow(() -> new ApplicationException(HttpStatus.NOT_FOUND, "NOT_FOUND_SESSION", "session does not exist"));
    }


    public Boolean delete(int idSession) throws ApplicationException {
        if (exists(idSession)){
            sessionRepository.deleteById(idSession);
            return true;
        }
        throw new ApplicationException(HttpStatus.NOT_FOUND, "NOT_FOUND_SESSION", "session does not exist");
    }


    public boolean exists(int idBranch){
        return this.sessionRepository.existsById(idBranch);
    }


}
