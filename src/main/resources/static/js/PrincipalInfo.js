function printPrincipal() {
    fetch('/info')
        .then(response => response.json())
        .then(principal => {
            let navbar = document.querySelector("#navbar");
            let roles = '';
            for (let key in principal.roles) {
                roles += `${principal.roles[key].name} `
            }
            navbar.innerHTML += `
                <nav class="navbar navbar-dark mb-3 bg-dark">
                <div class="container-fluid" m-1>
                <strong><a class="navbar-brand" href="/users" data-btn="home">${principal.name}</a></strong>
                <ul class="navbar-nav mr-auto">
                    <li class="nav-item active">
                        <span class="navbar-text-start" style="color: white">
                        with roles: ${roles}
                        </span>
                    </li>
                </ul>
                <span class="navbar-text"><a href="/logout" style="color: #808080; text-decoration: none" data-btn="logout">Logout</a></span>
                </nav>
                </div>
                `
        });
}

function printPrincipalInfo() {
    fetch('/info', {method: 'GET'})
        .then(response => response.json())
        .then(principal => {
            let principal_info = document.querySelector("#principal-info");
            let roles = '';
            for (let key in principal.roles) {
                roles += `${principal.roles[key].name} `
            }
            principal_info.innerHTML += `
        <tbody>
            <tr>
            <th scope="row">${principal.id}</th>
            <td>${principal.name}</td>
            <td>${principal.email}</td>
            <td>${roles}</td>
            </td>
            </tr>
        </tbody>
        `
        });
}
printPrincipal();
printPrincipalInfo();