async function fetchAbout() {
    let employees = document.querySelector('#staff');
    const template = document.querySelector('[employeeInfo]');

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
            let position = document.createElement('p');
            let lang = localStorage.getItem('lang');
            console.log(lang);
            if (lang === null) {
                lang = 'en';
                if (member.position === 'OWNER') {
                    position.textContent = 'Owner';
                } else if (member.position === 'MANAGER') {
                    position.textContent = 'Manager';
                } else if (member.position === 'EMPLOYEE') {
                    position.textContent = 'Employee';
                }
            } else if (lang === 'it') {
                if (member.position === 'OWNER') {
                    position.textContent = 'Proprietario';
                } else if (member.position === 'MANAGER') {
                    position.textContent = 'Manager';
                } else if (member.position === 'EMPLOYEE') {
                    position.textContent = 'Dipendente';
                }
            } else if (lang === 'bg') {
                if (member.position === 'OWNER') {
                    position.textContent = 'Собственик';
                } else if (member.position === 'MANAGER') {
                    position.textContent = 'Мениджър';
                } else if (member.position === 'EMPLOYEE') {
                    position.textContent = 'Служител';
                }
            } else {
                if (member.position === 'OWNER') {
                    position.textContent = 'Owner';
                } else if (member.position === 'MANAGER') {
                    position.textContent = 'Manager';
                } else if (member.position === 'EMPLOYEE') {
                    position.textContent = 'Employee';
                }
            }
            position.classList.add('mb-0');
            infoContainer.appendChild(position)
            let child = template.content.cloneNode(true).children[0];
            let age = child.querySelector('[age]');
            age.textContent = `${age.textContent}: ${member.age}`;
            age.classList.add('small', 'text-uppercase', 'text-muted');
            infoContainer.appendChild(child);
            employeeContainer.appendChild(infoContainer);
            employees.appendChild(employeeContainer);
        }))
}