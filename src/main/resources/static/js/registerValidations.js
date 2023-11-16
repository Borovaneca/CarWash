function displayValidationMessage(element, isValid, message) {
    var feedbackElement = document.getElementById(element.id + '-feedback');

    if (!feedbackElement) {
        feedbackElement = document.createElement('p');
        feedbackElement.id = element.id + '-feedback';
        element.parentNode.appendChild(feedbackElement);
    }

    feedbackElement.innerText = isValid ? '' : message;
    feedbackElement.style.color = isValid ? ''  : 'red';
}

function validateField(element, validationFunction, message) {
    var isValid = validationFunction(element.value.trim());
    displayValidationMessage(element, isValid, message);
    return isValid;
}

function validateUsername(username) {
    return username.length >= 3;
}

function validateForm() {
    var image = document.getElementById('image');
    var username = document.getElementById('username');
    var email = document.getElementById('email');
    var password = document.getElementById('password');
    var confirmPassword = document.getElementById('confirmPassword');

    if (!image.value.trim()) {
        alert('Please choose a profile picture.');
        return false;
    }

    const selectedFile = image.files[0];
    const fileType = selectedFile.type;
    if (!fileType.startsWith('image/')) {
        alert('Please select a valid image file (PNG or JPEG).');
        return false;
    }

    if (username.value.length < 3) {
        alert('Username must be at least 3 characters.');
        return false;
    }

    var emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    if (email.hasAttribute('required') && !emailRegex.test(email.value.trim())) {
        alert('Please enter a valid email address.');
        return false;
    }

    if (password.value.length < 8) {
        alert('Password must be at least 8 characters.');
        return false;
    }
    if (confirmPassword.value !== password.value) {
        alert('Passwords do not match.');
        return false;
    }
    return true;
}
function validateEmail(email) {
    // Your email validation logic here
    var emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    return emailRegex.test(email);
}

function validatePassword(password) {
    // Your password validation logic here
    return password.length >= 8;
}

function validateConfirmPassword(confirmPassword, password) {
    // Your confirm password validation logic here
    return confirmPassword === password;
}