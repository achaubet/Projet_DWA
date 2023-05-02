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
        message.users.forEach((user) => {
            const li = document.createElement("li");
                li.textContent = user;
                userList.appendChild(li);
            });
        }
        if(message.type === "message") {
           //console.log(message.);
        }
});

function updateUserList(){
    socket.send(JSON.stringify({message: "requestUpdateUserList"}));
}

setInterval(updateUserList, 5000);