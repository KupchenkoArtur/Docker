async function getAllUser() {
    const res = await fetch("http://localhost:8080/api");
    const users = await res.json();

    users.forEach(user => {
        usersToHTML(user);   
    });
}


window.addEventListener('DOMContentLoaded', getAllUser);


function usersToHTML({ id, firstName, surname, age, username, roleList }) {
    const tbody = document.getElementById('data');
    let strRole = '';

    roleList.forEach((role) => {
        strRole += role.role.substring(5) + ' ';
    })

    tbody.insertAdjacentHTML('beforeend', `
    <tr id="user${id}" >
        <td>${id}</td>
        <td>${firstName}</td>
        <td>${surname}</td>
        <td>${age}</td>
        <td>${username}</td>
        <td>${strRole}</td>
        <td>
            <!-- Button trigger modal -->
            <button type="button" class="btn btn-info editBtn" data-toggle="modal"
                    data-target="#editModal"
                    onclick="editUserData(${id})">
                Edit
            </button>
        </td>
        <td>
            <!-- Button trigger modal -->
            <button type="button" class="btn btn-danger"
                    data-toggle="modal"
                    data-target="#deleteModal"
                    onclick="deleteUserData(${id})">
                Delete
            </button>
        </td>
    </tr>`)
}