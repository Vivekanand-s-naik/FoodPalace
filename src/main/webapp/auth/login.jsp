<%@ page language="java"
	contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="c"
	uri="jakarta.tags.core"%>

<%@ include file="../includes/header.jsp"%>

<link rel="stylesheet"
	href="${pageContext.request.contextPath}/assets/css/login.css">

<%@ include file="../includes/navbar.jsp"%>

<main class="login-page">

	<section class="login-section py-5">

		<div class="container">

			<div class="row justify-content-center align-items-center">

				<!-- =====================================
							LEFT PANEL
				===================================== -->

				<div class="col-lg-6 d-none d-lg-block">

					<div class="login-banner">

						<img
							src="${pageContext.request.contextPath}/assets/images/login-banner.png"
							class="img-fluid"
							alt="Login Banner">

						<div class="banner-content mt-4">

							<h2>

								Welcome Back!

							</h2>

							<p>

								Order delicious food from your favourite
								restaurants with just a few clicks.

							</p>

							<div class="login-features">

								<div class="feature-item">

									<i class="fa-solid fa-burger"></i>

									<span>

										1000+ Restaurants

									</span>

								</div>

								<div class="feature-item">

									<i class="fa-solid fa-motorcycle"></i>

									<span>

										Fast Delivery

									</span>

								</div>

								<div class="feature-item">

									<i class="fa-solid fa-credit-card"></i>

									<span>

										Secure Payments

									</span>

								</div>

							</div>

						</div>

					</div>

				</div>

				<!-- =====================================
							LOGIN CARD
				===================================== -->

				<div class="col-lg-5 col-md-8 col-sm-10">

					<div class="card login-card shadow-lg border-0">

						<div class="card-body p-5">

							<div class="text-center mb-4">

								<div class="login-icon">

									<i class="fa-solid fa-user-lock"></i>

								</div>

								<h2 class="mt-3">

									Sign In

								</h2>

								<p class="text-muted">

									Login to continue

								</p>

							</div>

							<!-- ===========================
									SUCCESS MESSAGE
							=========================== -->

							<c:if test="${not empty successMessage}">

								<div class="alert alert-success">

									${successMessage}

								</div>

							</c:if>

							<!-- ===========================
									ERROR MESSAGE
							=========================== -->

							<c:if test="${not empty errorMessage}">

								<div class="alert alert-danger">

									${errorMessage}

								</div>

							</c:if>

							<!-- ===========================
									LOGIN FORM
							=========================== -->

							<form

								action="${pageContext.request.contextPath}/login"

								method="post"

								id="loginForm"

								novalidate>

								<!-- Email -->

								<div class="mb-4">

									<label
										for="email"
										class="form-label">

										Email Address

									</label>

									<div class="input-group">

										<span class="input-group-text">

											<i class="fa-solid fa-envelope"></i>

										</span>

										<input

											type="email"

											class="form-control"

											id="email"

											name="email"

											placeholder="Enter your email"

											required>

									</div>

									<small
										class="text-danger"
										id="emailError">

									</small>

								</div>

								<!-- Password -->

								<div class="mb-4">

									<label
										for="password"
										class="form-label">

										Password

									</label>

									<div class="input-group">

										<span class="input-group-text">

											<i class="fa-solid fa-lock"></i>

										</span>

										<input

											type="password"

											class="form-control"

											id="password"

											name="password"

											placeholder="Enter your password"

											required>

										<button

											class="btn btn-outline-secondary"

											type="button"

											id="togglePassword">

											<i class="fa-solid fa-eye"></i>

										</button>

									</div>

									<small
										class="text-danger"
										id="passwordError">

									</small>

								</div>

								<!-- ===========================
										REMEMBER ME
								=========================== -->

								<div
									class="d-flex justify-content-between align-items-center mb-4">

									<div class="form-check">

										<input

											class="form-check-input"

											type="checkbox"

											id="rememberMe"

											name="rememberMe">

										<label

											class="form-check-label"

											for="rememberMe">

											Remember Me

										</label>

									</div>

									<a

										href="${pageContext.request.contextPath}/auth/forgot-password.jsp"

										class="forgot-password">

										Forgot Password?

									</a>

								</div>

								<!-- ===========================
										LOGIN BUTTON
								=========================== -->

								<div class="d-grid mb-4">

									<button

										type="submit"

										class="btn btn-warning btn-lg">

										<i class="fa-solid fa-right-to-bracket me-2"></i>

										Login

									</button>

								</div>

							</form>

							<!-- ===========================
										DIVIDER
							=========================== -->

							<div class="divider my-4">

								<span>

									OR

								</span>

							</div>

							<!-- ===========================
									SOCIAL LOGIN
							=========================== -->

							<div class="row g-2 mb-4">

								<div class="col-6">

									<button

										type="button"

										class="btn btn-outline-danger w-100">

										<i class="fab fa-google me-2"></i>

										Google

									</button>

								</div>

								<div class="col-6">

									<button

										type="button"

										class="btn btn-outline-primary w-100">

										<i class="fab fa-facebook-f me-2"></i>

										Facebook

									</button>

								</div>

							</div>

							<!-- ===========================
										SIGN UP
							=========================== -->

							<div class="text-center">

								<p class="mb-0">

									Don't have an account?

									<a

										href="${pageContext.request.contextPath}/auth/register.jsp"

										class="register-link">

										Create Account

									</a>

								</p>

							</div>

						</div>

					</div>

				</div>

			</div>

		</div>

	</section>

</main>

<!-- ==========================================
			LOGIN JS
========================================== -->

<script

	src="${pageContext.request.contextPath}/assets/js/login.js">

</script>

<%@ include file="../includes/footer.jsp"%>