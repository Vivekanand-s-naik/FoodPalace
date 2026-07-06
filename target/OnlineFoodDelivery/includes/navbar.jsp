<%@ page language="java"
	contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<nav class="navbar navbar-expand-lg navbar-dark bg-dark shadow-sm sticky-top">

	<div class="container">

		<!-- ===========================
					LOGO
		=========================== -->

		<a class="navbar-brand fw-bold" href="${pageContext.request.contextPath}/index.jsp">
			<i class="fa-solid fa-utensils me-2"></i>
			OnlineFoodDelivery
		</a>

		<!-- ===========================
				MOBILE TOGGLE
		=========================== -->

		<button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#mainNavbar"
				aria-controls="mainNavbar" aria-expanded="false" aria-label="Toggle navigation">
			<span class="navbar-toggler-icon"></span>
		</button>

		<!-- ===========================
					MENU
		=========================== -->

		<div class="collapse navbar-collapse" id="mainNavbar">

			<c:set var="isAuthenticated" value="${not empty sessionScope.loggedUser or not empty sessionScope.userId}" />
			<c:set var="isAdmin" value="${sessionScope.userRole == 'ADMIN'}" />

			<ul class="navbar-nav mx-auto">

				<li class="nav-item">
					<a class="nav-link" href="${pageContext.request.contextPath}/index.jsp">Home</a>
				</li>

				<li class="nav-item">
					<a class="nav-link" href="${pageContext.request.contextPath}/restaurants">Restaurants</a>
				</li>

				<!-- REMOVE THIS LINE - Menu is accessed via restaurant cards -->
				<%--
				<li class="nav-item">
					<a class="nav-link" href="${pageContext.request.contextPath}/menu">Menu</a>
				</li>
				--%>

				<c:if test="${isAuthenticated}">
					<li class="nav-item">
						<a class="nav-link" href="${pageContext.request.contextPath}/cart">
							<i class="fa-solid fa-cart-shopping"></i> Cart
						</a>
					</li>

					<li class="nav-item">
						<a class="nav-link" href="${pageContext.request.contextPath}/orders">My Orders</a>
					</li>
				</c:if>

				<!-- Admin Dashboard (visible only to admin) -->
				<c:if test="${isAdmin}">
					<li class="nav-item">
						<a class="nav-link text-warning" href="${pageContext.request.contextPath}/admin/dashboard">
							<i class="fa-solid fa-gauge-high"></i> Dashboard
						</a>
					</li>
				</c:if>

				<c:if test="${isAuthenticated}">
					<li class="nav-item">
						<a class="nav-link" href="${pageContext.request.contextPath}/profile">Profile</a>
					</li>
				</c:if>

			</ul>

			<!-- ===========================
					RIGHT SIDE (Dynamic)
			=========================== -->

			<div class="d-flex align-items-center gap-2">

				<c:choose>

					<c:when test="${isAuthenticated}">

						<!-- Logged In -->
						<span class="text-light me-2">
							<i class="fa-solid fa-user me-1"></i>
							${sessionScope.userName}
						</span>

						<a href="${pageContext.request.contextPath}/logout" class="btn btn-outline-danger btn-sm">
							<i class="fa-solid fa-right-from-bracket me-1"></i>
							Logout
						</a>

					</c:when>

					<c:otherwise>

						<!-- Logged Out -->
						<a href="${pageContext.request.contextPath}/login" class="btn btn-outline-light">
							<i class="fa-solid fa-right-to-bracket me-1"></i>
							Login
						</a>

						<a href="${pageContext.request.contextPath}/register" class="btn btn-warning">
							<i class="fa-solid fa-user-plus me-1"></i>
							Register
						</a>

					</c:otherwise>

				</c:choose>

			</div>

		</div>

	</div>

</nav>