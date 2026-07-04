/* ==========================================
   ADMIN RESTAURANTS JAVASCRIPT
   ========================================== */

document.addEventListener('DOMContentLoaded', function() {

    // ---------- Delete confirmation ----------
    const deleteButtons = document.querySelectorAll('.admin-restaurants .btn-outline-danger');

    deleteButtons.forEach(function(btn) {
        btn.addEventListener('click', function(e) {
            //const restaurantName = this.closest('tr')?.querySelector('td:nth-child(2)')?.textContent || 'this restaurant';
			let restaurantName = 'this restaurant';

			const row = this.closest('tr');

			if (row) {
			    const cell = row.querySelector('td:nth-child(2)');

			    if (cell) {
			        restaurantName = cell.textContent;
			    }
			}
			if (!confirm('Are you sure you want to delete "' + restaurantName + '" permanently?')) {
                e.preventDefault();
            }
        });
    });

    // ---------- Edit button - open modal ----------
    const editButtons = document.querySelectorAll('.admin-restaurants .btn-outline-warning');

    editButtons.forEach(function(btn) {
        btn.addEventListener('click', function(e) {
            e.preventDefault();
            const url = this.getAttribute('href');
            if (url) {
                // Modal will open via URL parameter (handled in JSP)
                window.location.href = url;
            }
        });
    });

    // ---------- Search form validation ----------
    const searchForm = document.querySelector('.admin-restaurants .card-header form');

    if (searchForm) {
        searchForm.addEventListener('submit', function(e) {
            const searchInput = this.querySelector('input[name="search"]');
            if (searchInput && searchInput.value.trim() === '') {
                e.preventDefault();
                searchInput.classList.add('is-invalid');
                setTimeout(function() {
                    searchInput.classList.remove('is-invalid');
                }, 2000);
            }
        });
    }

    // ---------- Modal form validation ----------
    const modalForm = document.querySelector('#restaurantModal form');

    if (modalForm) {
        modalForm.addEventListener('submit', function(e) {
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

            // Validate rating
            const rating = this.querySelector('input[name="rating"]');
            if (rating && rating.value.trim() !== '') {
                const val = parseFloat(rating.value);
                if (isNaN(val) || val < 0 || val > 5) {
                    e.preventDefault();
                    rating.classList.add('is-invalid');
                    isValid = false;
                }
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
        const formInputs = modalForm.querySelectorAll('.form-control, .form-select');
        formInputs.forEach(function(input) {
            input.addEventListener('input', function() {
                if (this.value.trim() !== '') {
                    this.classList.remove('is-invalid');
                    this.classList.add('is-valid');
                }
            });
        });
    }

    // ---------- Pagination click ----------
    const paginationLinks = document.querySelectorAll('.admin-restaurants .pagination .page-link');

    paginationLinks.forEach(function(link) {
        link.addEventListener('click', function(e) {
            if (!this.closest('.disabled')) {
                const tableCard = document.querySelector('.admin-restaurants .card');
                if (tableCard) {
                    setTimeout(function() {
                        tableCard.scrollIntoView({
                            behavior: 'smooth',
                            block: 'start'
                        });
                    }, 100);
                }
            }
        });
    });

    // ---------- Status toggle confirmation ----------
    const statusButtons = document.querySelectorAll('.admin-restaurants .btn-outline-success, .admin-restaurants .btn-outline-danger');

    statusButtons.forEach(function(btn) {
        btn.addEventListener('click', function(e) {
            //const restaurantName = this.closest('tr')?.querySelector('td:nth-child(2)')?.textContent || 'this restaurant';
			let restaurantName = 'this restaurant';

			const row = this.closest('tr');

			if (row) {
			    const cell = row.querySelector('td:nth-child(2)');

			    if (cell) {
			        restaurantName = cell.textContent;
			    }
			}
			const action = this.querySelector('.fa-ban') ? 'deactivate' : 'activate';
            if (!confirm('Are you sure you want to ' + action + ' "' + restaurantName + '"?')) {
                e.preventDefault();
            }
        });
    });

    console.log('Admin Restaurants JS Loaded');
});