/* ==========================================
   LOGIN PAGE JAVASCRIPT
   ========================================== */

document.addEventListener('DOMContentLoaded', function() {

    // ---------- Password Toggle ----------
    const togglePassword = document.getElementById('togglePassword');

    const passwordInput = document.getElementById('password');

    if (togglePassword && passwordInput) {

        togglePassword.addEventListener('click', function() {

            const type = passwordInput.getAttribute('type') === 'password' ? 'text' : 'password';

            passwordInput.setAttribute('type', type);

            // Toggle icon
            const icon = this.querySelector('i');

            if (icon) {

                icon.classList.toggle('fa-eye');

                icon.classList.toggle('fa-eye-slash');

            }

        });

    }

    // ---------- Form Validation ----------
    const loginForm = document.getElementById('loginForm');

    if (loginForm) {

        loginForm.addEventListener('submit', function(e) {

            let isValid = true;

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

            // Show loading spinner if valid
            if (isValid) {

                const submitBtn = loginForm.querySelector('button[type="submit"]');

                if (submitBtn) {

                    submitBtn.disabled = true;

                    submitBtn.innerHTML = `
                        <i class="fa-solid fa-spinner fa-spin me-2"></i>
                        Logging in...
                    `;

                }

            }

        });

        // ---------- Real-time validation ----------
        const email = document.getElementById('email');

        const password = document.getElementById('password');

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

        // ---------- Remember Me Checkbox ----------
        const rememberMe = document.getElementById('rememberMe');

        if (rememberMe) {

            // Check if email is stored in localStorage
            const savedEmail = localStorage.getItem('savedEmail');

            if (savedEmail && email) {

                email.value = savedEmail;

                rememberMe.checked = true;

            }

            rememberMe.addEventListener('change', function() {

                if (this.checked && email) {

                    localStorage.setItem('savedEmail', email.value.trim());

                } else {

                    localStorage.removeItem('savedEmail');

                }

            });

        }

    }

    console.log('Login Page JS Loaded');

});