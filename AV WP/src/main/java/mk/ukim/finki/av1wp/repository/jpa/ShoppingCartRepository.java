package mk.ukim.finki.av1wp.repository.jpa;

import mk.ukim.finki.av1wp.model.ShoppingCart;
import mk.ukim.finki.av1wp.model.User;
import mk.ukim.finki.av1wp.model.enumaration.ShoppingCartStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {
    Optional<ShoppingCart> findByUserAndStatus(User user, ShoppingCartStatus status);
}
