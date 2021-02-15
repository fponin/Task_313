async function printFormAddUser() {
    let response = await fetch('/admin/roles');
    let roles = await response.json();


    let add_user = document.querySelector("#new-user")
    add_user.innerHTML += `
    <form name="addForm" id="addForm">
        <div class="form-group">
            <label><strong>Name</strong></label>
            <input type="text" class="form-control" placeholder="Name" name="name">
        </div>
        <div class="form-group">
            <label><strong>Email</strong></label>
            <input type="text" class="form-control" placeholder="E-mail" name="email">
        </div>
        <div class="form-group">
            <label><strong>Password</strong></label>
            <input type="password" class="form-control" placeholder="Password" name="password">
        </div>
        <div class="form-group">
            <label for="exampleFormControlSelect"><strong>Role</strong></label>
            <select multiple class="form-control" id="exampleFormControlSelect" size="2"
                id="rols" name="userRoles">
                <option>${roles[0].name}</option>
                <option selected="selected">${roles[1].name}</option>
            </select>
        </div>

        <button type="submit" class="btn btn-success" id="buttonAdd">Add new user</button>
    </form>`

    function addNewUser(e) {
        e.preventDefault();
        let user = {
            name: document.addForm.name.value,
            email: document.addForm.email.value,
            password: document.addForm.password.value,
            roles: translate(getAllOptions(document.addForm.userRoles))
        }

        fetch('http://localhost:8080/admin/addUser', {
            method: 'POST',
            headers: {
                'Accept': 'application/json, text/plain, */*',
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(user)
        }).then(function (response) {
            if (!response.ok) {
                return Promise.reject(new Error(
                    'Response failed: ' + response.status + ' (' + response.statusText + ')'
                ));
            }
            return response.json();
        }).finally(() => {
            getAllUsers();
            document.querySelector("#addForm").reset();
        });

    }

    document.querySelector("#buttonAdd").onclick = addNewUser;
}

printFormAddUser();





