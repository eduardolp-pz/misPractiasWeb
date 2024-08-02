let webSocket;
let tiempoReconexion = 1000;

self.onmessage = (e) => {
  let form = e.data;
  webSocket.send(JSON.stringify(form));
};

const checkConnection = () => {
  if (!webSocket || webSocket.readyState === WebSocket.CLOSED) {
    connect();
  }
}

const connect = () => {
  webSocket = new WebSocket("ws://" + location.hostname + ":" + location.port + "/ws" );
  webSocket.onclose = () => {
    setTimeout(() => {
      connect();
    }, tiempoReconexion);
  }

  webSocket.onmessage = (e) => {
    let form = e.data;
    webSocket.send(JSON.stringify(form));
  }
}

connect();
setInterval(checkConnection, tiempoReconexion);