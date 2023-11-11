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
