package com.flamexander.springmarket.services;

import com.flamexander.springmarket.entities.TempUser;
import com.flamexander.springmarket.repositories.TempUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TempUserService {
    private TempUserRepository tempUserRepository;

    @Autowired
    public void setTempUserRepository(TempUserRepository tempUserRepository) {
        this.tempUserRepository = tempUserRepository;
    }

    @Transactional
    public void save(TempUser tempUser){
        tempUserRepository.save(tempUser);
    }

    public TempUser create(String userName, String phone, String email, String address){
        TempUser tempUser = new TempUser();
        tempUser.setUserName(userName);
        tempUser.setAddress(address);
        tempUser.setPhone(phone);
        tempUser.setEmail(email);
        return tempUser;
    }
}
