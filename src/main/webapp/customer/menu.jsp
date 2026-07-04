<%@ page language="java"
	contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="c"
	uri="jakarta.tags.core"%>

<%@ include file="../includes/header.jsp"%>

<link rel="stylesheet"
	href="${pageContext.request.contextPath}/assets/css/menu.css">

<%@ include file="../includes/navbar.jsp"%>

<main class="menu-page">

	<!-- ==========================================
				RESTAURANT HEADER
	========================================== -->

	<section class="restaurant-header py-4">

		<div class="container">

			<div class="row align-items-center">

				<div class="col-lg-3">

					<img

						src="${pageContext.request.contextPath}/assets/images/restaurants/${restaurant.imagePath}"

						class="img-fluid rounded"

						alt="${restaurant.name}">

				</div>

				<div class="col-lg-9">

					<h2>

						${restaurant.name}

					</h2>

					<p>

						${restaurant.cuisine}

					</p>

					<div class="d-flex gap-3">

						<span class="badge bg-warning text-dark">

							⭐ ${restaurant.rating}

						</span>

						<span>

							<i class="fa-solid fa-clock me-2"></i>

							${restaurant.deliveryTime} mins

						</span>

						<span>

							<i class="fa-solid fa-location-dot me-2"></i>

							${restaurant.address}

						</span>

					</div>

				</div>

			</div>

		</div>

	</section>

	<!-- ==========================================
				CATEGORY FILTER
	========================================== -->

	<section class="category-filter py-3">

		<div class="container">

			<div class="d-flex flex-wrap gap-2">

				<a

					href="${pageContext.request.contextPath}/MenuServlet?restaurantId=${restaurant.restaurantId}"

					class="btn ${empty param.category ? 'btn-warning' : 'btn-outline-warning'}">

					All

				</a>

				<c:forEach

					var="category"

					items="${categories}">

					<a

						href="${pageContext.request.contextPath}/MenuServlet?restaurantId=${restaurant.restaurantId}&category=${category}"

						class="btn ${param.category == category ? 'btn-warning' : 'btn-outline-warning'}">

						${category}

					</a>

				</c:forEach>

			</div>

		</div>

	</section>

	<!-- ==========================================
				MENU ITEMS
	========================================== -->

	<section class="menu-items py-4">

		<div class="container">

			<div class="row">

				<c:choose>

					<c:when test="${not empty menuList}">

						<c:forEach

							var="item"

							items="${menuList}">

							<div class="col-lg-4 col-md-6 mb-4">

								<div class="card menu-card h-100 shadow-sm">

									<img

										src="${pageContext.request.contextPath}/assets/images/menu/${item.imagePath}"

										class="card-img-top"

										alt="${item.itemName}">

									<div class="card-body">

										<div class="d-flex justify-content-between">

											<h5>

												${item.itemName}

											</h5>

											<c:choose>

												<c:when test="${item.veg}">

													<span class="badge bg-success">

														Veg

													</span>

												</c:when>

												<c:otherwise>

													<span class="badge bg-danger">

														Non-Veg

													</span>

												</c:otherwise>

											</c:choose>

										</div>

										<p>

											${item.description}

										</p>

										<h5>

											₹ ${item.price}

										</h5>

										<c:if test="${not item.available}">

											<span class="badge bg-secondary">

												Out of Stock

											</span>

										</c:if>

									</div>

									<div class="card-footer bg-white">

										<form

											action="${pageContext.request.contextPath}/CartServlet"

											method="post">

											<input

												type="hidden"

												name="action"

												value="add">

											<input

												type="hidden"

												name="menuId"

												value="${item.menuId}">

											<input

												type="hidden"

												name="restaurantId"

												value="${restaurant.restaurantId}">

											<div class="row g-2">

												<div class="col-5">

													<input

														type="number"

														class="form-control"

														name="quantity"

														value="1"

														min="1"

														${not item.available ? 'disabled' : ''}>

												</div>

												<div class="col-7">

													<button

														type="submit"

														class="btn btn-warning w-100"

														${not item.available ? 'disabled' : ''}>

														<i class="fa-solid fa-cart-plus me-2"></i>

														Add to Cart

													</button>

												</div>

											</div>

										</form>

									</div>

								</div>

							</div>

						</c:forEach>

					</c:when>

					<c:otherwise>

						<div class="col-12">

							<div class="alert alert-info text-center">

								<h4>

									No Items Available

								</h4>

								<p>

									Please check back later

								</p>

							</div>

						</div>

					</c:otherwise>

				</c:choose>

			</div>

		</div>

	</section>

</main>

<script

	src="${pageContext.request.contextPath}/assets/js/menu.js">

</script>

<%@ include file="../includes/footer.jsp"%>