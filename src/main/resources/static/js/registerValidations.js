function displayValidationMessage(element, isValid, message) {
    var feedbackElement = document.getElementById(element.id + '-feedback');

    if (!feedbackElement) {
        feedbackElement = document.createElement('p');
        feedbackElement.id = element.id + '-feedback';
        if (element.length === 2) {
            element[1].parentNode.appendChild(feedbackElement);
        } else {
            element.parentNode.appendChild(feedbackElement);
        }
    }

    feedbackElement.innerText = isValid ? '' : message;
    feedbackElement.style.color = isValid ? ''  : 'red';
}

function validateField(element, validationFunction) {
    var isValid = true;
    if (element.length === 2) {
        isValid = validationFunction([element[0].value.trim(), element[1].value.trim()]);
    } else {
        isValid = validationFunction(element.value.trim());
    }
    const lang = localStorage.getItem('lang');
    if (validationFunction === validateUsername) {
        if (lang === null) {
            displayValidationMessage(element, isValid, 'Username must be at least 3 characters.');
        } else if (lang === 'en') {
            displayValidationMessage(element, isValid, 'Username must be at least 3 characters.');
        } else if (lang === 'it') {
            displayValidationMessage(element, isValid, 'Il nome utente deve contenere almeno 3 caratteri.');
        } else if (lang === 'bg') {
            displayValidationMessage(element, isValid, 'Потребителското име трябва да съдържа поне 3 знака.');
        }
    } else if (validationFunction === validateEmail) {
        if (lang === null) {
            displayValidationMessage(element, isValid, 'Please enter a valid email address.');
        } else if (lang === 'en') {
            displayValidationMessage(element, isValid, 'Please enter a valid email address.');
        } else if (lang === 'it') {
            displayValidationMessage(element, isValid, 'Inserisci un indirizzo email valido.');
        } else if (lang === 'bg') {
            displayValidationMessage(element, isValid, 'Моля, въведете валиден имейл адрес.');
        }
    } else if (validationFunction === validatePassword) {
        if (lang === null) {
            displayValidationMessage(element, isValid, 'Password must be at least 8 characters.');
        } else if (lang === 'en') {
            displayValidationMessage(element, isValid, 'Password must be at least 8 characters.');
        } else if (lang === 'it') {
            displayValidationMessage(element, isValid, 'La password deve contenere almeno 8 caratteri.');
        } else if (lang === 'bg') {
            displayValidationMessage(element, isValid, 'Паролата трябва да съдържа поне 8 знака.');
        }
    } else if (validationFunction === validateConfirmPassword) {
        if (lang === null) {
            displayValidationMessage(element, isValid, 'Passwords do not match.');
        } else if (lang === 'en') {
            displayValidationMessage(element, isValid, 'Passwords do not match.');
        } else if (lang === 'it') {
            displayValidationMessage(element, isValid, 'Le password non corrispondono.');
        } else if (lang === 'bg') {
            displayValidationMessage(element, isValid, 'Паролите не съвпадат.');
        }
    }
    return isValid;
}

function validateUsername(username) {
    return username.length >= 3;
}

function validateForm() {
    var item = localStorage.getItem('lang');
    var username = document.getElementById('username');
    var email = document.getElementById('email');
    var password = document.getElementById('password');


    var confirmPassword = document.getElementById('confirmPassword');
    if (username.value.length < 3) {
        console.log(item);
        if (item === null) {
            alert('Username must be at least 3 characters.');
        } else if (item === 'en') {
            alert('Username must be at least 3 characters.');
        } else if (item === 'it') {
            alert('Il nome utente deve contenere almeno 3 caratteri.');
        } else if (item === 'bg') {
            alert('Потребителското име трябва да съдържа поне 3 знака.');
        }
        return false;

    }
    var emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    if (email.hasAttribute('required') && !emailRegex.test(email.value.trim())) {
        if(item === null) {
            alert('Please enter a valid email address.');
        } else if (item === 'en') {
            alert('Please enter a valid email address.');
        } else if (item === 'it') {
            alert('Inserisci un indirizzo email valido.');
        } else if (item === 'bg') {
            alert('Моля, въведете валиден имейл адрес.');
        }
        return false;
    }

    if (password.value.length < 8) {
        if (item === null) {
            alert('Password must be at least 8 characters.');
        } else if (item === 'en') {
            alert('Password must be at least 8 characters.');
        } else if (item === 'it') {
            alert('La password deve contenere almeno 8 caratteri.');
        } else if (item === 'bg') {
            alert('Паролата трябва да съдържа поне 8 знака.');
        }
        return false;
    }
    if (confirmPassword.value !== password.value) {
        if (item === null) {
            alert('Passwords do not match.');
        } else if (item === 'en') {
            alert('Passwords do not match.');
        } else if (item === 'it') {
            alert('Le password non corrispondono.');
        } else if (item === 'bg') {
            alert('Паролите не съвпадат.');
        }
        return false;
    }
    return true;
}
function validateEmail(email) {
    var emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    return emailRegex.test(email);
}

function validatePassword(password) {
    return password.length >= 8;
}

function validateConfirmPassword(passwords) {
    return passwords[0] === passwords[1];
}