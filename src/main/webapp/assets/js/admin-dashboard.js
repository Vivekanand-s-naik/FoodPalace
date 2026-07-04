/* ==========================================
   ADMIN DASHBOARD JAVASCRIPT
   ========================================== */

document.addEventListener('DOMContentLoaded', function() {

    // ---------- Chart.js is loaded from CDN ----------
    // The chart is initialized in the JSP

    // ---------- Statistics card animation ----------
    const statCards = document.querySelectorAll('.stat-card');

    statCards.forEach(function(card) {
        card.addEventListener('mouseenter', function() {
            this.style.transform = 'translateY(-5px)';
            this.style.boxShadow = '0 8px 25px rgba(0,0,0,0.15)';
        });

        card.addEventListener('mouseleave', function() {
            this.style.transform = 'translateY(0)';
            this.style.boxShadow = '0 2px 4px rgba(0,0,0,0.08)';
        });

        // Counter animation for numbers
        const numberElement = card.querySelector('h2');
        if (numberElement) {
            const target = parseInt(numberElement.textContent.replace(/[^0-9.]/g, ''));
            if (!isNaN(target) && target > 0) {
                const duration = 1500;
                const step = target / (duration / 16);
                let current = 0;

                const observer = new IntersectionObserver(function(entries) {
                    entries.forEach(function(entry) {
                        if (entry.isIntersecting) {
                            const interval = setInterval(function() {
                                current += step;
                                if (current >= target) {
                                    current = target;
                                    clearInterval(interval);
                                }
                                const prefix = numberElement.textContent.replace(/[0-9.]/g, '');
                                numberElement.textContent = prefix + Math.round(current);
                            }, 16);
                            observer.unobserve(card);
                        }
                    });
                });

                observer.observe(card);
            }
        }
    });

    // ---------- Progress bar animation ----------
    const progressBars = document.querySelectorAll('.progress-bar');

    progressBars.forEach(function(bar) {
        const observer = new IntersectionObserver(function(entries) {
            entries.forEach(function(entry) {
                if (entry.isIntersecting) {
                    const width = bar.style.width;
                    bar.style.width = '0%';
                    setTimeout(function() {
                        bar.style.width = width;
                    }, 100);
                    observer.unobserve(bar);
                }
            });
        });

        observer.observe(bar);
    });

    // ---------- Table row hover effect ----------
    const tableRows = document.querySelectorAll('.recent-orders .table tbody tr');

    tableRows.forEach(function(row) {
        row.addEventListener('mouseenter', function() {
            this.style.backgroundColor = '#FFF8E1';
        });

        row.addEventListener('mouseleave', function() {
            this.style.backgroundColor = '';
        });
    });

    // ---------- Quick action card click ----------
    const actionCards = document.querySelectorAll('.action-card');

    actionCards.forEach(function(card) {
        card.addEventListener('click', function() {
            const link = this.closest('a');
            if (link) {
                // Add loading animation
                const icon = this.querySelector('.fa-solid');
                if (icon) {
                    icon.style.transform = 'scale(1.2) rotate(45deg)';
                    icon.style.transition = 'all 0.3s ease';
                }
                setTimeout(function() {
                    window.location.href = link.getAttribute('href');
                }, 200);
            }
        });
    });

    // ---------- Refresh data button ----------
    const refreshBtn = document.querySelector('.recent-orders .card-header .btn-warning');

    if (refreshBtn) {
        refreshBtn.addEventListener('click', function(e) {
            e.preventDefault();
            const originalText = this.innerHTML;
            this.innerHTML = '<i class="fa-solid fa-spinner fa-spin me-2"></i>Loading...';
            this.disabled = true;

            setTimeout(function() {
                window.location.reload();
            }, 1000);
        });
    }

    // ---------- Notification badge pulse ----------
    const notificationBadge = document.querySelector('.admin-dashboard .badge.bg-warning');

    if (notificationBadge) {
        setInterval(function() {
            notificationBadge.style.transform = 'scale(1.1)';
            setTimeout(function() {
                notificationBadge.style.transform = 'scale(1)';
            }, 200);
        }, 5000);
    }

    // ---------- Admin logout confirmation ----------
    const logoutBtn = document.querySelector('.admin-profile .btn-outline-danger');

    if (logoutBtn) {
        logoutBtn.addEventListener('click', function(e) {
            if (!confirm('Are you sure you want to logout?')) {
                e.preventDefault();
            }
        });
    }

    console.log('Admin Dashboard JS Loaded');
});