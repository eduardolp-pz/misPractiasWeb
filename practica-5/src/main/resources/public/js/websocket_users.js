let websocket_users;
let tiempoReconexion = 5000;

const connectUsers = () => {
  websocket_users = new WebSocket("ws://" + location.hostname + ":" + location.port + "/ws-usuario");
  websocket_users.onmessage = (data) => { recibirInformacionServidorUsers(data); }
}

$(document).ready(() =>{
  console.log("Iniciando Jquery --> websocket_users.js");
  connectUsers();
});

const verificarConexioUsers = () => {
  if (!websocket_users || websocket_users.readyState === WebSocket.CLOSED)
    connectUsers();
}

const recibirInformacionServidorUsers = (mensaje) => {
  console.log(mensaje);
  $("#nav_total_user").text('Usuarios (' + mensaje.data + ')');
}

setInterval(verificarConexioUsers, tiempoReconexion);

window.onbeforeunload = function() {
  if (websocket_users)
    websocket_users.close();

};
