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
        await fetch('http://localhost:8080/users/delete/' + username, {
            method: 'DELETE',
            headers: headers
        }).then(response => {
            if (response.ok) {
                alert("Account deleted successfully");
                window.location.href = "http://localhost:8080/users/login";
            }
        });
    }
}