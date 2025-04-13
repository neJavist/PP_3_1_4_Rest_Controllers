document.addEventListener('DOMContentLoaded', () => {
    fetchUsers();
    fetchRoles();

    document.getElementById('editUserForm').addEventListener('submit', saveUser);
});

async function fetchUsers() {
    const response = await fetch('/api/users');
    const users = await response.json();

    const tbody = document.getElementById('usersTableBody');
    tbody.innerHTML = '';

    users.forEach(user => {
        const row = document.createElement('tr');
        const roles = user.roles.map(r => r.name.replace('ROLE_', '')).join(', ');

        row.innerHTML = `
            <td>${user.id}</td>
            <td>${user.name}</td>
            <td>${user.age}</td>
            <td>${user.email}</td>
            <td>${user.password}</td>
            <td>${roles}</td>
            <td><button class="btn btn-warning btn-sm" onclick='openEditModal(${user.id})'>Edit</button></td>
            <td><button class="btn btn-danger btn-sm" onclick='deleteUser(${user.id})'>Delete</button></td>
        `;

        tbody.appendChild(row);
    });
}

let allRoles = [];

async function fetchRoles() {
    const response = await fetch('/api/roles');
    allRoles = await response.json();
}

async function openEditModal(userId) {
    const response = await fetch(`/api/users/${userId}`);
    const user = await response.json();

    document.getElementById('edit-id').value = user.id;
    document.getElementById('edit-name').value = user.name;
    document.getElementById('edit-age').value = user.age;
    document.getElementById('edit-email').value = user.email;
    document.getElementById('edit-password').value = user.password;

    const rolesDiv = document.getElementById('edit-roles');
    rolesDiv.innerHTML = '<label>Roles</label>';

    allRoles.forEach(role => {
        const checked = user.roles.some(r => r.name === role.name);
        rolesDiv.innerHTML += `
            <div class="form-check">
                <input class="form-check-input" type="checkbox" name="roles" value="${role.name}" ${checked ? 'checked' : ''}>
                <label class="form-check-label">${role.name.replace('ROLE_', '')}</label>
            </div>
        `;
    });

    $('#editUserModal').modal('show');
}

async function saveUser(event) {
    event.preventDefault();

    const form = document.getElementById('editUserForm');
    const formData = new FormData(form);

    const roles = [];
    formData.getAll('roles').forEach(name => roles.push({name}));

    const updatedUser = {
        id: formData.get('id'),
        name: formData.get('name'),
        age: formData.get('age'),
        email: formData.get('email'),
        password: formData.get('password'),
        roles: roles
    };

    const response = await fetch(`/api/users/edit/${updatedUser.id}`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(updatedUser)
    });

    if (response.ok) {
        $('#editUserModal').modal('hide');
        await fetchUsers();
    } else {
        alert('Ошибка при сохранении');
    }
}

async function deleteUser(userId) {
    if (!confirm('Удалить пользователя?')) return;

    const response = await fetch(`/api/users/delete/${userId}`, {
        method: 'DELETE'
    });

    if (response.ok) {
        await fetchUsers();
    } else {
        alert('Ошибка при удалении');
    }
}
