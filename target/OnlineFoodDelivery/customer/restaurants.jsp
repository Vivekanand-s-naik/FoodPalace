<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<%@ include file="../includes/header.jsp"%>

<link rel="stylesheet"
	href="${pageContext.request.contextPath}/assets/css/restaurants.css">

<%@ include file="../includes/navbar.jsp"%>

<main class="restaurants-page">

	<!-- ==========================================
				PAGE HEADER
	========================================== -->

	<section class="page-header py-5">

		<div class="container">

			<div class="text-center">

				<h1>Find Your Favourite Restaurant</h1>

				<p>Discover the best restaurants in your area</p>

			</div>

		</div>

	</section>

	<!-- ==========================================
				SEARCH & FILTER
	========================================== -->

	<section class="search-section py-4">

		<div class="container">

			<form action="${pageContext.request.contextPath}/restaurants"
				method="get" class="row g-3">
				<input type="hidden" name="page" value="1">

				<div class="col-md-5">

					<input type="text" class="form-control" name="keyword"
						placeholder="Search by restaurant or cuisine"
						value="${keyword}">

				</div>

				<div class="col-md-3">

					<select class="form-select" name="cuisine">

						<option value="" ${empty cuisine ? 'selected' : ''}>All Cuisines</option>

						<option value="Indian"
							${cuisine == 'Indian' ? 'selected' : ''}>Indian</option>

						<option value="Chinese"
							${cuisine == 'Chinese' ? 'selected' : ''}>Chinese

						</option>

						<option value="Italian"
							${cuisine == 'Italian' ? 'selected' : ''}>Italian

						</option>

						<option value="Mexican"
							${cuisine == 'Mexican' ? 'selected' : ''}>Mexican

						</option>

						<option value="Thai" ${cuisine == 'Thai' ? 'selected' : ''}>

							Thai</option>

						<option value="Japanese"
							${cuisine == 'Japanese' ? 'selected' : ''}>

							Japanese</option>

					</select>

				</div>

				<div class="col-md-2">

					<select class="form-select" name="sort">

						<option value="rating" ${empty sort || sort == 'rating' ? 'selected' : ''}>

							Rating</option>

						<option value="name" ${sort == 'name' ? 'selected' : ''}>

							Name</option>

					</select>

				</div>

				<div class="col-md-2">

					<button type="submit" class="btn btn-warning w-100">

						<i class="fa-solid fa-search me-2"></i> Search

					</button>

				</div>

				<div class="col-md-2">

					<a href="${pageContext.request.contextPath}/restaurants" class="btn btn-outline-secondary w-100">

						<i class="fa-solid fa-rotate-left me-2"></i> Reset

					</a>

				</div>

			</form>

		</div>

	</section>

	<!-- ==========================================
				RESTAURANT LIST
	========================================== -->

	<section class="restaurant-list py-5">

		<div class="container">

			<div class="row">

				<c:choose>

					<c:when test="${not empty restaurantList}">
						<c:forEach var="restaurant" items="${restaurantList}">

							<div class="col-lg-4 col-md-6 mb-4">

								<div class="card restaurant-card h-100 shadow-sm">

									<img
										src="${pageContext.request.contextPath}/assets/images/restaurants/${restaurant.imagePath}"
										class="card-img-top" alt="${restaurant.name}"
										onerror="this.src='${pageContext.request.contextPath}/assets/images/restaurants/restaurant-placeholder.jpg'">

									<div class="card-body">

										<div class="d-flex justify-content-between">

											<h5>${restaurant.name}</h5>

											<span class="badge bg-warning text-dark"> ⭐
												${restaurant.rating} </span>

										</div>

										<p class="text-muted">${restaurant.cuisine}</p>

										<p>

											<i class="fa-solid fa-location-dot me-2"></i>

											${restaurant.address}

										</p>

										<p>

											<i class="fa-solid fa-clock me-2"></i>

											${restaurant.deliveryTime} mins

										</p>

										<c:if test="${restaurant.active}">

											<span class="badge bg-success"> Open </span>

										</c:if>

									</div>

									<div class="card-footer bg-white">

										<a
											href="${pageContext.request.contextPath}/menu?restaurantId=${restaurant.restaurantId}"
											class="btn btn-warning w-100"> View Menu </a>

									</div>

								</div>

							</div>

						</c:forEach>

					</c:when>

					<c:otherwise>

						<div class="col-12">

							<div class="alert alert-warning text-center">

								<h4>No Restaurants Found</h4>

								<p>Try searching with another keyword</p>

							</div>

						</div>

					</c:otherwise>

				</c:choose>

			</div>

			<!-- ==========================================
						PAGINATION
			========================================== -->

			<c:if test="${totalPages > 1}">

				<nav>

					<ul class="pagination justify-content-center">

						<li class="page-item ${currentPage == 1 ? 'disabled' : ''}">

							<a class="page-link"
							href="${pageContext.request.contextPath}/restaurants?page=${currentPage - 1}&keyword=${keyword}&cuisine=${cuisine}&sort=${sort}">

								Previous </a>

						</li>

						<c:forEach begin="1" end="${totalPages}" var="i">

							<li class="page-item ${i == currentPage ? 'active' : ''}"><a
								class="page-link"
								href="${pageContext.request.contextPath}/restaurants?page=${i}&keyword=${keyword}&cuisine=${cuisine}&sort=${sort}">

									${i} </a></li>

						</c:forEach>

						<li
							class="page-item ${currentPage == totalPages ? 'disabled' : ''}">

							<a class="page-link"
							href="${pageContext.request.contextPath}/restaurants?page=${currentPage + 1}&keyword=${keyword}&cuisine=${cuisine}&sort=${sort}">

								Next </a>

						</li>

					</ul>

				</nav>

			</c:if>

		</div>

	</section>

</main>

<script
	src="${pageContext.request.contextPath}/assets/js/restaurants.js">
	
</script>

<%@ include file="../includes/footer.jsp"%>