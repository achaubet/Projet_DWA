/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */
const hostname = window.location.hostname;
console.log(hostname);
const socket = new WebSocket("ws://" + hostname + ":8080/ProjetCulDeChouette/LobbyWS");
let firstUser;
let hasInvited = false;
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
        if(hasInvited) {
            console.log("Has Invited!!!");
            document.getElementById("user-list-confirmed-global").hidden = false;
            document.getElementById("start-party-btn").hidden = false;
            document.getElementById("score-max-input").hidden = false;
            document.getElementById("score-max-text").hidden = false;
            const sendInvitationsBtn = document.getElementById("send-invitations-btn");
            sendInvitationsBtn.setAttribute("hidden", "");
            const userList = $('#user-list-confirmed');
            userList.empty();
            message.users.forEach((user) => {
                const li = $("<li>").text(user).addClass("draggable");
                userList.append(li);
            });
            userList.sortable();
            userList.disableSelection();
        } else {
            console.log(message.type);
            console.log("leader name received");
            firstUser = message.leader;
            console.log(firstUser);
            if(message.leader === user.username) {
                document.getElementById("send-invitations-btn").hidden = false;
            } else {
                document.getElementById("send-invitations-btn").hidden = true;
            }
            const userList = $('#user-list');
            userList.empty();
            message.users = message.users.filter((player) => player !== user.username);
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
    }
    if(message.type === "invitation") {
        console.log("Invitation reçue !");
        let timer;
        swal.fire({
            title: 'Vous avez été invité ! (10s pour répondre !)',
            icon: 'question',
            showDenyButton: true,
            showCancelButton: false,
            allowOutsideClick: false,
            allowEscapeKey: false,
            confirmButtonText: 'Accepter',
            denyButtonText: `Refuser`
        }).then((reponseInvit) => {
            console.log(reponseInvit.value);
            clearTimeout(timer);
            console.log("reponse invite");
            switch(reponseInvit.value){
                case true:
                    console.log("ICI????");
                    swal.fire({
                            title: 'En attente',
                            html: 'En attente du début de la partie',
                            allowOutsideClick: false,
                            allowEscapeKey: false,
                            didOpen: () => {
                                swal.showLoading();
                            }
                    });
                    socket.send(JSON.stringify({
                        message: "responseInvitation", 
                        user: user.username, 
                        reponse: "yes"
                    }));
                    break;
                case false:
                    window.location.href = "PageAccueil.jsp";
                    socket.send(JSON.stringify({
                        message: "responseInvitation", 
                        user: user.username, 
                        reponse: "no"
                    }));
                    break;
                default:
                    window.location.href = "PageAccueil.jsp";
                    socket.send(JSON.stringify({
                        message: "responseInvitation", 
                        user: user.username, 
                        reponse: "no"
                    }));
                    break;
            }
        });

        timer = setTimeout(function() {
            swal.close();
            window.location.href = "PageAccueil.jsp";
            socket.send(JSON.stringify({
                message: "responseInvitation", 
                user: user.username, 
                reponse: "no"
            }));
        }, 10000);
    }
    if(message.type === "message") {
        console.log("message");
    }
    if(message.type === "redirectToGame") {
        console.log("CA REDIRECT TO GAME.JSP");
        window.location.href = "Game.jsp";
    }
    if(message.type === "redirectToHomepage") {
        window.location.href = "PageAccueil.jsp";
    }
});


const inviteButton = document.getElementById("send-invitations-btn");
inviteButton.addEventListener("click",(event) => {
    let playerSelected = [];
    let playerNotSelected = [];
    const allCheckboxes = document.querySelectorAll(".player-checkbox");
    allCheckboxes.forEach((checkbox) => {
        if(checkbox.checked) {
            console.log(checkbox);
            playerSelected.push(checkbox.id);
        } else {
            playerNotSelected.push(checkbox.id);
        }
    });
    console.log(playerSelected);
    // On supprime le nom du leader qui aura une autre place dans le JSON
    playerSelected = playerSelected.filter((player) => player !== user.username);
    console.log(playerSelected);
    if(playerSelected.length < 1){
        swal.fire({
            icon: 'error',
            title: 'Erreur',
            text: 'Veuillez selectionner au moins 1 autre joueur'
        });
    } else {
        socket.send(JSON.stringify({
            message: "inviteSelectedUsers",
            leader: user.username, 
            users: playerSelected,
            notSelected: playerNotSelected
        }));
        const Toast = Swal.mixin({
            toast: true,
            position: 'top-end',
            showConfirmButton: false,
            timer: 3000,
            timerProgressBar: true,
            didOpen: (toast) => {
              toast.addEventListener('mouseenter', Swal.stopTimer)
              toast.addEventListener('mouseleave', Swal.resumeTimer)
            }
          });

        Toast.fire({
           icon: 'success',
           title: 'Invitations envoyées'
        });
        hasInvited = true;

    }
  });

function updateUserList(){
    socket.send(JSON.stringify({message: "requestUpdateUserList"}));
}

// setInterval(updateUserList, 20000); // Set time to 20s otherwise the leader doesn't have the time to select the players 

