package mk.ukim.finki.av1wp.service.impl;

import mk.ukim.finki.av1wp.model.Product;
import mk.ukim.finki.av1wp.model.ShoppingCart;
import mk.ukim.finki.av1wp.model.User;
import mk.ukim.finki.av1wp.model.enumaration.ShoppingCartStatus;
import mk.ukim.finki.av1wp.model.exceptions.ProductAlreadyInShoppingCartException;
import mk.ukim.finki.av1wp.model.exceptions.ProductNotFoundException;
import mk.ukim.finki.av1wp.model.exceptions.ShoppingCartNotFoundException;
import mk.ukim.finki.av1wp.model.exceptions.UserNotFoundException;
import mk.ukim.finki.av1wp.repository.jpa.ShoppingCartRepository;
import mk.ukim.finki.av1wp.repository.jpa.UserRepository;
import mk.ukim.finki.av1wp.service.ProductService;
import mk.ukim.finki.av1wp.service.ShoppingCartService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ShoppingCartImp implements ShoppingCartService {
    private final ShoppingCartRepository shoppingCartRepository;
    private final UserRepository userRepository;
    private final ProductService productService;

    public ShoppingCartImp(ShoppingCartRepository shoppingCartRepository, UserRepository userRepository, ProductService productService) {
        this.shoppingCartRepository = shoppingCartRepository;
        this.userRepository = userRepository;
        this.productService = productService;
    }

    @Override
    public List<Product> listAllProductsInShoppingCart(Long cartId) {
        if(!shoppingCartRepository.findById(cartId).isPresent()){
            throw new ShoppingCartNotFoundException(cartId);
        }
        return shoppingCartRepository.findById(cartId).get().getProducts();
    }

    @Override
    public ShoppingCart getActiveShoppingCart(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(username));
        return shoppingCartRepository.findByUserAndStatus(user, ShoppingCartStatus.CREATED)
                .orElseGet(() -> {
                    ShoppingCart shoppingCart = new ShoppingCart(user);
                    return shoppingCartRepository.save(shoppingCart);
                });
    }

    @Override
    public ShoppingCart addProductToShoppingCart(String username, Long productId) {
        ShoppingCart shoppingCart = getActiveShoppingCart(username);
        Product product = productService.findById(productId).orElseThrow(() -> new ProductNotFoundException(productId));
        if(shoppingCart.getProducts().stream()
                .filter(r -> r.getId().equals(productId))
                .collect(Collectors.toList()).size() > 0){
            throw new ProductAlreadyInShoppingCartException(productId, username);
        }
        shoppingCart.getProducts().add(product);
        return shoppingCartRepository.save(shoppingCart);
    }
}
