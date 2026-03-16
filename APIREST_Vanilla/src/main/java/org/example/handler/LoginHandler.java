package org.example.handler;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.example.model.Usuario;
import org.example.service.UsuarioService;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class LoginHandler implements HttpHandler {
    private final UsuarioService service = new UsuarioService();
    private final Gson gson = new Gson();

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if ("POST".equals(exchange.getRequestMethod())) {
            String body = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
            Usuario usuario = gson.fromJson(body, Usuario.class);

            boolean autenticado = service.login(usuario);

            String response;
            int statusCode;
            if (autenticado) {
                response = "{\"status\":\"Login exitoso\"}";
                statusCode = 200;
            } else {
                response = "{\"error\":\"Credenciales inválidas\"}";
                statusCode = 401;
            }

            exchange.getResponseHeaders().set("Content-Type", "application/json");
            exchange.sendResponseHeaders(statusCode, response.getBytes().length);
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(response.getBytes());
            }
        } else {
            exchange.sendResponseHeaders(405, -1);
        }
    }
}

