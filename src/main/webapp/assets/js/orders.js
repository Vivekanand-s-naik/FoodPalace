/* ==========================================
   ORDERS PAGE JAVASCRIPT
   ========================================== */

document.addEventListener('DOMContentLoaded', function() {

    // ---------- Status filter buttons ----------
    const filterButtons = document.querySelectorAll('.orders-list .btn-outline-warning, .orders-list .btn-warning');

    filterButtons.forEach(function(btn) {
        btn.addEventListener('click', function(e) {
            // Don't prevent default - allow navigation
            // Just add loading state
            const originalText = this.innerHTML;
            this.innerHTML = '<i class="fa-solid fa-spinner fa-spin me-2"></i>Loading...';
            this.disabled = true;

            setTimeout(function() {
                btn.innerHTML = originalText;
                btn.disabled = false;
            }, 500);
        });
    });

    // ---------- Cancel order confirmation ----------
    const cancelButtons = document.querySelectorAll('.orders-list .btn-outline-danger');

    cancelButtons.forEach(function(btn) {
        btn.addEventListener('click', function(e) {
            //const orderId = this.getAttribute('href')?.match(/orderId=(\d+)/)?.[1];
			let orderId = null;

			const href = this.getAttribute('href');

			if (href) {
			    const matches = href.match(/orderId=(\d+)/);

			    if (matches) {
			        orderId = matches[1];
			    }
			}
			if (!confirm('Are you sure you want to cancel Order #' + orderId + '?')) {
                e.preventDefault();
            }
        });
    });

    // ---------- Reorder button ----------
    const reorderButtons = document.querySelectorAll('.orders-list .btn-outline-success');

    reorderButtons.forEach(function(btn) {
        btn.addEventListener('click', function(e) {
            const originalText = this.innerHTML;
            this.innerHTML = '<i class="fa-solid fa-spinner fa-spin me-2"></i>Adding to Cart...';
            this.disabled = true;

            // Simulate reorder
            setTimeout(function() {
                btn.innerHTML = '<i class="fa-solid fa-check me-2"></i>Added!';
                btn.classList.remove('btn-outline-success');
                btn.classList.add('btn-success');

                setTimeout(function() {
                    btn.innerHTML = originalText;
                    btn.classList.remove('btn-success');
                    btn.classList.add('btn-outline-success');
                    btn.disabled = false;
                }, 1500);
            }, 1000);
        });
    });

    // ---------- Invoice download ----------
    const invoiceButtons = document.querySelectorAll('.orders-list .btn-outline-secondary');

    invoiceButtons.forEach(function(btn) {
        btn.addEventListener('click', function(e) {
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
    });

    // ---------- Order card expand/collapse ----------
    const orderCards = document.querySelectorAll('.orders-list .card');

    orderCards.forEach(function(card) {
        const header = card.querySelector('.card-body .row:first-child');
        if (header) {
            header.style.cursor = 'pointer';
            header.addEventListener('click', function() {
                const body = card.querySelector('.card-body .row:last-child');
                const details = card.querySelector('.card-body .mt-3');
                if (body && details) {
                    if (details.style.display === 'none') {
                        details.style.display = 'block';
                        body.style.display = 'block';
                    } else {
                        details.style.display = 'none';
                        body.style.display = 'none';
                    }
                }
            });
        }
    });

    // ---------- Search filter auto-submit ----------
    const searchInput = document.querySelector('.orders-list .form-control');

    if (searchInput) {
        let timeoutId = null;
        searchInput.addEventListener('input', function() {
            clearTimeout(timeoutId);
            timeoutId = setTimeout(function() {
                const form = searchInput.closest('form');
                if (form) {
                    form.submit();
                }
            }, 500);
        });
    }

    // ---------- Status badge tooltip ----------
    const statusBadges = document.querySelectorAll('.orders-list .badge');

    statusBadges.forEach(function(badge) {
        const status = badge.textContent.trim().toLowerCase();
        const tooltipText = {
            'delivered': 'Order has been delivered successfully',
            'preparing': 'Restaurant is preparing your order',
            'out for delivery': 'Order is on the way',
            'placed': 'Order has been placed',
            'cancelled': 'Order has been cancelled'
        };
        if (tooltipText[status]) {
            badge.setAttribute('data-bs-toggle', 'tooltip');
            badge.setAttribute('data-bs-placement', 'top');
            badge.setAttribute('data-bs-title', tooltipText[status]);
        }
    });

    console.log('Orders Page JS Loaded');
});