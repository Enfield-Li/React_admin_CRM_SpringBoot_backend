package com.example.demo.auth;

public enum SalePermission {
  EDIT_TAG("edit:tag"),
  EDIT_COMPANY("edit:company");

  private final String permission;

  SalePermission(String permission) {
    this.permission = permission;
  }

  public String getPermission() {
    return permission;
  }
}
