/* ==========================================
   CHECKOUT PAGE JAVASCRIPT
   ========================================== */

document.addEventListener('DOMContentLoaded', function() {

    // ---------- Address selection validation ----------
    const addressRadios = document.querySelectorAll('input[name="addressId"]');

    addressRadios.forEach(function(radio) {
        radio.addEventListener('change', function() {
            // Remove highlight from all address items
            document.querySelectorAll('.checkout-page .form-check').forEach(function(item) {
                item.style.borderColor = '#E5E5E5';
            });
            // Highlight selected
            const parent = this.closest('.form-check');
            if (parent) {
                parent.style.borderColor = '#FFC107';
                parent.style.backgroundColor = '#FFF8E1';
            }
        });
    });

    // ---------- Payment method selection ----------
    const paymentRadios = document.querySelectorAll('input[name="paymentMethod"]');

    paymentRadios.forEach(function(radio) {
        radio.addEventListener('change', function() {
            // Remove highlight from all payment items
            document.querySelectorAll('.checkout-page .form-check').forEach(function(item) {
                item.style.borderColor = '#E5E5E5';
                item.style.backgroundColor = 'transparent';
            });
            // Highlight selected
            const parent = this.closest('.form-check');
            if (parent) {
                parent.style.borderColor = '#FFC107';
                parent.style.backgroundColor = '#FFF8E1';
            }
        });
    });

    // ---------- Form validation ----------
    const checkoutForm = document.querySelector('.checkout-section form');

    if (checkoutForm) {
        checkoutForm.addEventListener('submit', function(e) {
            let isValid = true;

            // Validate address selection
            const selectedAddress = document.querySelector('input[name="addressId"]:checked');
            if (!selectedAddress) {
                e.preventDefault();
                isValid = false;
                // Highlight address section
                const addressCard = document.querySelector('.checkout-page .card:first-child');
                if (addressCard) {
                    addressCard.style.border = '2px solid #DC3545';
                    addressCard.style.borderRadius = '12px';
                    setTimeout(function() {
                        addressCard.style.border = 'none';
                    }, 3000);
                }
                alert('Please select a delivery address.');
            }

            // Validate payment method
            const selectedPayment = document.querySelector('input[name="paymentMethod"]:checked');
            if (!selectedPayment) {
                e.preventDefault();
                isValid = false;
                // Highlight payment section
                const paymentCard = document.querySelector('.checkout-page .card:nth-child(2)');
                if (paymentCard) {
                    paymentCard.style.border = '2px solid #DC3545';
                    paymentCard.style.borderRadius = '12px';
                    setTimeout(function() {
                        paymentCard.style.border = 'none';
                    }, 3000);
                }
                alert('Please select a payment method.');
            }

            // Validate terms agreement
            const termsCheckbox = document.getElementById('agree');
            if (termsCheckbox && !termsCheckbox.checked) {
                e.preventDefault();
                isValid = false;
                termsCheckbox.classList.add('is-invalid');
                setTimeout(function() {
                    termsCheckbox.classList.remove('is-invalid');
                }, 3000);
                alert('Please agree to the terms and conditions.');
            }

            // Show loading spinner if valid
            if (isValid) {
                const submitBtn = checkoutForm.querySelector('button[type="submit"]');
                if (submitBtn) {
                    submitBtn.disabled = true;
                    submitBtn.innerHTML = `
                        <i class="fa-solid fa-spinner fa-spin me-2"></i>
                        Processing Order...
                    `;
                }
            }
        });
    }

    // ---------- Terms checkbox validation ----------
    const termsCheckbox = document.getElementById('agree');

    if (termsCheckbox) {
        termsCheckbox.addEventListener('change', function() {
            if (this.checked) {
                this.classList.remove('is-invalid');
            }
        });
    }

    // ---------- Add address button smooth scroll ----------
    const addAddressBtn = document.querySelector('.checkout-page .btn-outline-warning[href*="profile"]');

    if (addAddressBtn) {
        addAddressBtn.addEventListener('click', function(e) {
            e.preventDefault();
            const targetUrl = this.getAttribute('href');
            if (targetUrl) {
                // Save current page to return after adding address
                localStorage.setItem('returnToCheckout', 'true');
                window.location.href = targetUrl;
            }
        });
    }

    // ---------- Order summary expansion ----------
    const summaryToggle = document.querySelector('.checkout-page .card-header .btn');

    if (summaryToggle) {
        summaryToggle.addEventListener('click', function() {
            //const body = this.closest('.card')?.querySelector('.card-body');
			let body = null;

			const card = this.closest('.card');

			if (card) {
			    body = card.querySelector('.card-body');
			}
			if (body) {
                if (body.style.display === 'none') {
                    body.style.display = 'block';
                    this.innerHTML = '<i class="fa-solid fa-chevron-up"></i> Hide';
                } else {
                    body.style.display = 'none';
                    this.innerHTML = '<i class="fa-solid fa-chevron-down"></i> Show';
                }
            }
        });
    }

    // ---------- Auto-select default address ----------
    const defaultAddress = document.querySelector('input[name="addressId"][checked]');
    if (defaultAddress) {
        defaultAddress.dispatchEvent(new Event('change'));
    }

    console.log('Checkout Page JS Loaded');
});