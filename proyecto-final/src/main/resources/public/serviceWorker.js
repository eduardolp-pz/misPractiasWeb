const CACHE_NAME = 'offline-app-v1';
const fallback = "/templates/503.html"
const urlsToCache = [
  "/formOffline",
  "/dashboardOffline",
  "/templates/formOffline.html",
  "/templates/dashboardOffline.html",
  "/templates/503.html",
  "../css/styles.css",
  "../js/scripts.js",
  "../js/offline.js",
  "../js/offlineText.js",
  "../css/offline.js.css",
  "../js/jquery-3.2.1.min.js",
  "../js/SyncDB.js",
  "../js/saveDataForm.js",
  "../js/location.js",
  "../js/dexie.js",
  "https://use.fontawesome.com/releases/v6.3.0/js/all.js",
  "https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js",
  "https://cdn.jsdelivr.net/npm/axios/dist/axios.min.js",
  "https://cdn.jsdelivr.net/npm/leaflet/dist/leaflet.js",
  "https://cdn.jsdelivr.net/npm/leaflet/dist/leaflet.css",
  "https://unpkg.com/leaflet/dist/leaflet.css",
  "https://unpkg.com/dexie/dist/dexie.js",
  "https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"
];

self.addEventListener('install', function (event) {
  event.waitUntil(
    caches.open(CACHE_NAME)
      .then(function (cache) {
        console.log('Cache abierto');
        return cache.addAll(urlsToCache);
      })
  );
});

self.addEventListener('activate', evt => {
  evt.waitUntil(
    caches.keys().then(function (keyList) {
      return Promise.all(keyList.map(function (key) {
        if (CACHE_NAME.indexOf(key) === -1) {
          return caches.delete(key);
        }
      }));
    })
  );
});

self.addEventListener('fetch', event => {
  event.respondWith(
    caches.match(event.request).then(response => {
      return response || fetch(event.request);
    }).catch(function () {
      return caches.match(fallback);
    })
  );
});

