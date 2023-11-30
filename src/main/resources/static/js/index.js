function fetchIndex() {
    let index = document.getElementById('index-response');

    fetch('https://carwash-carwash1.azuremicroservices.io/api/index/').then(response => response.json())
        .then(data => data.forEach(service => {
            let p = document.createElement('p');
            p.textContent = service.name + ': ' + service.description;
            index.appendChild(p)
        }))
}