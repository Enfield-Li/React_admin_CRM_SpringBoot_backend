package com.example.demo.auth.users;

import com.example.demo.entity.Sale;
import com.example.demo.repository.SaleRepository;
import java.util.Optional;
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
    Optional<Sale> sale = saleRepository.findByFullName(username);
    sale.orElseThrow(
      () ->
        new UsernameNotFoundException(
          String.format("Username %s not found", username)
        )
    );

    ApplicationUser saleDetails = sale.map(ApplicationUser::new).get();

    return saleDetails;
  }
}
