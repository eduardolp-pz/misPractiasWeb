let websocket_comments;

const connectComments = () => {
  websocket_comments = new WebSocket("ws://" + location.hostname + ":" + location.port + "/ws-comentario");
  websocket_comments.onmessage = (event) => {
    actualizarComentarios();
  };

}

$(document).ready(() => {
  console.log("Iniciando Jquery --> websocket_comments.js");
  connectComments();

  $(document).on('click', '.btn-danger', function(e) {
    e.preventDefault();
    const url = $(this).attr('href');
    $.ajax({
      url: url,
      type: 'GET',
      success: function(result) {
        websocket_comments.send(url);
      }
    });
  });
});

const verificarConexionComments = () => {
  if (!websocket_comments || websocket_comments.readyState === WebSocket.CLOSED) {
    connectComments();
  }
}

const recibirInformacionServidor = (mensaje) => {
  console.log(mensaje);
  actualizarComentarios();
}

const actualizarComentarios = () => {
  const page = window.location.href; // Obtén la URL de la página actual
  $.ajax({
    url: page,
    dataType: 'html',
    success: function (d) {
      const contenido = $(d).find("#comentarios");
      $("#comentarios").html(contenido.html());
    }
  });
}

setInterval(verificarConexionComments, tiempoReconexion);

window.onbeforeunload = function() {
  if (websocket_comments) {
    websocket_comments.close();
  }
};