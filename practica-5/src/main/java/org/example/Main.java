package org.example;

import controllers.*;
import encapsulation.Usuario;
import io.javalin.Javalin;
import io.javalin.http.staticfiles.Location;
import io.javalin.rendering.template.JavalinThymeleaf;
import org.eclipse.jetty.websocket.api.Session;
import services.BootstrapService;
import services.UsuarioServicio;

import java.util.ArrayList;
import java.util.List;

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

        path("/", () -> {
          get(BaseController::getHome);
        });

        path("/productos", () -> {
          get(ProductoControlador::listarProductosAdmin);
          post(ProductoControlador::agregarProducto);

          path("/eliminar/{id}", () -> {
            before(UsuarioControlador::esUsuarioAdmin);
            post(ProductoControlador::eliminarProducto);
          });

          path("/editar/{id}", () -> {
            before(UsuarioControlador::esUsuarioAdmin);
            get(ProductoControlador::listarProductosEditar);
            post(ProductoControlador::editarProducto);
          });

          path("/visualizar/{id}", () -> {
            get(ProductoControlador::visualizarProducto);
          });

          path("/{id}/comentario", () ->{
            post(ProductoControlador::agregarComentario);
          });

          path("/{id}/comentario/{comentarioId}/eliminar", () -> {
            get(ProductoControlador::eliminarComentario);
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

          path("/graficos", () -> {
            get(VentaProductosControlador::paginaGragicos);
          });

          path("/graficosSender", () -> {
            get(VentaProductosControlador::mostrarGraficos);
          });
        });

        path("/login", () -> {
          get(UsuarioControlador::login);
          post(UsuarioControlador::checkLogin);
        });
        path("/logout", () -> {
          get(UsuarioControlador::logout);
        });
        path("/logged", () -> {
          get(UsuarioControlador::logged);
        });

      });
    }).start(7070);

    app.before(ctx -> {
      CarroCompraControlador.setearCarroCompraSesion(ctx);
      return;
    });

    app.ws("/ws-usuario", ws -> {
      ws.onConnect(ctx -> {
        Usuario usuario = ctx.sessionAttribute("usuario");
        if (usuario == null)
          return;

        String username = usuario.getUsuario();
        UsuarioServicio usuarioServicio = UsuarioServicio.getInstance();
        if(usuarioServicio.getNumeroUsuariosConectados() > 0 && usuarioServicio.getListaUsuarios().contains(username))
          return;

        usuarioServicio.agregarUsuarioConectado(username);
        ctx.session.getRemote().sendString(String.valueOf(usuarioServicio.getNumeroUsuariosConectados()));
      });

      ws.onClose(ctx -> {
        Usuario usuario = ctx.sessionAttribute("usuario");
        if (usuario == null)
          return;

        String username = usuario.getUsuario();
        UsuarioServicio.getInstance().eliminarUsuarioConectado(username);
      });
    });

    app.ws("/ws-comentario", ws -> {
      ws.onConnect(ctx -> {
        UsuarioServicio.getInstance().agregarSesion(ctx.session);
      });

      ws.onMessage(ctx -> {
        UsuarioServicio.getInstance().notificarEliminacionComentario();
      });

      ws.onClose(ctx -> {
        UsuarioServicio.getInstance().eliminarSesion(ctx.session);
      });
    });

    app.ws("/ws-ventas", ws -> {
      ws.onConnect(ctx -> {
        UsuarioServicio.getInstance().agregarSesion(ctx.session);
      });

      ws.onMessage(ctx -> {
        UsuarioServicio.getInstance().notificarActualizacionVentas();
      });

      ws.onClose(ctx -> {
        UsuarioServicio.getInstance().eliminarSesion(ctx.session);
      });
    });


    BootstrapService bootstrapService = new BootstrapService();
    bootstrapService.startDb();
  }
}