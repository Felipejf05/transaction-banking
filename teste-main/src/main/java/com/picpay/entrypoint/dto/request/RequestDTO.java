package com.picpay.entrypoint.dto.request;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RequestDTO {

    private String firstName;
    private String lastName;
    private String cpf;
    private String email;
    private String address;
}


