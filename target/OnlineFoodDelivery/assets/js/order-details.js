/* ==========================================
   ORDER DETAILS PAGE JAVASCRIPT
   ========================================== */

document.addEventListener('DOMContentLoaded', function() {

    // ---------- Cancel order confirmation ----------
    const cancelButtons = document.querySelectorAll('.order-details-page .btn-danger');

    cancelButtons.forEach(function(btn) {
        btn.addEventListener('click', function(e) {
            //const orderId = this.getAttribute('href')?.match(/orderId=(\d+)/)?.[1];
			let orderId = null;

			const href = this.getAttribute('href');

			if (href) {
			    const match = href.match(/orderId=(\d+)/);

			    if (match) {
			        orderId = match[1];
			    }
			}
			if (!confirm('Are you sure you want to cancel Order #' + orderId + '?')) {
                e.preventDefault();
            }
        });
    });

    // ---------- Invoice download ----------
    const invoiceButton = document.querySelector('.order-details-page .btn-outline-secondary[href*="Invoice"]');

    if (invoiceButton) {
        invoiceButton.addEventListener('click', function(e) {
            const originalText = this.innerHTML;
            this.innerHTML = '<i class="fa-solid fa-spinner fa-spin me-2"></i>Downloading...';
            this.disabled = true;

            setTimeout(function() {
                btn.innerHTML = '<i class="fa-solid fa-check me-2"></i>Downloaded!';
                btn.classList.add('btn-success');
                btn.classList.remove('btn-outline-secondary');

                setTimeout(function() {
                    btn.innerHTML = originalText;
                    btn.classList.remove('btn-success');
                    btn.classList.add('btn-outline-secondary');
                    btn.disabled = false;
                }, 1500);
            }, 1200);
        });
    }

    // ---------- Reorder button ----------
    const reorderButton = document.querySelector('.order-details-page .btn-success');

    if (reorderButton) {
        reorderButton.addEventListener('click', function(e) {
            const originalText = this.innerHTML;
            this.innerHTML = '<i class="fa-solid fa-spinner fa-spin me-2"></i>Adding to Cart...';
            this.disabled = true;

            setTimeout(function() {
                btn.innerHTML = '<i class="fa-solid fa-check me-2"></i>Added to Cart!';
                btn.classList.remove('btn-success');
                btn.classList.add('btn-warning');

                setTimeout(function() {
                    btn.innerHTML = originalText;
                    btn.classList.remove('btn-warning');
                    btn.classList.add('btn-success');
                    btn.disabled = false;
                }, 1500);
            }, 1000);
        });
    }

    // ---------- Star rating interaction ----------
    const ratingStars = document.querySelectorAll('.rating-stars label');

    ratingStars.forEach(function(star) {
        star.addEventListener('click', function() {
            // Remove active class from all stars
            /*ratingStars.forEach(function(s) {
                s.querySelector('i')?.classList.remove('fa-solid');
                s.querySelector('i')?.classList.add('fa-regular');
            });*/
			ratingStars.forEach(function(s) {
			    const icon = s.querySelector('i');

			    if (icon) {
			        icon.classList.remove('fa-solid');
			        icon.classList.add('fa-regular');
			    }
			});

            // Add active class to clicked star and all before it
            let found = false;
            ratingStars.forEach(function(s) {
                /*if (s === star || found) {
                    s.querySelector('i')?.classList.remove('fa-regular');
                    s.querySelector('i')?.classList.add('fa-solid');
                    s.querySelector('i')?.style.color = '#FFC107';
                    found = true;
                }*/
				if (s === star || found) {
				    const icon = s.querySelector('i');

				    if (icon) {
				        icon.classList.remove('fa-regular');
				        icon.classList.add('fa-solid');
				        icon.style.color = '#FFC107';
				    }

				    found = true;
				}
            });

            // Update hidden input
            const ratingInput = document.querySelector('input[name="rating"]:checked');
            if (ratingInput) {
                ratingInput.checked = false;
            }
            const starId = star.getAttribute('for');
            if (starId) {
                const input = document.getElementById(starId);
                if (input) {
                    input.checked = true;
                }
            }
        });

        // Hover effect
        star.addEventListener('mouseenter', function() {
            const stars = this.closest('.rating-stars').querySelectorAll('label');
            let found = false;
            stars.forEach(function(s) {
                /*if (s === star || found) {
                    s.querySelector('i')?.classList.remove('fa-regular');
                    s.querySelector('i')?.classList.add('fa-solid');
                    s.querySelector('i')?.style.color = '#FFC107';
                    found = true;
                }*/
				if (s === star || found) {
				    const icon = s.querySelector('i');

				    if (icon) {
				        icon.classList.remove('fa-regular');
				        icon.classList.add('fa-solid');
				        icon.style.color = '#FFC107';
				    }

				    found = true;
				}
            });
        });

        star.addEventListener('mouseleave', function() {
            const stars = this.closest('.rating-stars').querySelectorAll('label');
            const checkedInput = document.querySelector('input[name="rating"]:checked');
            const checkedId = checkedInput ? checkedInput.id : null;

            stars.forEach(function(s) {
                const isChecked = s.getAttribute('for') === checkedId;
				const icon = s.querySelector('i');
				if (icon) {
				    if (isChecked) {
				        icon.classList.remove('fa-regular');
				        icon.classList.add('fa-solid');
				        icon.style.color = '#FFC107';
				    } else {
				        icon.classList.remove('fa-solid');
				        icon.classList.add('fa-regular');
				        icon.style.color = '#ddd';
				    }
				}
				
				/*if (isChecked) {
                    s.querySelector('i')?.classList.remove('fa-regular');
                    s.querySelector('i')?.classList.add('fa-solid');
                    s.querySelector('i')?.style.color = '#FFC107';
                } else {
                    s.querySelector('i')?.classList.remove('fa-solid');
                    s.querySelector('i')?.classList.add('fa-regular');
                    s.querySelector('i')?.style.color = '#ddd';
                }*/
            });
        });
    });

    // ---------- Review form validation ----------
    const reviewForm = document.querySelector('.review-section form');

    if (reviewForm) {
        reviewForm.addEventListener('submit', function(e) {
            const ratingInput = document.querySelector('input[name="rating"]:checked');
            if (!ratingInput) {
                e.preventDefault();
                alert('Please select a rating.');
                /*
				document.querySelector('.rating-stars')?.scrollIntoView({
                    behavior: 'smooth',
                    block: 'center'
                });
				*/
				const ratingStars = document.querySelector('.rating-stars');

				if (ratingStars) {
				    ratingStars.scrollIntoView({
				        behavior: 'smooth',
				        block: 'center'
				    });
				}
                return;
            }

            const commentInput = document.getElementById('comment');
            if (commentInput && commentInput.value.trim() === '') {
                e.preventDefault();
                commentInput.classList.add('is-invalid');
                setTimeout(function() {
                    commentInput.classList.remove('is-invalid');
                }, 3000);
                alert('Please add a comment.');
                return;
            }

            // Show loading state
            const submitBtn = reviewForm.querySelector('button[type="submit"]');
            if (submitBtn) {
                submitBtn.disabled = true;
                submitBtn.innerHTML = '<i class="fa-solid fa-spinner fa-spin me-2"></i>Submitting...';
            }
        });
    }

    // ---------- Timeline animation ----------
    const timelineItems = document.querySelectorAll('.timeline-item');

    timelineItems.forEach(function(item, index) {
        item.style.opacity = '0';
        item.style.transform = 'translateX(-20px)';
        item.style.transition = 'all 0.5s ease';

        setTimeout(function() {
            item.style.opacity = '1';
            item.style.transform = 'translateX(0)';
        }, 200 + (index * 200));
    });

    // ---------- Order summary expand/collapse ----------
    const summaryHeader = document.querySelector('.order-items .card-header');

    if (summaryHeader) {
        summaryHeader.style.cursor = 'pointer';
        summaryHeader.addEventListener('click', function() {
            //const body = this.closest('.card')?.querySelector('.card-body');
			let body = null;

			const card = this.closest('.card');

			if (card) {
			    body = card.querySelector('.card-body');
			}
			if (body) {
                if (body.style.display === 'none') {
                    body.style.display = 'block';
                    this.querySelector('h4').innerHTML = 'Ordered Items <i class="fa-solid fa-chevron-up"></i>';
                } else {
                    body.style.display = 'none';
                    this.querySelector('h4').innerHTML = 'Ordered Items <i class="fa-solid fa-chevron-down"></i>';
                }
            }
        });
    }

    console.log('Order Details Page JS Loaded');
});