<%@ page language="java"
	contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="c"
	uri="jakarta.tags.core"%>

<%@ include file="includes/header.jsp"%>

<link rel="stylesheet"
	href="${pageContext.request.contextPath}/assets/css/error.css">

<%@ include file="includes/navbar.jsp"%>

<main class="error-page">

	<!-- ==========================================
				ERROR SECTION
	========================================== -->

	<section class="error-section py-5">

		<div class="container">

			<div class="row justify-content-center">

				<div class="col-lg-8 text-center">

					<!-- Error Icon -->

					<div class="error-icon mb-4">

						<c:choose>

							<c:when test="${errorCode == '404'}">

								<i class="fa-solid fa-map-location-dot text-warning"></i>

							</c:when>

							<c:when test="${errorCode == '500'}">

								<i class="fa-solid fa-triangle-exclamation text-danger"></i>

							</c:when>

							<c:otherwise>

								<i class="fa-solid fa-circle-exclamation text-primary"></i>

							</c:otherwise>

						</c:choose>

					</div>

					<!-- Error Code -->

					<h1 class="error-code">

						${errorCode != null ? errorCode : 'Oops!'}

					</h1>

					<!-- Error Message -->

					<h2 class="error-title">

						${errorTitle != null ? errorTitle : 'Something Went Wrong'}

					</h2>

					<p class="error-message">

						${errorMessage != null ? errorMessage : "We're sorry, but an unexpected error occurred. Please try again later."}

					</p>

					<!-- Error Details (for developers) -->

					<c:if test="${not empty errorDetails}">

						<div class="alert alert-secondary text-start mt-4">

							<strong>

								Error Details:

							</strong>

							<br>

							<small class="text-muted">

								${errorDetails}

							</small>

						</div>

					</c:if>

					<!-- Action Buttons -->

					<div class="error-actions mt-4">

						<a

							href="${pageContext.request.contextPath}/index.jsp"

							class="btn btn-warning btn-lg me-3">

							<i class="fa-solid fa-house me-2"></i>

							Go Home

						</a>

						<button

							class="btn btn-outline-secondary btn-lg"

							onclick="history.back()">

							<i class="fa-solid fa-arrow-left me-2"></i>

							Go Back

						</button>

					</div>

					<!-- Help Section -->

					<div class="help-section mt-5">

						<h5>

							Need Help?

						</h5>

						<p>

							Contact our support team:

							<a

								href="mailto:support@onlinefooddelivery.com"

								class="text-warning">

								support@onlinefooddelivery.com

							</a>

						</p>

					</div>

				</div>

			</div>

		</div>

	</section>

</main>

<script

	src="${pageContext.request.contextPath}/assets/js/error.js">

</script>

<%@ include file="includes/footer.jsp"%>