if ('serviceWorker' in navigator) {
  window.addEventListener('load', function() {
    navigator.serviceWorker.register('/serviceWorker.js', { scope: '/' }).then(function(registration) {
      console.log('Registrando el servicio en el ambiente: ', registration.scope);
    }, function(err) {
      console.log('Service Worker falló el registro: ', err);
    });
  });
}