package com.sgm.login.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginUser {
    private Integer userId;
    private String phone;
}
