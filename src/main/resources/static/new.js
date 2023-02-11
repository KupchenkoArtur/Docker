async function showAllRole() {
    let dbRoles = [];
    let roles = await fetch("http://localhost:8080/api/roles");
    await roles.json().then(roles => {
        roles.forEach(role =>
            dbRoles.push(role))
    });
    return dbRoles;
}

async function showRole() {

    const inputRoles = document.getElementById('nRoles');

    let Array = await showAllRole();
    console.log(Array);
    inputRoles.innerHTML = `
        <option value="${Array[0].id}">${Array[0].role}</option>
        <option value="${Array[1].id}">${Array[1].role}</option>
        `
}


document.getElementById('profile-tab').addEventListener('click', showRole)

document.getElementById('addNewUser').addEventListener('click', createUser)

async function createUser() {
    const inputFirstName = document.getElementById('nFirstName');
    const inputSurname = document.getElementById('nSurname');
    const inputAge = document.getElementById('nAge');
    const inputUsername = document.getElementById('nUsername');
    const inputPassword = document.getElementById('nPassword');


    const firstName = inputFirstName.value;
    const surname = inputSurname.value;
    const age = inputAge.value;
    const username = inputUsername.value;
    const password = inputPassword.value;
    let listRoles = await roleArray(document.getElementById('nRoles'));


    if (firstName && surname && age && username && password && (listRoles.length !== 0)) {

        let res = await fetch("http://localhost:8080/api", {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({firstName, surname, age, username, password, roleList: listRoles})
        });
        const result = await res.json();
        await addUserInTable(result);
    }

    inputFirstName.value = ''
    inputSurname.value = ''
    inputAge.value = ''
    inputUsername.value = ''
    inputPassword.value = ''

}


// let roleArray = (options) => {
//
//     let array = []
//     for (let i = 0; i < options.length; i++) {
//         if (options[i].selected) {
//             let role = {
//                 id: options[i].value,
//                 role: dbRoles[i].role
//             }
//
//             array.push(role)
//         }
//     }
//     return array
// }
async function roleArray(options) {
    let dbRoles = await showAllRole();
    let array = []
    for (let i = 0; i < options.length; i++) {
        if (options[i].selected) {
            let role = {
                id: options[i].value,
                role: dbRoles[i].role
            }

            array.push(role)
        }
    }
    return array
}


async function addUserInTable(result) {
    const id = result.id;

    const res = await fetch(`http://localhost:8080/api/${id}`);
    const user = await res.json();

    let strRoles = '';

    user.roleList.forEach((role) => {
        strRoles += role.role.substring(5) + ' ';
    })


    const tbody = document.getElementById('data');

    tbody.insertAdjacentHTML('beforeend', `
    <tr id="user${user.id}" >
        <td>${user.id}</td>
        <td>${user.firstName}</td>
        <td>${user.surname}</td>
        <td>${user.age}</td>
        <td>${user.username}</td>
        <td>${strRoles}</td>
        <td>
            <!-- Button trigger modal -->
            <button type="button" class="btn btn-info editBtn" data-toggle="modal"
                    data-target="#editModal"
                    onclick="editUserData(${user.id})">
                Edit
            </button>
        </td>
        <td>
            <!-- Button trigger modal -->
            <button type="button" class="btn btn-danger"
                    data-toggle="modal"
                    data-target="#deleteModal"
                    onclick="deleteUserData(${user.id})">
                Delete
            </button>
        </td>
    </tr>`)

}









