document.addEventListener("DOMContentLoaded", () => {
    fetch('/api/users/current-user')
        .then(response => {
            if (!response.ok) {
                throw new Error("Ошибка загрузки пользователя");
            }
            return response.json();
        })
        .then(user => {
            const tbody = document.getElementById('userInfoBody');
            const row = document.createElement('tr');

            row.innerHTML = `
                    <td>${user.id}</td>
                    <td>${user.name}</td>
                    <td>${user.age}</td>
                    <td>${user.email}</td>
                    <td>${user.password}</td>
                    <td>${user.roles.map(role => role.name.replace('ROLE_','')).join(', ')}</td>
                `;
            tbody.appendChild(row);
        })
        .catch(error => {
            console.error('Ошибка загрузки пользователя:', error);
        });
});