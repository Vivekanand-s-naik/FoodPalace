/* ==========================================
   ADMIN USERS JAVASCRIPT
   ========================================== */

document.addEventListener('DOMContentLoaded', function() {

    // ---------- Filter form auto-submit ----------
    const filterSelect = document.querySelector('.admin-users .card-header select[name="role"]');

    if (filterSelect) {
        filterSelect.addEventListener('change', function() {
            const form = this.closest('form');
            if (form) {
                form.submit();
            }
        });
    }

    // ---------- Search form validation ----------
    const searchForm = document.querySelector('.admin-users .card-header form');

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

    // ---------- View user details modal ----------
    const viewButtons = document.querySelectorAll('.admin-users .btn-outline-warning[data-bs-target*="userModal"]');

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

    // ---------- Toggle user status confirmation ----------
    const toggleButtons = document.querySelectorAll('.admin-users .btn-outline-danger, .admin-users .btn-outline-success');

    toggleButtons.forEach(function(btn) {
        btn.addEventListener('click', function(e) {
            //const userName = this.closest('tr')?.querySelector('td:nth-child(2)')?.textContent || 'this user';
			let userName = 'this user';

			const row = this.closest('tr');

			if (row) {
			    const cell = row.querySelector('td:nth-child(2)');

			    if (cell) {
			        userName = cell.textContent;
			    }
			}
			const action = this.querySelector('.fa-ban') ? 'deactivate' : 'activate';
            if (!confirm('Are you sure you want to ' + action + ' user "' + userName + '"?')) {
                e.preventDefault();
            }
        });
    });

    // ---------- Role badge styling ----------
    const roleBadges = document.querySelectorAll('.admin-users .table .badge');

    roleBadges.forEach(function(badge) {
        const text = badge.textContent.trim().toUpperCase();
        if (text === 'ADMIN') {
            badge.className = 'badge bg-danger';
        } else if (text === 'CUSTOMER') {
            badge.className = 'badge bg-info';
            badge.style.color = 'white';
        }
    });

    // ---------- User status badge styling ----------
    const statusBadges = document.querySelectorAll('.admin-users .table .badge.bg-success, .admin-users .table .badge.bg-secondary');

    statusBadges.forEach(function(badge) {
        const text = badge.textContent.trim().toLowerCase();
        if (text === 'active') {
            badge.className = 'badge bg-success';
        } else {
            badge.className = 'badge bg-secondary';
        }
    });

    // ---------- Pagination click ----------
    const paginationLinks = document.querySelectorAll('.admin-users .pagination .page-link');

    paginationLinks.forEach(function(link) {
        link.addEventListener('click', function(e) {
            if (!this.closest('.disabled')) {
                const tableCard = document.querySelector('.admin-users .card');
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

    // ---------- User avatar fallback ----------
    const userAvatars = document.querySelectorAll('.admin-users .avatar-circle');

    userAvatars.forEach(function(avatar) {
        const parent = avatar.closest('.modal-body');
        if (parent) {
            //const userName = parent.querySelector('.table tr:nth-child(2) td')?.textContent || 'U';
			let userName = 'U';

			const cell = parent.querySelector('.table tr:nth-child(2) td');

			if (cell) {
			    userName = cell.textContent;
			}
			const initial = userName.charAt(0).toUpperCase();
            const icon = avatar.querySelector('i');
            if (icon) {
                icon.textContent = initial;
                icon.style.fontSize = '2.5rem';
                icon.style.fontWeight = '700';
                icon.style.fontStyle = 'normal';
            }
        }
    });

    console.log('Admin Users JS Loaded');
});