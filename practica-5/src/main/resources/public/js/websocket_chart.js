let websocket_ventas;

const connectVentas = () => {
  websocket_ventas = new WebSocket("ws://" + location.hostname + ":" + location.port + "/ws-ventas");
  websocket_ventas.onmessage = (event) => {
    actualizarGrafico();
  };
}

$(document).ready(() => {
  console.log("Iniciando Jquery --> websocket_ventas.js");
  connectVentas();
});

const verificarConexionVentas = () => {
  if (!websocket_ventas || websocket_ventas.readyState === WebSocket.CLOSED) {
    connectVentas();
  }
}

setInterval(verificarConexionVentas, 5000);

window.onbeforeunload = function() {
  if (websocket_ventas) {
    websocket_ventas.close();
  }
};

const actualizarGrafico = () => {
  console.log("Actualizando gráfico de ventas")
  const page = window.location.href; // Obtén la URL de la página actual
  $.ajax({
    url: page,
    dataType: 'html',
    success: async function (d) {
      const contenido = $(d).find("#graficos");
      await getVentasData();
      $("#graficos").html(contenido.html());
    }
  });
};

document.addEventListener('DOMContentLoaded', async () => {
  await getVentasData();  // Cargar los datos iniciales al cargar la página
});
