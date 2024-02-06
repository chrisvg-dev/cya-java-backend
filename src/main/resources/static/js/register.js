const URL = 'https://cya-postulation-project.cristhianvg.dev/api';
const select = document.getElementById("roles");
const tblUsers = document.getElementById("tblUsers");


const loadRoles = () => {
    fetch( URL + '/roles' ).then( data => data.json() ).then( data => {
        data.forEach(rol => {
            let option = ` <option value="${rol.id}">${rol.rolName}</option>`;
            select.innerHTML += option;
        });
    }).catch(err => console.log(err));
};

const deleteUser = (userId) => {
    if (confirm("¿Está seguro de esta acción?")){
        fetch( `${URL}/users/delete/${userId}`, {method: 'DELETE', headers: {'Content-Type': 'application/json'}} )
            .then( data => data.json() ).then( data => {
                loadUsers(tblUsers);
                alert(data.message);
                console.log(data);
            }).catch(err => console.log(err));
    }
};

const loadUsers = async () => {
    await fetch( URL + '/users' ).then( data => data.json() ).then( data => {
        tblUsers.innerHTML = '';
        data.forEach(user => {
            console.log(user);
            let row = `<div class="alert alert-primary mt-4" role="alert">\
                              El usuario <a href="#" class="alert-link">${user.name} ${user.lastName}</a> está registrado como ${user.roles[0].rolName}.\
                           </div>\
                        <div class="media text-muted pt-3">\
                        <button class="btn btn-danger btn-sm" style="float: right" onclick="deleteUser(${user.id})" >Eliminar</button>\
                         <p class="media-body pb-3 mb-0 small lh-125 border-bottom border-gray">\
                            <strong class="d-block text-gray-dark">@Email: ${user.email}</strong>\
                            El uso que se le de a esta cuenta es responsabilidad del que lo registra y del que la utiliza... <br><br>\

                            Creado el <span class="badge text-bg-warning">${user.createdAt}</span>
                         </p>\
                        </div>`;
            tblUsers.innerHTML += row;
        });
    }).catch(err => console.log(err));
};

const sendRequest = async (url, data) => {
    const stringData = JSON.stringify(data);
    await fetch( `${URL}${url}`, {method: 'post', body: stringData, headers: {'Content-Type': 'application/json'}} )
        .then( resp => resp.json() )
        .then( resp => {
            console.log(resp)
            if (resp.id > 0 ) {
                toastr.success( `Se ha registrado al usuario con el id: ${resp.id}` );
                loadUsers();
            } else {
                toastr.success( JSON.stringify(resp) );
            }
        } )
        .catch( resp => {
            console.log(resp);
            toastr.success(resp);
        });
};

