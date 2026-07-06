/* ==========================================
   MENU PAGE JAVASCRIPT
   ========================================== */

document.addEventListener('DOMContentLoaded', function() {

    const categoryButtons = document.querySelectorAll('.category-filter .btn');

    categoryButtons.forEach(function(btn) {
        btn.addEventListener('click', function() {
            categoryButtons.forEach(function(b) {
                b.classList.remove('btn-warning');
                b.classList.add('btn-outline-warning');
            });
            this.classList.remove('btn-outline-warning');
            this.classList.add('btn-warning');
        });
    });

    const quantityInputs = document.querySelectorAll('.menu-card .form-control[name="quantity"]');

    quantityInputs.forEach(function(input) {
        input.addEventListener('change', function() {
            let value = parseInt(this.value, 10);
            if (isNaN(value) || value < 1) {
                this.value = 1;
            }
        });

        input.addEventListener('input', function() {
            let value = parseInt(this.value, 10);
            if (isNaN(value) || value < 1) {
                this.value = 1;
            }
        });
    });

    // Use form submit event — never disable the button on click (that blocks POST)
    const addToCartForms = document.querySelectorAll('.menu-card form');

    addToCartForms.forEach(function(form) {
        form.addEventListener('submit', function() {
            const btn = form.querySelector('button[type="submit"]');
            if (!btn || btn.disabled) {
                return;
            }

            btn.innerHTML = '<i class="fa-solid fa-spinner fa-spin me-2"></i>Adding...';

            const cartIcon = document.querySelector('.navbar .fa-cart-shopping');
            if (cartIcon) {
                cartIcon.style.transform = 'scale(1.5)';
                cartIcon.style.color = '#FFC107';
                setTimeout(function() {
                    cartIcon.style.transform = 'scale(1)';
                    cartIcon.style.color = '';
                }, 300);
            }
        });
    });

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

    const headerImage = document.querySelector('.restaurant-header img');

    if (headerImage) {
        headerImage.addEventListener('error', function() {
            this.src = '/OnlineFoodDelivery/assets/images/restaurant-placeholder.jpg';
        });
    }

    const menuImages = document.querySelectorAll('.menu-card .card-img-top');

    menuImages.forEach(function(img) {
        img.addEventListener('error', function() {
            this.src = '/OnlineFoodDelivery/assets/images/menu-placeholder.jpg';
        });
    });

});
