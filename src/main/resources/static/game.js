let missionId = null;
let map = null;
let currentRoom = null;
let currentRoomId = null;
let availableDirections = null;

async function init() {
    // lai nav info japadod pa url to ieliek sessionStorage
    missionId = sessionStorage.getItem("missionId");

    if (!missionId) {
        window.location.href = "/index.html";
        return;
    }

    map = await fetch(`/missions/${missionId}.json`).then(m => m.json());
    currentRoom = await loadRoom(map.start);
    currentRoomId = currentRoom.id;
    availableDirections = map.roomConnections[currentRoomId];
    print(currentRoom.description);
}

async function loadRoom(roomId) {
    return fetch(`/missions/${missionId}/${roomId}.json`).then(r => r.json());
}

function handleCommand(input) {
    const tokens = input.toLowerCase().trim().split(" ");
    const action = tokens[0];
    const target = tokens[1];

    switch(action) {
        case "go" :
            handleGo(target);
            break;
        case "look" :
            handleLook();
            break;
        case "help" :
            handleHelp();
            break;
        default:
            return print("You can't do that. Try 'help' for a list of commands.");
    }
}

async function handleGo(direction) {
    if (!direction) {
        print("Where are you trying to go?");
        return;
    }

    if (!availableDirections[direction]) {
        print("You can't go there.");
        return;
    }

    currentRoom = await loadRoom(availableDirections[direction]);
    currentRoomId = currentRoom.id;
    availableDirections = map.roomConnections[currentRoomId];

    print(currentRoom.description);
}

function handleLook() {
    print(currentRoom.lookDescription);
    printDirections();
    if (currentRoom.escapeDescription) {
        print(currentRoom.escapeDescription);
    }
}

function handleHelp() {
    print("Help:\ngo [direction] - move in a direction [north, south, east, west]\nlook around\nmore actions coming soon.");
}

function print(text) {
    const output = document.getElementById("output");
    const line = document.createElement("p");
    line.textContent = text; // lietotajs nevar cross site scriptot jo netiek izmantots innerhtml. izmantoju textcontent
    output.appendChild(line);
    output.scrollTop = output.scrollHeight; // aizscrollo lidz apaksai
}

function printDirections() {
    let text = "You can go: ";
    // Object.keys() panem keyus ka array un .join parvers to par stringu
    text += Object.keys(availableDirections).join(", ");
    print(text);
}

// gaida kad lietotajs teksta ievades elementaa nospiez enter
document.getElementById("usercommand").addEventListener("keydown", k => {
    if (k.key === "Enter") {
        const input = k.target.value.trim();
        if (!input) return;
        print(input);
        handleCommand(input);
        k.target.value = "";
    }
});

init();