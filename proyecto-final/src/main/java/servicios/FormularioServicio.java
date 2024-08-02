package servicios;
import Util.MongoDb;
import dev.morphia.query.filters.Filters;
import entidades.Coordenadas;
import entidades.Formulario;
import entidades.Usuario;

import java.util.ArrayList;
import java.util.List;

public class FormularioServicio extends MongoDb<Formulario>  {
  private static FormularioServicio instancia;

  private FormularioServicio() {
    super(Formulario.class);
  }

  public static FormularioServicio getInstancia() {
    if (instancia == null) {
      instancia = new FormularioServicio();
    }
    return instancia;
  }

  public Formulario crear(String nombre, String sector, String nivelEducativo, Usuario usuario, Coordenadas coordenadas) {
    Formulario formulario = new Formulario();
    formulario.setNombre(nombre);
    formulario.setSector(sector);
    formulario.setNivelEducacion(nivelEducativo);
    formulario.setUsuario(usuario);
    formulario.setCoordenadas(coordenadas);

    this.createDb(formulario);
    return formulario;
  }

  public Formulario modificar(String id, String nombre, String sector, String nivelEducativo, Usuario usuario) {
    Formulario formulario = this.encontrar(id);
    formulario.setNombre(nombre);
    formulario.setSector(sector);
    formulario.setNivelEducacion(nivelEducativo);
    formulario.setUsuario(usuario);

    this.updateDb(formulario);
    return formulario;
  }

  public List<Formulario> listar() {
    return this.findAllDb();
  }

  public Formulario encontrar(String id) {
    return this.findDbByID(id);
  }

  public List<Formulario> encontrarPorUsuario(Usuario usuario) {
    return this.findAllDb().stream().filter(formulario -> formulario.getUsuario().getUsuario().equals(usuario.getUsuario())).toList();
  }
}
