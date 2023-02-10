async function dataFilling() {
    let principal = await fetch("http://localhost:8080/api/user/principal").then(function(response) {
        response.json().then(function(data) {
            navbarData(data);
            userTableData(data);

        })
    }).catch(function(error) {
        console.log(error)
    });

}

window.addEventListener('DOMContentLoaded', dataFilling);