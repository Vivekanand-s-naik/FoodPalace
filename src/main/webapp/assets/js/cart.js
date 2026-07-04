/* ==========================================
   CART PAGE JAVASCRIPT
   ========================================== */

document.addEventListener('DOMContentLoaded', function() {

    // ---------- Quantity input validation ----------
    const quantityInputs = document.querySelectorAll('.cart-page .form-control[name="quantity"]');

    quantityInputs.forEach(function(input) {
        input.addEventListener('change', function() {
            let value = parseInt(this.value);
            if (isNaN(value) || value < 1) {
                this.value = 1;
            }
        });

        input.addEventListener('input', function() {
            let value = parseInt(this.value);
            if (isNaN(value) || value < 1) {
                this.value = 1;
            }
        });
    });

    // ---------- Update quantity auto-submit ----------
    const quantityForms = document.querySelectorAll('.cart-page .card .row .form');

    quantityForms.forEach(function(form) {
        const quantityInput = form.querySelector('input[name="quantity"]');
        if (quantityInput) {
            let timeoutId = null;

            quantityInput.addEventListener('input', function() {
                clearTimeout(timeoutId);
                timeoutId = setTimeout(function() {
                    const btn = form.querySelector('.btn-success');
                    if (btn) {
                        btn.click();
                    }
                }, 800);
            });
        }
    });

    // ---------- Remove item confirmation ----------
    const removeButtons = document.querySelectorAll('.cart-page .btn-danger');

    removeButtons.forEach(function(btn) {
        btn.addEventListener('click', function(e) {
            //const itemName = this.closest('.card')?.querySelector('h4')?.textContent || 'this item';
			let itemName = 'this item';

			const card = this.closest('.card');
			if (card) {
			    const heading = card.querySelector('h4');
			    if (heading) {
			        itemName = heading.textContent;
			    }
			}
			if (!confirm('Remove ' + itemName + ' from your cart?')) {
                e.preventDefault();
            }
        });
    });

    // ---------- Coupon apply animation ----------
    const couponForm = document.querySelector('.cart-page .input-group form');

    if (couponForm) {
        const couponInput = couponForm.querySelector('input[name="couponCode"]');
        const applyBtn = couponForm.querySelector('.btn');

        if (applyBtn) {
            applyBtn.addEventListener('click', function(e) {
                e.preventDefault();

                if (!couponInput || couponInput.value.trim() === '') {
					if (couponInput) {
					    couponInput.classList.add('is-invalid');
					}
                    setTimeout(function() {
						if (couponInput) {
						    couponInput.classList.remove('is-invalid');
						}
                    }, 2000);
                    return;
                }

                // Simulate coupon application
                const originalText = this.innerHTML;
                this.innerHTML = '<i class="fa-solid fa-spinner fa-spin me-2"></i>Applying...';
                this.disabled = true;

                setTimeout(function() {
                    const discountMsg = document.createElement('div');
                    discountMsg.className = 'alert alert-success mt-3';
                    discountMsg.textContent = 'Coupon applied! You saved ₹50';
                    couponForm.appendChild(discountMsg);

                    applyBtn.innerHTML = '<i class="fa-solid fa-check me-2"></i>Applied';
                    applyBtn.disabled = true;

                    // Update total (simulate)
                    const totalElement = document.querySelector('.cart-page .card-body h4');
                    if (totalElement) {
                        const currentTotal = parseFloat(totalElement.textContent.replace('₹', '').trim());
                        if (!isNaN(currentTotal)) {
                            const newTotal = currentTotal - 50;
                            totalElement.textContent = '₹ ' + newTotal.toFixed(2);
                            totalElement.style.color = '#28A745';
                        }
                    }

                    setTimeout(function() {
                        discountMsg.remove();
                    }, 5000);
                }, 1500);
            });
        }

        if (couponInput) {
            couponInput.addEventListener('keypress', function(e) {
                if (e.key === 'Enter') {
                    e.preventDefault();
                    const form = this.closest('form');
                    if (form) {
                        const btn = form.querySelector('.btn');
                        if (btn) {
                            btn.click();
                        }
                    }
                }
            });
        }
    }

    // ---------- Proceed to checkout button validation ----------
    const checkoutBtn = document.querySelector('.cart-page .btn-warning[href*="checkout"]');

    if (checkoutBtn) {
        checkoutBtn.addEventListener('click', function(e) {
            const cartItems = document.querySelectorAll('.cart-page .card .row .col-md-9');
            if (cartItems.length === 0) {
                e.preventDefault();
                alert('Your cart is empty. Please add items before proceeding to checkout.');
            }
        });
    }

    // ---------- Quantity update button animation ----------
    const updateButtons = document.querySelectorAll('.cart-page .btn-success');

    updateButtons.forEach(function(btn) {
        btn.addEventListener('click', function() {
            const originalText = this.innerHTML;
            this.innerHTML = '<i class="fa-solid fa-check"></i>';
            this.style.backgroundColor = '#28A745';

            setTimeout(function() {
                btn.innerHTML = originalText;
                btn.style.backgroundColor = '';
            }, 1000);
        });
    });

    // ---------- Cart item count update ----------
    function updateCartBadge() {
        const cartBadge = document.querySelector('.navbar .badge.bg-warning');
        const cartItems = document.querySelectorAll('.cart-page .card .row .col-md-9');
        if (cartBadge && cartItems) {
            cartBadge.textContent = cartItems.length;
        }
    }

    // Update badge when items are removed
    const removeLinks = document.querySelectorAll('.cart-page .btn-danger[href*="remove"]');
    removeLinks.forEach(function(link) {
        link.addEventListener('click', function() {
            setTimeout(updateCartBadge, 100);
        });
    });

    console.log('Cart Page JS Loaded');
});