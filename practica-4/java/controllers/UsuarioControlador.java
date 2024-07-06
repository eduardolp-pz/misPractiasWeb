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
    Usuario usuario = new Usuario("admin", "Administrador", "admin", true);
    Usuario adminExiste = usuarioServicio.getUsuario("admin");
    if (adminExiste == null) usuarioServicio.dbCreate(usuario);
    Usuario usuario2 = new Usuario("usuario", "Usuario", "usuario", false);
    Usuario usuarioExiste = usuarioServicio.getUsuario("usuario");
    if (usuarioExiste == null) usuarioServicio.dbCreate(usuario2);


    String usuarioEncryptado = ctx.cookie("usuario");
    System.out.println(usuarioEncryptado);
    if (usuarioEncryptado == null) {
      ctx.render("/public/templates/login.html");
      return;
    }

    String username = usuarioServicio.decryptText(usuarioEncryptado);
    System.out.println(username);
    Usuario usuarioEnCookie = usuarioServicio.getUsuario(username);
    ctx.sessionAttribute("usuario", usuarioEnCookie);

    ctx.redirect("/comprar");
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
    boolean remember = ctx.formParam("remember") != null;
    System.out.println(remember);
    Usuario usuario = usuarioServicio.login(username, password);

    if (usuario == null){
      ctx.redirect("/login");
      return;
    }

    ctx.sessionAttribute("usuario", usuario);
    if(remember)
      ctx.cookie("usuario", usuarioServicio.encryptText(usuario.getUsuario()), 604800);


    ctx.redirect("/comprar");
  }

  public static void logout(@NotNull Context ctx) throws Exception {
    ctx.sessionAttribute("usuario", null);
    ctx.removeCookie("usuario");
    ctx.redirect("/login");
  }
}