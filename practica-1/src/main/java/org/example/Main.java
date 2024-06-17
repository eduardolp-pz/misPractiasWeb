package org.example;

import controllers.*;
import io.javalin.Javalin;
import io.javalin.http.staticfiles.Location;
import io.javalin.rendering.template.JavalinThymeleaf;

import static io.javalin.apibuilder.ApiBuilder.*;

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

        //1.
        path("/productos", () -> {
          before(UsuarioControlador::esUsuarioAdmin);
          get(ProductoControlador::listarProductosAdmin);
          post(ProductoControlador::agregarProducto);

          path("/eliminar/{id}", () -> {
            post(ProductoControlador::eliminarProducto);
          });

          path("/editar/{id}", () -> {
            get(ProductoControlador::listarProductosEditar);
            post(ProductoControlador::editarProducto);
          });

        });

        path("/comprar", () -> {
          get(ProductoControlador::listarProductos);
          post(VentaProductosControlador::comprarProductos);
          path("/agregar-al-carrito/{id}", () -> {
            post(CarroCompraControlador::agregarProductoAlCarro);
          });
        });

        path("/carrito", () -> {
          get(CarroCompraControlador::listarCarroCompra);
          path("/eliminar-unidad/{name}", () -> {
            post(CarroCompraControlador::eliminarProductoDelCarro);
          });
          path("/eliminar-todo", () -> {
            post(CarroCompraControlador::eliminarTodoDelCarro);
          });
        });

        path("/ventas", () -> {
          before(UsuarioControlador::esUsuarioAdmin);
          get(VentaProductosControlador::listarVentasProductos);
        });



        path("/login", () -> {
          get(UsuarioControlador::login);
          post(UsuarioControlador::checkLogin);
        });
        path("/logout", () -> {
          get(UsuarioControlador::logout);
        });
      });

    }).start(7070);

    app.before(ctx -> {
      CarroCompraControlador.setearCarroCompraSesion(ctx);
      return;
    });
    app.get("/", ctx -> {
      ctx.render("/public/templates/index.html");
    });
  }

}