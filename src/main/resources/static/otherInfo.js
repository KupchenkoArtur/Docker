async function dataFilling() {
    let principal = await fetch("http://localhost:8080/api/principal").then(function(response) {
        response.json().then(function(data) {
            navbarData(data);
            userTableData(data);

        })
    }).catch(function(error) {
        console.log(error)
    });

}

function navbarData({ username, roleList }) {
    const headerUsername = document.getElementById('headerUsername');

    let strRole = '';

    roleList.forEach((role) => {
        strRole += role.role.substring(5) + ' ';
    })

    headerUsername.insertAdjacentHTML('beforeend', `
        <a  class="font-weight-bold text-white" id="headerUsername">${username}</a>
        <a> with roles:</a>
        <a  id="headerRoles">${strRole}</a>
    `)
}


function userTableData({ id, firstName, surname, age, username, roleList }) {
    const userTable = document.getElementById('tbodyUserTable');
    
    let strRole = '';

    roleList.forEach((role) => {
        strRole += role.role.substring(5) + ' ';
    })

    userTable.insertAdjacentHTML('beforeend', `
        <tr>
            <td>${id}</td>
            <td>${firstName}</td>
            <td>${surname}</td>
            <td>${age}</td>
            <td>${username}</td>
            <td>${strRole}</td>
        </tr>
        `)

}





window.addEventListener('DOMContentLoaded', dataFilling);