package com.picpay.entrypoint.dto.request;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class RequestDTO {

    private Long id;
    private String firstName;
    private String lastName;
    private String cpf;
    private String email;
    private String address;
}


