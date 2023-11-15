function myAppointments() {
    const root = document.getElementById("tbody");
    fetch('http://localhost:8080/api/appointments/')
        .then(response => response.json())
        .then(vehicles => vehicles.forEach(
            vehicle => {

                let tr =  document.createElement('tr');
                let td1 = document.createElement('td');
                td1.scope = 'row';
                td1.textContent = vehicle.createOn;
                let td2 = document.createElement('td');
                td2.textContent = vehicle.vehicle;
                let td3 = document.createElement('td');
                td3.textContent = vehicle.madeFor;
                let td4 = document.createElement('td');
                td4.textContent = vehicle.service;
                let td5 = document.createElement('td');
                td5.textContent = vehicle.price + "0$";
                let td6 = document.createElement('td');
                td6.textContent = vehicle.status;
                td6.classList.add('btn');
                switch (vehicle.status) {
                    case "APPROVED":
                        td6.classList.add('btn-success');
                        break;
                    case "REJECTED":
                        td6.classList.add('btn-danger');
                        break;
                    case "PENDING":
                        td6.classList.add('btn-secondary');
                }
                tr.appendChild(td1);
                tr.appendChild(td2);
                tr.appendChild(td3);
                tr.appendChild(td4);
                tr.appendChild(td5);
                tr.appendChild(td6);
                root.appendChild(tr);
            }
        ))
}