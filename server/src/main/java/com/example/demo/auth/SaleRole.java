package com.example.demo.auth;

import com.google.common.collect.Sets;

import static com.example.demo.auth.SalePermission.*;

import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public enum SaleRole {
  SALE_PERSON(Sets.newHashSet()),
  SALE_ADMIN(Sets.newHashSet(EDIT_TAG, EDIT_COMPANY));

  private final Set<SalePermission> permissions;

  SaleRole(Set<SalePermission> permissions) {
    this.permissions = permissions;
  }

  public Set<SalePermission> getPermissions() {
    return permissions;
  }

  public Set<SimpleGrantedAuthority> getGrantedAuthorities() {
    Set<SimpleGrantedAuthority> permissions = getPermissions()
      .stream()
      .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
      .collect(Collectors.toSet());
    permissions.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
    return permissions;
  }
}
