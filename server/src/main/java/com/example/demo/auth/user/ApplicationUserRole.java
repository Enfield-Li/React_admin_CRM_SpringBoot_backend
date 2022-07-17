package com.example.demo.auth.user;

import com.google.common.collect.Sets;

import static com.example.demo.auth.user.ApplicationUserPermission.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public enum ApplicationUserRole {
  SALE_PERSON(Sets.newHashSet(EDIT_COMPANY)),
  SALE_ADMIN(Sets.newHashSet(CREATE_TAG)),
  SUPER_USER(Sets.newHashSet(CREATE_TAG, EDIT_COMPANY));

  private final Set<ApplicationUserPermission> permissions;

  ApplicationUserRole(Set<ApplicationUserPermission> permissions) {
    this.permissions = permissions;
  }

  public Set<ApplicationUserPermission> getPermissions() {
    return permissions;
  }

  public List<GrantedAuthority> getGrantedAuthorities() {
    List<GrantedAuthority> grantedAuthorities = getPermissions()
      .stream()
      .map(
        permission ->
          (GrantedAuthority) new SimpleGrantedAuthority(
            permission.getPermission()
          )
      )
      .collect(Collectors.toList());

    grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
    return grantedAuthorities;
  }
}
