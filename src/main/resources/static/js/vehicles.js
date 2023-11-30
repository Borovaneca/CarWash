function getVehicles() {
    let root = document.getElementById('tbodyVehicles');
    fetch('https://carwash-carwash1.azuremicroservices.io/api/my-vehicles/')
        .then(response => response.json())
        .then(vehicles => vehicles.forEach(
            vehicle => {
                let tr = document.createElement('tr');
                let td1 = document.createElement('td');
                td1.textContent = vehicle.brand;
                let td2 = document.createElement('td');
                td2.textContent = vehicle.model;
                let td3 = document.createElement('td');
                td3.textContent = vehicle.color;
                let td4 = document.createElement('td');
                td4.innerHTML = `<a href="/my-vehicles/remove/${vehicle.id}" class="btn btn-warning rounded">Remove</a>`;
                tr.appendChild(td1)
                tr.appendChild(td2)
                tr.appendChild(td3)
                tr.appendChild(td4)
                root.appendChild(tr);
            }
        ))
}