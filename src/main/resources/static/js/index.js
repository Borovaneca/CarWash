function fetchIndex() {
    let index = document.getElementById('index-response');

    fetch('http://localhost:8080/api/index/').then(response => response.json())
        .then(data => data.forEach(service => {
            let p = document.createElement('blockquote');
            p.textContent = service.name + ': ' + service.description;
            index.appendChild(p)
        }))
}