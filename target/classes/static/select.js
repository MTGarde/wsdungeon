// html fails ar visam misijam? like saraksts
// tad vajag initializing funkciju, kas sadabuj visas misijas no taa faila
async function init() {
    const missions = await fetch('/missions/mission-list.json').then(r => r.json());
    showMissionSelect(missions);
}// r ir envelope ar viskkadu info un .json() atgriez tiesi json dalu ka objektu
     //un tad var vinu izmantot ta ka x.title vai x.description utt

function showMissionSelect(missions) {
    const screen = document.getElementById("mission-buttons");
    missions.forEach(mission => {
        const option = document.createElement("div");
        const title = document.createElement("h2");
        const description = document.createElement("p");
        const button = document.createElement("button");

        title.textContent = mission.title;
        description.textContent = mission.description;
        button.textContent = "Start";

        button.addEventListener("click", () => startMission(mission.id));
        option.appendChild(title);
        option.appendChild(description);
        option.appendChild(button);
        screen.appendChild(option);
    });
}

init();
