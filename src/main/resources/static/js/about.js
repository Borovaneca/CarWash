function fetchAbout() {
let about = document.getElementById('team-member-container');

fetch('http://localhost:8080/api/about/').then(response => response.json())
    .then(staff => staff.forEach(member => {
        let teamContainer = document.createElement('div');
        teamContainer.classList.add('team-member');
        let image = document.createElement('img');
        image.src = member.imageUrl;
        image.alt = member.fullName;
        teamContainer.appendChild(image);

        let memberDetails = document.createElement('div');
        memberDetails.classList.add('member-details');
        let name = document.createElement('h3');
        name.textContent = member.fullName;
        memberDetails.appendChild(name);
        let age = document.createElement('p');
        age.textContent = 'Age: ' + member.age;
        memberDetails.appendChild(age);
        let role = document.createElement('p');
        role.textContent = 'Position: ' + member.position;
        memberDetails.appendChild(role);
        teamContainer.appendChild(memberDetails);
        about.appendChild(teamContainer);
    }))
}