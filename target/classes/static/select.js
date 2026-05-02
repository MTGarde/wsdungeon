async function init() {
    // r ir envelope ar viskkadu info un .json() atgriez tiesi json dalu ka objektu
    // un tad var vinu izmantot ta ka x.title vai x.description utt
    const missions = await fetch('/missions/mission-list.json').then(r => r.json());
    showMissionSelect(missions);
}

function showMissionSelect(missions) {
    const screen = document.getElementById("mission-buttons");
    missions.forEach(mission => {
        const option = document.createElement("div");   // izveido elementus
        const title = document.createElement("h2");
        const description = document.createElement("p");
        const button = document.createElement("button");

        title.textContent = mission.title;              // ieliek tajos tekstu ko vajag
        description.textContent = mission.description;  // neizmanto innerHTML drosibas apsverumu del
        button.textContent = mission.id;
        button.addEventListener("click", () => startMission(mission.id));

        option.appendChild(title);                      // pievieno elementus html
        option.appendChild(description);
        option.appendChild(button);
        screen.appendChild(option);
    });
}

function startMission(missionId) {
    console.log("Game sent with the mission id: ", missionId);
    sessionStorage.setItem("missionId", missionId); // lai nav parametrs japadod pa url, to padod
    window.location.href = "/game.html";
}

init();
