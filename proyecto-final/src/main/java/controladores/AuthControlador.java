package controladores;

import entidades.Usuario;
import io.javalin.http.Context;
import org.jetbrains.annotations.NotNull;
import servicios.AuthServicio;
import servicios.UsuarioServicio;

public class AuthControlador {
  private final static AuthServicio authServicio = AuthServicio.getInstancia();
  private final static UsuarioServicio usuarioServicio = UsuarioServicio.getInstancia();

  public static void esAdmin(@NotNull Context ctx) {
    Usuario usuario = ctx.sessionAttribute("usuario");
    if (usuario != null)
      ctx.redirect("/");
  }

  public static void login(@NotNull Context ctx) {
    String usuarioEncryptado = ctx.cookie("usuario");
    if (usuarioEncryptado == null) {
      ctx.render("/public/templates/login.html");
      return;
    }

    String usuario = authServicio.decryptText(usuarioEncryptado);
    Usuario usuarioLogueado = usuarioServicio.findByUsername(usuario);
    ctx.sessionAttribute("user", usuarioLogueado);

    ctx.redirect("/");
  }

  public static void checkLogin(Context ctx) {
    String username = ctx.formParam("username");
    String password = ctx.formParam("password");
    boolean remember = ctx.formParam("remember") != null;
    Usuario usuario = usuarioServicio.findByUsername(username);

    if (usuario == null) {
      ctx.redirect("/auth/login");
      return;
    }

    if(!usuario.isActivo()){
      ctx.sessionAttribute("inactive_user", "Usuario inactivo, favor contactar al administrador.");
      ctx.redirect("/auth/login");
      return;
    }

    if(!usuarioServicio.checkPassword(username, password)) {
      ctx.redirect("/auth/login");
      return;
    }

    ctx.sessionAttribute("usuario", usuario);
    ctx.sessionAttribute("usuario", usuario.getUsuario());
    if(remember)
      ctx.cookie("user", authServicio.encryptText(usuario.getUsuario()), 604800);

    ctx.redirect("/");
  }

  public static void logout(Context ctx) {
    ctx.req().getSession().invalidate();
    ctx.removeCookie("usuario");
    ctx.redirect("/");
  }
}
