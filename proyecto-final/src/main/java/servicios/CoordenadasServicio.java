package servicios;

import entidades.Coordenadas;
import Util.MongoDb;

public class CoordenadasServicio extends MongoDb<Coordenadas> {
  private static CoordenadasServicio instancia;

  private CoordenadasServicio() {
    super(Coordenadas.class);
  }

  public static CoordenadasServicio getInstancia() {
    if (instancia == null) {
      instancia = new CoordenadasServicio();
    }
    return instancia;
  }

  public Coordenadas crear(String latitude, String longitude) {
    Coordenadas coordenadas = new Coordenadas();
    double lat = Double.parseDouble(latitude);
    double lon = Double.parseDouble(longitude);
    coordenadas.setLatitud(lat);
    coordenadas.setLongitud(lon);

    this.createDb(coordenadas);
    return coordenadas;
  }

}
