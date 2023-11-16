async function getAllAppointmentsForToday() {

    let tbody = document.getElementById('tbody');

    fetch('http://localhost:8080/api/appointments/today')
        .then(response => response.json())
        .then(appointments => appointments.forEach(
            appointment => {
                let tr =  document.createElement('tr');
                let td1 = document.createElement('td');
                td1.scope = 'row';
                td1.textContent = appointment.madeBy;
                let td2 = document.createElement('td');
                td2.textContent = appointment.createOn;
                let td3 = document.createElement('td');
                td3.textContent = appointment.vehicle;
                let td4 = document.createElement('td');
                td4.textContent = appointment.service;
                let td5 = document.createElement('td');
                td5.textContent = '$' + appointment.price + '0';

                tr.appendChild(td1);
                tr.appendChild(td2);
                tr.appendChild(td3);
                tr.appendChild(td4);
                tr.appendChild(td5);
                tbody.appendChild(tr);
            }
        ))
}