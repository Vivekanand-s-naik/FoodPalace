/* ==========================================
   HOME PAGE JAVASCRIPT
   ========================================== */

document.addEventListener('DOMContentLoaded', function() {

    // ---------- Smooth scroll for anchor links ----------
    const anchorLinks = document.querySelectorAll('a[href^="#"]');

    anchorLinks.forEach(function(link) {
        link.addEventListener('click', function(e) {
            const targetId = this.getAttribute('href');
            if (targetId !== '#') {
                const targetElement = document.querySelector(targetId);
                if (targetElement) {
                    e.preventDefault();
                    targetElement.scrollIntoView({
                        behavior: 'smooth',
                        block: 'start'
                    });
                }
            }
        });
    });

    // ---------- Category cards click animation ----------
    const categoryCards = document.querySelectorAll('.category-card');

    categoryCards.forEach(function(card) {
        card.addEventListener('click', function() {
            const heading = this.querySelector('h5');
            const cuisine = heading ? heading.textContent : null;
            if (cuisine) {
                window.location.href = '/OnlineFoodDelivery/customer/restaurants.jsp?cuisine=' + encodeURIComponent(cuisine);
            }
        });
    });

    // ---------- Restaurant cards hover effect ----------
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

    // ---------- Hero section parallax effect ----------
    const heroSection = document.querySelector('.hero-section');

    if (heroSection) {
        window.addEventListener('scroll', function() {
            const scrolled = window.pageYOffset;
            const heroContent = heroSection.querySelector('.row');
            if (heroContent && scrolled < heroSection.offsetHeight) {
                heroContent.style.transform = 'translateY(' + (scrolled * 0.1) + 'px)';
                heroContent.style.opacity = 1 - (scrolled / (heroSection.offsetHeight * 0.8));
            }
        });
    }

    // ---------- Search form validation ----------
    //const searchForm = document.querySelector('.hero-section .btn-warning')?.closest('form') || 
    //                 document.querySelector('form[action*="restaurants"]');
    let searchForm = null;

    const searchButton = document.querySelector('.hero-section .btn-warning');

    if (searchButton) {
        searchForm = searchButton.closest('form');
    }

    if (!searchForm) {
        searchForm = document.querySelector('form[action*="restaurants"]');
    }
    if (searchForm) {
        searchForm.addEventListener('submit', function(e) {
            const searchInput = this.querySelector('input[type="text"]');
            if (searchInput && searchInput.value.trim() === '') {
                e.preventDefault();
                searchInput.classList.add('is-invalid');
                setTimeout(function() {
                    searchInput.classList.remove('is-invalid');
                }, 2000);
            }
        });
    }

    // ---------- Counter animation for statistics ----------
    const counters = document.querySelectorAll('.stat-number');

    counters.forEach(function(counter) {
        const target = parseInt(counter.getAttribute('data-target'));
        const duration = 2000;
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
                        counter.textContent = Math.round(current);
                    }, 16);
                    observer.unobserve(counter);
                }
            });
        });

        observer.observe(counter);
    });

    // ---------- Newsletter subscription ----------
    const newsletterForm = document.querySelector('.newsletter-section form');

    if (newsletterForm) {
        newsletterForm.addEventListener('submit', function(e) {
            e.preventDefault();
            const emailInput = this.querySelector('input[type="email"]');
            if (emailInput && validateEmail(emailInput.value.trim())) {
                const successMsg = document.createElement('div');
                successMsg.className = 'alert alert-success mt-3';
                successMsg.textContent = 'Thank you for subscribing!';
                this.appendChild(successMsg);
                emailInput.value = '';
                setTimeout(function() {
                    successMsg.remove();
                }, 5000);
            } else if (emailInput) {
                emailInput.classList.add('is-invalid');
                setTimeout(function() {
                    emailInput.classList.remove('is-invalid');
                }, 2000);
            }
        });
    }

    console.log('Home Page JS Loaded');
});