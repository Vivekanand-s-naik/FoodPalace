<%@ page language="java"
	contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="c"
	uri="jakarta.tags.core"%>

<%@ include file="../includes/header.jsp"%>

<link rel="stylesheet"
	href="${pageContext.request.contextPath}/assets/css/admin-users.css">

<%@ include file="../includes/navbar.jsp"%>

<main class="admin-users">

	<!-- ==========================================
				PAGE HEADER
	========================================== -->

	<section class="page-header py-4">

		<div class="container">

			<div class="d-flex justify-content-between align-items-center">

				<h1>

					Manage Users

				</h1>

				<div>

					<span class="badge bg-primary me-2">

						${totalUsers} Total Users

					</span>

					<span class="badge bg-success">

						${activeUsers} Active

					</span>

				</div>

			</div>

		</div>

	</section>

	<!-- ==========================================
				USERS TABLE
	========================================== -->

	<section class="users-table py-4">

		<div class="container">

			<div class="card shadow-sm">

				<div class="card-header">

					<div class="row align-items-center">

						<div class="col-md-4">

							<h5>

								All Users

							</h5>

						</div>

						<div class="col-md-8">

							<form

								action="${pageContext.request.contextPath}/admin/users"

								method="get"

								class="row g-2">

								<div class="col-md-6">

									<input

										type="text"

										name="search"

										class="form-control"

										placeholder="Search by name, email or phone"

										value="${param.search}">

								</div>

								<div class="col-md-3">

									<select

										name="role"

										class="form-select">

										<option value="">

											All Roles

										</option>

										<option value="CUSTOMER" ${param.role == 'CUSTOMER' ? 'selected' : ''}>

											Customer

										</option>

										<option value="ADMIN" ${param.role == 'ADMIN' ? 'selected' : ''}>

											Admin

										</option>

									</select>

								</div>

								<div class="col-md-3">

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

					<div class="table-responsive">

						<table class="table table-hover align-middle mb-0">

						<thead>

							<tr>

								<th>ID</th>

								<th>Name</th>

								<th>Email</th>

								<th>Phone</th>

								<th>Role</th>

								<th>Status</th>

								<th>Actions</th>

							</tr>

						</thead>

						<tbody>

							<c:forEach

								var="user"

								items="${users}">

								<tr>

									<td>

										${user.userId}

									</td>

									<td>

										${user.fullName}

									</td>

									<td>

										${user.email}

									</td>

									<td>

										${user.phone}

									</td>

									<td>

										<span class="badge ${user.role == 'ADMIN' ? 'bg-danger' : 'bg-info'}">

											${user.role}

										</span>

									</td>

									<td>

										<c:choose>

											<c:when test="${user.active}">

												<span class="badge bg-success">

													Active

												</span>

											</c:when>

											<c:otherwise>

												<span class="badge bg-secondary">

													Inactive

												</span>

											</c:otherwise>

										</c:choose>

									</td>

<td class="text-nowrap">

											<div class="d-flex gap-2 align-items-center">

												<button

													class="btn btn-sm btn-outline-warning"

													data-bs-toggle="modal"

													data-bs-target="#userModal${user.userId}">

													<i class="fa-solid fa-eye"></i>

												</button>

												<c:if test="${user.userId != sessionScope.userId}">

													<a

														href="${pageContext.request.contextPath}/admin/users?action=toggle&id=${user.userId}"

														class="btn btn-sm ${user.active ? 'btn-outline-danger' : 'btn-outline-success'}">

														<i class="fa-solid ${user.active ? 'fa-ban' : 'fa-check'}"></i>

													</a>

												</c:if>

											</div>

									</td>

								</tr>

							</c:forEach>

							<c:if test="${empty users}">

								<tr>

									<td colspan="7" class="text-center text-muted">

										No users found.

									</td>

								</tr>

							</c:if>

						</tbody>

						</table>

					</div>

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

									   href="${pageContext.request.contextPath}/admin/users?page=${currentPage - 1}&search=${param.search}&role=${param.role}">

										Previous

									</a>

								</li>

								<c:forEach begin="1" end="${totalPages}" var="i">

									<li class="page-item ${i == currentPage ? 'active' : ''}">

										<a class="page-link"

										   href="${pageContext.request.contextPath}/admin/users?page=${i}&search=${param.search}&role=${param.role}">

											${i}

										</a>

									</li>

								</c:forEach>

								<li class="page-item ${currentPage == totalPages ? 'disabled' : ''}">

									<a class="page-link"

									   href="${pageContext.request.contextPath}/admin/users?page=${currentPage + 1}&search=${param.search}&role=${param.role}">

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

				USER DETAIL MODALS

	========================================== -->

	<c:forEach var="user" items="${users}">

		<div class="modal fade" id="userModal${user.userId}" tabindex="-1">

			<div class="modal-dialog">

				<div class="modal-content">

					<div class="modal-header">

						<h5 class="modal-title">

							User Details - ${user.fullName}

						</h5>

						<button type="button" class="btn-close" data-bs-dismiss="modal"></button>

					</div>

					<div class="modal-body">

						<div class="text-center mb-4">

							<div class="avatar-circle">

								<i class="fa-solid fa-user fa-4x text-secondary"></i>

							</div>

						</div>

						<table class="table table-borderless">

							<tr>

								<th>User ID</th>

								<td>${user.userId}</td>

							</tr>

							<tr>

								<th>Full Name</th>

								<td>${user.fullName}</td>

							</tr>

							<tr>

								<th>Email</th>

								<td>${user.email}</td>

							</tr>

							<tr>

								<th>Phone</th>

								<td>${user.phone}</td>

							</tr>

							<tr>

								<th>Role</th>

								<td>${user.role}</td>

							</tr>

							<tr>

								<th>Status</th>

								<td>

									<c:choose>

										<c:when test="${user.active}">

											<span class="badge bg-success">Active</span>

										</c:when>

										<c:otherwise>

											<span class="badge bg-secondary">Inactive</span>

										</c:otherwise>

									</c:choose>

								</td>

							</tr>

							<tr>

								<th>Registered</th>

								<td>${user.registrationDate}</td>

							</tr>

						</table>

						<c:if test="${not empty user.addresses}">

							<h6>Saved Addresses</h6>

							<ul class="list-unstyled">

								<c:forEach var="address" items="${user.addresses}">

									<li class="border-bottom py-2">

										${address.street}, ${address.city}, ${address.state} - ${address.pincode}

									</li>

								</c:forEach>

							</ul>

						</c:if>

					</div>

					<div class="modal-footer">

						<button type="button" class="btn btn-secondary" data-bs-dismiss="modal">

							Close

						</button>

					</div>

				</div>

			</div>

		</div>

	</c:forEach>

</main>

<script src="${pageContext.request.contextPath}/assets/js/admin-users.js"></script>

<%@ include file="../includes/footer.jsp"%>