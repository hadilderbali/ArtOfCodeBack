package com.artofcode.artofcodebck.auth;

import com.artofcode.artofcodebck.user.Role;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
@Getter
@Setter
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {

  @JsonProperty("access_token")
  private String accessToken;
  @JsonProperty("refresh_token")
  private String refreshToken;
  @JsonProperty("role")
  private Role role;
  @JsonProperty("error")
  private String error;

}
