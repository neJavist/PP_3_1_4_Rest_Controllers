document.addEventListener("DOMContentLoaded", () => {
    fetch('/api/users/current-user')
        .then(response => {
            if (!response.ok) {
                throw new Error("Ошибка загрузки пользователя");
            }
            return response.json();
        })
        .then(user => {
            document.getElementById('navbarUserInfo').textContent =
                `${user.email} with roles: ${user.roles.map(r => r.name).join(', ')}`;
        })
        .catch(error => {
            console.error('Ошибка загрузки пользователя:', error);
        });
});