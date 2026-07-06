<%@ page language="java"
	contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="c"
	uri="jakarta.tags.core"%>

<%@ include file="../includes/header.jsp"%>

<link rel="stylesheet"
	href="${pageContext.request.contextPath}/assets/css/order-details.css">

<%@ include file="../includes/navbar.jsp"%>

<main class="order-details-page">

	<!-- ==========================================
				PAGE HEADER
	========================================== -->

	<section class="page-header py-4">

		<div class="container">

			<div class="d-flex justify-content-between align-items-center">

				<div>

					<h1>

						Order Details

					</h1>

					<p class="text-muted">

						Order #${order.orderId}

					</p>

				</div>

				<a

					href="${pageContext.request.contextPath}/customer/orders.jsp"

					class="btn btn-outline-secondary">

					<i class="fa-solid fa-arrow-left me-2"></i>

					Back to Orders

				</a>

			</div>

		</div>

	</section>

	<!-- ==========================================
				ORDER SUMMARY
	========================================== -->

	<section class="order-summary py-4">

		<div class="container">

			<div class="row">

				<div class="col-lg-8">

					<div class="card shadow-sm">

						<div class="card-body">

							<div class="row align-items-center">

								<div class="col-md-3">

									<strong>

										Order Date

									</strong>

									<p>

										${order.orderDate}

									</p>

								</div>

								<div class="col-md-3">

									<strong>

										Status

									</strong>

									<p>

										<c:choose>

											<c:when test="${order.status == 'DELIVERED'}">

												<span class="badge bg-success">

													Delivered

												</span>

											</c:when>

											<c:when test="${order.status == 'PREPARING'}">

												<span class="badge bg-warning text-dark">

													Preparing

												</span>

											</c:when>

											<c:when test="${order.status == 'OUT_FOR_DELIVERY'}">

												<span class="badge bg-primary">

													Out for Delivery

												</span>

											</c:when>

											<c:when test="${order.status == 'PLACED'}">

												<span class="badge bg-info text-dark">

													Placed

												</span>

											</c:when>

											<c:otherwise>

												<span class="badge bg-danger">

													Cancelled

												</span>

											</c:otherwise>

										</c:choose>

									</p>

								</div>

								<div class="col-md-3">

									<strong>

										Total Amount

									</strong>

									<h4>

										₹ ${order.totalAmount}

									</h4>

								</div>

								<div class="col-md-3">

									<strong>

										Payment

									</strong>

									<p>

										${order.paymentMethod}

									</p>

								</div>

							</div>

						</div>

					</div>

				</div>

				<div class="col-lg-4">

					<div class="card shadow-sm">

						<div class="card-body text-center">

							<c:if test="${order.status == 'PLACED' || order.status == 'PREPARING'}">

								<a

									href="${pageContext.request.contextPath}/orders?action=cancel&orderId=${order.orderId}"

									class="btn btn-danger w-100 mb-2"

									onclick="return confirm('Cancel this order?')">

									<i class="fa-solid fa-ban me-2"></i>

									Cancel Order

								</a>

							</c:if>

							<a

								href="${pageContext.request.contextPath}/InvoiceServlet?orderId=${order.orderId}"

								class="btn btn-outline-secondary w-100">

								<i class="fa-solid fa-file-arrow-down me-2"></i>

								Download Invoice

							</a>

						</div>

					</div>

				</div>

			</div>

		</div>

	</section>

	<!-- ==========================================
				ORDER TIMELINE
	========================================== -->

	<section class="order-timeline py-4">

		<div class="container">

			<div class="card shadow-sm">

				<div class="card-header">

					<h4>

						Order Progress

					</h4>

				</div>

				<div class="card-body">

					<div class="timeline">

						<div class="timeline-item">

							<div class="timeline-marker

								<c:if test="${order.status != 'CANCELLED'}">

									bg-success

								</c:if>

								<c:if test="${order.status == 'CANCELLED'}">

									bg-danger

								</c:if>">

							</div>

							<div class="timeline-content">

								<h6>

									Order Placed

								</h6>

								<small>

									${order.orderDate}

								</small>

							</div>

						</div>

						<c:if test="${order.status == 'PREPARING' || order.status == 'OUT_FOR_DELIVERY' || order.status == 'DELIVERED'}">

							<div class="timeline-item">

								<div class="timeline-marker

									<c:if test="${order.status == 'PREPARING' || order.status == 'OUT_FOR_DELIVERY' || order.status == 'DELIVERED'}">

										bg-success

									</c:if>">

								</div>

								<div class="timeline-content">

									<h6>

										Restaurant Preparing

									</h6>

									<small>

										${order.preparingTime}

									</small>

								</div>

							</div>

						</c:if>

						<c:if test="${order.status == 'OUT_FOR_DELIVERY' || order.status == 'DELIVERED'}">

							<div class="timeline-item">

								<div class="timeline-marker

									<c:if test="${order.status == 'OUT_FOR_DELIVERY' || order.status == 'DELIVERED'}">

										bg-success

									</c:if>">

								</div>

								<div class="timeline-content">

									<h6>

										Out for Delivery

									</h6>

									<small>

										${order.outForDeliveryTime}

									</small>

								</div>

							</div>

						</c:if>

						<c:if test="${order.status == 'DELIVERED'}">

							<div class="timeline-item">

								<div class="timeline-marker bg-success">

								</div>

								<div class="timeline-content">

									<h6>

										Delivered

									</h6>

									<small>

										${order.deliveredTime}

									</small>

								</div>

							</div>

						</c:if>

						<c:if test="${order.status == 'CANCELLED'}">

							<div class="timeline-item">

								<div class="timeline-marker bg-danger">

								</div>

								<div class="timeline-content">

									<h6>

										Order Cancelled

									</h6>

									<small>

										${order.cancelledTime}

									</small>

								</div>

							</div>

						</c:if>

					</div>

				</div>

			</div>

		</div>

	</section>

	<!-- ==========================================
				ORDER ITEMS
	========================================== -->

	<section class="order-items py-4">

		<div class="container">

			<div class="card shadow-sm">

				<div class="card-header">

					<h4>

						Ordered Items

					</h4>

				</div>

				<div class="card-body">

					<c:forEach

						var="item"

						items="${orderItems}">

						<div class="row border-bottom py-3">

							<div class="col-md-2">

								<img

									src="${pageContext.request.contextPath}/assets/images/menu/${item.imagePath}"

									class="img-fluid rounded"

									alt="${item.itemName}">

							</div>

							<div class="col-md-5">

								<h5>

									${item.itemName}

								</h5>

								<small>

									Qty :

									${item.quantity}

								</small>

							</div>

							<div class="col-md-2">

								<strong>

									₹ ${item.price}

								</strong>

							</div>

							<div class="col-md-3 text-end">

								<strong>

									₹ ${item.totalPrice}

								</strong>

							</div>

						</div>

					</c:forEach>

					<div class="row mt-3">

						<div class="col-md-6 offset-md-6">

							<div class="d-flex justify-content-between">

								<span>

									Subtotal

								</span>

								<span>

									₹ ${order.subtotal}

								</span>

							</div>

							<div class="d-flex justify-content-between">

								<span>

									Delivery Fee

								</span>

								<span>

									₹ ${order.deliveryFee}

								</span>

							</div>

							<div class="d-flex justify-content-between">

								<span>

									Tax

								</span>

								<span>

									₹ ${order.tax}

								</span>

							</div>

							<hr>

							<div class="d-flex justify-content-between">

								<h5>

									Grand Total

								</h5>

								<h5>

									₹ ${order.totalAmount}

								</h5>

							</div>

						</div>

					</div>

				</div>

			</div>

		</div>

	</section>

	<!-- ==========================================
				DELIVERY & PAYMENT DETAILS
	========================================== -->

	<section class="delivery-payment py-4">

		<div class="container">

			<div class="row">

				<div class="col-lg-6">

					<div class="card shadow-sm">

						<div class="card-header">

							<h4>

								Delivery Details

							</h4>

						</div>

						<div class="card-body">

							<p>

								<strong>

									Address

								</strong>

								<br>

								${order.deliveryAddress}

							</p>

							<p>

								<strong>

									Delivery Partner

								</strong>

								<br>

								${order.deliveryPartner}

							</p>

							<p>

								<strong>

									Contact

								</strong>

								<br>

								${order.deliveryContact}

							</p>

							<c:if test="${order.status == 'OUT_FOR_DELIVERY' || order.status == 'DELIVERED'}">

								<p>

									<strong>

										Tracking ID

									</strong>

									<br>

									${order.trackingId}

								</p>

							</c:if>

						</div>

					</div>

				</div>

				<div class="col-lg-6">

					<div class="card shadow-sm">

						<div class="card-header">

							<h4>

								Payment Details

							</h4>

						</div>

						<div class="card-body">

							<p>

								<strong>

									Method

								</strong>

								<br>

								${order.paymentMethod}

							</p>

							<p>

								<strong>

									Status

								</strong>

								<br>

								<c:choose>

									<c:when test="${order.paymentStatus == 'SUCCESS'}">

										<span class="badge bg-success">

											Paid

										</span>

									</c:when>

									<c:when test="${order.paymentStatus == 'PENDING'}">

										<span class="badge bg-warning text-dark">

											Pending

										</span>

									</c:when>

									<c:otherwise>

										<span class="badge bg-danger">

											Failed

										</span>

									</c:otherwise>

								</c:choose>

							</p>

							<p>

								<strong>

									Transaction ID

								</strong>

								<br>

								${order.transactionId}

							</p>

						</div>

					</div>

				</div>

			</div>

		</div>

	</section>

	<!-- ==========================================
				RESTAURANT INFORMATION
	========================================== -->

	<section class="restaurant-info py-4">

		<div class="container">

			<div class="card shadow-sm">

				<div class="card-header">

					<h4>

						Restaurant Details

					</h4>

				</div>

				<div class="card-body">

					<div class="row align-items-center">

						<div class="col-md-2">

							<img

								src="${pageContext.request.contextPath}/assets/images/restaurants/${restaurant.imagePath}"

								class="img-fluid rounded"

								alt="${restaurant.name}">

						</div>

						<div class="col-md-6">

							<h5>

								${restaurant.name}

							</h5>

							<p>

								${restaurant.cuisine}

							</p>

							<p>

								⭐ ${restaurant.rating}

							</p>

						</div>

						<div class="col-md-4 text-end">

							<a

								href="${pageContext.request.contextPath}/menu?restaurantId=${restaurant.restaurantId}"

								class="btn btn-warning">

								View Menu

							</a>

						</div>

					</div>

				</div>

			</div>

		</div>

	</section>

	<!-- ==========================================
				REVIEW SECTION
	========================================== -->

	<c:if test="${order.status == 'DELIVERED'}">

		<section class="review-section py-4">

			<div class="container">

				<div class="card shadow-sm">

					<div class="card-header">

						<h4>

							Rate Your Order

						</h4>

					</div>

					<div class="card-body">

						<c:choose>

							<c:when test="${not empty existingReview}">

								<div class="alert alert-info">

									<p>

										<strong>

											Your Rating

										</strong>

										<br>

										<span class="text-warning">

											${existingReview.rating} ⭐

										</span>

									</p>

									<p>

										<strong>

											Comment

										</strong>

										<br>

										${existingReview.comment}

									</p>

								</div>

							</c:when>

							<c:otherwise>

								<form

									action="${pageContext.request.contextPath}/ReviewServlet"

									method="post">

									<input

										type="hidden"

										name="orderId"

										value="${order.orderId}">

									<input

										type="hidden"

										name="restaurantId"

										value="${restaurant.restaurantId}">

									<div class="mb-3">

										<label

											class="form-label">

											Rating

										</label>

										<div class="rating-stars">

											<input

												type="radio"

												name="rating"

												id="star5"

												value="5">

											<label for="star5">

												<i class="fa-regular fa-star"></i>

											</label>

											<input

												type="radio"

												name="rating"

												id="star4"

												value="4">

											<label for="star4">

												<i class="fa-regular fa-star"></i>

											</label>

											<input

												type="radio"

												name="rating"

												id="star3"

												value="3">

											<label for="star3">

												<i class="fa-regular fa-star"></i>

											</label>

											<input

												type="radio"

												name="rating"

												id="star2"

												value="2">

											<label for="star2">

												<i class="fa-regular fa-star"></i>

											</label>

											<input

												type="radio"

												name="rating"

												id="star1"

												value="1">

											<label for="star1">

												<i class="fa-regular fa-star"></i>

											</label>

										</div>

									</div>

									<div class="mb-3">

										<label

											for="comment"

											class="form-label">

											Comment

										</label>

										<textarea

											class="form-control"

											id="comment"

											name="comment"

											rows="4"

											placeholder="Share your experience..."></textarea>

									</div>

									<button

										type="submit"

										class="btn btn-warning">

										Submit Review

									</button>

								</form>

							</c:otherwise>

						</c:choose>

					</div>

				</div>

			</div>

		</section>

	</c:if>

	<!-- ==========================================
				REORDER BUTTON
	========================================== -->

	<section class="reorder-section py-4">

		<div class="container text-center">

			<a

				href="${pageContext.request.contextPath}/orders?action=reorder&orderId=${order.orderId}"

				class="btn btn-success btn-lg">

				<i class="fa-solid fa-rotate-right me-2"></i>

				Reorder This Order

			</a>

		</div>

	</section>

</main>

<script

	src="${pageContext.request.contextPath}/assets/js/order-details.js">

</script>

<%@ include file="../includes/footer.jsp"%>