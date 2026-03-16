package org.example.handler;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.example.model.Usuario;
import org.example.service.UsuarioService;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class UsuarioListHandler implements HttpHandler {
    private final UsuarioService service = new UsuarioService();
    private final Gson gson = new Gson();

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if ("GET".equals(exchange.getRequestMethod())) {
            try {
                List<Usuario> usuarios = service.obtenerTodos();
                String response = gson.toJson(usuarios);

                exchange.getResponseHeaders().set("Content-Type", "application/json");
                exchange.sendResponseHeaders(200, response.getBytes(StandardCharsets.UTF_8).length);

                try (OutputStream os = exchange.getResponseBody()) {
                    os.write(response.getBytes(StandardCharsets.UTF_8));
                }
            } catch (Exception e) {
                String error = "{\"error\":\"No se pudo obtener la lista de usuarios\"}";
                exchange.sendResponseHeaders(500, error.getBytes().length);
                try (OutputStream os = exchange.getResponseBody()) {
                    os.write(error.getBytes());
                }
            }
        } else {
            exchange.sendResponseHeaders(405, -1);
        }
    }
}

