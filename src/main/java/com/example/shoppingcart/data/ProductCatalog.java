package com.example.shoppingcart.data;

import com.example.shoppingcart.model.Product;

import java.math.BigDecimal;
import java.util.List;

public final class ProductCatalog {

    private static final List<Product> PRODUCTS = List.of(
            new Product(
                    "CD01",
                    "86 (the band) - True Life Songs and Pictures",
                    new BigDecimal("14.95")
            ),

            new Product(
                    "CD02",
                    "Paddlefoot - The first CD",
                    new BigDecimal("12.95")
            ),

            new Product(
                    "CD03",
                    "Paddlefoot - The second CD",
                    new BigDecimal("14.95")
            ),

            new Product(
                    "CD04",
                    "Joe Rut - Genuine Wood Grained Finish",
                    new BigDecimal("14.95")
            )
    );

    private ProductCatalog() {
    }

    // Lay toan bo danh sach san pham
    public static List<Product> getAllProducts() {
        return PRODUCTS;
    }

    // Tim san pham bang ma san pham
    public static Product findById(String productId) {

        if (productId == null) {
            return null;
        }

        return PRODUCTS.stream()
                .filter(product ->
                        product.getId().equals(productId))
                .findFirst()
                .orElse(null);
    }
}