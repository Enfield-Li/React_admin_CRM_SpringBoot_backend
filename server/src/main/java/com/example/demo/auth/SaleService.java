package com.example.demo.auth;

import com.example.demo.entity.Sale;
import com.example.demo.repository.SaleRepository;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class SaleService implements UserDetailsService {

  private final SaleRepository saleRepository;

  @Autowired
  public SaleService(SaleRepository applicationUserDao) {
    this.saleRepository = applicationUserDao;
  }

  @Override
  public UserDetails loadUserByUsername(String username)
    throws UsernameNotFoundException {
    System.out.println("loadUserByUsername, username: " + username);
    Optional<Sale> sale = saleRepository.findByFullName(username);
    System.out.println("loadUserByUsername, sale tostring: " + sale.toString());

    sale.orElseThrow(
      () ->
        new UsernameNotFoundException(
          String.format("Username %s not found", username)
        )
    );

    SaleDetails saleDetails = sale.map(SaleDetails::new).get();
    System.out.println("saleDetailes: " + saleDetails.toString());

    return saleDetails;
  }
}
