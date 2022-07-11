package com.example.demo.auth.users;

public enum ApplicationUserPermission {
  CREATE_TAG("create:tag"),
  EDIT_COMPANY("edit:company");

  private final String permission;

  ApplicationUserPermission(String permission) {
    this.permission = permission;
  }

  public String getPermission() {
    return permission;
  }
}
