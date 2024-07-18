package com.dish_dash.authentication.domain.model;

import javax.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class AuthenticationInfo {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private String userID;

  private String username;
  private String password;
  // TODO
  //  @Column(name = "role", length = 32, columnDefinition = "varchar(32) default 'USER' ")
  //  @Enumerated(EnumType.STRING)
  private String roles;
}
