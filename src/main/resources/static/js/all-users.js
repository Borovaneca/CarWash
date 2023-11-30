async function getAllUsers() {

    let elementId = 1;
    let usersArr = [];
    const searchInput = document.getElementById('search-bar');
    let usersNum = document.getElementById('count-users');
    let counter = 0;
    searchInput.addEventListener('input', (e) => {
        const value = e.target.value;
        let filteredUsers = usersArr;
        counter = 0;
        const checkBox = document.getElementById('bannedUserTrue');
        if (checkBox.checked) {
            filteredUsers = filteredUsers.filter(user => user.banned === 'Yes').slice();
            usersArr.forEach(user => document.getElementById(`${user.element}`).classList.toggle("hide", true));
        }
        filteredUsers.forEach(user => {
            console.log(user.banned);
            const isMatch = user.username.toLowerCase().includes(value.toLowerCase()) ||
                user.email.toLowerCase().includes(value.toLowerCase()) ||
                user.role.toLowerCase().includes(value.toLowerCase()) ||
                user.registeredOn.toLowerCase().includes(value.toLowerCase());
            if (value.toLowerCase() === 'banned') {
                document.getElementById(`${user.element}`).classList.toggle("hide", user.banned !== 'Yes');
                if (user.banned === 'Yes') {
                    counter++;
                }
            } else if (isMatch) {
                counter++;
                document.getElementById(`${user.element}`).classList.toggle("hide", !isMatch);
            } else {
                document.getElementById(`${user.element}`).classList.toggle("hide", true);
            }
        })
        usersNum.textContent = counter.toString();
    });

    let tbody = document.getElementById('tbody');


    fetch('https://carwash-carwash1.azuremicroservices.io/api/owner/users/all')
        .then(response => response.json())
        .then(users => {
            usersNum.textContent = users.length.toString()
            usersArr = users.map(user => {

                    let tr = document.createElement('tr');
                    tr.id = `${elementId++}`;
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
                    profileUrl.href = `/users/view/${user.username}`;
                    profileUrl.appendChild(img);
                    picture.appendChild(profileUrl);
                    username.textContent = user.username;
                    email.textContent = user.email;
                    higherRole.textContent = user.role;
                    age.textContent = user.age;
                    registered.textContent = user.registeredOn;
                    if (user.isBanned === 'Yes' && localStorage.getItem('lang') === null) {
                        status.textContent = 'Yes';
                    } else if (user.isBanned === 'Yes' && localStorage.getItem('lang') === 'en') {
                        status.textContent = 'Yes';
                    } else if (user.isBanned === 'Yes' && localStorage.getItem('lang') === 'bg') {
                        status.textContent = 'Да';
                    } else if (user.isBanned === 'Yes' && localStorage.getItem('lang') === 'it') {
                        status.textContent = 'Sì';
                    }

                    if (user.isBanned === 'No' && localStorage.getItem('lang') === null) {
                        status.textContent = 'No';
                    } else if (user.isBanned === 'No' && localStorage.getItem('lang') === 'en') {
                        status.textContent = 'No';
                    } else if (user.isBanned === 'No' && localStorage.getItem('lang') === 'bg') {
                        status.textContent = 'Не';
                    } else if (user.isBanned === 'No' && localStorage.getItem('lang') === 'it') {
                        status.textContent = 'No';
                    }
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
                    addRoleButton.classList.add('btn', 'btn-primary', 'btn-sm');
                    if (localStorage.getItem('lang') === null) {
                        addRoleButton.textContent = 'Add Role';
                    } else if (localStorage.getItem('lang') === 'en') {
                        addRoleButton.textContent = 'Add Role';
                    } else if (localStorage.getItem('lang') === 'bg') {
                        addRoleButton.textContent = 'Добави Роля';
                    } else if (localStorage.getItem('lang') === 'it') {
                        addRoleButton.textContent = 'Aggiungi Ruolo';
                    }
                    addRoleButton.onclick = () => addRole(user.id, select);

                    let removeRoleButton = document.createElement('button');
                    removeRoleButton.type = 'button'
                    removeRoleButton.classList.add('btn', 'btn-danger', 'btn-sm');
                    if (localStorage.getItem('lang') === null) {
                        removeRoleButton.textContent = 'Remove Role';
                    } else if (localStorage.getItem('lang') === 'en') {
                        removeRoleButton.textContent = 'Remove Role';
                    } else if (localStorage.getItem('lang') === 'bg') {
                        removeRoleButton.textContent = 'Премахни Роля';
                    } else if (localStorage.getItem('lang') === 'it') {
                        removeRoleButton.textContent = 'Rimuovi Ruolo';
                    }
                    removeRoleButton.onclick = () => removeRole(user.id, select);

                    let ban = document.createElement('td');
                    let banButton = document.createElement('button');
                    banButton.type = 'button';
                    if (user.isBanned) {
                        banButton.classList.add('btn', 'btn-danger', 'btn-sm');
                        banButton.textContent = 'Ban';
                    } else {
                        banButton.classList.add('btn', 'btn-success', 'btn-sm');
                        banButton.textContent = 'Unban';
                    }
                    banButton.onclick = () => banOrUnbanUser(user.id);
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
                    return {
                        username: user.username,
                        email: user.email,
                        role: user.role,
                        age: user.age,
                        registeredOn: user.registeredOn,
                        element: `${tr.id}`,
                        banned: user.isBanned
                    };
                }
            )
        })
}

async function addRole(userId, selectElement) {

    const csrfToken = document.querySelector("meta[name='_csrf']").getAttribute("content");
    const csrfHeader = document.querySelector("meta[name='_csrf_header']").getAttribute("content");

    const headers = {
        [csrfHeader]: csrfToken
    };

    let roleValue = selectElement.value;

    await fetch(`https://carwash-carwash1.azuremicroservices.io/owner/users/add-role/${userId}/${roleValue}`, {
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

    await fetch(`https://carwash-carwash1.azuremicroservices.io/owner/users/remove-role/${id}/${roleValue}`, {
        method: 'POST',
        headers: headers
    })

    location.reload();
    alert('Role removed successfully!');
    return false;
}

async function banOrUnbanUser(id) {

    const csrfToken = document.querySelector("meta[name='_csrf']").getAttribute("content");
    const csrfHeader = document.querySelector("meta[name='_csrf_header']").getAttribute("content");

    const headers = {
        'Content-Type': 'application/json',
        [csrfHeader]: csrfToken
    };

    try {

        const response = await fetch(`https://carwash-carwash1.azuremicroservices.io/api/owner/users/ban/${id}`, {
            method: 'POST',
            headers: headers
        })
        let promise = response.json();
        promise.then(data => {
            if (data.isBanned === 'Yes') {
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

