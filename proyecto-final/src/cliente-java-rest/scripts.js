document.getElementById('loginForm').addEventListener('submit', login);

function login(event) {
  event.preventDefault();

  const loginData = {
    username: document.getElementById('loginUsername').value,
    password: document.getElementById('loginPassword').value
  };

  fetch('http://localhost:3000/rest/login', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify(loginData)
  })
    .then(response => response.json())
    .then(data => {
      if (data.token) {
        document.getElementById('jwtToken').value = data.token;
        alert('Login successful!');
      } else {
        alert('Login failed!');
      }
    })
    .catch(error => {
      console.error('Error:', error);
    });
}

document.getElementById('createForm').addEventListener('submit', createForm);

function createForm(event) {
  event.preventDefault();

  const formData = {
    username: document.getElementById('username').value,
    name: document.getElementById('name').value,
    sector: document.getElementById('sector').value,
    educationLevel: document.getElementById('educationLevel').value,
    latitude: document.getElementById('latitude').value,
    longitude: document.getElementById('longitude').value
  };

  const token = document.getElementById('jwtToken').value;

  fetch('http://localhost:3000/rest/createForm', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${token}`
    },
    body: JSON.stringify(formData)
  })
    .then(response => response.json())
    .then(data => {
      alert('Form created successfully!');
      console.log(data);
    })
    .catch(error => {
      console.error('Error:', error);
    });
}

function getForms() {
  const username = document.getElementById('searchUsername').value;
  const token = document.getElementById('jwtToken').value;

  fetch(`http://localhost:3000/rest/getForms/${username}`, {
    method: 'GET',
    headers: {
      'Authorization': `Bearer ${token}`
    }
  })
    .then(response => response.json())
    .then(data => {
      console.log(data);
      const formsList = document.getElementById('formsList');
      formsList.innerHTML = '';

      data.forms.forEach(form => {
        const li = document.createElement('li');
        li.textContent = `Name: ${form.name}, Sector: ${form.sector}, Education Level: ${form.educationLevel}, Coordinates: (${form.coordinates.latitude}, ${form.coordinates.longitude})`;
        formsList.appendChild(li);
      });
    })
    .catch(error => {
      console.error('Error:', error);
    });
}

function getLocation() {
  if (navigator.geolocation) {
    navigator.geolocation.getCurrentPosition(showPosition, showError);
  } else {
    alert("Geolocation is not supported by this browser.");
  }
}

function showPosition(position) {
  document.getElementById('latitude').value = position.coords.latitude;
  document.getElementById('longitude').value = position.coords.longitude;
}

function showError(error) {
  switch(error.code) {
    case error.PERMISSION_DENIED:
      alert("User denied the request for Geolocation.");
      break;
    case error.POSITION_UNAVAILABLE:
      alert("Location information is unavailable.");
      break;
    case error.TIMEOUT:
      alert("The request to get user location timed out.");
      break;
    case error.UNKNOWN_ERROR:
      alert("An unknown error occurred.");
      break;
  }
}

getLocation();