async function deleteAccount() {
    const csrfToken = document.querySelector("meta[name='_csrf']").getAttribute("content");
    const csrfHeader = document.querySelector("meta[name='_csrf_header']").getAttribute("content");
    const username = document.getElementById("logged-user").textContent;

    const headers = {
        'Content-Type': 'application/json',
        [csrfHeader]: csrfToken
    };

    var result = confirm("Are you sure you want to delete your account?");
    if (result) {
        await fetch('/users/delete/' + username, {
            method: 'DELETE',
            headers: headers
        }).catch(error => {
            alert("Account deleted successfully");
            window.location.href = "https://carwash-carwash1.azuremicroservices.io/users/login";
        })
    }
}