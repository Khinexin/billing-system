package com.demo.billingsystem.dto;

import java.util.List;

import com.demo.billingsystem.model.UserRole;

import lombok.Data;

@Data
public class UserRespDTO {

  private Integer id;
  private String username;
  List<UserRole> userRoles;

}
