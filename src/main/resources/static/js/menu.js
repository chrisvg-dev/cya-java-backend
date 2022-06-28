const URL = 'http://localhost:8080/api';
const menu = document.getElementById("menu");
const fMenu = document.getElementById("fMenu");

const loadMenu = () => {
    fetch( URL + '/menu' ).then( data => data.json() )
        .then( data => {
            menu.innerHTML = '';
            data.forEach(item => {
                let option = `<tr>\
                        <td>${item.id}</td>\
                        <td>${item.name}</td>\
                        <td>${item.path}</td>\
                    </tr>`;
                menu.innerHTML += option;
            });
        }).catch(err => console.log(err));
};

loadMenu();


fMenu.addEventListener("submit", (evt) => {
    evt.preventDefault();
    const data = {
        name: document.getElementById('name').value,
        path: document.getElementById('path').value,
    };
    const stringData = JSON.stringify(data);
    fetch( `${URL}/menu`, {method: 'post', body: stringData, headers: {'Content-Type': 'application/json'}} )
        .then( resp => resp.json() )
        .then( resp => {
            console.log(resp);
            toastr.success(JSON.stringify(resp));
            loadMenu();
        } )
        .catch( resp => {
            alert(resp);
            console.log(resp);
        });
});