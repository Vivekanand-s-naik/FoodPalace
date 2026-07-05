<%@ page language="java"
	contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!-- ==========================================
			FOOTER
========================================== -->

<footer class="footer-section">

	<div class="container">

		<div class="row gy-4">

			<!-- Company -->

			<div class="col-lg-4 col-md-6">

				<h4 class="footer-title">

					OnlineFoodDelivery

				</h4>

				<p>

					Order delicious food from your favourite
					restaurants anytime and anywhere.
					Fast delivery, secure payments,
					and quality service.

				</p>

				<div class="social-icons">

					<a href="#"><i class="fab fa-facebook-f"></i></a>

					<a href="#"><i class="fab fa-instagram"></i></a>

					<a href="#"><i class="fab fa-twitter"></i></a>

					<a href="#"><i class="fab fa-linkedin-in"></i></a>

				</div>

			</div>

			<!-- Quick Links -->

			<div class="col-lg-2 col-md-6">

				<h5>

					Quick Links

				</h5>

				<ul class="list-unstyled">

					<li>

						<a href="${pageContext.request.contextPath}/index.jsp">

							Home

						</a>

					</li>

					<li>

						<a href="${pageContext.request.contextPath}/restaurants.jsp">

							Restaurants

						</a>

					</li>

					<li>

						<a href="${pageContext.request.contextPath}/menu.jsp">

							Menu

						</a>

					</li>

					<li>

						<a href="${pageContext.request.contextPath}/cart.jsp">

							Cart

						</a>

					</li>

				</ul>

			</div>

			<!-- Customer -->

			<div class="col-lg-3 col-md-6">

				<h5>

					Customer

				</h5>

				<ul class="list-unstyled">

					<li>

						<a href="${pageContext.request.contextPath}/auth/login.jsp">

							Login

						</a>

					</li>

					<li>

						<a href="${pageContext.request.contextPath}/auth/register.jsp">

							Register

						</a>

					</li>

					<li>

						<a href="${pageContext.request.contextPath}/orders.jsp">

							My Orders

						</a>

					</li>

					<li>

						<a href="${pageContext.request.contextPath}/customer/profile.jsp">

							My Profile

						</a>

					</li>

				</ul>

			</div>

			<!-- Contact -->

			<div class="col-lg-3 col-md-6">

				<h5>

					Contact

				</h5>

				<p>

					<i class="fa-solid fa-location-dot"></i>

					Bengaluru, Karnataka

				</p>

				<p>

					<i class="fa-solid fa-phone"></i>

					+91 9876543210

				</p>

				<p>

					<i class="fa-solid fa-envelope"></i>

					support@onlinefooddelivery.com

				</p>

			</div>

		</div>

		<hr>

		<div class="row">

			<div class="col-md-6">

				<p>

					© 2026 OnlineFoodDelivery.
					All Rights Reserved.

				</p>

			</div>

			<div class="col-md-6 text-md-end">

				<a href="#"><i class="fab fa-facebook-f"></i></a>

				<a href="#"><i class="fab fa-instagram"></i></a>

				<a href="#"><i class="fab fa-x-twitter"></i></a>

				<a href="#"><i class="fab fa-linkedin"></i></a>

			</div>

		</div>

	</div>

</footer>

<!-- ==========================================
			BOOTSTRAP JS
========================================== -->

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.7/dist/js/bootstrap.bundle.min.js">
</script>

<!-- ==========================================
			COMMON JS
========================================== -->

<script src="${pageContext.request.contextPath}/assets/js/common.js">
</script>

<!-- ==========================================
			FOOTER JS
========================================== -->

<script src="${pageContext.request.contextPath}/assets/js/footer.js">
</script>

</body>

</html>