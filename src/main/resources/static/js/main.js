// function printPrincipal() {
//     fetch('/info')
//         .then(response => response.json())
//         .then(principal => {
//             let navbar = document.querySelector("#navbar");
//             let roles = '';
//             for (let key in principal.roles) {
//                 roles += `${principal.roles[key].name} `
//             }
//             navbar.innerHTML += `
//                 <nav class="navbar navbar-dark mb-3 bg-dark">
//                 <div class="container-fluid" m-1>
//                 <strong><a class="navbar-brand" href="/users" data-btn="home">${principal.name}</a></strong>
//                 <ul class="navbar-nav mr-auto">
//                     <li class="nav-item active">
//                         <span class="navbar-text-start" style="color: white">
//                         with roles: ${roles}
//                         </span>
//                     </li>
//                 </ul>
//                 <span class="navbar-text"><a href="/logout" style="color: #808080; text-decoration: none" data-btn="logout">Logout</a></span>
//                 </nav>
//                 </div>
//                 `
//         });
// }
//
// function printPrincipalInfo() {
//     fetch('/info', {method: 'GET'})
//         .then(response => response.json())
//         .then(principal => {
//             let principal_info = document.querySelector("#principal-info");
//             let roles = '';
//             for (let key in principal.roles) {
//                 roles += `${principal.roles[key].name} `
//             }
//             principal_info.innerHTML += `
//         <tbody>
//             <tr>
//             <th scope="row">${principal.id}</th>
//             <td>${principal.name}</td>
//             <td>${principal.email}</td>
//             <td>${roles}</td>
//             </td>
//             </tr>
//         </tbody>
//         `
//         });
// }

function getAllOptions(select) {
    let result = [];
    let options = select && select.options;
    let opt;

    for (let i = 0, iLen = options.length; i < iLen; i++) {
        opt = options[i];
        if (opt.selected) {
            result.push(opt.value || opt.text);
        }
    }
    return result;
}

function translate(array) {
    let result = [];

    if (array.indexOf("ROLE_ADMIN") >= 0) {
        result.push({"id": 1, "name": "ROLE_ADMIN"});
    }
    if (array.indexOf("ROLE_USER") >= 0) {
        result.push({"id": 2, "name": "ROLE_USER"});
    }
    return result;
}

function getAllUsers() {
    fetch('/admin/roles')
        .then(response => response.json())
        .then(allRoles => {
            fetch('/admin/users')
                .then(response => response.json())
                .then(usersList => {
                    let output = '';
                    usersList.forEach(function (user) {
                        let roles = '';
                        for (let key in user.roles) {
                            roles += `${user.roles[key].name} `
                        }
                        output += `
                            <tr>
                                <td>${user.id}</td>
                                <td>${user.name}</td>
                                <td>${user.email}</td>
                                <td>${roles}</td>
                                <td>
                                    <form id="editForm">
                                        <button type="button" class="btn btn-info" data-toggle="modal"
                                                data-target="#staticBackdropEdit"
                                                data-btn="edit" data-id="${user.id}">EDIT
                                        </button>
                                    </form>
                                </td>
                                <td>
                                    <form>
                                        <button type="button" class="btn btn-danger" data-toggle="modal"
                                                data-target="#staticBackdropDelete"
                                                data-btn="delete" data-id="${user.id}">DELETE
                                        </button>
                                    </form>
                                </td>
                            </tr>`;
                    });

                    document.querySelector('#all-users').innerHTML = output;

                })
        })
}

document.addEventListener('click', event => {
    event.preventDefault();
    const btnType = event.target.dataset.btn;

    if (btnType === 'edit') {
        const id = event.target.dataset.id;
        fetch('/admin/roles')
            .then(response => response.json())
            .then(allRoles => {
                fetch('/admin/user/' + id)
                    .then(response => response.json())
                    .then(data => {
                        document.querySelector("#disabledTextInput").value = data.id;
                        document.querySelector("#editName").value = data.name;
                        document.querySelector("#editEmail").value = data.email;
                        document.querySelector("#editPassword").value = ""
                        let formsRoles = '';
                        for (let key in allRoles) {
                            if (data.roles.find(role => role.name === allRoles[key].name)) {
                                formsRoles += `<option selected="selected">${allRoles[key].name}</option>`
                            } else {
                                formsRoles += `<option>${allRoles[key].name}</option>`
                            }
                        }
                        document.querySelector("#exampleFormControlSelectEdit").innerHTML = formsRoles;
                    })
            })
    }
        if (btnType === 'submitEdit') {
            let url = 'http://localhost:8080/admin/users';
            let user = {
                id: document.querySelector("#disabledTextInput").value,
                name: document.querySelector("#editName").value,
                email: document.querySelector("#editEmail").value,
                password: document.querySelector("#editPassword").value,
                roles: translate(getAllOptions(document.querySelector("#exampleFormControlSelectEdit")))
            }
            console.log(user);
            fetch(url, {
                method: 'PUT',
                headers: {
                    'Accept': 'application/json, text/plain, */*',
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(user)
            }).then(function (response) {
                // if (!response.ok) {
                //     return Promise.reject(new Error(
                //         'Response failed: ' + response.status + ' (' + response.statusText + ')'
                //     ));
                // }
                return response.json();
            }).finally(() => {
                getAllUsers();
                $('#staticBackdropEdit').modal('hide');
            });
        }

        if (btnType === 'delete') {
            const id = event.target.dataset.id;
            const url = '/admin/user/' + id;
            fetch(url)
                .then(response => response.json())
                .then(data => {
                    document.querySelector("#disabledTextInputDelete").value = data.id;
                    document.querySelector("#deleteName").value = data.name;
                    document.querySelector("#deleteEmail").value = data.email;
                    let block = '';
                    if (data.roles.length > 1) {
                        block += `
                    <option>${data.roles[0].name}</option>
                    <option>${data.roles[1].name}</option>`
                        document.querySelector("#exampleFormControlSelectDelete").innerHTML = block;
                    } else {
                        block += `<option>${data.roles[0].name}</option>`
                    }
                    document.querySelector("#exampleFormControlSelectDelete").innerHTML = block;
                });
        }
        if (btnType === 'submitDelete') {
            let id = document.querySelector("#disabledTextInputDelete").value;
            let url = 'http://localhost:8080/admin/users/' + id;
            fetch(url, {
                method: 'DELETE',
                headers: {
                    'Accept': 'application/json, text/plain, */*',
                    'Content-Type': 'application/json'
                },
            }).then(function (response) {
                if (!response.ok) {
                    return Promise.reject(new Error(
                        'Response failed: ' + response.status + ' (' + response.statusText + ')'
                    ));
                }
                return response.json();
            }).finally(() => {
                getAllUsers();
                $('.modal').modal('hide');
            });

        }

        if (btnType === 'logout') {
            document.location.href = "/logout"
        }
        if (btnType === 'home') {
            document.location.href = "/users"
        }
    }
)

getAllUsers();
// printPrincipal();
// printPrincipalInfo();

