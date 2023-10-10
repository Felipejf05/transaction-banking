package com.picpay.core.usecase;
import com.picpay.core.common.mapper.UserMapper;
import com.picpay.core.domain.User;
import com.picpay.dataprovider.database.entity.UserEntity;
import com.picpay.dataprovider.database.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserCase {

    @Autowired
    private final UserMapper userMapper;

    @Autowired
    private final UserRepository userRepository;

    public User create(User user){

        UserEntity userEntity = new UserEntity();

        userEntity.setFirstName(user.getFirstName());
        userEntity.setLastName(user.getLastName());
        userEntity.setCpf(user.getCpf());
        userEntity.setEmail(user.getEmail());
        userEntity.setAddress(user.getAddress());

        return userMapper.toUserDomain(userRepository.save(userEntity));
    }

    public List <UserEntity> findAll(){

        return this.userRepository.findAll();

    }

}
