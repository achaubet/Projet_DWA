/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */
const path = window.location.pathname;
const pathParts = path.split('/');
const directory = pathParts[1];
const ws = new WebSocket("ws://" + window.location.hostname + ":8080/"+ directory +"/CulDeChouetteWS");
const grelotteBtn = document.getElementById("grelotte-picotte-btn");
const caillouBtn = document.getElementById("pas-mou-caillou-btn");

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
            document.getElementById("roll-chouette-btn").hidden = false;
        } else {
            document.getElementById("roll-chouette-btn").hidden = true;
        }
    }
    if(message.type === "diceChouetteResult") {
        console.log("Recepetion des resultats de la Chouette");
        document.getElementById("chouette-number").innerHTML = "Chouette: " +  message.dice1 + "   et   " + message.dice2;
        // document.getElementById("dice-number2").innerHTML = message.dice2;
        if(actualPlayer === user.username) {
            document.getElementById("roll-chouette-btn").hidden = true;
            document.getElementById("roll-cul-btn").hidden = false;
        }
    }
    if(message.type === "diceCulResult") {
        console.log("Recepetion des resultats du Cul");
        document.getElementById("cul-number").innerHTML = "Cul: " +  message.dice;
        document.getElementById("roll-cul-btn").hidden = true;
    }
    if(message.type === "activateSuiteButton") {
        grelotteBtn.classList.remove('bg-red-500');
        grelotteBtn.classList.remove('hover:bg-red-700');
        grelotteBtn.classList.add('bg-blue-500');
        grelotteBtn.classList.add('hover:bg-blue-700');
        grelotteBtn.addEventListener("click", grelotteClickHandler);   
    }
    if(message.type === "activateChouetteVelute") {
        caillouBtn.classList.remove('bg-red-500');
        caillouBtn.classList.remove('hover:bg-red-700');
        caillouBtn.classList.add('bg-blue-500');
        caillouBtn.classList.add('hover:bg-blue-700');
        caillouBtn.addEventListener("click", caillouClickHandler);
    }
    if(message.type === "spectatorMode") {
        document.getElementById("roll-chouette-btn").hidden = true;
        document.getElementById("roll-cul-btn").hidden = true;
        grelotteBtn.hidden = true;
        caillouBtn.hidden = true;    
        grelotteBtn.classList.remove('bg-blue-500');
        grelotteBtn.classList.remove('hover:bg-blue-700');
        caillouBtn.classList.remove('bg-blue-500');
        caillouBtn.classList.remove('hover:bg-blue-700');
    }
    if(message.type === "endGame") {
        console.log("Partie terminée !");
        swal.fire({
            title: 'La partie est terminée !',
            icon: 'info',
            showDenyButton: false,
            showCancelButton: false,
            allowOutsideClick: false,
            allowEscapeKey: false,
            confirmButtonText: 'Ok'
        }).then(() => {
            window.location.href = "PageAccueil.jsp";
        });
    }
    if(message.type === "userHasDisconnected") {
        swal.fire({
            title: 'Un joueur s\'est déconnecté de la partie !',
            icon: 'error',
            showDenyButton: false,
            showCancelButton: false,
            allowOutsideClick: false,
            allowEscapeKey: false,
            confirmButtonText: 'Ok'
        }).then(() => {
            window.location.href = "PageAccueil.jsp";
        });
    }    
    if(message.type === "serverError") {
        swal.fire({
            title: 'Une erreur interne s\'est produite !',
            icon: 'error',
            showDenyButton: false,
            showCancelButton: false,
            allowOutsideClick: false,
            allowEscapeKey: false,
            confirmButtonText: 'Ok'
        }).then(() => {
            window.location.href = "PageAccueil.jsp";
        });
    }
    
});

const grelotteClickHandler = () => {
    ws.send(JSON.stringify({message: "interactionSuite", player: user.username}));
    grelotteBtn.removeEventListener("click", grelotteClickHandler);
    grelotteBtn.classList.remove('bg-blue-500');
    grelotteBtn.classList.remove('hover:bg-blue-700');
    grelotteBtn.classList.add('bg-red-500');
    grelotteBtn.classList.add('hover:bg-red-700');
};

const caillouClickHandler = () => {
    ws.send(JSON.stringify({message: "interactionChouetteVelute", player: user.username}));
    caillouBtn.removeEventListener("click", caillouClickHandler);
    caillouBtn.classList.remove('bg-blue-500');
    caillouBtn.classList.remove('hover:bg-blue-700');
    caillouBtn.classList.add('bg-red-500');
    caillouBtn.classList.add('hover:bg-red-700');
};


document.getElementById("roll-chouette-btn").addEventListener("click", () => {
    ws.send(JSON.stringify({message: "rollDiceChouette", player: user.username}));
});

document.getElementById("roll-cul-btn").addEventListener("click", () => {
    ws.send(JSON.stringify({message: "rollDiceCul", player: user.username}));
});

