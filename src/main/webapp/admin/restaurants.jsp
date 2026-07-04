<%@ page language="java"
	contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="c"
	uri="jakarta.tags.core"%>

<%@ include file="../includes/header.jsp"%>

<link rel="stylesheet"
	href="${pageContext.request.contextPath}/assets/css/admin-restaurants.css">

<%@ include file="../includes/navbar.jsp"%>

<main class="admin-restaurants">

	<!-- ==========================================
				PAGE HEADER
	========================================== -->

	<section class="page-header py-4">

		<div class="container">

			<div class="d-flex justify-content-between align-items-center">

				<h1>

					Manage Restaurants

				</h1>

				<a

					href="${pageContext.request.contextPath}/admin/restaurants.jsp?action=add"

					class="btn btn-warning">

					<i class="fa-solid fa-plus me-2"></i>

					Add Restaurant

				</a>

			</div>

		</div>

	</section>

	<!-- ==========================================
				RESTAURANTS TABLE
	========================================== -->

	<section class="restaurants-table py-4">

		<div class="container">

			<div class="card shadow-sm">

				<div class="card-header">

					<div class="row align-items-center">

						<div class="col-md-6">

							<h5>

								All Restaurants

							</h5>

						</div>

						<div class="col-md-6">

							<form

								action="${pageContext.request.contextPath}/admin/restaurants"

								method="get"

								class="d-flex">

								<input

									type="text"

									name="search"

									class="form-control me-2"

									placeholder="Search by name or cuisine"

									value="${param.search}">

								<button

									type="submit"

									class="btn btn-outline-warning">

									Search

								</button>

							</form>

						</div>

					</div>

				</div>

				<div class="card-body">

					<table class="table table-hover">

						<thead>

							<tr>

								<th>ID</th>

								<th>Name</th>

								<th>Cuisine</th>

								<th>Rating</th>

								<th>Status</th>

								<th>Actions</th>

							</tr>

						</thead>

						<tbody>

							<c:forEach

								var="restaurant"

								items="${restaurants}">

								<tr>

									<td>

										${restaurant.restaurantId}

									</td>

									<td>

										${restaurant.name}

									</td>

									<td>

										${restaurant.cuisine}

									</td>

									<td>

										⭐ ${restaurant.rating}

									</td>

									<td>

										<c:choose>

											<c:when test="${restaurant.active}">

												<span class="badge bg-success">

													Active

												</span>

											</c:when>

											<c:otherwise>

												<span class="badge bg-danger">

													Inactive

												</span>

											</c:otherwise>

										</c:choose>

									</td>

									<td>

										<a

											href="${pageContext.request.contextPath}/admin/restaurants.jsp?action=edit&id=${restaurant.restaurantId}"

											class="btn btn-sm btn-outline-warning">

											<i class="fa-solid fa-pen"></i>

										</a>

										<a

											href="${pageContext.request.contextPath}/admin/restaurants?action=delete&id=${restaurant.restaurantId}"

											class="btn btn-sm btn-outline-danger"

											onclick="return confirm('Are you sure?')">

											<i class="fa-solid fa-trash"></i>

										</a>

									</td>

								</tr>

							</c:forEach>

							<c:if test="${empty restaurants}">

								<tr>

									<td colspan="6" class="text-center text-muted">

										No restaurants found.

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

									   href="${pageContext.request.contextPath}/admin/restaurants?page=${currentPage - 1}&search=${param.search}">

										Previous

									</a>

								</li>

								<c:forEach begin="1" end="${totalPages}" var="i">

									<li class="page-item ${i == currentPage ? 'active' : ''}">

										<a class="page-link"

										   href="${pageContext.request.contextPath}/admin/restaurants?page=${i}&search=${param.search}">

											${i}

										</a>

									</li>

								</c:forEach>

								<li class="page-item ${currentPage == totalPages ? 'disabled' : ''}">

									<a class="page-link"

									   href="${pageContext.request.contextPath}/admin/restaurants?page=${currentPage + 1}&search=${param.search}">

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
				ADD/EDIT RESTAURANT MODAL
	========================================== -->

	<div class="modal fade" id="restaurantModal" tabindex="-1">

		<div class="modal-dialog modal-lg">

			<div class="modal-content">

				<div class="modal-header">

					<h5 class="modal-title">

						${action == 'edit' ? 'Edit Restaurant' : 'Add New Restaurant'}

					</h5>

					<button

						type="button"

						class="btn-close"

						data-bs-dismiss="modal">

					</button>

				</div>

				<div class="modal-body">

					<form

						id="restaurantForm"

						action="${pageContext.request.contextPath}/admin/restaurants"

						method="post">

						<input type="hidden" name="action" value="${action}">

						<input type="hidden" name="restaurantId" value="${restaurant.restaurantId}">

						<div class="row">

							<div class="col-md-6 mb-3">

								<label class="form-label">

									Restaurant Name

								</label>

								<input

									type="text"

									class="form-control"

									name="name"

									value="${restaurant.name}"

									required>

							</div>

							<div class="col-md-6 mb-3">

								<label class="form-label">

									Owner Name

								</label>

								<input

									type="text"

									class="form-control"

									name="ownerName"

									value="${restaurant.ownerName}">

							</div>

							<div class="col-md-6 mb-3">

								<label class="form-label">

									Email

								</label>

								<input

									type="email"

									class="form-control"

									name="email"

									value="${restaurant.email}">

							</div>

							<div class="col-md-6 mb-3">

								<label class="form-label">

									Phone

								</label>

								<input

									type="tel"

									class="form-control"

									name="phone"

									value="${restaurant.phone}">

							</div>

							<div class="col-md-6 mb-3">

								<label class="form-label">

									Cuisine

								</label>

								<input

									type="text"

									class="form-control"

									name="cuisine"

									value="${restaurant.cuisine}"

									required>

							</div>

							<div class="col-md-6 mb-3">

								<label class="form-label">

									Rating

								</label>

								<input

									type="number"

									step="0.1"

									min="0"

									max="5"

									class="form-control"

									name="rating"

									value="${restaurant.rating}">

							</div>

							<div class="col-md-12 mb-3">

								<label class="form-label">

									Address

								</label>

								<textarea

									class="form-control"

									name="address"

									rows="2">${restaurant.address}</textarea>

							</div>

							<div class="col-md-6 mb-3">

								<div class="form-check">

									<input

										class="form-check-input"

										type="checkbox"

										name="active"

										id="activeCheck"

										${restaurant.active ? 'checked' : ''}>

									<label

										class="form-check-label"

										for="activeCheck">

										Active

									</label>

								</div>

							</div>

						</div>

						<button

							type="submit"

							class="btn btn-warning">

							<i class="fa-solid fa-save me-2"></i>

							Save Restaurant

						</button>

					</form>

				</div>

			</div>

		</div>

	</div>

</main>

<!-- ==========================================
			SCRIPT TO OPEN MODAL IF ACTION IS add/edit
========================================== -->

<script>

	window.onload = function() {

		const urlParams = new URLSearchParams(window.location.search);

		const action = urlParams.get('action');

		if (action === 'add' || action === 'edit') {

			const modal = new bootstrap.Modal(

				document.getElementById('restaurantModal')

			);

			modal.show();

		}

	};

</script>

<script

	src="${pageContext.request.contextPath}/assets/js/admin-restaurants.js">

</script>

<%@ include file="../includes/footer.jsp"%>