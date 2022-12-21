package mk.ukim.finki.av1wp.repository.impl;

import mk.ukim.finki.av1wp.bootsrap.DataHolder;
import mk.ukim.finki.av1wp.model.ShoppingCart;
import mk.ukim.finki.av1wp.model.enumaration.ShoppingCartStatus;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class InMemoryShoppingCartRepository {
    public Optional<ShoppingCart> findById(Long id){
        return DataHolder.shoppingCarts.stream()
                .filter(r -> r.getId().equals(id))
                .findFirst();
    }

    public Optional<ShoppingCart> findByUsernameAndStatus(String username, ShoppingCartStatus status){
        return DataHolder.shoppingCarts.stream()
                .filter(r -> r.getUser().getUsername().equals(username) && r.getStatus().equals(status))
                .findFirst();
    }

    public ShoppingCart save(ShoppingCart shoppingCart){
        DataHolder.shoppingCarts.removeIf(r -> r.getUser().getUsername().equals(shoppingCart.getUser().getUsername()));
        DataHolder.shoppingCarts.add(shoppingCart);
        return shoppingCart;
    }
}
