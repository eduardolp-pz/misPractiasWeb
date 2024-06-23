package controller;

import entity.Usuario;
import io.javalin.http.Context;
import org.jetbrains.annotations.NotNull;
import services.UsuarioServicio;

public class UsuarioControlador {
  private final static UsuarioServicio usuarioServicio = UsuarioServicio.getInstance();

  public static void login(@NotNull Context ctx) throws Exception {
    ctx.render("/public/templates/login.html");
  }

  public static void checkLogin(@NotNull Context ctx) throws Exception {
    String username = ctx.formParam("username");
    String password = ctx.formParam("password");
    Usuario usuario = usuarioServicio.login(username, password);

    if (usuario == null)
      ctx.redirect("/login");

    ctx.sessionAttribute("usuario", usuario);
    ctx.redirect("/inicio");
  }

  public static void isLogged(@NotNull Context ctx) throws Exception {
    Usuario usuario = ctx.sessionAttribute("usuario");
    if (usuario == null) {
      ctx.redirect("/login");
    }
  }

  public static void isNotLogged(@NotNull Context ctx) throws Exception {
    Usuario usuario = ctx.sessionAttribute("usuario");
    if (usuario != null) {
      ctx.redirect("/inicio");
    }
  }

  public static void logout(@NotNull Context ctx) throws Exception {
    ctx.sessionAttribute("usuario", null);
    ctx.redirect("/login");
  }
}
