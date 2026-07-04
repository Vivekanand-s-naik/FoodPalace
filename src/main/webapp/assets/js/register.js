/* ==========================================
   REGISTER PAGE JAVASCRIPT
   ========================================== */

document.addEventListener('DOMContentLoaded', function() {

    // ---------- Password Toggle ----------
    const togglePassword = document.getElementById('togglePassword');
    const toggleConfirmPassword = document.getElementById('toggleConfirmPassword');
    const passwordInput = document.getElementById('password');
    const confirmPasswordInput = document.getElementById('confirmPassword');

    if (togglePassword && passwordInput) {
        togglePassword.addEventListener('click', function() {
            const type = passwordInput.getAttribute('type') === 'password' ? 'text' : 'password';
            passwordInput.setAttribute('type', type);
            const icon = this.querySelector('i');
            if (icon) {
                icon.classList.toggle('fa-eye');
                icon.classList.toggle('fa-eye-slash');
            }
        });
    }

    if (toggleConfirmPassword && confirmPasswordInput) {
        toggleConfirmPassword.addEventListener('click', function() {
            const type = confirmPasswordInput.getAttribute('type') === 'password' ? 'text' : 'password';
            confirmPasswordInput.setAttribute('type', type);
            const icon = this.querySelector('i');
            if (icon) {
                icon.classList.toggle('fa-eye');
                icon.classList.toggle('fa-eye-slash');
            }
        });
    }

    // ---------- Form Validation ----------
    const registerForm = document.getElementById('registerForm');

    if (registerForm) {
        registerForm.addEventListener('submit', function(e) {
            let isValid = true;

            // Validate Full Name
            const fullName = document.getElementById('fullName');
            const fullNameError = document.getElementById('fullNameError');
            if (fullName && fullName.value.trim() === '') {
                e.preventDefault();
                showError('fullNameError', 'Full Name is required');
                fullName.classList.add('is-invalid');
                isValid = false;
            } else if (fullName && fullName.value.trim().length < 3) {
                e.preventDefault();
                showError('fullNameError', 'Full Name must be at least 3 characters');
                fullName.classList.add('is-invalid');
                isValid = false;
            } else if (fullName) {
                clearError('fullNameError');
                fullName.classList.remove('is-invalid');
                fullName.classList.add('is-valid');
            }

            // Validate Email
            const email = document.getElementById('email');
            const emailError = document.getElementById('emailError');
            if (email && email.value.trim() === '') {
                e.preventDefault();
                showError('emailError', 'Email is required');
                email.classList.add('is-invalid');
                isValid = false;
            } else if (email && !validateEmail(email.value.trim())) {
                e.preventDefault();
                showError('emailError', 'Please enter a valid email address');
                email.classList.add('is-invalid');
                isValid = false;
            } else if (email) {
                clearError('emailError');
                email.classList.remove('is-invalid');
                email.classList.add('is-valid');
            }

            // Validate Phone
            const phone = document.getElementById('phone');
            const phoneError = document.getElementById('phoneError');
            if (phone && phone.value.trim() === '') {
                e.preventDefault();
                showError('phoneError', 'Phone number is required');
                phone.classList.add('is-invalid');
                isValid = false;
            } else if (phone && !validatePhone(phone.value.trim())) {
                e.preventDefault();
                showError('phoneError', 'Please enter a valid 10-digit phone number');
                phone.classList.add('is-invalid');
                isValid = false;
            } else if (phone) {
                clearError('phoneError');
                phone.classList.remove('is-invalid');
                phone.classList.add('is-valid');
            }

            // Validate Password
            const password = document.getElementById('password');
            const passwordError = document.getElementById('passwordError');
            if (password && password.value.trim() === '') {
                e.preventDefault();
                showError('passwordError', 'Password is required');
                password.classList.add('is-invalid');
                isValid = false;
            } else if (password && password.value.length < 6) {
                e.preventDefault();
                showError('passwordError', 'Password must be at least 6 characters');
                password.classList.add('is-invalid');
                isValid = false;
            } else if (password) {
                clearError('passwordError');
                password.classList.remove('is-invalid');
                password.classList.add('is-valid');
            }

            // Validate Confirm Password
            const confirmPassword = document.getElementById('confirmPassword');
            const confirmPasswordError = document.getElementById('confirmPasswordError');
            if (confirmPassword && confirmPassword.value.trim() === '') {
                e.preventDefault();
                showError('confirmPasswordError', 'Please confirm your password');
                confirmPassword.classList.add('is-invalid');
                isValid = false;
            } else if (confirmPassword && password && confirmPassword.value !== password.value) {
                e.preventDefault();
                showError('confirmPasswordError', 'Passwords do not match');
                confirmPassword.classList.add('is-invalid');
                isValid = false;
            } else if (confirmPassword) {
                clearError('confirmPasswordError');
                confirmPassword.classList.remove('is-invalid');
                confirmPassword.classList.add('is-valid');
            }

            // Validate Address
            const address = document.getElementById('address');
            const addressError = document.getElementById('addressError');
            if (address && address.value.trim() === '') {
                e.preventDefault();
                showError('addressError', 'Address is required');
                address.classList.add('is-invalid');
                isValid = false;
            } else if (address) {
                clearError('addressError');
                address.classList.remove('is-invalid');
                address.classList.add('is-valid');
            }

            // Validate Terms
            const terms = document.getElementById('terms');
            if (terms && !terms.checked) {
                e.preventDefault();
                alert('Please agree to the Terms & Conditions');
                isValid = false;
            }

            // Show loading spinner if valid
            if (isValid) {
                const submitBtn = registerForm.querySelector('button[type="submit"]');
                if (submitBtn) {
                    submitBtn.disabled = true;
                    submitBtn.innerHTML = `
                        <i class="fa-solid fa-spinner fa-spin me-2"></i>
                        Creating Account...
                    `;
                }
            }
        });

        // ---------- Real-time validation ----------
        const fullName = document.getElementById('fullName');
        const email = document.getElementById('email');
        const phone = document.getElementById('phone');
        const password = document.getElementById('password');
        const confirmPassword = document.getElementById('confirmPassword');
        const address = document.getElementById('address');

        if (fullName) {
            fullName.addEventListener('input', function() {
                if (this.value.trim() === '') {
                    showError('fullNameError', 'Full Name is required');
                    this.classList.add('is-invalid');
                    this.classList.remove('is-valid');
                } else if (this.value.trim().length < 3) {
                    showError('fullNameError', 'Full Name must be at least 3 characters');
                    this.classList.add('is-invalid');
                    this.classList.remove('is-valid');
                } else {
                    clearError('fullNameError');
                    this.classList.remove('is-invalid');
                    this.classList.add('is-valid');
                }
            });
        }

        if (email) {
            email.addEventListener('input', function() {
                if (this.value.trim() === '') {
                    showError('emailError', 'Email is required');
                    this.classList.add('is-invalid');
                    this.classList.remove('is-valid');
                } else if (!validateEmail(this.value.trim())) {
                    showError('emailError', 'Please enter a valid email address');
                    this.classList.add('is-invalid');
                    this.classList.remove('is-valid');
                } else {
                    clearError('emailError');
                    this.classList.remove('is-invalid');
                    this.classList.add('is-valid');
                }
            });
        }

        if (phone) {
            phone.addEventListener('input', function() {
                if (this.value.trim() === '') {
                    showError('phoneError', 'Phone number is required');
                    this.classList.add('is-invalid');
                    this.classList.remove('is-valid');
                } else if (!validatePhone(this.value.trim())) {
                    showError('phoneError', 'Please enter a valid 10-digit phone number');
                    this.classList.add('is-invalid');
                    this.classList.remove('is-valid');
                } else {
                    clearError('phoneError');
                    this.classList.remove('is-invalid');
                    this.classList.add('is-valid');
                }
            });
        }

        if (password) {
            password.addEventListener('input', function() {
                if (this.value.trim() === '') {
                    showError('passwordError', 'Password is required');
                    this.classList.add('is-invalid');
                    this.classList.remove('is-valid');
                } else if (this.value.length < 6) {
                    showError('passwordError', 'Password must be at least 6 characters');
                    this.classList.add('is-invalid');
                    this.classList.remove('is-valid');
                } else {
                    clearError('passwordError');
                    this.classList.remove('is-invalid');
                    this.classList.add('is-valid');
                }
            });
        }

        if (confirmPassword) {
            confirmPassword.addEventListener('input', function() {
                if (this.value.trim() === '') {
                    showError('confirmPasswordError', 'Please confirm your password');
                    this.classList.add('is-invalid');
                    this.classList.remove('is-valid');
                } else if (password && this.value !== password.value) {
                    showError('confirmPasswordError', 'Passwords do not match');
                    this.classList.add('is-invalid');
                    this.classList.remove('is-valid');
                } else {
                    clearError('confirmPasswordError');
                    this.classList.remove('is-invalid');
                    this.classList.add('is-valid');
                }
            });
        }

        if (address) {
            address.addEventListener('input', function() {
                if (this.value.trim() === '') {
                    showError('addressError', 'Address is required');
                    this.classList.add('is-invalid');
                    this.classList.remove('is-valid');
                } else {
                    clearError('addressError');
                    this.classList.remove('is-invalid');
                    this.classList.add('is-valid');
                }
            });
        }
    }

    console.log('Register Page JS Loaded');
});