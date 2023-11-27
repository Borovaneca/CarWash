async function fetchAbout() {
    let employees = document.querySelector('#staff');

    await fetch('http://localhost:8080/api/about/').then(response => response.json())
        .then(staff => staff.forEach(member => {
            let employeeContainer = document.createElement('div');
            employeeContainer.classList.add('col-xl-3', 'col-sm-6', 'mb-5');
            let infoContainer = document.createElement('div');
            infoContainer.classList.add('bg-white', 'rounded', 'shadow-sm', 'py-5', 'px-4');
            let image = document.createElement('img');
            image.alt = '';
            image.width = 100;
            image.classList.add('img-fluid', 'rounded-circle', 'mb-3','img-thumbnail', 'shadow-sm');
            image.src = member.image;
            let anchor = document.createElement('a');
            anchor.href = `/users/view/${member.username}`;
            anchor.appendChild(image);
            infoContainer.appendChild(anchor);
            let fullName = document.createElement('h5');
            fullName.classList.add('mb-0');
            fullName.textContent = member.fullName;
            infoContainer.appendChild(fullName);
            let position = document.getElementById('position');
            position.textContent = member.position;
            position.classList.add('mb-0');
            infoContainer.appendChild(position)
            let age = document.getElementById('age');
            age.textContent = `${age.textContent}: ${member.age}`;
            age.classList.add('small', 'text-uppercase', 'text-muted');
            infoContainer.appendChild(age);
            employeeContainer.appendChild(infoContainer);
            employees.appendChild(employeeContainer);
        }))
}