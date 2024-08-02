// Obtener la ubicación del cliente
navigator.geolocation.getCurrentPosition((position) => {
  let latitud = position.coords.latitude;
  let longitud = position.coords.longitude;
  document.getElementById('latitude').value = latitud;
  document.getElementById('longitude').value = longitud;
}, (error) => {
  console.error('Error al obtener la ubicación del cliente:', error.message);
});