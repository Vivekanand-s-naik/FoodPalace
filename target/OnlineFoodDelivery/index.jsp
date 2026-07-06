<%@ page language="java"
	contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ include file="includes/header.jsp"%>

<link rel="stylesheet"
	href="${pageContext.request.contextPath}/assets/css/index.css">

<%@ include file="includes/navbar.jsp"%>

<!-- ==========================================
			HERO SECTION
========================================== -->

<section class="hero-section">

	<div class="container">

		<div class="row align-items-center">

			<div class="col-lg-6">

				<h1>

					Delicious Food

					Delivered To

					Your Doorstep

				</h1>

				<p>

					Discover thousands of restaurants
					and enjoy fresh meals delivered
					quickly to your home.

				</p>

				<div class="hero-buttons">

					<a href="${pageContext.request.contextPath}/restaurants"
						class="btn btn-warning btn-lg">

						Order Now

					</a>

					<a href="${pageContext.request.contextPath}/auth/register.jsp"
						class="btn btn-outline-dark btn-lg">

						Get Started

					</a>

				</div>

			</div>

			<div class="col-lg-6">

				<img
				src="${pageContext.request.contextPath}/assets/images/hero.png"
				class="img-fluid"
				alt="Food Delivery">

			</div>

		</div>

	</div>

</section>

<!-- ==========================================
			FEATURES
========================================== -->

<section class="py-5">

	<div class="container">

		<div class="row text-center">

			<div class="col-md-4">

				<i class="fas fa-burger fa-3x mb-3"></i>

				<h4>Quality Food</h4>

				<p>

					Freshly prepared meals
					from trusted restaurants.

				</p>

			</div>

			<div class="col-md-4">

				<i class="fas fa-motorcycle fa-3x mb-3"></i>

				<h4>Fast Delivery</h4>

				<p>

					Get your food delivered
					in under 30 minutes.

				</p>

			</div>

			<div class="col-md-4">

				<i class="fas fa-credit-card fa-3x mb-3"></i>

				<h4>Secure Payment</h4>

				<p>

					Multiple payment methods
					with complete security.

				</p>

			</div>

		</div>

	</div>

</section>

<!-- ==========================================
			POPULAR CATEGORIES
========================================== -->

<section class="py-5 bg-light">

	<div class="container">

		<div class="text-center mb-5">

			<h2>

				Popular Categories

			</h2>

		</div>

		<div class="row">

			<div class="col-md-3">

				<div class="category-card">

					<i class="fa-solid fa-pizza-slice"></i>

					<h5>Pizza</h5>

				</div>

			</div>

			<div class="col-md-3">

				<div class="category-card">

					<i class="fa-solid fa-burger"></i>

					<h5>Burger</h5>

				</div>

			</div>

			<div class="col-md-3">

				<div class="category-card">

					<i class="fa-solid fa-bowl-rice"></i>

					<h5>Biryani</h5>

				</div>

			</div>

			<div class="col-md-3">

				<div class="category-card">

					<i class="fa-solid fa-drumstick-bite"></i>

					<h5>Chicken</h5>

				</div>

			</div>

		</div>

	</div>

</section>

<!-- ==========================================
		FEATURED RESTAURANTS
========================================== -->

<section class="py-5">

	<div class="container">

		<div class="text-center mb-5">

			<h2>

				Featured Restaurants

			</h2>

		</div>

		<div class="row">

			<div class="col-lg-4">

				<div class="restaurant-card">

					<img
					src="${pageContext.request.contextPath}/assets/images/restaurant1.jpg"
					class="img-fluid"
					alt="Restaurant">

					<h4>

						Spice Garden

					</h4>

					<p>

						⭐ 4.8 | Indian

					</p>

					<a href="${pageContext.request.contextPath}/restaurants"
						class="btn btn-warning">

						View Menu

					</a>

				</div>

			</div>

			<div class="col-lg-4">

				<div class="restaurant-card">

					<img
					src="${pageContext.request.contextPath}/assets/images/restaurant2.jpg"
					class="img-fluid"
					alt="Restaurant">

					<h4>

						Pizza Palace

					</h4>

					<p>

						⭐ 4.9 | Italian

					</p>

					<a href="${pageContext.request.contextPath}/restaurants"
						class="btn btn-warning">

						View Menu

					</a>

				</div>

			</div>

			<div class="col-lg-4">

				<div class="restaurant-card">

					<img
					src="${pageContext.request.contextPath}/assets/images/restaurant3.jpg"
					class="img-fluid"
					alt="Restaurant">

					<h4>

						Dragon House

					</h4>

					<p>

						⭐ 4.7 | Chinese

					</p>

					<a href="${pageContext.request.contextPath}/restaurants"
						class="btn btn-warning">

						View Menu

					</a>

				</div>

			</div>

		</div>

	</div>

</section>

<!-- ==========================================
			CALL TO ACTION
========================================== -->

<section class="py-5 bg-dark text-white text-center">

	<div class="container">

		<h2>

			Ready To Order?

		</h2>

		<p>

			Browse hundreds of restaurants
			and enjoy your favourite meals.

		</p>

		<a href="${pageContext.request.contextPath}/restaurants.jsp"
			class="btn btn-warning btn-lg">

			Explore Restaurants

		</a>

	</div>

</section>

<script
src="${pageContext.request.contextPath}/assets/js/index.js">
</script>

<%@ include file="includes/footer.jsp"%>