package com.demo.billingsystem.dto;

import java.util.List;

import com.demo.billingsystem.model.UserRole;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDataDTO {
  
  private String username;
  private String password;
  List<UserRole> userRoles;

}
