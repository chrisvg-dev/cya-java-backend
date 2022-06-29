const URL = 'http://localhost:8080/api';
const select = document.getElementById("roles");


const loadRoles = () => {
    fetch( URL + '/roles' ).then( data => data.json() ).then( data => {
        data.forEach(rol => {
            let option = `<div class="form-check"><input name="roles" class="form-check-input" type="checkbox" value="${rol.id}" />`;
            option += `<label class="form-check-label"> ${rol.rolName} </label></div>`;
            select.innerHTML += option;
        });
    }).catch(err => console.log(err));
};

const sendRequest = (url, data) => {
    const stringData = JSON.stringify(data);
    fetch( `${URL}${url}`, {method: 'post', body: stringData, headers: {'Content-Type': 'application/json'}} )
        .then( resp => resp.json() )
        .then( resp => {
            console.log(resp)
            if (resp.id > 0 ) {
                toastr.success( `Se ha registrado al usuario con el id: ${resp.id}` );
            } else {
                toastr.success( JSON.stringify(resp) );
            }
        } )
        .catch( resp => {
            console.log(resp);
            toastr.success(resp);
        });
};

