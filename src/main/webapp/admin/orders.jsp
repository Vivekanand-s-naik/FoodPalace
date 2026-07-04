<%@ page language="java"
	contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="c"
	uri="jakarta.tags.core"%>

<%@ include file="../includes/header.jsp"%>

<link rel="stylesheet"
	href="${pageContext.request.contextPath}/assets/css/admin-orders.css">

<%@ include file="../includes/navbar.jsp"%>

<main class="admin-orders">

	<!-- ==========================================
				PAGE HEADER
	========================================== -->

	<section class="page-header py-4">

		<div class="container">

			<div class="d-flex justify-content-between align-items-center">

				<h1>

					Manage Orders

				</h1>

				<div>

					<span class="badge bg-warning text-dark me-2">

						${pendingOrders} Pending

					</span>

					<span class="badge bg-primary">

						${totalOrders} Total

					</span>

				</div>

			</div>

		</div>

	</section>

	<!-- ==========================================
				ORDERS TABLE
	========================================== -->

	<section class="orders-table py-4">

		<div class="container">

			<div class="card shadow-sm">

				<div class="card-header">

					<div class="row align-items-center">

						<div class="col-md-4">

							<h5>

								All Orders

							</h5>

						</div>

						<div class="col-md-8">

							<form

								action="${pageContext.request.contextPath}/admin/orders"

								method="get"

								class="row g-2">

								<div class="col-md-4">

									<input

										type="text"

										name="search"

										class="form-control"

										placeholder="Order ID or Customer"

										value="${param.search}">

								</div>

								<div class="col-md-3">

									<select

										name="status"

										class="form-select">

										<option value="">

											All Status

										</option>

										<option value="PLACED" ${param.status == 'PLACED' ? 'selected' : ''}>

											Placed

										</option>

										<option value="PREPARING" ${param.status == 'PREPARING' ? 'selected' : ''}>

											Preparing

										</option>

										<option value="OUT_FOR_DELIVERY" ${param.status == 'OUT_FOR_DELIVERY' ? 'selected' : ''}>

											Out for Delivery

										</option>

										<option value="DELIVERED" ${param.status == 'DELIVERED' ? 'selected' : ''}>

											Delivered

										</option>

										<option value="CANCELLED" ${param.status == 'CANCELLED' ? 'selected' : ''}>

											Cancelled

										</option>

									</select>

								</div>

								<div class="col-md-3">

									<input

										type="date"

										name="date"

										class="form-control"

										value="${param.date}">

								</div>

								<div class="col-md-2">

									<button

										type="submit"

										class="btn btn-outline-warning w-100">

										Filter

									</button>

								</div>

							</form>

						</div>

					</div>

				</div>

				<div class="card-body">

					<table class="table table-hover">

						<thead>

							<tr>

								<th>Order ID</th>

								<th>Customer</th>

								<th>Restaurant</th>

								<th>Amount</th>

								<th>Date</th>

								<th>Status</th>

								<th>Actions</th>

							</tr>

						</thead>

						<tbody>

							<c:forEach

								var="order"

								items="${orders}">

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

										${order.orderDate}

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

										<button

											class="btn btn-sm btn-outline-warning"

											data-bs-toggle="modal"

											data-bs-target="#orderModal${order.orderId}">

											<i class="fa-solid fa-eye"></i>

										</button>

										<c:if test="${order.status != 'DELIVERED' && order.status != 'CANCELLED'}">

											<button

												class="btn btn-sm btn-outline-success"

												data-bs-toggle="modal"

												data-bs-target="#updateStatusModal${order.orderId}">

												<i class="fa-solid fa-pen"></i>

											</button>

										</c:if>

									</td>

								</tr>

							</c:forEach>

							<c:if test="${empty orders}">

								<tr>

									<td colspan="7" class="text-center text-muted">

										No orders found.

									</td>

								</tr>

							</c:if>

						</tbody>

					</table>

					<!-- ==========================================

								PAGINATION

					========================================== -->

					<div class="d-flex justify-content-between align-items-center mt-4">

						<span class="text-muted small">

							Showing ${startIndex} to ${endIndex} of ${totalCount} entries

						</span>

						<nav>

							<ul class="pagination mb-0">

								<li class="page-item ${currentPage == 1 ? 'disabled' : ''}">

									<a class="page-link"

									   href="${pageContext.request.contextPath}/admin/orders?page=${currentPage - 1}&search=${param.search}&status=${param.status}&date=${param.date}">

										Previous

									</a>

								</li>

								<c:forEach begin="1" end="${totalPages}" var="i">

									<li class="page-item ${i == currentPage ? 'active' : ''}">

										<a class="page-link"

										   href="${pageContext.request.contextPath}/admin/orders?page=${i}&search=${param.search}&status=${param.status}&date=${param.date}">

											${i}

										</a>

									</li>

								</c:forEach>

								<li class="page-item ${currentPage == totalPages ? 'disabled' : ''}">

									<a class="page-link"

									   href="${pageContext.request.contextPath}/admin/orders?page=${currentPage + 1}&search=${param.search}&status=${param.status}&date=${param.date}">

										Next

									</a>

								</li>

							</ul>

						</nav>

					</div>

				</div>

			</div>

		</div>

	</section>

	<!-- ==========================================

				ORDER DETAIL MODALS

	========================================== -->

	<c:forEach var="order" items="${orders}">

		<!-- Order Details Modal -->

		<div class="modal fade" id="orderModal${order.orderId}" tabindex="-1">

			<div class="modal-dialog modal-lg">

				<div class="modal-content">

					<div class="modal-header">

						<h5 class="modal-title">

							Order #${order.orderId} Details

						</h5>

						<button type="button" class="btn-close" data-bs-dismiss="modal"></button>

					</div>

					<div class="modal-body">

						<div class="row">

							<div class="col-md-6">

								<p><strong>Customer:</strong> ${order.customerName}</p>

								<p><strong>Email:</strong> ${order.customerEmail}</p>

								<p><strong>Phone:</strong> ${order.customerPhone}</p>

							</div>

							<div class="col-md-6">

								<p><strong>Restaurant:</strong> ${order.restaurantName}</p>

								<p><strong>Order Date:</strong> ${order.orderDate}</p>

								<p><strong>Status:</strong> ${order.status}</p>

							</div>

							<div class="col-12">

								<p><strong>Delivery Address:</strong><br>${order.deliveryAddress}</p>

							</div>

							<div class="col-12">

								<h6>Order Items</h6>

								<table class="table table-sm">

									<thead>

										<tr>

											<th>Item</th>

											<th>Qty</th>

											<th>Price</th>

											<th>Total</th>

										</tr>

									</thead>

									<tbody>

										<c:forEach var="item" items="${order.orderItems}">

											<tr>

												<td>${item.itemName}</td>

												<td>${item.quantity}</td>

												<td>₹ ${item.price}</td>

												<td>₹ ${item.totalPrice}</td>

											</tr>

										</c:forEach>

									</tbody>

								</table>

							</div>

							<div class="col-12 text-end">

								<h5>Grand Total: ₹ ${order.totalAmount}</h5>

							</div>

						</div>

					</div>

				</div>

			</div>

		</div>

		<!-- Update Status Modal -->

		<c:if test="${order.status != 'DELIVERED' && order.status != 'CANCELLED'}">

			<div class="modal fade" id="updateStatusModal${order.orderId}" tabindex="-1">

				<div class="modal-dialog">

					<div class="modal-content">

						<div class="modal-header">

							<h5 class="modal-title">

								Update Order #${order.orderId} Status

							</h5>

							<button type="button" class="btn-close" data-bs-dismiss="modal"></button>

						</div>

						<div class="modal-body">

							<form action="${pageContext.request.contextPath}/admin/orders" method="post">

								<input type="hidden" name="action" value="updateStatus">

								<input type="hidden" name="orderId" value="${order.orderId}">

								<div class="mb-3">

									<label class="form-label">Current Status</label>

									<select name="status" class="form-select" required>

										<option value="PLACED" ${order.status == 'PLACED' ? 'selected' : ''}>Placed</option>

										<option value="PREPARING" ${order.status == 'PREPARING' ? 'selected' : ''}>Preparing</option>

										<option value="OUT_FOR_DELIVERY" ${order.status == 'OUT_FOR_DELIVERY' ? 'selected' : ''}>Out for Delivery</option>

										<option value="DELIVERED" ${order.status == 'DELIVERED' ? 'selected' : ''}>Delivered</option>

										<option value="CANCELLED" ${order.status == 'CANCELLED' ? 'selected' : ''}>Cancelled</option>

									</select>

								</div>

								<button type="submit" class="btn btn-warning">

									<i class="fa-solid fa-save me-2"></i>

									Update Status

								</button>

							</form>

						</div>

					</div>

				</div>

			</div>

		</c:if>

	</c:forEach>

</main>

<script src="${pageContext.request.contextPath}/assets/js/admin-orders.js"></script>

<%@ include file="../includes/footer.jsp"%>