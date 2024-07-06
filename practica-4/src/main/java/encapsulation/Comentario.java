package encapsulation;
import jakarta.persistence.*;
import java.io.Serializable;
@Entity
public class Comentario implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;
  private String comentario;
  @ManyToOne
  private Usuario autor;
  @ManyToOne
  private Producto producto;

  public Comentario() {
  }

  public Comentario(String comentario, Usuario autor, Producto producto) {
    this.comentario = comentario;
    this.autor = autor;
    this.producto = producto;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getComentario() {
    return comentario;
  }

  public void setComentario(String comentario) {
    this.comentario = comentario;
  }

  public Usuario getAutor() {
    return autor;
  }

  public void setAutor(Usuario autor) {
    this.autor = autor;
  }

  public Producto getProducto() {
    return producto;
  }

  public void setProducto(Producto producto) {
    this.producto = producto;
  }
}
