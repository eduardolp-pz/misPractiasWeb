package services;
import encapsulation.Comentario;
import util.BaseServiceDatabase;
import java.util.List;

public class ComentarioService extends BaseServiceDatabase<Comentario> {
  private static ComentarioService instance;

  public static ComentarioService getInstance() {
    if (instance == null) {
      instance = new ComentarioService();
    }
    return instance;
  }

  public ComentarioService() {
    super(Comentario.class);
  }

  public Comentario find(String comentarioId) {
    return this.dbFind(comentarioId);
  }

  public List<Comentario> findAll(){
    return this.dbFindAll();
  }

  public Comentario create(Comentario comentario){
    return this.dbCreate(comentario);
  }

  public Comentario modify(Comentario comentario){
    return this.dbModify(comentario);
  }

  public boolean delete(String comentarioId){
    return this.dbRemove(comentarioId);
  }
}