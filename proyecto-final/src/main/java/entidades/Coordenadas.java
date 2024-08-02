package entidades;

import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Id;
import dev.morphia.annotations.Reference;
import org.bson.types.ObjectId;

@Entity("coordenadas")
public class Coordenadas {
  @Id
  ObjectId id;
  private double latitud;
  private double longitud;

  public Coordenadas() {
  }

  public Coordenadas(double latitud, double longitud) {
    this.latitud = latitud;
    this.longitud = longitud;
  }

  public ObjectId getId() {
    return id;
  }

  public void setId(ObjectId id) {
    this.id = id;
  }

  public double getLatitud() {
    return latitud;
  }

  public void setLatitud(double latitud) {
    this.latitud = latitud;
  }

  public double getLongitud() {
    return longitud;
  }

  public void setLongitud(double longitud) {
    this.longitud = longitud;
  }
}
