// 代码生成时间: 2025-09-30 03:32:21
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import java.math.BigDecimal;

@Service
public class DiscountService {

    private static final BigDecimal NO_DISCOUNT = BigDecimal.ONE;
    private final DiscountRepository discountRepository;

    @Autowired
    public DiscountService(DiscountRepository discountRepository) {
        this.discountRepository = discountRepository;
    }

    /**
     * 根据商品ID获取折扣优惠
     * 
     * @param productId 商品ID
     * @return 折扣优惠的百分比
     */
    public BigDecimal getDiscountByProductId(String productId) {
        Discount discount = discountRepository.findById(productId).orElseThrow(\() ->
            new ResponseStatusException(HttpStatus.NOT_FOUND, "Discount not found for product ID: " + productId, null)
        );
        return discount.getPercentage();
    }

    /**
     * 应用折扣优惠到商品价格
     * 
     * @param productId 商品ID
     * @param originalPrice 商品原价
     * @return 折扣后的价格
     */
    public BigDecimal applyDiscount(String productId, BigDecimal originalPrice) {
        BigDecimal discountPercentage = getDiscountByProductId(productId);
        BigDecimal discountAmount = originalPrice.multiply(discountPercentage).divide(NO_DISCOUNT);
        return originalPrice.subtract(discountAmount);
    }
}
