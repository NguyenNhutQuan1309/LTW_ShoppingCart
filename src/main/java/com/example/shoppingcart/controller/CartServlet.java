package com.example.shoppingcart.controller;

import com.example.shoppingcart.data.ProductCatalog;
import com.example.shoppingcart.model.Product;
import com.example.shoppingcart.model.ShoppingCart;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/cart")
public class CartServlet extends HttpServlet {

    // Xu ly truy cap trang gio hang
    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        if ("continue".equals(action)) {
            response.sendRedirect(
                    request.getContextPath() + "/index.html"
            );
            return;
        }

        if ("checkout".equals(action)) {
            showCheckout(request, response);
            return;
        }

        showCart(request, response);
    }

    // Xu ly thao tac POST
    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String action = request.getParameter("action");

        HttpSession session = request.getSession();

        ShoppingCart cart = getCart(session);

        // 1. ADD TO CART
        if ("add".equals(action)) {

            String productId =
                    request.getParameter("productId");

            Product product =
                    ProductCatalog.findById(productId);

            if (product != null) {
                cart.addProduct(product);
            }

            response.sendRedirect(
                    request.getContextPath() + "/cart"
            );

            return;
        }

        // 2. UPDATE QUANTITY
        if ("update".equals(action)) {

            String productId =
                    request.getParameter("productId");

            String quantityText =
                    request.getParameter("quantity");

            try {
                int quantity = Integer.parseInt(quantityText);

                cart.updateQuantity(productId, quantity);

                response.sendRedirect(
                        request.getContextPath() + "/cart"
                );

            } catch (NumberFormatException e) {

                request.setAttribute(
                        "errorMessage",
                        "Invalid quantity!"
                );

                showCart(request, response);
            }

            return;
        }

        // 3. REMOVE ITEM
        if ("remove".equals(action)) {

            String productId =
                    request.getParameter("productId");

            cart.removeItem(productId);

            response.sendRedirect(
                    request.getContextPath() + "/cart"
            );

            return;
        }

        // 4. CONTINUE SHOPPING
        if ("continue".equals(action)) {

            response.sendRedirect(
                    request.getContextPath() + "/index.html"
            );

            return;
        }

        // 5. CHECKOUT
        if ("checkout".equals(action)) {

            showCheckout(request, response);

            return;
        }

        response.sendRedirect(
                request.getContextPath() + "/index.html"
        );
    }

    // Lay gio hang tu session
    private ShoppingCart getCart(HttpSession session) {

        ShoppingCart cart =
                (ShoppingCart) session.getAttribute("cart");

        if (cart == null) {

            cart = new ShoppingCart();

            session.setAttribute("cart", cart);
        }

        return cart;
    }

    // Hien thi trang gio hang
    private void showCart(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        ShoppingCart cart = getCart(request.getSession());

        request.setAttribute("cart", cart);

        request.getRequestDispatcher(
                "/WEB-INF/views/cart.jsp"
        ).forward(request, response);
    }

    // Hien thi trang checkout
    private void showCheckout(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        ShoppingCart cart = getCart(request.getSession());

        if (cart.isEmpty()) {

            response.sendRedirect(
                    request.getContextPath() + "/cart"
            );

            return;
        }

        request.setAttribute("cart", cart);

        request.getRequestDispatcher(
                "/WEB-INF/views/checkout.jsp"
        ).forward(request, response);
    }
}