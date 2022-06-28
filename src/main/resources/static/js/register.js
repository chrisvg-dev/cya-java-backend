const URL = 'http://localhost:8080/api';
const select = document.getElementById("roles");
const fUsers = document.getElementById("fUsers");

fetch( URL + '/roles' ).then( data => data.json() ).then( data => {
    data.forEach(rol => {
        let option = `<div class="form-check"><input name="roles" class="form-check-input" type="checkbox" value="${rol.id}" />`;
        option += `<label class="form-check-label"> ${rol.rolName} </label></div>`;
        select.innerHTML += option;
    });
}).catch(err => console.log(err));

fUsers.addEventListener("submit", (evt) => {
    evt.preventDefault();
    const checked = document.querySelectorAll('input[type="checkbox"]:checked');
    let selected = Array.from(checked).map(x => parseInt(x.value));
    const data = {
        roles: selected,
        name: document.getElementById('name').value,
        lastName: document.getElementById('lastName').value,
        email: document.getElementById('email').value,
        password: document.getElementById('password').value
    };
    const stringData = JSON.stringify(data);
    console.log(stringData);
    console.log( `${URL}/users` );
    fetch( `${URL}/users`, {method: 'post', body: stringData, headers: {'Content-Type': 'application/json'}} )
        .then( resp => resp.json() )
        .then( resp => {
            console.log(resp)
            toastr.success(JSON.stringify(resp));
        } )
        .catch( resp => {
            console.log(resp);
            toastr.success(resp);
        });
});