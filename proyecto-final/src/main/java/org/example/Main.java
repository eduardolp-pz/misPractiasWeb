package org.example;

import io.javalin.Javalin;
import io.javalin.http.staticfiles.Location;
import io.javalin.rendering.template.JavalinThymeleaf;
import controladores.*;

import static io.javalin.apibuilder.ApiBuilder.*;

public class Main {
  public static void main(String[] args) {
    System.out.println("Hello world!");

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
          get(FormularioControlador::vistaInicial);
        });

        path("/dashboard", () -> {
          before(FormularioControlador::esAdmin);
          get(FormularioControlador::vistaFormulario);
        });

        path("/dashboardOffline", () -> {
          before(FormularioControlador::esAdmin);
          get(FormularioControlador::dashboardOffline);
        });

        path("/form", () -> {
          before(FormularioControlador::esAdmin);
          get(FormularioControlador::vistaFormulario);
          post(FormularioControlador::postFormulario);

          path("/offline", () -> {
            before(FormularioControlador::esAdmin);
            get(FormularioControlador::formulariosOffline);
          });

          path("/{id}", () -> {
            before(FormularioControlador::esAdmin);
            get(FormularioControlador::mostrarFormulario);
            post(FormularioControlador::editarFormulario);
          });
        });

        path("/ws", () -> {
          ws(ws -> ws.onMessage(FormularioControlador::onMessage));
        });

        path("/auth", () -> {
          path("/login", () -> {
            before(AuthControlador::esAdmin);
            get(AuthControlador::login);
            post(AuthControlador::checkLogin);
          });
          path("/logout", () -> {
            get(AuthControlador::logout);
          });
        });

        path("/users", () -> {
          before("/", UsuarioControlador::esAdmin);
          get("/", UsuarioControlador::listar);
          post("/", UsuarioControlador::crear);

          path("/edit/{username}", () -> {
            post(UsuarioControlador::editar);
          });

          path("/{username}", () -> {
            before(UsuarioControlador::esAdmin);
            get(UsuarioControlador::listarUno);
            delete(UsuarioControlador::eliminar);
          });

          path("/{id}", () -> {
            put(UsuarioControlador::editar);
          });
        });

        path("/rest", () -> {
          path("/getForms/{username}", () -> {
            get(FormularioControlador::getUserForms);
          });

          path("/createForm", () -> {
            post(FormularioControlador::createNewForm);
          });

          path("/login", () -> {
            post(FormularioControlador::restLogin);
          });
        });

      });
    }).start(7070);
  }
}