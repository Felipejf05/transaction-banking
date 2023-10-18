package com.picpay.entrypoint.controller;



import com.picpay.core.domain.User;
import com.picpay.core.usecase.UserCase;
import com.picpay.dataprovider.database.entity.UserEntity;
import com.picpay.entrypoint.dto.request.RequestDTO;
import com.picpay.entrypoint.mapper.UserDTOMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/v1")
public class UserController {


    private  UserCase userCase;



    private  UserDTOMapper userDTOMapper;


    @PostMapping(value = "/create")
    public ResponseEntity<RequestDTO> create(@RequestBody RequestDTO dto){

        User user = userCase.create(userDTOMapper.toUserDomain(dto));

        return ResponseEntity.ok().body(userDTOMapper.toDto(user));
    }

    @GetMapping(value = "/findAll")
    public List<UserEntity> findAll(){

        return userCase.findAll();
    }
}
