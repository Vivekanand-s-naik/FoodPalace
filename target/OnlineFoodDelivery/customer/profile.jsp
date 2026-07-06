<%@ page language="java"
	contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="c"
	uri="jakarta.tags.core"%>

<%@ include file="../includes/header.jsp"%>

<link rel="stylesheet"
	href="${pageContext.request.contextPath}/assets/css/profile.css">

<%@ include file="../includes/navbar.jsp"%>

<main class="profile-page">

	<!-- ==========================================
				PAGE HEADER
	========================================== -->

	<section class="page-header py-5">

		<div class="container text-center">

			<h1>

				My Profile

			</h1>

			<p>

				Manage your personal information and addresses

			</p>

		</div>

	</section>

	<!-- ==========================================
				PROFILE
	========================================== -->

	<section class="profile-section pb-5">

		<div class="container">

			<div class="row">

				<!-- =====================================
						LEFT SIDEBAR
				===================================== -->

				<div class="col-lg-4">

					<div class="card shadow-sm">

						<div class="card-body text-center">

							<div class="profile-avatar">

								<i class="fa-solid fa-user fa-5x text-secondary"></i>

							</div>

							<h4>

								${sessionScope.userName}

							</h4>

							<p class="text-muted">

								${sessionScope.userEmail}

							</p>

							<span class="badge bg-info">

								${sessionScope.userRole}

							</span>

							<hr>

							<div class="d-grid gap-2">

								<a

									href="#profile-info"

									class="btn btn-outline-warning">

									<i class="fa-solid fa-user me-2"></i>

									Personal Info

								</a>

								<a

									href="#address-section"

									class="btn btn-outline-warning">

									<i class="fa-solid fa-location-dot me-2"></i>

									Addresses

								</a>

								<a

									href="${pageContext.request.contextPath}/orders"

									class="btn btn-outline-warning">

									<i class="fa-solid fa-receipt me-2"></i>

									My Orders

								</a>

								<a

									href="${pageContext.request.contextPath}/logout"

									class="btn btn-outline-danger">

									<i class="fa-solid fa-right-from-bracket me-2"></i>

									Logout

								</a>

							</div>

						</div>

					</div>

				</div>

				<!-- =====================================
						RIGHT CONTENT
				===================================== -->

				<div class="col-lg-8">

					<!-- =====================================
							PERSONAL INFO
					===================================== -->

					<div class="card shadow-sm mb-4" id="profile-info">

						<div class="card-header">

							<h4>

								Personal Information

							</h4>

						</div>

						<div class="card-body">

							<form

								action="${pageContext.request.contextPath}/profile"

								method="post">

								<input type="hidden" name="action" value="update">

								<div class="row">

									<div class="col-md-6 mb-3">

										<label

											class="form-label">

											Full Name

										</label>

										<input

											type="text"

											class="form-control"

											name="fullName"

											value="${sessionScope.userName}"

											required>

									</div>

									<div class="col-md-6 mb-3">

										<label

											class="form-label">

											Email Address

										</label>

										<input

											type="email"

											class="form-control"

											value="${sessionScope.userEmail}"

											disabled>

									</div>

									<div class="col-md-6 mb-3">

										<label

											class="form-label">

											Phone Number

										</label>

										<input

											type="tel"

											class="form-control"

											name="phone"

											value="${sessionScope.userPhone}"

											required>

									</div>

									<div class="col-md-6 mb-3">

										<label

											class="form-label">

											Date of Birth

										</label>

										<input

											type="date"

											class="form-control"

											name="dob"

											value="${sessionScope.userDob}">

									</div>

									<div class="col-md-12 mb-3">

										<label

											class="form-label">

											New Password

										</label>

										<input

											type="password"

											class="form-control"

											name="newPassword"

											placeholder="Leave blank to keep current">

									</div>

								</div>

								<button

									type="submit"

									class="btn btn-warning">

									<i class="fa-solid fa-save me-2"></i>

									Update Profile

								</button>

							</form>

						</div>

					</div>

					<!-- =====================================
							ADDRESSES
					===================================== -->

					<div class="card shadow-sm" id="address-section">

						<div class="card-header d-flex justify-content-between">

							<h4>

								Saved Addresses

							</h4>

							<button

								class="btn btn-sm btn-warning"

								data-bs-toggle="modal"

								data-bs-target="#addressModal">

								<i class="fa-solid fa-plus"></i>

								Add Address

							</button>

						</div>

						<div class="card-body">

							<c:choose>

								<c:when test="${not empty addressList}">

									<c:forEach

										var="address"

										items="${addressList}">

										<div class="address-item border-bottom py-3">

											<div class="d-flex justify-content-between">

												<div>

													<strong>

														${address.addressType}

													</strong>

													<c:if test="${address.defaultAddress}">

														<span class="badge bg-success">

															Default

														</span>

													</c:if>

													<p>

														${address.houseNo},

														${address.street},

														${address.city},

														${address.state}

														-

														${address.pincode}

													</p>

												</div>

												<div>

													<a

														href="${pageContext.request.contextPath}/profile?action=setDefault&addressId=${address.addressId}"

														class="btn btn-sm btn-outline-success">

														Set Default

													</a>

													<a

														href="${pageContext.request.contextPath}/profile?action=deleteAddress&addressId=${address.addressId}"

														class="btn btn-sm btn-outline-danger"

														onclick="return confirm('Delete this address?')">

														<i class="fa-solid fa-trash"></i>

													</a>

												</div>

											</div>

										</div>

									</c:forEach>

								</c:when>

								<c:otherwise>

									<div class="alert alert-info">

										No addresses saved.

									</div>

								</c:otherwise>

							</c:choose>

						</div>

					</div>

				</div>

			</div>

		</div>

	</section>

	<!-- ==========================================
			ADD ADDRESS MODAL
	========================================== -->

	<div class="modal fade" id="addressModal" tabindex="-1">

		<div class="modal-dialog">

			<div class="modal-content">

				<div class="modal-header">

					<h5 class="modal-title">

						Add New Address

					</h5>

					<button

						type="button"

						class="btn-close"

						data-bs-dismiss="modal">

					</button>

				</div>

				<div class="modal-body">

					<form

							action="${pageContext.request.contextPath}/profile"

						<div class="mb-3">

							<label

								class="form-label">

								Address Type

							</label>

							<select

								class="form-select"

								name="addressType"

								required>

								<option value="Home">

									Home

								</option>

								<option value="Work">

									Work

								</option>

								<option value="Other">

									Other

								</option>

							</select>

						</div>

						<div class="mb-3">

							<label

								class="form-label">

								House/Flat No

							</label>

							<input

								type="text"

								class="form-control"

								name="houseNo"

								required>

						</div>

						<div class="mb-3">

							<label

								class="form-label">

								Street

							</label>

							<input

								type="text"

								class="form-control"

								name="street"

								required>

						</div>

						<div class="row">

							<div class="col-md-6 mb-3">

								<label

									class="form-label">

									City

								</label>

								<input

									type="text"

									class="form-control"

									name="city"

									required>

							</div>

							<div class="col-md-6 mb-3">

								<label

									class="form-label">

									State

								</label>

								<input

									type="text"

									class="form-control"

									name="state"

									required>

							</div>

						</div>

						<div class="mb-3">

							<label

								class="form-label">

								Pincode

							</label>

							<input

								type="text"

								class="form-control"

								name="pincode"

								pattern="[0-9]{5,6}"

								required>

						</div>

						<div class="mb-3 form-check">

							<input

								class="form-check-input"

								type="checkbox"

								name="defaultAddress"

								id="defaultAddress">

							<label

								class="form-check-label"

								for="defaultAddress">

								Set as Default Address

							</label>

						</div>

						<button

							type="submit"

							class="btn btn-warning w-100">

							<i class="fa-solid fa-save me-2"></i>

							Save Address

						</button>

					</form>

				</div>

			</div>

		</div>

	</div>

</main>

<script>
	window.contextPath = '${pageContext.request.contextPath}';
</script>

<script

	src="${pageContext.request.contextPath}/assets/js/profile.js">

</script>

<%@ include file="../includes/footer.jsp"%>