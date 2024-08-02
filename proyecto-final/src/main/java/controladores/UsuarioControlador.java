package controladores;

import entidades.Usuario;
import io.javalin.http.Context;
import io.smallrye.common.constraint.NotNull;
import servicios.UsuarioServicio;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UsuarioControlador {
  private final static UsuarioServicio usuarioServicio = UsuarioServicio.getInstancia();

  public static void esAdmin(@NotNull Context ctx) {
    Usuario usuario = ctx.sessionAttribute("usuario");
    if (usuario == null || !usuario.isAdministrador())
      ctx.redirect("/auth/login");
  }

  public static void crear(@NotNull Context ctx) {
    String usuario = ctx.formParam("username");
    if (usuarioServicio.findByUsername(usuario) != null)
      return;

    String nombre = ctx.formParam("name");
    String password = ctx.formParam("password");
    boolean administrador = ctx.formParam("admin") != null;
    boolean encuestador = ctx.formParam("pollster") != null;

    usuarioServicio.crear(usuario, nombre, password, administrador, encuestador);
    ctx.redirect("/users/" + usuario);
  }

  public static void eliminar(@NotNull Context ctx) {
    String usuario = ctx.pathParam("username");
    Usuario usuarioEnSesion = ctx.sessionAttribute("user");

    assert usuarioEnSesion != null;
    if(usuarioEnSesion.getUsuario().equals(usuario)) {
      ctx.redirect("/users");
      return;
    }

    usuarioServicio.desactivar(usuario);
    ctx.redirect("/users");
  }

  public static void listar(@NotNull Context ctx) {
    String usuario_autoDelete = ctx.sessionAttribute("usuario_autoDelete");
    ctx.sessionAttribute("usuario_autoDelete", null);
    List<Usuario> users = usuarioServicio.findAllDb();

    Map<String, Object> modelo = new HashMap<>();
    modelo.put("users", users);

    ctx.render("/public/templates/users.html", modelo);
  }

  public static void listarUno(@NotNull Context ctx) {
    String username = ctx.pathParam("username");
    Usuario usuario = usuarioServicio.findByUsername(username);

    Map<String, Object> modelo = new HashMap<>();
    modelo.put("user", usuario);

    ctx.render("/public/templates/showUser.html", modelo);
  }

  public static void editar(@NotNull Context ctx) {
    String usuario = ctx.pathParam("username");
    String nombre = ctx.formParam("name");
    String password = ctx.formParam("password");
    boolean administrador = ctx.formParam("admin") != null;
    boolean encuestador = ctx.formParam("pollster") != null;

    usuarioServicio.modificar(usuario, nombre, password, administrador, encuestador);
    ctx.redirect("/users/" + usuario);
  }
}
