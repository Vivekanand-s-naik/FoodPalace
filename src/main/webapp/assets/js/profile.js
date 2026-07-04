/* ==========================================
   PROFILE PAGE JAVASCRIPT
   ========================================== */

document.addEventListener('DOMContentLoaded', function() {

    // ---------- Profile form validation ----------
    const profileForm = document.querySelector('.profile-page .card .card-body form');

    if (profileForm) {
        profileForm.addEventListener('submit', function(e) {
            let isValid = true;

            // Validate Full Name
            const fullName = this.querySelector('input[name="fullName"]');
            if (fullName && fullName.value.trim() === '') {
                e.preventDefault();
                fullName.classList.add('is-invalid');
                isValid = false;
            } else if (fullName) {
                fullName.classList.remove('is-invalid');
                fullName.classList.add('is-valid');
            }

            // Validate Phone
            const phone = this.querySelector('input[name="phone"]');
            if (phone && phone.value.trim() === '') {
                e.preventDefault();
                phone.classList.add('is-invalid');
                isValid = false;
            } else if (phone && !validatePhone(phone.value.trim())) {
                e.preventDefault();
                phone.classList.add('is-invalid');
                isValid = false;
            } else if (phone) {
                phone.classList.remove('is-invalid');
                phone.classList.add('is-valid');
            }

            // Validate New Password (optional)
            const newPassword = this.querySelector('input[name="newPassword"]');
            if (newPassword && newPassword.value.trim() !== '' && newPassword.value.length < 6) {
                e.preventDefault();
                newPassword.classList.add('is-invalid');
                isValid = false;
            } else if (newPassword && newPassword.value.trim() !== '') {
                newPassword.classList.remove('is-invalid');
                newPassword.classList.add('is-valid');
            }

            if (isValid) {
                const submitBtn = this.querySelector('button[type="submit"]');
                if (submitBtn) {
                    submitBtn.disabled = true;
                    submitBtn.innerHTML = '<i class="fa-solid fa-spinner fa-spin me-2"></i>Saving...';
                }
            }
        });

        // Real-time validation
        const fullName = profileForm.querySelector('input[name="fullName"]');
        const phone = profileForm.querySelector('input[name="phone"]');
        const newPassword = profileForm.querySelector('input[name="newPassword"]');

        if (fullName) {
            fullName.addEventListener('input', function() {
                if (this.value.trim() === '') {
                    this.classList.add('is-invalid');
                    this.classList.remove('is-valid');
                } else {
                    this.classList.remove('is-invalid');
                    this.classList.add('is-valid');
                }
            });
        }

        if (phone) {
            phone.addEventListener('input', function() {
                if (this.value.trim() === '' || !validatePhone(this.value.trim())) {
                    this.classList.add('is-invalid');
                    this.classList.remove('is-valid');
                } else {
                    this.classList.remove('is-invalid');
                    this.classList.add('is-valid');
                }
            });
        }

        if (newPassword) {
            newPassword.addEventListener('input', function() {
                if (this.value.trim() !== '' && this.value.length < 6) {
                    this.classList.add('is-invalid');
                    this.classList.remove('is-valid');
                } else if (this.value.trim() !== '') {
                    this.classList.remove('is-invalid');
                    this.classList.add('is-valid');
                } else {
                    this.classList.remove('is-invalid');
                    this.classList.remove('is-valid');
                }
            });
        }
    }

    // ---------- Address form validation ----------
    const addressForm = document.querySelector('#addressModal form');

    if (addressForm) {
        addressForm.addEventListener('submit', function(e) {
            let isValid = true;

            const requiredFields = this.querySelectorAll('[required]');
            requiredFields.forEach(function(field) {
                if (field.value.trim() === '') {
                    e.preventDefault();
                    field.classList.add('is-invalid');
                    isValid = false;
                } else {
                    field.classList.remove('is-invalid');
                    field.classList.add('is-valid');
                }
            });

            // Validate Pincode
            const pincode = this.querySelector('input[name="pincode"]');
            if (pincode && pincode.value.trim() !== '' && !/^[0-9]{5,6}$/.test(pincode.value.trim())) {
                e.preventDefault();
                pincode.classList.add('is-invalid');
                isValid = false;
            }

            if (isValid) {
                const submitBtn = this.querySelector('button[type="submit"]');
                if (submitBtn) {
                    submitBtn.disabled = true;
                    submitBtn.innerHTML = '<i class="fa-solid fa-spinner fa-spin me-2"></i>Saving...';
                }
            }
        });

        // Real-time validation for address form
        const addressInputs = addressForm.querySelectorAll('.form-control, .form-select');
        addressInputs.forEach(function(input) {
            input.addEventListener('input', function() {
                if (this.value.trim() !== '') {
                    this.classList.remove('is-invalid');
                    this.classList.add('is-valid');
                }
            });
        });

        const pincodeInput = addressForm.querySelector('input[name="pincode"]');
        if (pincodeInput) {
            pincodeInput.addEventListener('input', function() {
                if (this.value.trim() !== '' && /^[0-9]{5,6}$/.test(this.value.trim())) {
                    this.classList.remove('is-invalid');
                    this.classList.add('is-valid');
                }
            });
        }
    }

    // ---------- Set default address confirmation ----------
    const setDefaultButtons = document.querySelectorAll('.address-item .btn-outline-success');

    setDefaultButtons.forEach(function(btn) {
        btn.addEventListener('click', function(e) {
            if (!confirm('Set this as your default address?')) {
                e.preventDefault();
            }
        });
    });

    // ---------- Delete address confirmation ----------
    const deleteAddressButtons = document.querySelectorAll('.address-item .btn-outline-danger');

    deleteAddressButtons.forEach(function(btn) {
        btn.addEventListener('click', function(e) {
            if (!confirm('Delete this address permanently?')) {
                e.preventDefault();
            }
        });
    });

    // ---------- Sidebar navigation active state ----------
    const sidebarLinks = document.querySelectorAll('.profile-page .card:first-child .btn-outline-warning');

    sidebarLinks.forEach(function(link) {
        link.addEventListener('click', function(e) {
            sidebarLinks.forEach(function(l) {
                l.classList.remove('btn-warning');
                l.classList.add('btn-outline-warning');
            });
            this.classList.remove('btn-outline-warning');
            this.classList.add('btn-warning');
        });
    });

    // ---------- Check for return from checkout ----------
    if (localStorage.getItem('returnToCheckout') === 'true') {
        localStorage.removeItem('returnToCheckout');
        // Show a notification
        const alert = document.createElement('div');
        alert.className = 'alert alert-success alert-dismissible fade show';
        alert.innerHTML = `
            Address added successfully! 
            <a href="/OnlineFoodDelivery/customer/checkout.jsp" class="btn btn-warning btn-sm ms-3">
                Return to Checkout
            </a>
            <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
        `;
        const container = document.querySelector('.profile-page .container');
        if (container) {
            container.insertBefore(alert, container.firstChild);
        }
    }

    console.log('Profile Page JS Loaded');
});