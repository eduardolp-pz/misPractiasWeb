const getVentasData = async () => {
  try {
    const response = await axios.get('/ventas/graficosSender/');
    const ventasData = response.data;
    console.log(ventasData);

    const totalVentas = Object.values(ventasData).reduce((acc, value) => acc + value, 0);
    document.getElementById('total-ventas').textContent = totalVentas;

    const chartData = {
      labels: Object.keys(ventasData),
      datasets: [{
        data: Object.values(ventasData),
        backgroundColor: getBackgroundColors(Object.keys(ventasData).length),
        borderColor: getBorderColors(Object.keys(ventasData).length),
        borderWidth: 1
      }]
    };

    const ctx = document.getElementById('productosChart').getContext('2d');
    const config = {
      type: 'pie',
      data: chartData,
      options: {
        responsive: true
      }
    };
    new Chart(ctx, config);
  } catch (error) {
    console.error('Error al cargar los datos:', error);
  }
};

const getBackgroundColors = (numColors) => {
  const colors = [];
  for (let i = 0; i < numColors; i++) {
    const color = `rgba(${Math.floor(Math.random() * 256)}, ${Math.floor(Math.random() * 256)}, ${Math.floor(Math.random() * 256)}, 0.6)`;
    colors.push(color);
  }
  return colors;
};

const getBorderColors = (numColors) => {
  const colors = [];
  for (let i = 0; i < numColors; i++) {
    const backgroundColor = `rgba(${Math.floor(Math.random() * 256)}, ${Math.floor(Math.random() * 256)}, ${Math.floor(Math.random() * 256)}, 0.6)`;
    // Convertir el color de fondo a un color ligeramente más oscuro para el borde
    const borderColor = backgroundColor.replace(/[^,]+(?=\))/, '0.6)');
    colors.push(borderColor);
  }
  return colors;
};

document.addEventListener('DOMContentLoaded', getVentasData);