<%@ page language="java"
	contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="c"
	uri="jakarta.tags.core"%>

<%@ include file="../includes/header.jsp"%>

<link rel="stylesheet"
	href="${pageContext.request.contextPath}/assets/css/register.css">

<%@ include file="../includes/navbar.jsp"%>

<main class="register-page">

	<section class="register-section py-5">

		<div class="container">

			<div class="row justify-content-center align-items-center">

				<!-- =====================================
						REGISTRATION FORM
				===================================== -->

				<div class="col-lg-7 col-md-10">

					<div class="card register-card shadow border-0">

						<div class="card-body p-5">

							<div class="text-center mb-4">

								<div class="register-icon">

									<i class="fa-solid fa-user-plus"></i>

								</div>

								<h2 class="mt-3">

									Create Account

								</h2>

								<p class="text-muted">

									Join OnlineFoodDelivery today.

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

							<form

								action="${pageContext.request.contextPath}/register"

								method="post"

								id="registerForm"

								novalidate>

								<div class="row">

									<!-- Full Name -->

									<div class="col-md-6 mb-4">

										<label
											for="fullName"
											class="form-label">

											Full Name

										</label>

										<input

											type="text"

											class="form-control"

											id="fullName"

											name="fullName"

											placeholder="Enter full name"

											required>

										<small
											class="text-danger"
											id="fullNameError"></small>

									</div>

									<!-- Email -->

									<div class="col-md-6 mb-4">

										<label
											for="email"
											class="form-label">

											Email Address

										</label>

										<input

											type="email"

											class="form-control"

											id="email"

											name="email"

											placeholder="Enter email"

											required>

										<small
											class="text-danger"
											id="emailError"></small>

									</div>

									<!-- Phone -->

									<div class="col-md-6 mb-4">

										<label
											for="phone"
											class="form-label">

											Phone Number

										</label>

										<input

											type="tel"

											class="form-control"

											id="phone"

											name="phone"

											placeholder="Enter phone number"

											required>

										<small
											class="text-danger"
											id="phoneError"></small>

									</div>

									<!-- Password -->

									<div class="col-md-6 mb-4">

										<label
											for="password"
											class="form-label">

											Password

										</label>

										<div class="input-group">

											<input

												type="password"

												class="form-control"

												id="password"

												name="password"

												placeholder="Enter password"

												required>

											<button

												type="button"

												class="btn btn-outline-secondary"

												id="togglePassword">

												<i class="fa-solid fa-eye"></i>

											</button>

										</div>

										<small
											class="text-danger"
											id="passwordError"></small>

									</div>

									<!-- Confirm Password -->

									<div class="col-md-6 mb-4">

										<label
											for="confirmPassword"
											class="form-label">

											Confirm Password

										</label>

										<div class="input-group">

											<input

												type="password"

												class="form-control"

												id="confirmPassword"

												name="confirmPassword"

												placeholder="Confirm password"

												required>

											<button

												type="button"

												class="btn btn-outline-secondary"

												id="toggleConfirmPassword">

												<i class="fa-solid fa-eye"></i>

											</button>

										</div>

										<small
											class="text-danger"
											id="confirmPasswordError"></small>

									</div>

									<!-- Address -->

									<div class="col-12 mb-4">

										<label
											for="address"
											class="form-label">

											Address

										</label>

										<textarea

											class="form-control"

											id="address"

											name="address"

											rows="4"

											placeholder="Enter your address"

											required>

										</textarea>

										<small
											class="text-danger"
											id="addressError"></small>

									</div>

									<!-- Role -->

									<div class="col-md-6 mb-4">

										<label
											for="role"
											class="form-label">

											Account Type

										</label>

										<select

											class="form-select"

											id="role"

											name="role">

											<option value="CUSTOMER" selected>

												Customer

											</option>

										</select>

									</div>

									<!-- Gender -->

									<div class="col-md-6 mb-4">

										<label
											class="form-label d-block">

											Gender

										</label>

										<div class="form-check form-check-inline">

											<input

												class="form-check-input"

												type="radio"

												name="gender"

												id="male"

												value="Male">

											<label
												class="form-check-label"
												for="male">

												Male

											</label>

										</div>

										<div class="form-check form-check-inline">

											<input

												class="form-check-input"

												type="radio"

												name="gender"

												id="female"

												value="Female">

											<label
												class="form-check-label"
												for="female">

												Female

											</label>

										</div>

										<div class="form-check form-check-inline">

											<input

												class="form-check-input"

												type="radio"

												name="gender"

												id="other"

												value="Other">

											<label
												class="form-check-label"
												for="other">

												Other

											</label>

										</div>

									</div>

								</div>

								<!-- Terms -->

								<div class="form-check mb-4">

									<input

										class="form-check-input"

										type="checkbox"

										id="terms"

										name="terms"

										required>

									<label

										class="form-check-label"

										for="terms">

										I agree to the

										<a href="#">

											Terms & Conditions

										</a>

										and

										<a href="#">

											Privacy Policy

										</a>

									</label>

								</div>

								<!-- Register Button -->

								<div class="d-grid mb-4">

									<button

										type="submit"

										class="btn btn-warning btn-lg">

										<i class="fa-solid fa-user-plus me-2"></i>

										Create Account

									</button>

								</div>

							</form>

							<!-- Divider -->

							<div class="divider text-center my-4">

								<span>

									OR

								</span>

							</div>

							<!-- Social Register -->

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

							<!-- Login Link -->

							<div class="text-center">

								<p class="mb-0">

									Already have an account?

									<a

										href="${pageContext.request.contextPath}/auth/login.jsp"

										class="fw-semibold">

										Login Here

									</a>

								</p>

							</div>

						</div>

					</div>

				</div>

				<!-- =====================================
							RIGHT PANEL
				===================================== -->

				<div class="col-lg-5 d-none d-lg-block">

					<div class="register-banner text-center">

						<img

							src="${pageContext.request.contextPath}/assets/images/register-banner.png"

							class="img-fluid"

							alt="Register Banner">

						<h3 class="mt-4">

							Fresh Food Awaits You

						</h3>

						<p>

							Create your account and enjoy fast delivery,
							exclusive discounts, secure payments, and
							access to thousands of restaurants.

						</p>

					</div>

				</div>

			</div>

		</div>

	</section>

</main>

<!-- ==========================================
			REGISTER JS
========================================== -->

<script

	src="${pageContext.request.contextPath}/assets/js/register.js">

</script>

<%@ include file="../includes/footer.jsp"%>