package com.example.demo.sale;

import com.example.demo.sale.repository.SaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class SaleService implements UserDetailsService {

  private final SaleRepository applicationUserDao;

  @Autowired
  public SaleService(SaleRepository applicationUserDao) {
    this.applicationUserDao = applicationUserDao;
  }

  @Override
  public UserDetails loadUserByUsername(String username)
    throws UsernameNotFoundException {
    return applicationUserDao
      .findByFullName(username)
      .orElseThrow(
        () ->
          new UsernameNotFoundException(
            String.format("Username %s not found", username)
          )
      );
  }
}
