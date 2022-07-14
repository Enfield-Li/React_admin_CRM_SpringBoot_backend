package com.example.demo.auth.user;

import com.example.demo.entity.Sale;
import java.util.Collection;
import java.util.List;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Data
public class ApplicationUser implements UserDetails {

  private Long id;
  private String username;
  private String password;
  private List<GrantedAuthority> authorities;

  public ApplicationUser(Sale sale) {
    this.id = sale.getId();
    this.username = sale.getUsername();
    this.password = sale.getPassword();
    this.authorities = sale.getUserAuthorities();
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return authorities;
  }

  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public String getUsername() {
    return username;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }
}
