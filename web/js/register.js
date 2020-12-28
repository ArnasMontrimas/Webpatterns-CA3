$(() => {
  const cardNumInput = $('input[name="cardNumber"]')[0];
  const ownerNameInput = $('input[name="ownerName"]')[0];
  const cvvInput = $('input[name="cardCvv"]')[0];
  const expirationDate = $('input[name="expirationDate"]')[0];

  if (cardNumInput) {
    // Change card input validation message
    cardNumInput.oninvalid = function () {
      this.setCustomValidity("Card numbers must be 16 digits long.");
    }
    cardNumInput.oninput = function () {
      this.setCustomValidity("");
    };
  
    ownerNameInput.oninvalid = function () {
      this.setCustomValidity("Only names from basaic characters are allowed");
    }
    ownerNameInput.oninput = function () {
      this.setCustomValidity("");
    };
  
    cvvInput.oninvalid = function () {
      this.setCustomValidity("CVV can only contains 3 or 4 digits.");
    }
    cvvInput.oninput = function () {
      this.setCustomValidity("");
    };
  
    expirationDate.oninvalid = function () {
      this.setCustomValidity("Expiration should in the format MM/YY");
    }
    expirationDate.oninput = function () {
      this.setCustomValidity("");
    };
  }

  const pwdContainer = $('#passwordContainer')
  const pwdInput = $('#password')
  const pwdStrengthMeter = $('#pwdStrengthMeter')
  const submitBtn = $('#registerSubmit')
  const usernameInput = $('#username')
  const conditionMsg = pwdContainer.find('small')

  /**
   * Check validity and strength of Password input
   */
  pwdInput.on('change paste keyup', function () {
    const password = $(this).val()
    // Validity
    const lengthCheck = password.length >= 12
    const lowerCheck = /[a-z]/.test(password)
    // Having an uppercase but not first letter
    const upperCheck = /^[^A-Z].*[A-Z]/.test(password)
    const numberCheck = /[0-9].*\D$/.test(password)
    // A symbol in the list but not as a last character
    const symbolCheck = /[\\!"#$%&'()*+,-./:;<=>?@[\]^_`{|}~\s].*[^\\!"#$%&'()*+,-./:;<=>?@[\]^_`{|}~\s]$/.test(password)
    const notUserName = password && !password.toLowerCase().includes(usernameInput.val().toLowerCase())

    // Order of displayed conditions messages
    const inputChecks = [
      lengthCheck,
      lowerCheck && upperCheck,
      numberCheck && symbolCheck,
      notUserName
    ]

    // When changing password, can't contain current password
    if (conditionMsg.length === 6) {
      inputChecks.push(
        !password.includes($("#currentPassword").val())
      )
      inputChecks.push(
        password === $("#confirmPassword").val()
      )
    }

    let errors = 0
    for (let i = 0; i < inputChecks.length; i++) {
      // Condition isn't respected
      if (!inputChecks[i]) {
        $(conditionMsg[i]).addClass('text-danger');
        errors++
      } else {
        $(conditionMsg[i]).removeClass('text-danger');
      }
    }

    if (errors > 0) {
      submitBtn.prop('disabled', true)
      pwdContainer.find('.input-group').addClass('is-invalid')
    } else {
      submitBtn.prop('disabled', false)
      pwdContainer.find('.input-group').removeClass('is-invalid')
    }

    // Check password strength
    const resultStrength = zxcvbn(password, [usernameInput.val(), $("#oldPassword").val() || ""])
    const passwordScore = errors > 0 ? 0 : (resultStrength.score === 0 ? 1 : resultStrength.score)
    const scoreText = ['Invalid', 'Very weak', 'Weak', 'Good', 'Very Good']
    pwdStrengthMeter.attr('data-level', passwordScore)
    pwdStrengthMeter.find('span').text(scoreText[passwordScore])
  })

  /**
   * When username changes, check that password isn't equal
   */
  usernameInput.on('change input paste', function () {
    if (pwdInput.val()) {
      // Trigger password input verification
      pwdInput.trigger('change')
    }
  })
  $("#currentPassword").on('change input paste', function () {
    if (pwdInput.val()) {
      // Trigger password input verification
      pwdInput.trigger('change')
    }
  })
  $("#confirmPassword").on('change input paste', function () {
    if (pwdInput.val()) {
      // Trigger password input verification
      pwdInput.trigger('change')
    }
  })
})
