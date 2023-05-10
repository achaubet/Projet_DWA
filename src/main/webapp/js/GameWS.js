/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */

const ws = new WebSocket("ws://" + window.location.hostname + ":8080/ProjetCulDeChouette/CulDeChouetteWS");
let actualPlayer;
// @OnOpen
ws.addEventListener("open", (event) => {
    ws.send(JSON.stringify(user));
});

// @OnMessage
ws.addEventListener("message", (event) => {
    const message = JSON.parse(event.data);
    console.log(message);
    if(message.type === "diceRoll") {
        const diceNumber = message.data;
        document.getElementById("dice-number").innerText = diceNumber;
    }
    if(message.type === "scoreboard") {
        console.log("Scoreboard reçu");
        const scoreboardList = document.getElementById('scoreboard-list');
        scoreboardList.innerHTML = '';
        for(const playerScore of message.playersScore) {
            const player = Object.keys(playerScore)[0];
            const score = playerScore[player];
            const li = document.createElement('li');
            li.textContent = `${player}: ${score}`;
            scoreboardList.appendChild(li);
        }
    }
    if(message.type === "actualPlayer") {
        console.log("Player actuel reçu");
        actualPlayer = message.player;
        console.log("Joueur actuel: " + actualPlayer);
        document.getElementById('actualPlayer').innerHTML = actualPlayer;
        if(actualPlayer === user.username) {
            document.getElementById("rool-dice-btn").hidden = false;
        } else {
            document.getElementById("rool-dice-btn").hidden = true;
        }
    }
});

document.getElementById("roll-dice-btn").addEventListener("click", () => {
    // Send a request to roll the dice
    ws.send(JSON.stringify({type: "roll_dice", player: user.username}));
});

