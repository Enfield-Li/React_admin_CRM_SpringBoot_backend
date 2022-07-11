package com.example.demo.auth.users;

import com.example.demo.entity.Sale;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
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
    this.authorities =
      Arrays
        .stream(sale.getRole().split(","))
        .map(SimpleGrantedAuthority::new)
        .collect(Collectors.toList());
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
