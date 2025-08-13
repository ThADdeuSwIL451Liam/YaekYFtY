// 代码生成时间: 2025-08-14 01:41:30
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

@Service
public class ShoppingCartService {

    private final ShoppingCartRepository repository;

    @Autowired
    public ShoppingCartService(ShoppingCartRepository repository) {
        this.repository = repository;
    }

    // Adds a product to the shopping cart
    public ResponseEntity<?> addItemToCart(String productId, String cartId) {
        try {
            Optional<ShoppingCart> cart = repository.findById(cartId);
            if (cart.isPresent()) {
                ShoppingCartItem item = new ShoppingCartItem(productId);
                cart.get().addItem(item);
                repository.save(cart.get());
                return ResponseEntity.ok(cart.get());
            } else {
                return ResponseEntity.badRequest().body("Cart not found.");
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error adding item to cart.");
        }
    }

    // Removes a product from the shopping cart
    public ResponseEntity<?> removeItemFromCart(String productId, String cartId) {
        try {
            Optional<ShoppingCart> cart = repository.findById(cartId);
            if (cart.isPresent()) {
                cart.get().removeItem(productId);
                repository.save(cart.get());
                return ResponseEntity.ok(cart.get());
            } else {
                return ResponseEntity.badRequest().body("Cart not found.");
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error removing item from cart.");
        }
    }

    // Retrieves the shopping cart details
    public ResponseEntity<ShoppingCart> getCartDetails(String cartId) {
        try {
            Optional<ShoppingCart> cart = repository.findById(cartId);
            return cart.map(ResponseEntity::ok).orElseGet(
                () -> ResponseEntity.badRequest().body("Cart not found.")
            );
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error retrieving cart details.");
        }
    }
}
