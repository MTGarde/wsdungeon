document.getElementById("register-btn").addEventListener("click", async () => {
    const username = document.getElementById("username").value.trim();
    const password = document.getElementById("password").value;
    const confirm = document.getElementById("confirm-password").value;
    const error = document.getElementById("error-message");

    if (!username || !password) {
        error.textContent = "Please fill in all fields.";
        return;
    }

    if (password !== confirm) {
        error.textContent = "Passwords do not match.";
        return;
    }

    const response = await fetch('/register', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ username, password })
    });

    const data = await response.json();

    if (response.ok) {
        sessionStorage.setItem("userId", data.userId);
        window.location.href = "/";
    } else {
        error.textContent = data.message;
    }
});