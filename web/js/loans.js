$(() => {
  // Confirming payment fees
  $(".return-loan-fees-modal .submit-button").on('click', function () {
    const cardCvvInput = $(this.parentElement.parentElement).find('input[name="cardCvv"]')
    if (/^[0-9]{3,4}$/.test(cardCvvInput.val())) {
      cardCvvInput.removeClass('is-invalid');
      $(this.parentElement.parentElement).find('form').trigger('submit')
    } else {
      cardCvvInput.addClass('is-invalid');
    }
  })
})
