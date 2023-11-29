function getAllAppointments() {

    let tbody = document.getElementById("tbody-all-appointments");

    fetch('http://localhost:8080/api/all-appointments')
        .then(response => response.json())
        .then(data => data.forEach(
                appointment => {

                    let row = document.createElement("tr");
                    let id = document.createElement("td");
                    let createdBy = document.createElement("td");
                    let madeOn = document.createElement("td");
                    let madeFor = document.createElement("td");
                    let service = document.createElement("td");
                    let vehicle = document.createElement("td");
                    let price = document.createElement("td");
                    let status = document.createElement("td");


                    row.scope = "row";
                    id.textContent = appointment.id;
                    createdBy.textContent = appointment.createdBy;
                    madeOn.textContent = appointment.madeOn;
                    madeFor.textContent = appointment.madeFor;
                    service.textContent = appointment.service;
                    vehicle.textContent = appointment.vehicle;
                    price.textContent = '$' + appointment.price + '0';

                    const lang = localStorage.getItem('lang');
                    if (lang === null) {
                        if (appointment.status === 'PENDING') {
                            status.textContent = 'PENDING';
                            status.style.color = 'orange';
                        } else if (appointment.status === 'ACCEPTED') {
                            status.textContent = 'ACCEPTED';
                            status.style.color = 'green';
                        } else if (appointment.status === 'REJECTED') {
                            status.textContent = 'REJECTED';
                            status.style.color = 'red';
                        }
                    } else if (lang === 'en') {
                        if (appointment.status === 'PENDING') {
                            status.textContent = 'PENDING';
                            status.style.color = 'orange';
                        } else if (appointment.status === 'ACCEPTED') {
                            status.textContent = 'ACCEPTED';
                            status.style.color = 'green';
                        } else if (appointment.status === 'REJECTED') {
                            status.textContent = 'REJECTED';
                            status.style.color = 'red';
                        }

                    } else if (lang === 'it') {
                        if (appointment.status === 'PENDING') {
                            status.textContent = 'IN ATTESA';
                            status.style.color = 'orange';
                        } else if (appointment.status === 'ACCEPTED') {
                            status.textContent = 'ACCETTATO';
                            status.style.color = 'green';
                        } else if (appointment.status === 'REJECTED') {
                            status.textContent = 'RIFIUTATO';
                            status.style.color = 'red';

                        }
                    } else if (lang === 'bg') {
                        if (appointment.status === 'PENDING') {
                            status.textContent = 'ИЗЧАКВА';
                            status.style.color = 'orange';
                        } else if (appointment.status === 'ACCEPTED') {
                            status.textContent = 'ПРИЕТ';
                            status.style.color = 'green';
                        } else if (appointment.status === 'REJECTED') {
                            status.textContent = 'ОТХВЪРЛЕН';
                            status.style.color = 'red';
                        }
                    }

                    let tr2 = document.createElement("tr");
                    tr2.classList.add("spacer");
                    let tdInside = document.createElement("td");
                    tdInside.colSpan = 100;
                    tr2.appendChild(tdInside);

                    row.appendChild(id);
                    row.appendChild(createdBy);
                    row.appendChild(madeOn);
                    row.appendChild(madeFor);
                    row.appendChild(service);
                    row.appendChild(vehicle);
                    row.appendChild(price);
                    row.appendChild(status);
                    tbody.appendChild(row);
                    tbody.appendChild(tr2);
                }
            )
        );
}