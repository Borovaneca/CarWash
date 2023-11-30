function getAllAwaitingApprovalAppointments() {
    const root =  document.getElementById('tbody');

    fetch('https://carwash-carwash1.azuremicroservices.io/api/awaiting-approval/')
        .then(response => response.json())
        .then(appointments => appointments.forEach(
            appointment => {
                let tr =  document.createElement('tr');
                let td1 = document.createElement('td');
                td1.scope = 'row';
                td1.textContent = appointment.createBy;
                let td2 = document.createElement('td');
                td2.textContent = appointment.createOn;
                let td3 = document.createElement('td');
                td3.textContent = appointment.vehicle;
                let td4 = document.createElement('td');
                td4.textContent = appointment.madeFor;
                let td5 = document.createElement('td');
                td5.textContent = appointment.service;
                let td6 = document.createElement('td');
                td6.textContent = appointment.price + "0$";
                let td7 = document.createElement('td');
                let approve = document.createElement('button');
                approve.classList.add('btn');
                approve.classList.add('btn-success');
                approve.onclick = () => approved(appointment.id);
                let thumbUp = document.createElement('i')
                thumbUp.classList.add('fa-solid');
                thumbUp.classList.add('fa-thumbs-up');
                thumbUp.title = "Approve";
                approve.appendChild(thumbUp);
                let decline = document.createElement('button');
                decline.classList.add('btn');
                decline.classList.add('btn-danger');
                decline.title = "Decline";
                decline.onclick = () => declined(appointment.id);
                let thumbDown = document.createElement('i')
                thumbDown.classList.add('fa-solid');
                thumbDown.classList.add('fa-thumbs-down');
                thumbDown.title = "Decline";
                decline.appendChild(thumbDown)
                td7.classList.add('d-flex');
                td7.classList.add('justify-content-around');
                td7.appendChild(decline);
                td7.appendChild(approve);
                decline.appendChild(thumbDown);
                tr.appendChild(td1);
                tr.appendChild(td2);
                tr.appendChild(td3);
                tr.appendChild(td4);
                tr.appendChild(td5);
                tr.appendChild(td6);
                tr.appendChild(td7);
                root.appendChild(tr);
            }
        ))
}

async function approved(id) {
    const csrfToken = document.querySelector("meta[name='_csrf']").getAttribute("content");
    const csrfHeader = document.querySelector("meta[name='_csrf_header']").getAttribute("content");

    const headers = {
        'Content-Type': 'application/json',
        [csrfHeader]: csrfToken
    };

    await fetch(`https://carwash-carwash1.azuremicroservices.io/manager/awaiting-approval/approve/${id}`, {
        method: 'POST',
        headers: headers
    });
    location.reload();
}

async function declined(id) {
    const csrfToken = document.querySelector("meta[name='_csrf']").getAttribute("content");
    const csrfHeader = document.querySelector("meta[name='_csrf_header']").getAttribute("content");

    const headers = {
        'Content-Type': 'application/json',
        [csrfHeader]: csrfToken
    };

    await fetch(`https://carwash-carwash1.azuremicroservices.io/manager/awaiting-approval/decline/${id}`, {
        method: 'POST',
        headers: headers
    });
    location.reload();
}