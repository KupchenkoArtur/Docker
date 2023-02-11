async function editUserData(id) {

    let href = `http://localhost:8080/api/${id}`;
    let dbRoles = await showAllRole();
    $.get(href, function (user) {
        $('.myForm #id').val(user.id);
        $('.myForm #firstname').val(user.firstName);
        $('.myForm #surname').val(user.surname);
        $('.myForm #age').val(user.age);
        $('.myForm #username').val(user.username);
        $('.myForm #password').val(user.password);
        const inputRoles = document.getElementById('roles');

        inputRoles.innerHTML = `
            <option value="${dbRoles[0].id}">${dbRoles[0].role}</option>
            <option value="${dbRoles[1].id}">${dbRoles[1].role}</option>
            `

    })

    document.getElementById('edit-user-button').addEventListener('click', async () => {
        const inputId = document.getElementById('id');
        const inputFirstName = document.getElementById('firstname');
        const inputSurname = document.getElementById('surname');
        const inputAge = document.getElementById('age');
        const inputUsername = document.getElementById('username');
        const inputPassword = document.getElementById('password');


        const id = inputId.value;
        const firstName = inputFirstName.value;
        const surname = inputSurname.value;
        const age = inputAge.value;
        const username = inputUsername.value;
        const password = inputPassword.value;
        const listRoleEditUser = await roleArray(document.getElementById('roles'))

        if (id && firstName && surname && age && username && password) {
            const res = await fetch("http://localhost:8080/api", {
                method: 'PATCH',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({id, firstName, surname, age, username, password, roleList: listRoleEditUser})
            });
            const result = await res.json();
            await editUserInTable(result);
            $('#editModal').modal('toggle');
        }
    })
}


async function editUserInTable(result) {

    const id = result.id;
    const res = await fetch(`http://localhost:8080/api/${id}`);
    const user = await res.json();

    document.getElementById(`user${user.id}`).remove();
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
            
            <button type="button" class="btn btn-info editBtn" data-toggle="modal"
                    data-target="#editModal"
                    onclick="editUserData(${user.id})">
                Edit
            </button>
        </td>
        <td>
         
            <button type="button" class="btn btn-danger"
                    data-toggle="modal"
                    data-target="#deleteModal"
                    onclick="deleteUserData(${user.id})">
                Delete
            </button>
        </td>
    </tr>`)

}