const sessionId = window.location.pathname.split("/")[2];
// id ir ar indeksu 2 jo splitojot pirmais strings ir "" => "/game/id123" sadala vietas kur ir "/" un tad sanak "", "game", "id123"
let stompClient = null;

function print(text) { // izvada jebkadu tekstu
    const output = document.getElementById("output");
    const line = document.createElement("p");
    line.textContent = text; // lietotajs nevar cross site scriptot jo netiek izmantots innerhtml
    output.appendChild(line);
    output.scrollTop = output.scrollHeight; // aizscrollo lidz apaksai
}

function connect() {
    const socket = new SockJS('/ws');
    stompClient = Stomp.over(socket);

    stompClient.connect({}, () => {
        console.log("WebSocket connected");

        // subscribo pasreizejas sesijas endpointam
        stompClient.subscribe(`/topic/game/${sessionId}`, message => {
            const data = JSON.parse(message.body);
            console.log("parsed data:", data);
            if (data.message) print(data.message);
            if (data.description) print(data.description);
        });

        // kad savienojas, ielade sakuma speles stavokli
        loadGameState();
    });
}

async function sendCommand(input) {
    stompClient.send(`/app/game/${sessionId}/command`, {}, JSON.stringify({ command: input, userId : sessionStorage.getItem("userId") }));
}

async function loadGameState() {
    const response = await fetch(`/game/start/${sessionId}`);
    const data = await response.json();
    if (data.message) print(data.message);
    if (data.description) print(data.description);
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

connect();