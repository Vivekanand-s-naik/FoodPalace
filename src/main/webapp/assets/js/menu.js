/* ==========================================
   MENU PAGE JAVASCRIPT
   ========================================== */

document.addEventListener('DOMContentLoaded', function() {

    // ---------- Category filter buttons ----------
    const categoryButtons = document.querySelectorAll('.category-filter .btn');

    categoryButtons.forEach(function(btn) {
        btn.addEventListener('click', function(e) {
            // Remove active class from all buttons
            categoryButtons.forEach(function(b) {
                b.classList.remove('btn-warning');
                b.classList.add('btn-outline-warning');
            });

            // Add active class to clicked button
            this.classList.remove('btn-outline-warning');
            this.classList.add('btn-warning');
        });
    });

    // ---------- Quantity input validation ----------
    const quantityInputs = document.querySelectorAll('.menu-card .form-control[name="quantity"]');

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

    // ---------- Add to cart button animation ----------
    const addToCartButtons = document.querySelectorAll('.menu-card .btn-warning');

    addToCartButtons.forEach(function(btn) {
        btn.addEventListener('click', function(e) {
            // Check if button is disabled (out of stock)
            if (this.disabled) return;

            // Show loading state
            const originalText = this.innerHTML;
            this.innerHTML = '<i class="fa-solid fa-spinner fa-spin me-2"></i>Adding...';
            this.disabled = true;

            // Animate cart icon in navbar
            const cartIcon = document.querySelector('.navbar .fa-cart-shopping');
            if (cartIcon) {
                cartIcon.style.transform = 'scale(1.5)';
                cartIcon.style.color = '#FFC107';
                setTimeout(function() {
                    cartIcon.style.transform = 'scale(1)';
                    cartIcon.style.color = '';
                }, 300);
            }

            // Reset button after 1.5 seconds (form will submit anyway)
            setTimeout(function() {
                this.innerHTML = originalText;
                this.disabled = false;
            }.bind(this), 1500);
        });
    });

    // ---------- Search form validation ----------
    const searchForm = document.querySelector('.menu-filter form');

    if (searchForm) {
        searchForm.addEventListener('submit', function(e) {
            const searchInput = this.querySelector('input[name="keyword"]');
            if (searchInput && searchInput.value.trim() === '') {
                e.preventDefault();
                searchInput.classList.add('is-invalid');
                setTimeout(function() {
                    searchInput.classList.remove('is-invalid');
                }, 2000);
            }
        });
    }

    // ---------- Restaurant header image lazy loading ----------
    const headerImage = document.querySelector('.restaurant-header img');

    if (headerImage) {
        headerImage.addEventListener('load', function() {
            this.style.opacity = '0';
            this.style.transition = 'opacity 0.5s ease';
            setTimeout(function() {
                headerImage.style.opacity = '1';
            }, 100);
        });

        // Handle error - show fallback
        headerImage.addEventListener('error', function() {
            this.src = '/OnlineFoodDelivery/assets/images/restaurant-placeholder.jpg';
        });
    }

    // ---------- Menu card image lazy loading ----------
    const menuImages = document.querySelectorAll('.menu-card .card-img-top');

    menuImages.forEach(function(img) {
        img.addEventListener('error', function() {
            this.src = '/OnlineFoodDelivery/assets/images/menu-placeholder.jpg';
        });
    });

    // ---------- Price formatting ----------
    const priceElements = document.querySelectorAll('.menu-card h5');

    priceElements.forEach(function(el) {
        const text = el.textContent.trim();
        if (text.startsWith('₹')) {
            const price = parseFloat(text.replace('₹', '').trim());
            if (!isNaN(price)) {
                el.textContent = '₹ ' + price.toFixed(2);
            }
        }
    });

    console.log('Menu Page JS Loaded');
});