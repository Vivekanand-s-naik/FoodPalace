/* ==========================================
   ONLINE FOOD DELIVERY - COMMON JAVASCRIPT
   ========================================== */

document.addEventListener('DOMContentLoaded', function() {

    // ---------- Auto-hide alerts after 5 seconds ----------
    const alerts = document.querySelectorAll('.alert');

    alerts.forEach(function(alert) {

        setTimeout(function() {

            alert.style.transition = 'opacity 0.5s ease';

            alert.style.opacity = '0';

            setTimeout(function() {

                alert.style.display = 'none';

            }, 500);

        }, 5000);

    });

    // ---------- Back to top button ----------
    const backToTop = document.createElement('button');

    backToTop.innerHTML = '<i class="fa-solid fa-arrow-up"></i>';

    backToTop.className = 'back-to-top';

    backToTop.style.cssText = `
        position: fixed;
        bottom: 30px;
        right: 30px;
        width: 50px;
        height: 50px;
        border-radius: 50%;
        background-color: #FFC107;
        color: #1A1A2E;
        border: none;
        font-size: 1.2rem;
        cursor: pointer;
        box-shadow: 0 4px 15px rgba(255, 193, 7, 0.4);
        transition: all 0.3s ease;
        opacity: 0;
        visibility: hidden;
        z-index: 999;
    `;

    document.body.appendChild(backToTop);

    backToTop.addEventListener('mouseenter', function() {
        this.style.transform = 'scale(1.1)';
        this.style.boxShadow = '0 6px 25px rgba(255, 193, 7, 0.6)';
    });

    backToTop.addEventListener('mouseleave', function() {
        this.style.transform = 'scale(1)';
        this.style.boxShadow = '0 4px 15px rgba(255, 193, 7, 0.4)';
    });

    window.addEventListener('scroll', function() {

        if (window.scrollY > 300) {

            backToTop.style.opacity = '1';

            backToTop.style.visibility = 'visible';

        } else {

            backToTop.style.opacity = '0';

            backToTop.style.visibility = 'hidden';

        }

    });

    backToTop.addEventListener('click', function() {

        window.scrollTo({

            top: 0,

            behavior: 'smooth'

        });

    });

    // ---------- Tooltip initialization ----------
    const tooltipTriggerList = [].slice.call(
        document.querySelectorAll('[data-bs-toggle="tooltip"]')
    );

    tooltipTriggerList.map(function(tooltipTriggerEl) {
        return new bootstrap.Tooltip(tooltipTriggerEl);
    });

    // ---------- Form validation helper ----------
    window.validateEmail = function(email) {

        const re = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

        return re.test(email);

    };

    window.validatePhone = function(phone) {

        const re = /^[0-9]{10}$/;

        return re.test(phone);

    };

    window.showError = function(elementId, message) {

        const element = document.getElementById(elementId);

        if (element) {

            element.textContent = message;

            element.style.display = 'block';

        }

    };

    window.clearError = function(elementId) {

        const element = document.getElementById(elementId);

        if (element) {

            element.textContent = '';

            element.style.display = 'none';

        }

    };

    // ---------- Loading spinner ----------
    window.showLoading = function() {

        const spinner = document.createElement('div');

        spinner.id = 'loadingSpinner';

        spinner.style.cssText = `
            position: fixed;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            background: rgba(0,0,0,0.5);
            display: flex;
            align-items: center;
            justify-content: center;
            z-index: 9999;
        `;

        spinner.innerHTML = `
            <div class="spinner-border text-warning" role="status">
                <span class="visually-hidden">Loading...</span>
            </div>
        `;

        document.body.appendChild(spinner);

    };

    window.hideLoading = function() {

        const spinner = document.getElementById('loadingSpinner');

        if (spinner) {

            spinner.remove();

        }

    };

    // ---------- Number formatting ----------
    window.formatCurrency = function(amount) {

        return '₹ ' + Number(amount).toFixed(2).replace(/\B(?=(\d{3})+(?!\d))/g, ',');

    };

    // ---------- Mobile menu close on link click ----------
    const navLinks = document.querySelectorAll('.navbar-nav .nav-link');

    const navbarToggler = document.querySelector('.navbar-toggler');

    const navbarCollapse = document.querySelector('.navbar-collapse');

    if (navbarToggler && navbarCollapse) {

        navLinks.forEach(function(link) {

            link.addEventListener('click', function() {

                if (navbarCollapse.classList.contains('show')) {

                    navbarToggler.click();

                }

            });

        });

    }

    // ---------- Cart counter animation ----------
    const cartBadge = document.querySelector('.cart-badge');

    if (cartBadge) {

        const observer = new MutationObserver(function() {

            cartBadge.style.transform = 'scale(1.3)';

            setTimeout(function() {

                cartBadge.style.transform = 'scale(1)';

            }, 200);

        });

        observer.observe(cartBadge, { childList: true, subtree: true });

    }

    console.log('OnlineFoodDelivery - Common JS Loaded');

});