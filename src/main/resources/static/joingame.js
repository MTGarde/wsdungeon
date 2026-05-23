const inputs = document.querySelector("#code-inputs").querySelectorAll(".code-box"); // .code-box, jo ta ir klase, selects tapat ka css syntax
// query selector tpc ka tad var .forEach(), jo vins atgriez statisku sarakjstu      // #code-inputs, jo tas ir id. kad selecto element type tad ir bez neka
const btn = document.querySelector('#join-btn');

inputs.forEach((input, i) => {
    input.addEventListener('input', () => {
        input.value = input.value.replace(/[^0-9]/g, ''); // g (global) nozime visus matchus stringaa - visu kas nav cipars aizvieto ar tuksumu.
        if (input.value && i < inputs.length - 1) {
            inputs[i + 1].focus(); // focus parvieto kursoru uz nakamo laucinu
        }
        const code = [...inputs].map(n => n.value).join(''); // panem ievaditos ciparus un savieno stringaa
        btn.disabled = code.length < 6; // disablo pogu kamer nav ievadits pilns kods
    });

    input.addEventListener('keydown', (k) => {
        if(k.key === 'Backspace' && !input.value && i > 0) {// ja current laucins ir tukss un eksiste ieprieksejais laucins
            inputs[i - 1].focus();
            inputs[i - 1].value = '';
        }
    });

    input.addEventListener('paste', (v) => {
        v.preventDefault(); // izsleds default pastosanas biznesu
        const pasted = v.clipboardData.getData('text').replace(/[^0-9]/g, '').slice(0,6); // dabuj no clipboarda tekstu, izdzes visu, kas nav cipars un saisina uz 6 cipariem
        pasted.split('').forEach((char, n) => { // sadala iepastoto tekstu pa chariem un ieliek laucinos
            if (inputs[n]) inputs[n].value = char;
        });
        inputs[Math.min(pasted.length, 5)].focus(); // parvieto kursoru uz pedejo aizpildito laucinu neparsniedzot pedejo
        btn.disabled = pasted.length < 6; // disablo pogu kamer nav ievadits pilns kods
    });
});

btn.addEventListener('click', async () => { // aizsuta join kodu caur url un user id caur body uz controlleri endpointam @Postm.. /gameroom/{joincode} joinGameRoom()..

     const code = [...inputs].map(n => n.value).join(''); // nolasa ievaditos ciparus un savieno kodu stringaa

     const response = await fetch(`/gameroom/${code}`, { // caur controlleri izsauc service joinGameRoom()
        method: 'POST', // post nozime, ka suta datus
        headers: { 'Content-Type' : 'application/json'}, // suta datus json forma, jo @RequestBody lasa json
        body: JSON.stringify({ userId : userId}) // partaisa par json stringu
     });

     const data = await response.json(); //

     if(response.ok) { // parbauda status code 200-299 true, 404 utt false
        window.location.href = `/gameroom/${code}`;  // ja viss ok tad ielaiz lietotaju game rooma
     } else {
        const error = document.getElementById("error-message");
        error.textContent = data.message;
        error.style.display = "block";
     }
});
