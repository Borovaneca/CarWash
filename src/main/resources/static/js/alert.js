function check() {
    let requiredInput = Array.from(document.getElementsByClassName("requiredInput"));
    for (const required of requiredInput) {
        let text = required.value;
        if (text === null || text === "") {
            alert("Check the required fields!")
            return;
        }
    }
}