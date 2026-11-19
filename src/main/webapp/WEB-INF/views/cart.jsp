<%@ page contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>

<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">

    <title>Your cart</title>

    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/assets/css/style.css">
</head>

<body>

<h1>Your cart</h1>

<!-- Hiển thị thông báo lỗi -->
<c:if test="${not empty errorMessage}">
    <p class="error">
        <c:out value="${errorMessage}" />
    </p>
</c:if>

<!-- Kiểm tra giỏ hàng có rỗng không -->
<c:choose>

    <c:when test="${empty cart.items}">

        <p>Your cart is empty.</p>

    </c:when>

    <c:otherwise>

        <table>

            <thead>
            <tr>
                <th>Quantity</th>
                <th>Description</th>
                <th>Price</th>
                <th>Amount</th>
                <th></th>
            </tr>
            </thead>

            <tbody>

            <!-- Duyet qua tung san pham trong gio-->
            <c:forEach var="item" items="${cart.items}">

                <tr>

                    <!-- Quantity & Update -->
                    <td>

                        <form class="quantity-form"
                              action="${pageContext.request.contextPath}/cart"
                              method="post">

                            <input type="hidden"
                                   name="action"
                                   value="update">

                            <input type="hidden"
                                   name="productId"
                                   value="${item.product.id}">

                            <input class="quantity-input"
                                   type="number"
                                   name="quantity"
                                   min="0"
                                   value="${item.quantity}"
                                   required>

                            <button type="submit">
                                Update
                            </button>

                        </form>

                    </td>

                    <!-- Ten san pham -->
                    <td>
                        <c:out value="${item.product.description}" />
                    </td>

                    <!-- Don gia -->
                    <td>
                        $<fmt:formatNumber
                            value="${item.product.price}"
                            minFractionDigits="2"
                            maxFractionDigits="2" />
                    </td>

                    <!-- Thanh tien -->
                    <td>
                        $<fmt:formatNumber
                            value="${item.total}"
                            minFractionDigits="2"
                            maxFractionDigits="2" />
                    </td>

                    <!-- Remove Item -->
                    <td>

                        <form action="${pageContext.request.contextPath}/cart"
                              method="post">

                            <input type="hidden"
                                   name="action"
                                   value="remove">

                            <input type="hidden"
                                   name="productId"
                                   value="${item.product.id}">

                            <button type="submit">
                                Remove Item
                            </button>

                        </form>

                    </td>

                </tr>

            </c:forEach>

            </tbody>

        </table>

        <p>
            <strong>To change the quantity</strong>,
            enter the new quantity and click on the Update button.
        </p>

    </c:otherwise>

</c:choose>

<!-- Cac nut chuc nang -->
<div class="cart-actions">

    <!-- Continue Shopping -->
    <form action="${pageContext.request.contextPath}/cart"
          method="post">

        <input type="hidden"
               name="action"
               value="continue">

        <button type="submit">
            Continue Shopping
        </button>

    </form>

    <!-- Checkout -->
    <form action="${pageContext.request.contextPath}/cart"
          method="post">

        <input type="hidden"
               name="action"
               value="checkout">

        <button type="submit">
            Checkout
        </button>

    </form>

</div>

</body>
</html>