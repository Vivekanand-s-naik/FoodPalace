<%@ page language="java"
	contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="c"
	uri="jakarta.tags.core"%>

<%@ include file="../includes/header.jsp"%>

<link rel="stylesheet"
	href="${pageContext.request.contextPath}/assets/css/cart.css">

<%@ include file="../includes/navbar.jsp"%>

<main class="cart-page">

	<!-- ==========================================
				PAGE HEADER
	========================================== -->

	<section class="page-header py-5">

		<div class="container text-center">

			<h1>

				My Shopping Cart

			</h1>

			<p>

				Review your selected items before checkout.

			</p>

		</div>

	</section>

	<!-- ==========================================
				CART
	========================================== -->

	<section class="cart-section pb-5">

		<div class="container">

			<div class="row">

				<!-- =====================================
						CART ITEMS
				===================================== -->

				<div class="col-lg-8">

					<c:choose>

						<c:when test="${not empty cartItems}">

							<c:forEach

								var="item"

								items="${cartItems}">

								<div class="card mb-4 shadow-sm">

									<div class="row g-0 align-items-center">

										<div class="col-md-3">

											<img

												src="${pageContext.request.contextPath}/assets/images/menu/${item.imagePath}"

												class="img-fluid rounded-start"

												alt="${item.itemName}">

										</div>

										<div class="col-md-9">

											<div class="card-body">

												<div

													class="d-flex justify-content-between">

													<h4>

														${item.itemName}

													</h4>

													<h5>

														₹ ${item.price}

													</h5>

												</div>

												<p>

													${item.description}

												</p>

												<div

													class="row align-items-center">

													<div class="col-md-4">

														<form

															action="${pageContext.request.contextPath}/CartServlet"

															method="post"

															class="d-flex gap-2">

															<input

																type="hidden"

																name="action"

																value="update">

															<input

																type="hidden"

																name="cartItemId"

																value="${item.cartItemId}">

															<input

																type="number"

																class="form-control"

																name="quantity"

																value="${item.quantity}"

																min="1"

																style="width: 80px;">

															<button

																type="submit"

																class="btn btn-sm btn-success">

																<i class="fa-solid fa-rotate"></i>

															</button>

														</form>

													</div>

													<div class="col-md-3">

														<strong>

															₹ ${item.totalPrice}

														</strong>

													</div>

													<div class="col-md-5 text-end">

														<a

															href="${pageContext.request.contextPath}/CartServlet?action=remove&cartItemId=${item.cartItemId}"

															class="btn btn-danger"

															onclick="return confirm('Remove this item?')">

															<i class="fa-solid fa-trash"></i>

															Remove

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

								<h3>

									Your Cart is Empty

								</h3>

								<p>

									Add some delicious food to continue.

								</p>

								<a

									href="${pageContext.request.contextPath}/customer/restaurants.jsp"

									class="btn btn-warning">

									Browse Restaurants

								</a>

							</div>

						</c:otherwise>

					</c:choose>

				</div>

				<!-- =====================================
						ORDER SUMMARY
				===================================== -->

				<div class="col-lg-4">

					<div class="card shadow">

						<div class="card-header">

							<h4>

								Order Summary

							</h4>

						</div>

						<div class="card-body">

							<div

								class="d-flex justify-content-between mb-3">

								<span>

									Subtotal

								</span>

								<span>

									₹ ${cart.subTotal}

								</span>

							</div>

							<div

								class="d-flex justify-content-between mb-3">

								<span>

									Delivery Fee

								</span>

								<span>

									₹ ${cart.deliveryFee}

								</span>

							</div>

							<div

								class="d-flex justify-content-between mb-3">

								<span>

									Tax (GST)

								</span>

								<span>

									₹ ${cart.tax}

								</span>

							</div>

							<hr>

							<div

								class="d-flex justify-content-between">

								<h5>

									Grand Total

								</h5>

								<h4>

									₹ ${cart.grandTotal}

								</h4>

							</div>

							<hr>

							<form

								action="${pageContext.request.contextPath}/CouponServlet"

								method="post">

								<div class="input-group mb-3">

									<input

										type="text"

										class="form-control"

										name="couponCode"

										placeholder="Coupon Code">

									<button

										class="btn btn-outline-warning"

										type="submit">

										Apply

									</button>

								</div>

							</form>

							<div class="d-grid mb-3">

								<a

									href="${pageContext.request.contextPath}/customer/checkout.jsp"

									class="btn btn-warning btn-lg"

									${empty cartItems ? 'disabled' : ''}>

									<i class="fa-solid fa-credit-card me-2"></i>

									Proceed To Checkout

								</a>

							</div>

							<div class="d-grid">

								<a

									href="${pageContext.request.contextPath}/customer/restaurants.jsp"

									class="btn btn-outline-secondary">

									<i class="fa-solid fa-utensils me-2"></i>

									Continue Shopping

								</a>

							</div>

						</div>

					</div>

					<!-- =====================================

								DELIVERY INFO

					===================================== -->

					<div class="card shadow mt-4">

						<div class="card-header">

							<h5>

								Delivery Information

							</h5>

						</div>

						<div class="card-body">

							<div class="mb-3">

								<i class="fa-solid fa-truck-fast me-2 text-warning"></i>

								Estimated Delivery

								<strong>

									30 - 45 Minutes

								</strong>

							</div>

							<div class="mb-3">

								<i class="fa-solid fa-wallet me-2 text-success"></i>

								Cash On Delivery Available

							</div>

							<div>

								<i class="fa-solid fa-shield-halved me-2 text-primary"></i>

								100% Secure Payment

							</div>

						</div>

					</div>

				</div>

			</div>

		</div>

	</section>

</main>

<script

	src="${pageContext.request.contextPath}/assets/js/cart.js">

</script>

<%@ include file="../includes/footer.jsp"%>