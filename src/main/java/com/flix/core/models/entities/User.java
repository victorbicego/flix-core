package com.flix.core.models.entities;

import com.flix.core.models.enums.Role;
import java.util.Collection;
import java.util.List;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Document(collection = "user")
@Data
public class User implements UserDetails {

  @Id private String id;
  private String username;
  private String password;
  private Role role;
  private String name;
  private String surname;
  private boolean isAccountNonExpired;
  private boolean isAccountNonLocked;
  private boolean isCredentialNonExpired;
  private boolean isEnabled;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return List.of(new SimpleGrantedAuthority(role.name()));
  }

  @Override
  public boolean isAccountNonExpired() {
    return isAccountNonExpired;
  }

  @Override
  public boolean isAccountNonLocked() {
    return isAccountNonLocked;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return isCredentialNonExpired;
  }

  @Override
  public boolean isEnabled() {
    return isEnabled;
  }
}
