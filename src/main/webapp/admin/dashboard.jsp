<%@ page language="java"
	contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<%@ include file="../includes/header.jsp"%>

<link rel="stylesheet"
	href="${pageContext.request.contextPath}/assets/css/dashboard.css">

<%@ include file="../includes/navbar.jsp"%>

<main class="admin-dashboard">

	<!-- ==========================================
				PAGE HEADER
	========================================== -->

	<section class="page-header py-4">

		<div class="container">

			<div class="d-flex justify-content-between align-items-center">

				<h1>

					Admin Dashboard

				</h1>

				<span class="badge bg-warning text-dark">

					Welcome, ${sessionScope.adminName}

				</span>

			</div>

		</div>

	</section>

	<!-- ==========================================
				STATISTICS CARDS
	========================================== -->

	<section class="stats-section py-4">

		<div class="container">

			<div class="row g-4">

				<div class="col-lg-3 col-md-6">

					<div class="card stat-card shadow-sm border-0">

						<div class="card-body">

							<div class="d-flex justify-content-between">

								<div>

									<h6 class="text-muted">

										Total Orders

									</h6>

									<h2 class="fw-bold">

										${totalOrders}

									</h2>

								</div>

								<i class="fa-solid fa-cart-shopping fa-3x text-warning"></i>

							</div>

							<small class="text-success">

								<i class="fa-solid fa-arrow-up"></i>

								12.5%

							</small>

							<small class="text-muted">

								vs last week

							</small>

						</div>

					</div>

				</div>

				<div class="col-lg-3 col-md-6">

					<div class="card stat-card shadow-sm border-0">

						<div class="card-body">

							<div class="d-flex justify-content-between">

								<div>

									<h6 class="text-muted">

										Revenue

									</h6>

									<h2 class="fw-bold">

										₹ ${totalRevenue}

									</h2>

								</div>

								<i class="fa-solid fa-indian-rupee-sign fa-3x text-success"></i>

							</div>

							<small class="text-success">

								<i class="fa-solid fa-arrow-up"></i>

								8.2%

							</small>

							<small class="text-muted">

								vs last week

							</small>

						</div>

					</div>

				</div>

				<div class="col-lg-3 col-md-6">

					<div class="card stat-card shadow-sm border-0">

						<div class="card-body">

							<div class="d-flex justify-content-between">

								<div>

									<h6 class="text-muted">

										Active Restaurants

									</h6>

									<h2 class="fw-bold">

										${activeRestaurants}

									</h2>

								</div>

								<i class="fa-solid fa-store fa-3x text-primary"></i>

							</div>

							<small class="text-success">

								<i class="fa-solid fa-arrow-up"></i>

								3

							</small>

							<small class="text-muted">

								new this month

							</small>

						</div>

					</div>

				</div>

				<div class="col-lg-3 col-md-6">

					<div class="card stat-card shadow-sm border-0">

						<div class="card-body">

							<div class="d-flex justify-content-between">

								<div>

									<h6 class="text-muted">

										Total Users

									</h6>

									<h2 class="fw-bold">

										${totalUsers}

									</h2>

								</div>

								<i class="fa-solid fa-users fa-3x text-danger"></i>

							</div>

							<small class="text-success">

								<i class="fa-solid fa-arrow-up"></i>

								5.7%

							</small>

							<small class="text-muted">

								vs last week

							</small>

						</div>

					</div>

				</div>

			</div>

		</div>

	</section>

	<!-- ==========================================
				CHARTS & RECENT ACTIVITY
	========================================== -->

	<section class="charts-section py-4">

		<div class="container">

			<div class="row">

				<div class="col-lg-8">

					<div class="card shadow-sm">

						<div class="card-header">

							<h5>

								Order Trends (Last 7 Days)

							</h5>

						</div>

						<div class="card-body">

							<canvas id="orderChart"

								height="250">

							</canvas>

						</div>

					</div>

				</div>

				<div class="col-lg-4">

					<div class="card shadow-sm">

						<div class="card-header">

							<h5>

								Top Categories

							</h5>

						</div>

						<div class="card-body">

							<ul class="list-unstyled">

								<c:forEach

									var="category"

									items="${topCategories}">

									<li class="mb-3">

										<div class="d-flex justify-content-between">

											<span>

												${category.name}

											</span>

											<span>

												${category.count}

											</span>

										</div>

										<div class="progress">

											<div

												class="progress-bar"

												role="progressbar"

												style="width: ${category.percentage}%;"

												aria-valuenow="${category.percentage}"

												aria-valuemin="0"

												aria-valuemax="100">

											</div>

										</div>

									</li>

								</c:forEach>

							</ul>

						</div>

					</div>

				</div>

			</div>

		</div>

	</section>

	<!-- ==========================================
				RECENT ORDERS
	========================================== -->

	<section class="recent-orders py-4">

		<div class="container">

			<div class="card shadow-sm">

				<div class="card-header d-flex justify-content-between">

					<h5>

						Recent Orders

					</h5>

					<a

						href="${pageContext.request.contextPath}/admin/orders.jsp"

						class="btn btn-sm btn-warning">

						View All

					</a>

				</div>

				<div class="card-body">

					<table class="table table-hover">

						<thead>

							<tr>

								<th>Order ID</th>

								<th>Customer</th>

								<th>Restaurant</th>

								<th>Amount</th>

								<th>Status</th>

								<th>Action</th>

							</tr>

						</thead>

						<tbody>

							<c:forEach

								var="order"

								items="${recentOrders}">

								<tr>

									<td>

										#${order.orderId}

									</td>

									<td>

										${order.customerName}

									</td>

									<td>

										${order.restaurantName}

									</td>

									<td>

										₹ ${order.totalAmount}

									</td>

									<td>

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

									</td>

									<td>

										<a

											href="${pageContext.request.contextPath}/admin/order-details.jsp?orderId=${order.orderId}"

											class="btn btn-sm btn-outline-warning">

											View

										</a>

									</td>

								</tr>

							</c:forEach>

							<c:if test="${empty recentOrders}">

								<tr>

									<td colspan="6" class="text-center text-muted">

										No recent orders

									</td>

								</tr>

							</c:if>

						</tbody>

					</table>

				</div>

			</div>

		</div>

	</section>

	<!-- ==========================================
				QUICK ACTIONS
	========================================== -->

	<section class="quick-actions py-4">

		<div class="container">

			<div class="row g-4">

				<div class="col-lg-3 col-md-6">

					<a

						href="${pageContext.request.contextPath}/admin/restaurants.jsp?action=add"

						class="text-decoration-none">

						<div class="card action-card shadow-sm text-center">

							<div class="card-body">

								<i class="fa-solid fa-store fa-3x text-primary"></i>

								<h5 class="mt-3">

									Add Restaurant

								</h5>

							</div>

						</div>

					</a>

				</div>

				<div class="col-lg-3 col-md-6">

					<a

						href="${pageContext.request.contextPath}/admin/menu.jsp"

						class="text-decoration-none">

						<div class="card action-card shadow-sm text-center">

							<div class="card-body">

								<i class="fa-solid fa-utensils fa-3x text-success"></i>

								<h5 class="mt-3">

									Manage Menu

								</h5>

							</div>

						</div>

					</a>

				</div>

				<div class="col-lg-3 col-md-6">

					<a

						href="${pageContext.request.contextPath}/admin/orders.jsp"

						class="text-decoration-none">

						<div class="card action-card shadow-sm text-center">

							<div class="card-body">

								<i class="fa-solid fa-list-check fa-3x text-warning"></i>

								<h5 class="mt-3">

									View Orders

								</h5>

							</div>

						</div>

					</a>

				</div>

				<div class="col-lg-3 col-md-6">

					<a

						href="${pageContext.request.contextPath}/admin/users.jsp"

						class="text-decoration-none">

						<div class="card action-card shadow-sm text-center">

							<div class="card-body">

								<i class="fa-solid fa-users-gear fa-3x text-danger"></i>

								<h5 class="mt-3">

									Manage Users

								</h5>

							</div>

						</div>

					</a>

				</div>

			</div>

		</div>

	</section>

	<!-- ==========================================
				ADMIN PROFILE
	========================================== -->

	<section class="admin-profile py-4">

		<div class="container">

			<div class="card shadow-sm">

				<div class="card-body">

					<div class="row align-items-center">

						<div class="col-md-2 text-center">

							<img

								src="${pageContext.request.contextPath}/assets/images/admin-avatar.png"

								class="rounded-circle"

								width="100"

								alt="Admin">

						</div>

						<div class="col-md-6">

							<h4>

								${sessionScope.adminName}

							</h4>

							<p class="text-muted">

								Administrator • OnlineFoodDelivery

							</p>

							<p>

								<i class="fa-solid fa-envelope me-2"></i>

								${sessionScope.adminEmail}

							</p>

						</div>

						<div class="col-md-4 text-end">

							<a

								href="${pageContext.request.contextPath}/auth/logout"

								class="btn btn-outline-danger">

								<i class="fa-solid fa-right-from-bracket me-2"></i>

								Logout

							</a>

						</div>

					</div>

				</div>

			</div>

		</div>

	</section>

</main>

<!-- ==========================================
			CHART.JS
========================================== -->

<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>

<script>

	// Order Trend Chart

	const ctx = document.getElementById('orderChart').getContext('2d');

	const orderChart = new Chart(ctx, {

		type: 'line',

		data: {

			labels: ${orderTrendLabels},  // e.g., ['Mon', 'Tue', ...]

			datasets: [{

				label: 'Orders',

				data: ${orderTrendData},

				borderColor: 'rgb(255, 193, 7)',

				backgroundColor: 'rgba(255, 193, 7, 0.2)',

				tension: 0.3,

				fill: true

			}]

		},

		options: {

			responsive: true,

			plugins: {

				legend: {

					display: false

				}

			},

			scales: {

				y: {

					beginAtZero: true

				}

			}

		}

	});

</script>

<script

	src="${pageContext.request.contextPath}/assets/js/dashboard.js">

</script>

<%@ include file="../includes/footer.jsp"%>