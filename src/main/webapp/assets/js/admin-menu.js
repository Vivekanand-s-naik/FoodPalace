/* ==========================================
   ADMIN MENU JAVASCRIPT
   ========================================== */

document.addEventListener('DOMContentLoaded', function() {

    // ---------- Delete confirmation ----------
    const deleteButtons = document.querySelectorAll('.admin-menu .btn-outline-danger');

    deleteButtons.forEach(function(btn) {
        btn.addEventListener('click', function(e) {
            //const itemName = this.closest('tr')?.querySelector('td:nth-child(2)')?.textContent || 'this item';
			let itemName = 'this item';

			const row = this.closest('tr');

			if (row) {
			    const cell = row.querySelector('td:nth-child(2)');

			    if (cell) {
			        itemName = cell.textContent;
			    }
			}
			if (!confirm('Are you sure you want to delete "' + itemName + '" permanently?')) {
                e.preventDefault();
            }
        });
    });

    // ---------- Edit button - open modal ----------
    const editButtons = document.querySelectorAll('.admin-menu .btn-outline-warning');

    editButtons.forEach(function(btn) {
        btn.addEventListener('click', function(e) {
            e.preventDefault();
            const url = this.getAttribute('href');
            if (url) {
                window.location.href = url;
            }
        });
    });

    // ---------- Filter form auto-submit ----------
    const filterSelect = document.querySelector('.admin-menu .card-header select[name="restaurantId"]');

    if (filterSelect) {
        filterSelect.addEventListener('change', function() {
            const form = this.closest('form');
            if (form) {
                form.submit();
            }
        });
    }

    // ---------- Search form validation ----------
    const searchForm = document.querySelector('.admin-menu .card-header form');

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
    const modalForm = document.querySelector('#menuModal form');

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

            // Validate price
            const price = this.querySelector('input[name="price"]');
            if (price && price.value.trim() !== '') {
                const val = parseFloat(price.value);
                if (isNaN(val) || val < 0) {
                    e.preventDefault();
                    price.classList.add('is-invalid');
                    isValid = false;
                }
            }

            // Validate quantity
            const quantity = this.querySelector('input[name="quantity"]');
            if (quantity && quantity.value.trim() !== '') {
                const val = parseInt(quantity.value);
                if (isNaN(val) || val < 0) {
                    e.preventDefault();
                    quantity.classList.add('is-invalid');
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

    // ---------- Image preview ----------
    const imageInput = document.querySelector('#menuModal input[type="file"]');

    if (imageInput) {
        imageInput.addEventListener('change', function() {
            const file = this.files[0];
            if (file) {
                const reader = new FileReader();
                reader.onload = function(e) {
                    const preview = document.createElement('img');
                    preview.src = e.target.result;
                    preview.style.maxWidth = '100px';
                    preview.style.maxHeight = '100px';
                    preview.style.marginTop = '10px';
                    preview.style.borderRadius = '8px';
                    preview.className = 'img-fluid';

                    //const existingPreview = this.parentElement?.querySelector('.img-fluid');
					let existingPreview = null;

					const parent = this.parentElement;

					if (parent) {
					    existingPreview = parent.querySelector('.img-fluid');
					}
					if (existingPreview) {
                        existingPreview.remove();
                    }

                    //this.parentElement?.appendChild(preview);
					const parentelem = this.parentElement;

					if (parentelem) {
					    parentelem.appendChild(preview);
					}
                }.bind(this);
                reader.readAsDataURL(file);
            }
        });
    }

    // ---------- Pagination click ----------
    const paginationLinks = document.querySelectorAll('.admin-menu .pagination .page-link');

    paginationLinks.forEach(function(link) {
        link.addEventListener('click', function(e) {
            if (!this.closest('.disabled')) {
                const tableCard = document.querySelector('.admin-menu .card');
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

    console.log('Admin Menu JS Loaded');
});