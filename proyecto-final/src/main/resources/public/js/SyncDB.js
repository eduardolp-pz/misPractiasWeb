let worker = new Worker('../js/Worker.js');
const db = new Dexie('myDb');
db.version(1).stores({
    form: '++id, name, educationLevel, sector, username, latitude, longitude'
});

$(document).ready(() =>{
    Offline.on('down', () => {
        $('#sync').prop('disabled', true);
    });

    Offline.on('up', () => {
        $('#sync').prop('disabled', false);
    });

    $('#sync').click(() => {
        db.form.each((form) => {
            worker.postMessage(form);
        });

        db.form.clear();
        alert('datos sincronizados')
    });
});
