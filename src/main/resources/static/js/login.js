document.getElementById("login-btn").addEventListener("click", async () => {
    const username = document.getElementById("username").value.trim();
    const password = document.getElementById("password").value;
    const error = document.getElementById("error-message");

    if (!username || !password) {
        error.textContent = "Please fill in all fields.";
        return;
    }

    const response = await fetch('/login', {
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

document.getElementById("password").addEventListener("keydown", e => { // enter poga lai pieslegtos
    if (e.key === "Enter") document.getElementById("login-btn").click();
});
