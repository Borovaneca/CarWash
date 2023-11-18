async function getAllUsers() {
    const csrfToken = document.querySelector("meta[name='_csrf']").getAttribute("content");
    const csrfHeader = document.querySelector("meta[name='_csrf_header']").getAttribute("content");


    let tbody = document.getElementById('tbody');

    fetch('http://localhost:8080/api/owner/users/all')
        .then(response => response.json())
        .then(users => users.forEach(
            user => {

                let tr = document.createElement('tr');
                let picture = document.createElement('td');
                let profileUrl = document.createElement('a');
                let username = document.createElement('td');
                let email = document.createElement('td');
                let higherRole = document.createElement('td');
                let age = document.createElement('td');
                let registered = document.createElement('td');
                let status = document.createElement('td');

                let img = document.createElement('img');
                img.src = user.locatedOn;
                img.width = 100;
                img.alt = `${user.username}`;
                profileUrl.href = `http://localhost:8080/users/view/${user.username}`;
                profileUrl.appendChild(img);
                picture.appendChild(profileUrl);
                username.textContent = user.username;
                email.textContent = user.email;
                higherRole.textContent = user.role;
                age.textContent = user.age;
                registered.textContent = user.registeredOn;
                status.textContent = user.isBanned;

                let changeRole = document.createElement('td');
                changeRole.classList.add('text-center', 'wrap');
                let select = document.createElement('select');
                select.id = 'role';
                let option3 = document.createElement('option');
                option3.value = 'EMPLOYEE';
                option3.textContent = 'Employee'
                select.appendChild(option3);
                let option2 = document.createElement('option');
                option2.value = 'MANAGER';
                option2.textContent = 'Manager';
                select.appendChild(option2);
                let option = document.createElement('option');
                option.value = 'OWNER';
                option.textContent = 'Owner';
                select.appendChild(option);
                select.addEventListener('change', () => {
                    let roleSelect = document.getElementById('role');
                    let roleValue = roleSelect.value;
                });

                let addRoleButton = document.createElement('button');
                addRoleButton.type = 'button'
                addRoleButton.classList.add('btn',  'btn-primary', 'btn-sm');
                addRoleButton.textContent = 'Add Role';
                addRoleButton.onclick = () => addRole(user.id, select);

                let removeRoleButton = document.createElement('button');
                removeRoleButton.type = 'button'
                removeRoleButton.classList.add('btn',  'btn-danger', 'btn-sm');
                removeRoleButton.textContent = 'Remove Role';
                removeRoleButton.onclick = () => removeRole(user.id, select);

                let ban = document.createElement('td');
                let banButton = document.createElement('button');
                banButton.type = 'button';
                banButton.classList.add('btn', 'btn-danger', 'btn-sm');
                banButton.textContent = 'Ban/Unban';
                banButton.onclick = () => banUser(user.id);
                ban.appendChild(banButton);

                changeRole.appendChild(select)
                changeRole.appendChild(addRoleButton)
                changeRole.appendChild(removeRoleButton);
                tr.appendChild(picture);
                tr.appendChild(username);
                tr.appendChild(email);
                tr.appendChild(higherRole);
                tr.appendChild(age);
                tr.appendChild(registered);
                tr.appendChild(status);
                tr.appendChild(changeRole);
                tr.appendChild(ban);
                tbody.appendChild(tr);

            }
        ))
}

async function addRole(userId, selectElement) {

    const csrfToken = document.querySelector("meta[name='_csrf']").getAttribute("content");
    const csrfHeader = document.querySelector("meta[name='_csrf_header']").getAttribute("content");

    const headers = {
        [csrfHeader]: csrfToken
    };

    let roleValue = selectElement.value;

    await fetch(`http://localhost:8080/owner/users/add-role/${userId}/${roleValue}`, {
        method: 'POST',
        headers: headers
    })

    location.reload();
    alert('Role added successfully!');
    return false;
}

async function removeRole(id, select) {
    const csrfToken = document.querySelector("meta[name='_csrf']").getAttribute("content");
    const csrfHeader = document.querySelector("meta[name='_csrf_header']").getAttribute("content");

    const headers = {
        [csrfHeader]: csrfToken
    };

    let roleValue = select.value;

    await fetch(`http://localhost:8080/owner/users/remove-role/${id}/${roleValue}`, {
        method: 'POST',
        headers: headers
    })

    location.reload();
    alert('Role removed successfully!');
    return false;
}

async function banUser(id) {

    const csrfToken = document.querySelector("meta[name='_csrf']").getAttribute("content");
    const csrfHeader = document.querySelector("meta[name='_csrf_header']").getAttribute("content");

    const headers = {
        'Content-Type': 'application/json',
        [csrfHeader]: csrfToken
    };

    try {

        const response = await fetch(`http://localhost:8080/api/owner/users/ban/${id}`, {
            method: 'POST',
            headers: headers
        })
        let promise = response.json();
        promise.then(data => {
            if (data.isBanned === 'true') {
                alert('User banned successfully!');
            } else {
                alert('User unbanned successfully!');
            }
        })
        location.reload();
    } catch (error) {
        return false;
    }
}

