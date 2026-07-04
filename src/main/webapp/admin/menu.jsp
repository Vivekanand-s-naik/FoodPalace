<%@ page language="java"
	contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="c"
	uri="jakarta.tags.core"%>

<%@ include file="../includes/header.jsp"%>

<link rel="stylesheet"
	href="${pageContext.request.contextPath}/assets/css/admin-menu.css">

<%@ include file="../includes/navbar.jsp"%>

<main class="admin-menu">

	<!-- ==========================================
				PAGE HEADER
	========================================== -->

	<section class="page-header py-4">

		<div class="container">

			<div class="d-flex justify-content-between align-items-center">

				<h1>

					Manage Menu Items

				</h1>

				<a

					href="${pageContext.request.contextPath}/admin/menu.jsp?action=add"

					class="btn btn-warning">

					<i class="fa-solid fa-plus me-2"></i>

					Add Menu Item

				</a>

			</div>

		</div>

	</section>

	<!-- ==========================================
				FILTERS & SEARCH
	========================================== -->

	<section class="filters-section py-4">

		<div class="container">

			<div class="card shadow-sm">

				<div class="card-body">

					<form

						action="${pageContext.request.contextPath}/admin/menu"

						method="get"

						class="row g-3">

						<div class="col-md-4">

							<label class="form-label">

								Restaurant

							</label>

							<select

								name="restaurantId"

								class="form-select">

								<option value="">

									All Restaurants

								</option>

								<c:forEach

									var="rest"

									items="${restaurantList}">

									<option

										value="${rest.restaurantId}"

										${rest.restaurantId == param.restaurantId ? 'selected' : ''}>

										${rest.name}

									</option>

								</c:forEach>

							</select>

						</div>

						<div class="col-md-4">

							<label class="form-label">

								Category

							</label>

							<select

								name="category"

								class="form-select">

								<option value="">

									All Categories

								</option>

								<option value="Veg"

									${param.category == 'Veg' ? 'selected' : ''}>

									Veg

								</option>

								<option value="Non-Veg"

									${param.category == 'Non-Veg' ? 'selected' : ''}>

									Non-Veg

								</option>

								<option value="Beverage"

									${param.category == 'Beverage' ? 'selected' : ''}>

									Beverage

								</option>

								<option value="Dessert"

									${param.category == 'Dessert' ? 'selected' : ''}>

									Dessert

								</option>

							</select>

						</div>

						<div class="col-md-4">

							<label class="form-label">

								Search

							</label>

							<div class="d-flex">

								<input

									type="text"

									name="search"

									class="form-control me-2"

									placeholder="Search by name"

									value="${param.search}">

								<button

									type="submit"

									class="btn btn-warning">

									<i class="fa-solid fa-search"></i>

								</button>

							</div>

						</div>

					</form>

				</div>

			</div>

		</div>

	</section>

	<!-- ==========================================
				MENU ITEMS TABLE
	========================================== -->

	<section class="menu-table py-4">

		<div class="container">

			<div class="card shadow-sm">

				<div class="card-header">

					<h5>

						All Menu Items

					</h5>

				</div>

				<div class="card-body">

					<table class="table table-hover">

						<thead>

							<tr>

								<th>ID</th>

								<th>Item Name</th>

								<th>Restaurant</th>

								<th>Price</th>

								<th>Category</th>

								<th>Available</th>

								<th>Actions</th>

							</tr>

						</thead>

						<tbody>

							<c:forEach

								var="item"

								items="${menuItems}">

								<tr>

									<td>

										${item.menuId}

									</td>

									<td>

										${item.itemName}

									</td>

									<td>

										${item.restaurantName}

									</td>

									<td>

										₹ ${item.price}

									</td>

									<td>

										<span class="badge ${item.category == 'Veg' ? 'bg-success' : 'bg-danger'}">

											${item.category}

										</span>

									</td>

									<td>

										<c:choose>

											<c:when test="${item.available}">

												<span class="badge bg-success">

													Yes

												</span>

											</c:when>

											<c:otherwise>

												<span class="badge bg-secondary">

													No

												</span>

											</c:otherwise>

										</c:choose>

									</td>

									<td>

										<a

											href="${pageContext.request.contextPath}/admin/menu.jsp?action=edit&id=${item.menuId}"

											class="btn btn-sm btn-outline-warning">

											<i class="fa-solid fa-pen"></i>

										</a>

										<a

											href="${pageContext.request.contextPath}/admin/menu?action=delete&id=${item.menuId}"

											class="btn btn-sm btn-outline-danger"

											onclick="return confirm('Are you sure?')">

											<i class="fa-solid fa-trash"></i>

										</a>

									</td>

								</tr>

							</c:forEach>

							<c:if test="${empty menuItems}">

								<tr>

									<td colspan="7" class="text-center text-muted">

										No menu items found.

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

									   href="${pageContext.request.contextPath}/admin/menu?page=${currentPage - 1}&restaurantId=${param.restaurantId}&category=${param.category}&search=${param.search}">

										Previous

									</a>

								</li>

								<c:forEach begin="1" end="${totalPages}" var="i">

									<li class="page-item ${i == currentPage ? 'active' : ''}">

										<a class="page-link"

										   href="${pageContext.request.contextPath}/admin/menu?page=${i}&restaurantId=${param.restaurantId}&category=${param.category}&search=${param.search}">

											${i}

										</a>

									</li>

								</c:forEach>

								<li class="page-item ${currentPage == totalPages ? 'disabled' : ''}">

									<a class="page-link"

									   href="${pageContext.request.contextPath}/admin/menu?page=${currentPage + 1}&restaurantId=${param.restaurantId}&category=${param.category}&search=${param.search}">

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
				ADD/EDIT MENU MODAL
	========================================== -->

	<div class="modal fade" id="menuModal" tabindex="-1">

		<div class="modal-dialog modal-lg">

			<div class="modal-content">

				<div class="modal-header">

					<h5 class="modal-title">

						${action == 'edit' ? 'Edit Menu Item' : 'Add Menu Item'}

					</h5>

					<button

						type="button"

						class="btn-close"

						data-bs-dismiss="modal">

					</button>

				</div>

				<div class="modal-body">

					<form

						id="menuForm"

						action="${pageContext.request.contextPath}/admin/menu"

						method="post">

						<input type="hidden" name="action" value="${action}">

						<input type="hidden" name="menuId" value="${menuItem.menuId}">

						<div class="row">

							<div class="col-md-6 mb-3">

								<label class="form-label">

									Item Name

								</label>

								<input

									type="text"

									class="form-control"

									name="itemName"

									value="${menuItem.itemName}"

									required>

							</div>

							<div class="col-md-6 mb-3">

								<label class="form-label">

									Restaurant

								</label>

								<select

									name="restaurantId"

									class="form-select"

									required>

									<option value="">

										Select Restaurant

									</option>

									<c:forEach

										var="rest"

										items="${restaurantList}">

										<option

											value="${rest.restaurantId}"

											${rest.restaurantId == menuItem.restaurantId ? 'selected' : ''}>

											${rest.name}

										</option>

									</c:forEach>

								</select>

							</div>

							<div class="col-md-6 mb-3">

								<label class="form-label">

									Price (₹)

								</label>

								<input

									type="number"

									step="0.01"

									min="0"

									class="form-control"

									name="price"

									value="${menuItem.price}"

									required>

							</div>

							<div class="col-md-6 mb-3">

								<label class="form-label">

									Category

								</label>

								<select

									name="category"

									class="form-select"

									required>

									<option value="Veg"

										${menuItem.category == 'Veg' ? 'selected' : ''}>

										Veg

									</option>

									<option value="Non-Veg"

										${menuItem.category == 'Non-Veg' ? 'selected' : ''}>

										Non-Veg

									</option>

									<option value="Beverage"

										${menuItem.category == 'Beverage' ? 'selected' : ''}>

										Beverage

									</option>

									<option value="Dessert"

										${menuItem.category == 'Dessert' ? 'selected' : ''}>

										Dessert

									</option>

								</select>

							</div>

							<div class="col-md-12 mb-3">

								<label class="form-label">

									Description

								</label>

								<textarea

									class="form-control"

									name="description"

									rows="2">${menuItem.description}</textarea>

							</div>

							<div class="col-md-6 mb-3">

								<div class="form-check">

									<input

										class="form-check-input"

										type="checkbox"

										name="available"

										id="availableCheck"

										${menuItem.available ? 'checked' : ''}>

									<label

										class="form-check-label"

										for="availableCheck">

										Available

									</label>

								</div>

							</div>

						</div>

						<button

							type="submit"

							class="btn btn-warning">

							<i class="fa-solid fa-save me-2"></i>

							Save Menu Item

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

				document.getElementById('menuModal')

			);

			modal.show();

		}

	};

</script>

<script

	src="${pageContext.request.contextPath}/assets/js/admin-menu.js">

</script>

<%@ include file="../includes/footer.jsp"%>