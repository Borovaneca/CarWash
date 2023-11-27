function myAppointments() {
    const language = localStorage.getItem('language');
    const lang = navigator.language || navigator.userLanguage;
    console.log(lang);

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
                if (localStorage.getItem('lang') === "bg") {

                    switch (vehicle.status) {
                        case "APPROVED":
                            td6.textContent = "Одобрена";
                            td6.classList.add('btn-success');
                            break;
                        case "REJECTED":
                            td6.textContent = "Отхвърлена";
                            td6.classList.add('btn-danger');
                            break;
                        case "PENDING":
                            td6.textContent = "Изчакваща";
                            td6.classList.add('btn-secondary');
                    }
                } else if (localStorage.getItem('lang') === "it") {
                    switch (vehicle.status) {
                        case "APPROVED":
                            td6.textContent = "Approvato";
                            td6.classList.add('btn-success');
                            break;
                        case "REJECTED":
                            td6.textContent = "Rifiutato";
                            td6.classList.add('btn-danger');
                            break;
                        case "PENDING":
                            td6.textContent = "In attesa";
                            td6.classList.add('btn-secondary');
                    }
                } else {

                    switch (vehicle.status) {
                        case "APPROVED":
                            td6.textContent = "Approved";
                            td6.classList.add('btn-success');
                            break;
                        case "REJECTED":
                            td6.textContent = "Rejected";
                            td6.classList.add('btn-danger');
                            break;
                        case "PENDING":
                            td6.textContent = "Pending";
                            td6.classList.add('btn-secondary');
                    }
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