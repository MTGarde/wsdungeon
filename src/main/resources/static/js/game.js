console.log("full pathname:", window.location.pathname);
console.log("split result:", window.location.pathname.split("/"));
const sessionId = window.location.pathname.split("/")[2];
// id ir ar indeksu 2 jo splitojot pirmais strings ir "" => "/game/id123" sadala vietas kur ir "/" un tad sanak "", "game", "id123"

function print(text) { // izvada jebkadu tekstu
    const output = document.getElementById("output");
    const line = document.createElement("p");
    line.textContent = text; // lietotajs nevar cross site scriptot jo netiek izmantots innerhtml
    output.appendChild(line);
    output.scrollTop = output.scrollHeight; // aizscrollo lidz apaksai
}

async function sendCommand(input) {
    const response = await fetch(`/game/${sessionId}/command`, {
        method: 'POST',
        headers: {'Content-Type': 'application/json'}, // nosaka, ka tiek sutiti json dati
        body: JSON.stringify({ command: input }) // aizsuta uz service ievadito tekstu
    });
    const data = await response.json(); // await - kods iet talak tikai kad tiek sanemta atbilde
    if(data.message) print(data.message);
    if(data.description) print(data.description);
    if(data.exits) print("You can go " + data.exits.join(", "));
}

async function init() { // tikai kad uzsak speli
    const response = await fetch(`/game/start/${sessionId}`);
    const data = await response.json();
    if(data.message) print(data.message);
    if(data.description) print(data.description);
}

// gaida kad lietotajs teksta ievades elementaa nospiez enter
document.getElementById("usercommand").addEventListener("keydown", k => {
    if (k.key === "Enter") {
        const input = k.target.value.trim();
        if (!input) return;
        print(input);
        sendCommand(input);
        k.target.value = "";
    }
});

init();