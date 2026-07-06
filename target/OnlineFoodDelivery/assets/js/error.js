/* ==========================================
   ERROR PAGE JAVASCRIPT
   ========================================== */

document.addEventListener('DOMContentLoaded', function() {

    // ---------- Auto-redirect after 10 seconds ----------
    const redirectTimer = document.querySelector('.error-page');

    if (redirectTimer) {
        let countdown = 10;
        const timerElement = document.createElement('div');
        timerElement.className = 'mt-3 text-muted';
        timerElement.innerHTML = `Redirecting to home in <span id="countdown">${countdown}</span> seconds...`;

        const helpSection = document.querySelector('.help-section');
        if (helpSection) {
            helpSection.parentNode.insertBefore(timerElement, helpSection.nextSibling);
        }

        const interval = setInterval(function() {
            countdown--;
            const countdownElement = document.getElementById('countdown');
            if (countdownElement) {
                countdownElement.textContent = countdown;
            }
            if (countdown <= 0) {
                clearInterval(interval);
                window.location.href = '/OnlineFoodDelivery/index.jsp';
            }
        }, 1000);

        // Stop redirect if user interacts
        document.addEventListener('click', function() {
            clearInterval(interval);
            const timerElement = document.querySelector('.error-page .mt-3');
            if (timerElement) {
                timerElement.innerHTML = '<span class="text-success">Redirect cancelled.</span>';
            }
        });
    }

    // ---------- Error code animation ----------
    const errorCode = document.querySelector('.error-code');

    if (errorCode) {
        const text = errorCode.textContent;
        errorCode.textContent = '';

        let i = 0;
        const interval = setInterval(function() {
            if (i < text.length) {
                errorCode.textContent += text.charAt(i);
                i++;
            } else {
                clearInterval(interval);
            }
        }, 150);
    }

    // ---------- Error icon shake animation ----------
    const errorIcon = document.querySelector('.error-icon');

    if (errorIcon) {
        setInterval(function() {
            errorIcon.style.transform = 'rotate(-10deg)';
            setTimeout(function() {
                errorIcon.style.transform = 'rotate(10deg)';
            }, 100);
            setTimeout(function() {
                errorIcon.style.transform = 'rotate(0deg)';
            }, 200);
        }, 3000);
    }

    // ---------- Copy error details ----------
    const errorDetails = document.querySelector('.alert-secondary small');

    if (errorDetails) {
        const copyBtn = document.createElement('button');
        copyBtn.className = 'btn btn-sm btn-outline-secondary mt-2';
        copyBtn.innerHTML = '<i class="fa-regular fa-copy me-1"></i> Copy Error Details';

        const alertBox = document.querySelector('.alert-secondary');
        if (alertBox) {
            alertBox.appendChild(copyBtn);

            copyBtn.addEventListener('click', function() {
                const text = errorDetails.textContent;
                navigator.clipboard.writeText(text).then(function() {
                    const originalText = copyBtn.innerHTML;
                    copyBtn.innerHTML = '<i class="fa-solid fa-check me-1"></i> Copied!';
                    copyBtn.classList.add('btn-success');
                    copyBtn.classList.remove('btn-outline-secondary');

                    setTimeout(function() {
                        copyBtn.innerHTML = originalText;
                        copyBtn.classList.remove('btn-success');
                        copyBtn.classList.add('btn-outline-secondary');
                    }, 2000);
                });
            });
        }
    }

    // ---------- Go back button with fallback ----------
    const goBackBtn = document.querySelector('.error-actions .btn-outline-secondary');

    if (goBackBtn) {
        goBackBtn.addEventListener('click', function(e) {
            e.preventDefault();
            if (document.referrer && !document.referrer.includes('error')) {
                window.history.back();
            } else {
                window.location.href = '/OnlineFoodDelivery/index.jsp';
            }
        });
    }

    console.log('Error Page JS Loaded');
});