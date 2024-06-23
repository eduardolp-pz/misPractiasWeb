package org.example;
//
//Asignación en Aula ICC-352
//
//
//
//Crear una aplicación Web que cumpla los siguientes criterios:
//
//
//
//  1) Un filtro debe interceptar las peticiones y si el usuario no está logueado debe debe mostrar un formulario con los campos de usuario y contraseña.
//
//2) Un proceso para recibir validar el formulario y si cumple con la autentifación redireccionar a la ruta (/) mostrando un mensaje de inicio.
//
//  3) Incluir de css en la pagina de inicio y formulario.
//
//
//
//Subir el repositorio de los realizado y documento de imagenes del proyecto.

import controller.UsuarioControlador;
import io.javalin.Javalin;
import io.javalin.http.staticfiles.Location;
import io.javalin.rendering.template.JavalinThymeleaf;

import static io.javalin.apibuilder.ApiBuilder.*;
import static io.javalin.apibuilder.ApiBuilder.get;

public class Main {
  public static void main(String[] args) {
    System.out.println("Iniciando aplicación de Javalin 6");

    Javalin app = Javalin.create(config -> {
      config.staticFiles.add(staticFileConfig -> {
        staticFileConfig.hostedPath = "/";
        staticFileConfig.directory = "/public";
        staticFileConfig.location = Location.CLASSPATH;
        staticFileConfig.precompress=false;
        staticFileConfig.aliasCheck=null;
      });
      config.fileRenderer(new JavalinThymeleaf());

      config.router.apiBuilder(() -> {
        path("/", () -> {
          get(ctx -> ctx.redirect("/inicio"));
        });

        path("/inicio", () -> {
          before(UsuarioControlador::isLogged);
          get(ctx -> ctx.render("/public/templates/index.html"));
        });

        path("/login", () -> {
          before(UsuarioControlador::isNotLogged);
          get(UsuarioControlador::login);
          post(UsuarioControlador::checkLogin);
        });

        path("/logout", () -> {
          get(UsuarioControlador::logout);
        });
      });

    }).start(7070);

  }
}