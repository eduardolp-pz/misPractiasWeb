package controllers;

import encapsulation.Usuario;
import io.javalin.http.Context;
import org.jetbrains.annotations.NotNull;
import services.UsuarioServicio;

import java.util.List;
import java.util.Map;

public class UsuarioControlador {
  private final static UsuarioServicio usuarioServicio = UsuarioServicio.getInstance();


  public static void listarUsuarios(@NotNull Context ctx) throws Exception {
    List<Usuario> listaUsuarios = usuarioServicio.getListaUsuarios();
    Map<String, Object> listaUsuariosMap = Map.of("usuarios", listaUsuarios);
//    ctx.render();
  }

  public static void login(@NotNull Context ctx) throws Exception {
    ctx.render("/public/templates/login.html");
  }

  public static void esUsuarioAdmin(@NotNull Context ctx) throws Exception {
    Usuario usuario = ctx.sessionAttribute("usuario");
    if (usuario == null || !usuario.getIsAdmin()) {
      ctx.redirect("/");
    }
  }

  public static void checkLogin(@NotNull Context ctx) throws Exception {
    String username = ctx.formParam("usuario");
    String password = ctx.formParam("contrasena");
    Usuario usuario = usuarioServicio.login(username, password);

    if (usuario == null)
      ctx.redirect("/login");

    ctx.sessionAttribute("usuario", usuario);
    ctx.redirect("/comprar");
  }

  public static void logout(@NotNull Context ctx) throws Exception {
    ctx.sessionAttribute("usuario", null);
    ctx.redirect("/login");
  }
}