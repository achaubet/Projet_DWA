/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */
const hostname = window.location.hostname;
console.log(hostname);
const ws = new WebSocket("ws://" + hostname + ":8080/ProjetCulDeChouette/CulDeChouetteWS");

// @OnOpen
ws.addEventListener("open", (event) => {
    ws.send(JSON.stringify(user));
});

