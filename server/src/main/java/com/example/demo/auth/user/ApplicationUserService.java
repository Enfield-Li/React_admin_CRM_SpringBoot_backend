package com.example.demo.auth.user;

import com.example.demo.repository.SaleRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class ApplicationUserService implements UserDetailsService {

  private final SaleRepository repo;

  public ApplicationUserService(SaleRepository repo) {
    this.repo = repo;
  }

  @Override
  public UserDetails loadUserByUsername(String username)
    throws UsernameNotFoundException {
    return repo
      .findByFullName(username)
      .map(ApplicationUser::new)
      .orElseThrow(
        () ->
          new UsernameNotFoundException(
            String.format("Username %s not found", username)
          )
      );
  }
}
