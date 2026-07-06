<%@ page language="java"
	contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="c"
	uri="jakarta.tags.core"%>

<%@ include file="../includes/header.jsp"%>

<link rel="stylesheet"
	href="${pageContext.request.contextPath}/assets/css/orders.css">

<%@ include file="../includes/navbar.jsp"%>

<main class="orders-page">

	<!-- ==========================================
				PAGE HEADER
	========================================== -->

	<section class="page-header py-5">

		<div class="container text-center">

			<h1>

				My Orders

			</h1>

			<p>

				View and manage all your previous orders.

			</p>

		</div>

	</section>

	<!-- ==========================================
				ORDER STATISTICS
	========================================== -->

	<section class="order-statistics py-4">

		<div class="container">

			<div class="row">

				<div class="col-lg-3 col-md-6 mb-4">

					<div class="card text-center shadow-sm">

						<div class="card-body">

							<i class="fa-solid fa-cart-shopping fa-3x text-warning mb-3"></i>

							<h3>

								${totalOrders}

							</h3>

							<p>

								Total Orders

							</p>

						</div>

					</div>

				</div>

				<div class="col-lg-3 col-md-6 mb-4">

					<div class="card text-center shadow-sm">

						<div class="card-body">

							<i class="fa-solid fa-circle-check fa-3x text-success mb-3"></i>

							<h3>

								${completedOrders}

							</h3>

							<p>

								Completed

							</p>

						</div>

					</div>

				</div>

				<div class="col-lg-3 col-md-6 mb-4">

					<div class="card text-center shadow-sm">

						<div class="card-body">

							<i class="fa-solid fa-motorcycle fa-3x text-primary mb-3"></i>

							<h3>

								${activeOrders}

							</h3>

							<p>

								Active Orders

							</p>

						</div>

					</div>

				</div>

				<div class="col-lg-3 col-md-6 mb-4">

					<div class="card text-center shadow-sm">

						<div class="card-body">

							<i class="fa-solid fa-indian-rupee-sign fa-3x text-danger mb-3"></i>

							<h3>

								₹ ${totalSpent}

							</h3>

							<p>

								Total Spent

							</p>

						</div>

					</div>

				</div>

			</div>

		</div>

	</section>

	<!-- ==========================================
				ORDERS LIST
	========================================== -->

	<section class="orders-list pb-5">

		<div class="container">

			<!-- Status Filter -->

			<div class="mb-4">

				<div class="d-flex flex-wrap gap-2">

					<a

						href="${pageContext.request.contextPath}/orders"

						class="btn ${empty param.status ? 'btn-warning' : 'btn-outline-warning'}">

						All

					</a>

					<a

						href="${pageContext.request.contextPath}/orders?status=PLACED"

						class="btn ${param.status == 'PLACED' ? 'btn-warning' : 'btn-outline-warning'}">

						Placed

					</a>

					<a

						href="${pageContext.request.contextPath}/orders?status=PREPARING"

						class="btn ${param.status == 'PREPARING' ? 'btn-warning' : 'btn-outline-warning'}">

						Preparing

					</a>

					<a

						href="${pageContext.request.contextPath}/orders?status=OUT_FOR_DELIVERY"

						class="btn ${param.status == 'OUT_FOR_DELIVERY' ? 'btn-warning' : 'btn-outline-warning'}">

						Out for Delivery

					</a>

					<a

						href="${pageContext.request.contextPath}/orders?status=DELIVERED"

						class="btn ${param.status == 'DELIVERED' ? 'btn-warning' : 'btn-outline-warning'}">

						Delivered

					</a>

					<a

						href="${pageContext.request.contextPath}/orders?status=CANCELLED"

						class="btn ${param.status == 'CANCELLED' ? 'btn-warning' : 'btn-outline-warning'}">

						Cancelled

					</a>

				</div>

			</div>

			<c:choose>

				<c:when test="${not empty orderList}">

					<c:forEach

						var="order"

						items="${orderList}">

						<div class="card shadow-sm mb-4">

							<div class="card-body">

								<div class="row align-items-center">

									<div class="col-lg-3">

										<h5>

											Order #

											${order.orderId}

										</h5>

										<p class="text-muted">

											${order.orderDate}

										</p>

									</div>

									<div class="col-lg-2">

										<strong>

											₹ ${order.totalAmount}

										</strong>

									</div>

									<div class="col-lg-2">

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

									</div>

									<div class="col-lg-2">

										${order.paymentMethod}

									</div>

									<div class="col-lg-3 text-end">

										<a

											href="${pageContext.request.contextPath}/customer/order-details.jsp?orderId=${order.orderId}"

											class="btn btn-warning">

											View Details

										</a>

									</div>

								</div>

								<hr>

								<div class="row">

									<div class="col-lg-6">

										<strong>

											Delivery Address

										</strong>

										<p>

											${order.deliveryAddress}

										</p>

									</div>

									<div class="col-lg-6">

										<strong>

											Restaurant

										</strong>

										<p>

											${order.restaurantName}

										</p>

									</div>

								</div>

								<div class="mt-3">

									<c:if test="${order.status == 'DELIVERED'}">

										<a

											href="${pageContext.request.contextPath}/orders?action=reorder&orderId=${order.orderId}"

											class="btn btn-outline-success">

											<i class="fa-solid fa-rotate-right me-2"></i>

											Reorder

										</a>

									</c:if>

									<c:if test="${order.status == 'PLACED' || order.status == 'PREPARING'}">

										<a

											href="${pageContext.request.contextPath}/orders?action=cancel&orderId=${order.orderId}"

											class="btn btn-outline-danger"

											onclick="return confirm('Cancel this order?')">

											<i class="fa-solid fa-ban me-2"></i>

											Cancel Order

										</a>

									</c:if>

									<a

										href="${pageContext.request.contextPath}/InvoiceServlet?orderId=${order.orderId}"

										class="btn btn-outline-secondary">

										<i class="fa-solid fa-file-arrow-down me-2"></i>

										Invoice

									</a>

								</div>

							</div>

						</div>

					</c:forEach>

				</c:when>

				<c:otherwise>

					<div class="alert alert-info text-center">

						<h3>

							No Orders Found

						</h3>

						<p>

							Start ordering your favourite food.

						</p>

						<a

							href="${pageContext.request.contextPath}/restaurants"

							class="btn btn-warning">

							Browse Restaurants

						</a>

					</div>

				</c:otherwise>

			</c:choose>

			<!-- ==========================================

						PAGINATION

			========================================== -->

			<c:if test="${totalPages > 1}">

				<nav>

					<ul class="pagination justify-content-center">

						<li class="page-item ${currentPage == 1 ? 'disabled' : ''}">

							<a class="page-link"

							   href="${pageContext.request.contextPath}/orders?page=${currentPage - 1}&status=${param.status}">

								Previous

							</a>

						</li>

						<c:forEach begin="1" end="${totalPages}" var="i">

							<li class="page-item ${i == currentPage ? 'active' : ''}">

								<a class="page-link"

								   href="${pageContext.request.contextPath}/orders?page=${i}&status=${param.status}">

									${i}

								</a>

							</li>

						</c:forEach>

						<li class="page-item ${currentPage == totalPages ? 'disabled' : ''}">

							<a class="page-link"

							   href="${pageContext.request.contextPath}/orders?page=${currentPage + 1}&status=${param.status}">

								Next

							</a>

						</li>

					</ul>

				</nav>

			</c:if>

		</div>

	</section>

</main>

<script

	src="${pageContext.request.contextPath}/assets/js/orders.js">

</script>

<%@ include file="../includes/footer.jsp"%>