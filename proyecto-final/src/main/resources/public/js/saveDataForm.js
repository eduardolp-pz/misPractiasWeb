$(document).ready(() => {
    const db = new Dexie('myDb');
    db.version(1).stores({
      form: '++id, name, educationLevel, sector, username, latitude, longitude'
    });
  let editId = -1;

  $(document).on('submit', '#form', (e) => {
    e.preventDefault();
    const form = $(e.target).serializeArray();
    $(document).trigger('reset');
    if (form[4].name === 'idHidden') editId =  parseInt(form[4].value);

    if(editId !== -1) {
      db.form.update(editId, {
        name: form[0].value,
        educationLevel: form[2].value,
        sector: form[1].value,
        username: form[3].value,
      }).then(updated => {
        if (updated) {
          console.log('Actualizado');
        } else {
          console.log('No hayado');
        }
      }).catch(error => {
        console.error('Error al actualizar:', error);
      });
      editId = -1;
      alert('Actualizado!');
      return;
    }


    getPosition().then((position) => {
      console.log('Position: ', position);
      db.form.add({
        name: form[0].value,
        educationLevel: form[1].value,
        sector: form[2].value,
        username: localStorage.getItem('username'),
        latitude: position[0],
        longitude: position[1],
      });
    }).then(() => {
      alert('Guaradado!')
    });
  });
});



const getPosition = () =>{
  return new Promise((resolve, reject) => {
    console.log('Obteniendo posicion');
    if(!navigator.geolocation) {
      reject('No disponible.');
    }
    navigator.geolocation.getCurrentPosition(
      (position) => {
        console.log('posicion: ', position);
        const {latitude, longitude} = position.coords;
        resolve([latitude, longitude]);
      },
      (error) => {
        if (error.code !== error.PERMISSION_DENIED) {
          reject('Error');
          return [];
        }
        reject('Permisos denegados');
      }
    );
  });
}