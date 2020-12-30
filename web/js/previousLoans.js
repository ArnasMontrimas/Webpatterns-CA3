$(() => {
  $('.starrr:not(.rated)').starrr({
    change: function(e, value){
      $(this).data('rate', value)
    }
  })

  // Already rated
  $('.starrr.rated').each(function () {
    const rating = $(this).data('rating')
    $(this).starrr({
      readOnly: true,
      rating
    })
  })

  // Submitting book rating and comment
  $(".rate-book-modal .submit-button").on('click', function () {
    const rating = $(this.parentElement.parentElement).find('.starrr')
    const ratingInput = $(this.parentElement.parentElement).find('input[name="rating"]')
    const comment = $(this.parentElement.parentElement).find('textarea')

    if (rating.data('rate')) {
      ratingInput.val(rating.data('rate'))
      $(this.parentElement.parentElement).find('form').trigger('submit')
    }
  })
})
