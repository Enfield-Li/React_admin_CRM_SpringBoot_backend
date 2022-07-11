package com.example.demo.auth.users;

import static com.example.demo.auth.users.ApplicationUserPermission.*;

import com.google.common.collect.Sets;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public enum ApplicationUserRole {
  SALE_PERSON(Sets.newHashSet(EDIT_COMPANY)),
  SALE_ADMIN(Sets.newHashSet(CREATE_TAG)),
  SUPER_USER(Sets.newHashSet());

  private final Set<ApplicationUserPermission> permissions;

  ApplicationUserRole(Set<ApplicationUserPermission> permissions) {
    this.permissions = permissions;
  }

  public Set<ApplicationUserPermission> getPermissions() {
    return permissions;
  }

  public List<GrantedAuthority> getGrantedAuthorities() {
    List<GrantedAuthority> permissions = getPermissions()
      .stream()
      .map(
        permission ->
          (GrantedAuthority) new SimpleGrantedAuthority(
            permission.getPermission()
          )
      )
      .collect(Collectors.toList());

    permissions.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
    return permissions;
  }
}
