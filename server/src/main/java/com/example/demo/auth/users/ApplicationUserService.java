package com.example.demo.auth.users;

import com.example.demo.repository.SaleRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class ApplicationUserService implements UserDetailsService {

  private final SaleRepository saleRepository;

  public ApplicationUserService(SaleRepository applicationUserDao) {
    this.saleRepository = applicationUserDao;
  }

  @Override
  public UserDetails loadUserByUsername(String username)
    throws UsernameNotFoundException {
    ApplicationUser saleDetails = saleRepository
      .findByFullName(username)
      .map(ApplicationUser::new)
      .orElseThrow(
        () ->
          new UsernameNotFoundException(
            String.format("Username %s not found", username)
          )
      );

    return saleDetails;
  }
}
