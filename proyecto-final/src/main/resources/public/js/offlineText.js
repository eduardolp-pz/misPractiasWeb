Offline.on('down', function () {
  let offlineMessage = document.querySelector('.offline-ui-content');
  offlineMessage.textContent = 'No hay conexión a internet';
});

Offline.on('reconnect:connecting', function () {
  let offlineMessage = document.querySelector('.offline-ui-content');
  offlineMessage.textContent = 'Intentado reconectar...';
});


Offline.on('up', function () {
  let offlineMessage = document.querySelector('.offline-ui-content');
  offlineMessage.textContent = 'Conexión a internet restablecida';
});

document.addEventListener('DOMContentLoaded', function () {
  let retryButton = document.querySelector('.offline-ui-retry');
  if (retryButton) {
    retryButton.textContent = 'Reintentar conexión';
  }
});