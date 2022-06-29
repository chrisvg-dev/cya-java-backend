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

const deleteRole = (roleId) => {
    if (confirm("¿Está seguro de esta acción?")){
        fetch( `${URL}/roles/delete/${roleId}`, {method: 'DELETE', headers: {'Content-Type': 'application/json'}} )
            .then( data => data.json() ).then( data => {
            loadRoles();
            if ( data.status == 500 ) {
                alert("Necesita borrar a los usuarios que tienen asignado este rol.");
            } else {
                alert(data.message);
            }
            console.log(data);
        }).catch(err => console.log(err));
    }
};

const loadRoles = () => {
    fetch( URL + '/roles' ).then( data => data.json() )
        .then( data => {
            tblRoles.innerHTML = '';
            data.forEach(rol => {
                let row = `<div class="alert alert-primary mt-1" role="alert"> El rol tiene el nombre ${rol.rolName}</div>\
                        <div class="media text-muted pt-3 mb-3">\
                        <button class="btn btn-danger btn-sm" style="float: right" onclick="deleteRole(${rol.id})" >Eliminar</button>\
                         <p class="media-body pb-3 mb-0 small lh-125 border-bottom border-gray">\
                            <strong class="d-block text-gray-dark">La lista de menú de este rol es la siguiente: </strong>`;
                let menu = `<nav aria-label="breadcrumb"> <ol class="breadcrumb">`;
                rol.menu.forEach(item => {
                    menu += `<li class="breadcrumb-item"><a href="${item.path}">${item.name}</a></li>`;
                });
                menu += ` </ol></nav>`;
                row += menu;
                row += `</p></div><br>`;

                tblRoles.innerHTML += row;
            });
        }).catch(err => console.log(err));
};

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
            if (resp.id > 0 ) {
                toastr.success( `Se ha registrado al usuario con el id: ${resp.id}` );
                loadRoles();
            } else {
                toastr.info( JSON.stringify(resp) );
            }
            loadRoles();
        } )
        .catch( resp => {
            alert(resp);
            console.log(resp);
        });
});