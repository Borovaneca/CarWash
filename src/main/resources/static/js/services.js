function getServices() {
    let root = document.getElementById('services-container');
    fetch('http://localhost:8080/api/services/')
        .then(response => response.json())
        .then(services => services.forEach(
            service => {

                let card = document.createElement('div');
                card.classList.add("card");
                let videoContainer = document.createElement('div');
                videoContainer.classList.add("video-container")
                let video = document.createElement('iframe');
                video.width = "300";
                video.height = "169";
                video.src = 'https://www.youtube.com/embed/' + service.urlVideo;
                video.title = service.name;
                let text = video.src;
                videoContainer.appendChild(video).classList.add("video-container");

                let cardBody = document.createElement('div');
                cardBody.classList.add("card-body");

                let cardTitle = document.createElement('h2');
                cardTitle.classList.add("card-title");
                cardTitle.textContent = service.name;

                let cardDescription = document.createElement('p');
                cardDescription.textContent = service.description;
                cardDescription.classList.add("card-description")

                let cardPrice = document.createElement('p');
                cardPrice.textContent = '$' + service.price + '0';
                cardPrice.classList.add("card-price");

                cardBody.appendChild(cardTitle)
                cardBody.appendChild(cardDescription)
                cardBody.appendChild(cardPrice)

                card.appendChild(videoContainer)
                card.appendChild(cardBody)


                root.appendChild(card)
            }
        ))
}