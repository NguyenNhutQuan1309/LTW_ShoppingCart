package com.example.shoppingcart.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

/** Quan ly cac CartItem cua mot khach hang trong HttpSession. */
public class ShoppingCart {
    // Moi ma san pham chi xuat hien 1 lan trong gio
    private final Map<String, CartItem> items = new LinkedHashMap<>();

    /** Them san pham */
    public synchronized void addProduct(Product product) {
        CartItem item = items.get(product.getId());
        if (item == null) {
            items.put(product.getId(), new CartItem(product, 1));
        } else {
            item.setQuantity(item.getQuantity() + 1);
        }
    }

    /** Cap nhat so luong, 0 hoac -1 thi xoa */
    public synchronized void updateQuantity(String productId, int quantity) {
        if (quantity <= 0) {
            removeItem(productId);
            return;
        }

        CartItem item = items.get(productId);
        if (item != null) {
            item.setQuantity(quantity);
        }
    }

    /** xoa san pham khoi gio. */
    public synchronized void removeItem(String productId) {
        items.remove(productId);
    }

    /** Tra ve toan bo ban sao de jsp doc. */
    public synchronized Collection<CartItem> getItems() {
        return new ArrayList<>(items.values());
    }

    /** Tong so luong san pham */
    public synchronized int getTotalQuantity() {
        int total = 0;
        for (CartItem item : items.values()) {
            total += item.getQuantity();
        }
        return total;
    }

    /** Tong tien. */
    public synchronized BigDecimal getGrandTotal() {
        BigDecimal total = BigDecimal.ZERO;
        for (CartItem item : items.values()) {
            total = total.add(item.getTotal());
        }
        return total;
    }

    public synchronized boolean isEmpty() {
        return items.isEmpty();
    }
}
