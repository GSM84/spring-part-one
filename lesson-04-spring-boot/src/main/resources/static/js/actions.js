function showHideFilter(_id) {
    var element = document.getElementById(_id);

    if (element.style.display == 'none'){
        element.style.display = 'block';
    } else {
        element.style.display = 'none';
    }
}