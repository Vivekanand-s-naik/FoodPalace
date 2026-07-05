<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ include file="../includes/header.jsp"%>
<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/cart.css">
<%@ include file="../includes/navbar.jsp"%>

<main class="cart-page">
    <section class="page-header py-5">
        <div class="container text-center">
            <h1>My Shopping Cart</h1>
            <p>Review your selected items before checkout.</p>
        </div>
    </section>

    <section class="cart-section pb-5">
        <div class="container">
            <div class="row">
                <!-- Cart Items -->
                <div class="col-lg-8">
                    <c:choose>
                        <c:when test="${not empty cartItems}">
                            <c:forEach var="item" items="${cartItems}">
                                <div class="card mb-4 shadow-sm">
                                    <div class="row g-0 align-items-center">
                                        <div class="col-md-3">
                                            <img
                                                src="${pageContext.request.contextPath}/assets/images/menu/${item.imagePath}"
                                                class="img-fluid rounded-start"
                                                alt="${item.itemName}"
                                                onerror="this.src='${pageContext.request.contextPath}/assets/images/menu-placeholder.jpg'">
                                        </div>
                                        <div class="col-md-9">
                                            <div class="card-body">
                                                <div class="d-flex justify-content-between">
                                                    <h4>${item.itemName}</h4>
                                                    <h5>₹ ${item.price}</h5>
                                                </div>
                                                <p>${item.description}</p>
                                                <div class="row align-items-center">
                                                    <div class="col-md-4">
                                                        <form action="${pageContext.request.contextPath}/cart" method="post" class="d-flex gap-2">
                                                            <input type="hidden" name="action" value="update">
                                                            <input type="hidden" name="cartItemId" value="${item.cartItemId}">
                                                            <input type="number" class="form-control" name="quantity" value="${item.quantity}" min="1" style="width: 80px;">
                                                            <button type="submit" class="btn btn-sm btn-success">Update</button>
                                                        </form>
                                                    </div>
                                                    <div class="col-md-3">
                                                        <strong>₹ ${item.price * item.quantity}</strong>
                                                    </div>
                                                    <div class="col-md-5 text-end">
                                                        <a href="${pageContext.request.contextPath}/cart?action=remove&cartItemId=${item.cartItemId}"
                                                           class="btn btn-danger"
                                                           onclick="return confirm('Remove this item?')">
                                                            <i class="fa-solid fa-trash"></i> Remove
                                                        </a>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </c:forEach>
                        </c:when>
                        <c:otherwise>
                            <div class="alert alert-warning text-center">
                                <h3>Your Cart is Empty</h3>
                                <p>Add some delicious food to continue.</p>
                                <a href="${pageContext.request.contextPath}/restaurants" class="btn btn-warning">Browse Restaurants</a>
                            </div>
                        </c:otherwise>
                    </c:choose>
                </div>

                <!-- Order Summary -->
                <div class="col-lg-4">
                    <div class="card shadow">
                        <div class="card-header"><h4>Order Summary</h4></div>
                        <div class="card-body">
                            <div class="d-flex justify-content-between mb-3">
                                <span>Subtotal</span>
                                <span>₹ ${subTotal}</span>
                            </div>
                            <div class="d-flex justify-content-between mb-3">
                                <span>Delivery Fee</span>
                                <span>₹ ${deliveryFee}</span>
                            </div>
                            <div class="d-flex justify-content-between mb-3">
                                <span>Tax (GST)</span>
                                <span>₹ ${tax}</span>
                            </div>
                            <hr>
                            <div class="d-flex justify-content-between">
                                <h5>Grand Total</h5>
                                <h4>₹ ${grandTotal}</h4>
                            </div>
                            <hr>
                            <div class="d-grid mb-3">
                                <a href="${pageContext.request.contextPath}/checkout" class="btn btn-warning btn-lg ${empty cartItems ? 'disabled' : ''}">
                                    <i class="fa-solid fa-credit-card me-2"></i> Proceed To Checkout
                                </a>
                            </div>
                            <div class="d-grid">
                                <a href="${pageContext.request.contextPath}/restaurants" class="btn btn-outline-secondary">
                                    <i class="fa-solid fa-utensils me-2"></i> Continue Shopping
                                </a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </section>
</main>

<script src="${pageContext.request.contextPath}/assets/js/cart.js"></script>
<%@ include file="../includes/footer.jsp"%>