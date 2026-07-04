<%@ page language="java"
	contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="c"
	uri="jakarta.tags.core"%>

<%@ include file="../includes/header.jsp"%>

<link rel="stylesheet"
	href="${pageContext.request.contextPath}/assets/css/checkout.css">

<%@ include file="../includes/navbar.jsp"%>

<main class="checkout-page">

	<!-- ==========================================
				PAGE HEADER
	========================================== -->

	<section class="page-header py-5">

		<div class="container text-center">

			<h1>

				Checkout

			</h1>

			<p>

				Review your order and complete your purchase.

			</p>

		</div>

	</section>

	<!-- ==========================================
				CHECKOUT
	========================================== -->

	<section class="checkout-section pb-5">

		<div class="container">

			<form

				action="${pageContext.request.contextPath}/OrderServlet"

				method="post">

				<div class="row">

					<!-- =====================================
							LEFT SIDE
					===================================== -->

					<div class="col-lg-8">

						<!-- Delivery Address -->

						<div class="card shadow-sm mb-4">

							<div class="card-header">

								<h4>

									Delivery Address

								</h4>

							</div>

							<div class="card-body">

								<c:choose>

									<c:when test="${not empty addressList}">

										<c:forEach

											var="address"

											items="${addressList}">

											<div class="form-check mb-3">

												<input

													class="form-check-input"

													type="radio"

													name="addressId"

													value="${address.addressId}"

													${address.defaultAddress ? 'checked' : ''}

													required>

												<label

													class="form-check-label">

													<strong>

														${address.addressType}

													</strong>

													<br>

													${address.houseNo},

													${address.street},

													${address.city},

													${address.state}

													-

													${address.pincode}

												</label>

											</div>

										</c:forEach>

									</c:when>

									<c:otherwise>

										<div class="alert alert-warning">

											No address found. Please add an address.

										</div>

									</c:otherwise>

								</c:choose>

								<div class="mt-3">

									<a

										href="${pageContext.request.contextPath}/customer/profile.jsp#address"

										class="btn btn-outline-warning">

										<i class="fa-solid fa-plus me-2"></i>

										Add New Address

									</a>

								</div>

							</div>

						</div>

						<!-- Payment Method -->

						<div class="card shadow-sm mb-4">

							<div class="card-header">

								<h4>

									Payment Method

								</h4>

							</div>

							<div class="card-body">

								<div class="form-check mb-3">

									<input

										class="form-check-input"

										type="radio"

										name="paymentMethod"

										value="UPI"

										checked

										required>

									<label

										class="form-check-label">

										<i class="fa-brands fa-google-pay me-2"></i>

										UPI Payment

									</label>

								</div>

								<div class="form-check mb-3">

									<input

										class="form-check-input"

										type="radio"

										name="paymentMethod"

										value="CARD">

									<label

										class="form-check-label">

										<i class="fa-solid fa-credit-card me-2"></i>

										Debit / Credit Card

									</label>

								</div>

								<div class="form-check mb-3">

									<input

										class="form-check-input"

										type="radio"

										name="paymentMethod"

										value="NET_BANKING">

									<label

										class="form-check-label">

										<i class="fa-solid fa-building-columns me-2"></i>

										Net Banking

									</label>

								</div>

								<div class="form-check">

									<input

										class="form-check-input"

										type="radio"

										name="paymentMethod"

										value="COD">

									<label

										class="form-check-label">

										<i class="fa-solid fa-money-bill-wave me-2"></i>

										Cash On Delivery

									</label>

								</div>

							</div>

						</div>

						<!-- Order Instructions -->

						<div class="card shadow-sm">

							<div class="card-header">

								<h4>

									Order Instructions

								</h4>

							</div>

							<div class="card-body">

								<textarea

									class="form-control"

									name="instructions"

									rows="4"

									placeholder="Special cooking instructions, delivery notes..."></textarea>

							</div>

						</div>

					</div>

					<!-- =====================================
							RIGHT SIDE
					===================================== -->

					<div class="col-lg-4">

						<div class="card shadow">

							<div class="card-header">

								<h4>

									Order Summary

								</h4>

							</div>

							<div class="card-body">

								<c:forEach

									var="item"

									items="${cartItems}">

									<div class="d-flex justify-content-between mb-3">

										<div>

											<strong>

												${item.itemName}

											</strong>

											<br>

											<small>

												Qty :

												${item.quantity}

											</small>

										</div>

										<div>

											₹ ${item.totalPrice}

										</div>

									</div>

								</c:forEach>

								<hr>

								<div class="d-flex justify-content-between mb-2">

									<span>

										Subtotal

									</span>

									<span>

										₹ ${cart.subTotal}

									</span>

								</div>

								<div class="d-flex justify-content-between mb-2">

									<span>

										Delivery Fee

									</span>

									<span>

										₹ ${cart.deliveryFee}

									</span>

								</div>

								<div class="d-flex justify-content-between mb-2">

									<span>

										GST

									</span>

									<span>

										₹ ${cart.tax}

									</span>

								</div>

								<hr>

								<div class="d-flex justify-content-between">

									<h4>

										Total

									</h4>

									<h4>

										₹ ${cart.grandTotal}

									</h4>

								</div>

								<hr>

								<div class="form-check mb-4">

									<input

										class="form-check-input"

										type="checkbox"

										id="agree"

										required>

									<label

										class="form-check-label"

										for="agree">

										I confirm that the above order details

										and delivery address are correct.

									</label>

								</div>

								<div class="d-grid">

									<button

										type="submit"

										class="btn btn-warning btn-lg">

										<i class="fa-solid fa-circle-check me-2"></i>

										Place Order

									</button>

								</div>

								<div class="d-grid mt-3">

									<a

										href="${pageContext.request.contextPath}/customer/cart.jsp"

										class="btn btn-outline-secondary">

										<i class="fa-solid fa-arrow-left me-2"></i>

										Back To Cart

									</a>

								</div>

							</div>

						</div>

						<!-- ==========================

								DELIVERY DETAILS

						========================== -->

						<div class="card shadow mt-4">

							<div class="card-header">

								<h5>

									Estimated Delivery

								</h5>

							</div>

							<div class="card-body">

								<p>

									<i class="fa-solid fa-truck-fast text-warning me-2"></i>

									30 - 45 Minutes

								</p>

								<p>

									<i class="fa-solid fa-store text-success me-2"></i>

									Restaurant Preparing Fresh Food

								</p>

								<p>

									<i class="fa-solid fa-phone text-primary me-2"></i>

									Delivery partner will contact you

									before arrival.

								</p>

							</div>

						</div>

						<!-- ==========================

								SECURE PAYMENT

						========================== -->

						<div class="card shadow mt-4">

							<div class="card-header">

								<h5>

									Secure Checkout

								</h5>

							</div>

							<div class="card-body">

								<div class="mb-3">

									<i class="fa-solid fa-lock text-success me-2"></i>

									SSL Encrypted Payment

								</div>

								<div class="mb-3">

									<i class="fa-solid fa-credit-card text-primary me-2"></i>

									Multiple Payment Options

								</div>

								<div>

									<i class="fa-solid fa-shield-halved text-warning me-2"></i>

									100% Safe & Secure

								</div>

							</div>

						</div>

					</div>

				</div>

			</form>

		</div>

	</section>

</main>

<script

	src="${pageContext.request.contextPath}/assets/js/checkout.js">

</script>

<%@ include file="../includes/footer.jsp"%>