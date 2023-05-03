/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */
const socket = new WebSocket("ws://localhost:8080/ProjetCulDeChouette/LobbyWS");
               
// @OnOpen
socket.addEventListener("open", (event) => {
    socket.send(JSON.stringify(user));
});
        
// @OnMessage
socket.addEventListener("message", (event) => {
    const message = JSON.parse(event.data);
    console.log(message);
    if (message.type === "userList") {
        const userList = document.getElementById("user-list");
        userList.innerHTML = "";
    }
    if(message.type === "userList") {
        const userList = $('#user-list');
        userList.empty();
        message.users.forEach((user) => {
            const li = $('<li>');
            const checkbox = $('<input>').attr('type', 'checkbox').attr('id', user).addClass('mr-2 player-checkbox');
            const label = $('<label>').attr('for', user).text(user);
            li.append(checkbox, label);
            userList.append(li);
        });
        // userList.sortable();
        userList.disableSelection();
    }
    if(message.type === "userListConfirmed") {
        const userList = $('#user-list-confirmed');
        userList.empty();
        message.users.forEach((user) => {
            const li = $("<li>").text(user).addClass("draggable");
            userList.append(li);
        });
        userList.sortable();
        userList.disableSelection();
    }
    if(message.type === "invitation") {
        console.log("Inivité!");
        swal.fire({
            title: 'Vous avez été invité !',
            icon: 'question',
            showDenyButton: true,
            showCancelButton: true,
            confirmButtonText: 'Accepter',
            denyButtonText: `Refuser`
        });
    }
    if(message.type === "message") {
        console.log("message");
    }
    if(message.type === "redirectToGame") {
        const confirmed = confirm("Voulez-vous rejoindre la partie ?");
        if (confirmed) {
            console.log("CA REDIRECT TO GAME.JSP");
            window.location.href = "Game.jsp";
        }
    }
});


const inviteButton = document.getElementById("send-invitations-btn");
inviteButton.addEventListener("click",(event) => {
    console.log("COUCOU");
    let playerSelected = [];
    const allCheckboxes = document.querySelectorAll(".player-checkbox");
    allCheckboxes.forEach((checkbox) => {
        if(checkbox.checked) {
            //console.log(checkbox);
            playerSelected.push(checkbox.id);
        }
    });
    console.log(playerSelected);
    if(playerSelected.length < 2){
        swal.fire({
            icon: 'error',
            title: 'Erreur',
            text: 'Veuillez selectionner au moins 2 joueurs'
        });
    } else {
        socket.send(JSON.stringify({message: "inviteSelectedUsers", leader: user.username, users: playerSelected}));
        swal.fire({
            title: 'Attente',
            html: 'Attente de la réponse des joueurs',
            allowOutsideClick: false,
            allowEscapeKey: false,
            didOpen: () => {
                swal.showLoading();
            },
        }).then(() => {
            // débloquer la liste d'ordre des joueurs et débloquer bouton "Demarrer partie"
        });
    }
  });

function updateUserList(){
    socket.send(JSON.stringify({message: "requestUpdateUserList"}));
}

setInterval(updateUserList, 20000); // Set time to 20s otherwise the leader doesn't have the time to select the players 
