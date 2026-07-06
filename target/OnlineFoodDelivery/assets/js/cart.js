/* ==========================================
   CART PAGE JAVASCRIPT
   ========================================== */

document.addEventListener('DOMContentLoaded', function() {

    const summaryCard = document.querySelector('.cart-page .col-lg-4 .card-body');
    const restaurantCount = summaryCard
        ? parseInt(summaryCard.dataset.restaurantCount || '0', 10)
        : 0;
    const deliveryPerRestaurant = summaryCard
        ? parseFloat(summaryCard.dataset.deliveryPerRestaurant || '40')
        : 40;
    const taxRate = summaryCard
        ? parseFloat(summaryCard.dataset.taxRate || '0.05')
        : 0.05;

    function formatMoney(amount) {
        return '₹ ' + amount.toFixed(2);
    }

    function recalculateRestaurantSubtotal(groupCard) {
        let groupSubtotal = 0;
        groupCard.querySelectorAll('.cart-item-row').forEach(function(row) {
            const lineTotalEl = row.querySelector('.line-total');
            if (lineTotalEl) {
                groupSubtotal += parseFloat(lineTotalEl.dataset.amount || '0');
            }
        });
        const groupSubtotalEl = groupCard.querySelector('.restaurant-subtotal');
        if (groupSubtotalEl) {
            groupSubtotalEl.textContent = formatMoney(groupSubtotal);
        }
        return groupSubtotal;
    }

    function recalculateOrderSummary() {
        let subTotal = 0;
        document.querySelectorAll('.cart-page .card.mb-4.shadow-sm').forEach(function(groupCard) {
            subTotal += recalculateRestaurantSubtotal(groupCard);
        });

        const activeRestaurantCount = document.querySelectorAll('.cart-page .card.mb-4.shadow-sm').length;
        const deliveryFee = subTotal > 0 ? deliveryPerRestaurant * activeRestaurantCount : 0;
        const tax = subTotal * taxRate;
        const grandTotal = subTotal + deliveryFee + tax;

        const subTotalEl = document.getElementById('summary-subtotal');
        const deliveryEl = document.getElementById('summary-delivery');
        const taxEl = document.getElementById('summary-tax');
        const grandTotalEl = document.getElementById('summary-grand-total');

        if (subTotalEl) subTotalEl.textContent = formatMoney(subTotal);
        if (deliveryEl) deliveryEl.textContent = formatMoney(deliveryFee);
        if (taxEl) taxEl.textContent = formatMoney(tax);
        if (grandTotalEl) grandTotalEl.textContent = formatMoney(grandTotal);
    }

    function updateLineTotal(row, quantity) {
        const unitPrice = parseFloat(row.dataset.unitPrice || '0');
        const qty = Math.max(1, quantity);
        const total = unitPrice * qty;
        const lineTotalEl = row.querySelector('.line-total');
        if (lineTotalEl) {
            lineTotalEl.textContent = formatMoney(total);
            lineTotalEl.dataset.amount = total.toFixed(2);
        }
        const groupCard = row.closest('.card.mb-4.shadow-sm');
        if (groupCard) {
            recalculateRestaurantSubtotal(groupCard);
        }
        recalculateOrderSummary();
    }

    document.querySelectorAll('.cart-item-row').forEach(function(row) {
        const quantityInput = row.querySelector('input[name="quantity"]');
        if (!quantityInput) {
            return;
        }

        quantityInput.addEventListener('input', function() {
            let value = parseInt(this.value, 10);
            if (isNaN(value) || value < 1) {
                value = 1;
            }
            updateLineTotal(row, value);
        });

        quantityInput.addEventListener('change', function() {
            let value = parseInt(this.value, 10);
            if (isNaN(value) || value < 1) {
                this.value = 1;
                value = 1;
            }
            updateLineTotal(row, value);
        });
    });

    document.querySelectorAll('.cart-page form[action*="cart"]').forEach(function(form) {
        const quantityInput = form.querySelector('input[name="quantity"]');
        const updateBtn = form.querySelector('.btn-success');

        if (quantityInput && updateBtn) {
            quantityInput.addEventListener('keydown', function(e) {
                if (e.key === 'Enter') {
                    e.preventDefault();
                    form.submit();
                }
            });
        }
    });

    const removeButtons = document.querySelectorAll('.cart-page .btn-danger');

    removeButtons.forEach(function(btn) {
        btn.addEventListener('click', function(e) {
            let itemName = 'this item';
            const row = this.closest('.cart-item-row');
            if (row) {
                const heading = row.querySelector('h5');
                if (heading) {
                    itemName = heading.textContent.trim();
                }
            }
            if (!confirm('Remove ' + itemName + ' from your cart?')) {
                e.preventDefault();
            }
        });
    });

    const checkoutBtn = document.querySelector('.cart-page .btn-warning[href*="checkout"]');

    if (checkoutBtn) {
        checkoutBtn.addEventListener('click', function(e) {
            const cartItems = document.querySelectorAll('.cart-item-row');
            if (cartItems.length === 0) {
                e.preventDefault();
                alert('Your cart is empty. Please add items before proceeding to checkout.');
            }
        });
    }

    const updateButtons = document.querySelectorAll('.cart-page .btn-success');

    updateButtons.forEach(function(btn) {
        btn.addEventListener('click', function() {
            const originalText = this.innerHTML;
            this.innerHTML = '<i class="fa-solid fa-check"></i>';
            this.style.backgroundColor = '#28A745';

            setTimeout(function() {
                btn.innerHTML = originalText;
                btn.style.backgroundColor = '';
            }, 1000);
        });
    });

});
