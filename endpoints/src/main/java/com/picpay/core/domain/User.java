package com.picpay.core.domain;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class User {

    private Long id;
    private String firstName;
    private String lastName;
    private String cpf;
    private String email;
    private String address;
}
