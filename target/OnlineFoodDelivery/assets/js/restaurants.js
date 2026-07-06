/* ==========================================
   RESTAURANTS PAGE JAVASCRIPT
   ========================================== */

document.addEventListener('DOMContentLoaded', function() {

    // ---------- Search form validation ----------
    const searchForm = document.querySelector('.search-section form');

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

    // ---------- Filter change auto-submit ----------
    const filterSelects = document.querySelectorAll('.search-section .form-select');

    filterSelects.forEach(function(select) {
        select.addEventListener('change', function() {
            const form = this.closest('form');
            if (form) {
                form.submit();
            }
        });
    });

    // ---------- Restaurant card hover effect ----------
    const restaurantCards = document.querySelectorAll('.restaurant-card');

    restaurantCards.forEach(function(card) {
        card.addEventListener('mouseenter', function() {
            this.style.transform = 'translateY(-8px)';
            this.style.boxShadow = '0 8px 25px rgba(0,0,0,0.15)';
        });

        card.addEventListener('mouseleave', function() {
            this.style.transform = 'translateY(0)';
            this.style.boxShadow = '0 2px 4px rgba(0,0,0,0.08)';
        });
    });

    // ---------- Rating star display ----------
    const ratingBadges = document.querySelectorAll('.restaurant-card .badge.bg-warning');

    ratingBadges.forEach(function(badge) {
        const ratingText = badge.textContent.trim();
        const rating = parseFloat(ratingText.replace('⭐', '').trim());
        if (rating >= 4.5) {
            badge.classList.add('bg-success');
            badge.classList.remove('bg-warning');
            badge.style.color = 'white';
        } else if (rating >= 3.5) {
            badge.classList.add('bg-warning');
            badge.style.color = '#1A1A2E';
        } else {
            badge.classList.add('bg-secondary');
            badge.classList.remove('bg-warning');
            badge.style.color = 'white';
        }
    });

    // ---------- Search input clear button ----------
    const searchInput = document.querySelector('.search-section input[name="keyword"]');

    if (searchInput) {
        const clearBtn = document.createElement('button');
        clearBtn.type = 'button';
        clearBtn.className = 'btn btn-outline-secondary';
        clearBtn.innerHTML = '<i class="fa-solid fa-xmark"></i>';
        clearBtn.style.display = 'none';
        clearBtn.style.marginLeft = '5px';

        const parentDiv = searchInput.parentElement;
        if (parentDiv) {
            parentDiv.style.display = 'flex';
            parentDiv.appendChild(clearBtn);

            searchInput.addEventListener('input', function() {
                if (this.value.trim() !== '') {
                    clearBtn.style.display = 'block';
                } else {
                    clearBtn.style.display = 'none';
                }
            });

            clearBtn.addEventListener('click', function() {
                searchInput.value = '';
                searchInput.focus();
                this.style.display = 'none';
                const form = searchInput.closest('form');
                if (form) {
                    form.submit();
                }
            });
        }
    }

    // ---------- Pagination click smooth scroll ----------
    const paginationLinks = document.querySelectorAll('.pagination .page-link');

    paginationLinks.forEach(function(link) {
        link.addEventListener('click', function(e) {
            if (!this.closest('.disabled')) {
                const restaurantList = document.querySelector('.restaurant-list');
                if (restaurantList) {
                    setTimeout(function() {
                        restaurantList.scrollIntoView({
                            behavior: 'smooth',
                            block: 'start'
                        });
                    }, 100);
                }
            }
        });
    });

    console.log('Restaurants Page JS Loaded');
});