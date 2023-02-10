async function deleteUserData(id) {

    let href = `http://localhost:8080/api/${id}`
        $.get(href, function (user) {
            $('.myDeleteForm #dId').val(user.id);
            $('.myDeleteForm #dFirstname').val(user.firstname);
            $('.myDeleteForm #dSurname').val(user.surname);
            $('.myDeleteForm #dAge').val(user.age);
            $('.myDeleteForm #dUsername').val(user.username);
            const inputRoles = document.getElementById('dRoles');


            inputRoles.innerHTML = `
        <option value="${dbRoles[0].id}" name="ROLE_ADMIN" >${dbRoles[0].name}</option>
        <option value="${dbRoles[1].id}" name="ROLE_USER" >${dbRoles[1].name}</option>
        `
        })

    

    document.getElementById('delete-user-button').addEventListener('click', async () =>{
            const res = await fetch(`http://localhost:8080/api/${id}`, {
            method: "DELETE",
            headers: {
                'Content-Type': 'application/json',
            }
        });
        document.getElementById(`user${id}`).remove();


        $('#deleteModal').modal('toggle');

    })

   
}