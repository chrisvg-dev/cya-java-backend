const URL = 'http://localhost:8080/api';
const roles = document.getElementById("roles");
const menu = document.getElementById("menu");
const fRoles = document.getElementById("fMenu");

const loadMenu = () => {
    fetch( URL + '/menu' ).then( data => data.json() ).then( data => {
        menu.innerHTML = '';
        data.forEach(item => {
            let option = `<tr>\
                        <td><input name="roles" class="form-check-input" type="checkbox" value="${item.id}" /></td>\
                        <td>${item.id}</td>\
                        <td>${item.name}</td>\
                    </tr>`;
            menu.innerHTML += option;
        });
    }).catch(err => console.log(err));
};
loadMenu();

const loadRoles = () => {
    fetch( URL + '/roles' ).then( data => data.json() )
        .then( data => {
            roles.innerHTML = '';
            data.forEach(item => {
                let option = `<tr>\
                        <td>${item.id}</td>\
                        <td>${item.rolName}</td>\
                    </tr>`;
                roles.innerHTML += option;
            });
        }).catch(err => console.log(err));
};

//loadRoles();


fRoles.addEventListener("submit", (evt) => {
    evt.preventDefault();
    const checked = document.querySelectorAll('input[type="checkbox"]:checked');
    let selected = Array.from(checked).map(x => parseInt(x.value));
    const data = {
        menu: selected,
        roleName: document.getElementById('name').value
    };
    const stringData = JSON.stringify(data);
    fetch( `${URL}/roles`, {method: 'post', body: stringData, headers: {'Content-Type': 'application/json'}} )
        .then( resp => resp.json() )
        .then( resp => {
            console.log(resp);
            toastr.success(JSON.stringify(resp));
            loadRoles();
        } )
        .catch( resp => {
            alert(resp);
            console.log(resp);
        });
});