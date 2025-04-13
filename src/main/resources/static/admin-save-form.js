document.addEventListener("DOMContentLoaded", () => {
    fetch('/api/roles')
        .then(response => {
            if (!response.ok) {
                throw new Error("Ошибка загрузки ролей");
            }
            return response.json();
        })
        .then(roles => {
            const rolesContainer = document.getElementById("rolesContainer");
            roles.forEach(role => {
                const roleElement = document.createElement("div");
                roleElement.classList.add("form-check");

                const checkbox = document.createElement("input");
                checkbox.type = "checkbox";
                checkbox.classList.add("form-check-input", "role-checkbox");
                checkbox.id = "role_" + role.id;
                checkbox.value = role.name;

                const label = document.createElement("label");
                label.classList.add("form-check-label");
                label.setAttribute("for", "role_" + role.id);
                label.textContent = role.name;

                roleElement.appendChild(checkbox);
                roleElement.appendChild(label);
                rolesContainer.appendChild(roleElement);
            });
        })
        .catch(error => {
            console.error('Ошибка загрузки ролей:', error);
        });
});


document.getElementById("userForm").addEventListener("submit", async function (e) {
    e.preventDefault();

    const name = document.getElementById("name").value;
    const age = document.getElementById("age").value;
    const email = document.getElementById("email").value;
    const password = document.getElementById("password").value;

    const selectedRoles = Array.from(document.querySelectorAll(".role-checkbox:checked"))
        .map(checkbox => ({name: checkbox.value}));

    const newUser = {
        name,
        age,
        email,
        password,
        roles: selectedRoles
    };

    const response = await fetch("/api/users/add", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(newUser)
    });

    if (response.ok) {
        window.location.href = "/admin";
    } else {
        const error = await response.json();
        alert("Ошибка: " + (error.message || response.status));
    }
});