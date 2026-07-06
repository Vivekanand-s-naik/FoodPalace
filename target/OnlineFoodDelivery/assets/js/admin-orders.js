/* ==========================================
   ADMIN ORDERS JAVASCRIPT
   ========================================== */

document.addEventListener('DOMContentLoaded', function() {

    // ---------- Filter form auto-submit ----------
    const filterSelects = document.querySelectorAll('.admin-orders .card-header select');

    filterSelects.forEach(function(select) {
        select.addEventListener('change', function() {
            const form = this.closest('form');
            if (form) {
                form.submit();
            }
        });
    });

    // ---------- Date filter auto-submit ----------
    const dateInput = document.querySelector('.admin-orders .card-header input[type="date"]');

    if (dateInput) {
        dateInput.addEventListener('change', function() {
            const form = this.closest('form');
            if (form) {
                form.submit();
            }
        });
    }

    // ---------- View order details modal ----------
    const viewButtons = document.querySelectorAll('.admin-orders .btn-outline-warning[data-bs-target*="orderModal"]');

    viewButtons.forEach(function(btn) {
        btn.addEventListener('click', function() {
            const modalId = this.getAttribute('data-bs-target');
            if (modalId) {
                const modal = document.querySelector(modalId);
                if (modal) {
                    const modalInstance = new bootstrap.Modal(modal);
                    modalInstance.show();
                }
            }
        });
    });

    // ---------- Update status modal ----------
    const statusButtons = document.querySelectorAll('.admin-orders .btn-outline-success[data-bs-target*="updateStatusModal"]');

    statusButtons.forEach(function(btn) {
        btn.addEventListener('click', function() {
            const modalId = this.getAttribute('data-bs-target');
            if (modalId) {
                const modal = document.querySelector(modalId);
                if (modal) {
                    const modalInstance = new bootstrap.Modal(modal);
                    modalInstance.show();
                }
            }
        });
    });

    // ---------- Status update form validation ----------
    const statusForms = document.querySelectorAll('.admin-orders #updateStatusModal .modal-body form');

    statusForms.forEach(function(form) {
        form.addEventListener('submit', function(e) {
            const statusSelect = this.querySelector('select[name="status"]');
            if (statusSelect && statusSelect.value === '') {
                e.preventDefault();
                statusSelect.classList.add('is-invalid');
                setTimeout(function() {
                    statusSelect.classList.remove('is-invalid');
                }, 2000);
                return;
            }

            const submitBtn = this.querySelector('button[type="submit"]');
            if (submitBtn) {
                submitBtn.disabled = true;
                submitBtn.innerHTML = '<i class="fa-solid fa-spinner fa-spin me-2"></i>Updating...';
            }
        });
    });

    // ---------- Search form validation ----------
    const searchForm = document.querySelector('.admin-orders .card-header form');

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

    // ---------- Status badge colors ----------
    const statusBadges = document.querySelectorAll('.admin-orders .table .badge');

    statusBadges.forEach(function(badge) {
        const text = badge.textContent.trim().toLowerCase();
        const colorMap = {
            'delivered': 'bg-success',
            'preparing': 'bg-warning',
            'out for delivery': 'bg-primary',
            'placed': 'bg-info',
            'cancelled': 'bg-danger'
        };

        if (colorMap[text]) {
            badge.className = 'badge ' + colorMap[text];
            if (text === 'preparing' || text === 'placed') {
                badge.style.color = '#1A1A2E';
            }
        }
    });

    // ---------- Pagination click ----------
    const paginationLinks = document.querySelectorAll('.admin-orders .pagination .page-link');

    paginationLinks.forEach(function(link) {
        link.addEventListener('click', function(e) {
            if (!this.closest('.disabled')) {
                const tableCard = document.querySelector('.admin-orders .card');
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

    console.log('Admin Orders JS Loaded');
});