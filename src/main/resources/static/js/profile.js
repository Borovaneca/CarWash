function showForm(id) {

    let root = document.getElementById(id);
    root.style.zIndex = '1';
}

function hide(formId) {
    let root = document.getElementById(formId);
    root.style.zIndex = '-1';
}

function submit(formId) {
    hide(formId)
}

function checkImage() {
    let input = document.getElementById("input-file");
    let file = input.files[0];

    if (!file) {
        alert('No image selected');
        return;
    }

    const allowedTypes = ['image/png', 'image/jpeg'];
    if (!allowedTypes.includes(file.type)) {
        alert('Please select a valid image file (PNG or JPEG).');
        input.value = '';
    }
}