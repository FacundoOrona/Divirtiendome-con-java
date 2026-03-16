package org.example.handler;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.example.model.Usuario;
import org.example.dao.UsuarioDAO;
import org.example.service.UsuarioService;
import org.example.validator.UsuarioValidator;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

/// UsuarioHandler: maneja las peticiones al endpoint /usuarios.
/// Si es POST, lee el cuerpo, extrae username y password,
/// crea el objeto Usuario y lo guarda con el UsuarioDAO.
/// Devuelve un JSON de confirmación.
public class UsuarioHandler implements HttpHandler {
    private final UsuarioService service = new UsuarioService();

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if ("POST".equals(exchange.getRequestMethod())) {
            InputStream is = exchange.getRequestBody();
            String body = new String(is.readAllBytes(), StandardCharsets.UTF_8);

            // Parseo básico
            String username = body.split("\"username\":\"")[1].split("\"")[0];
            String password = body.split("\"password\":\"")[1].split("\"")[0];

            Usuario usuario = new Usuario(username, password);

            // Usar el service para registrar
            boolean registrado = service.registrarUsuario(usuario);

            String response;
            int statusCode;
            if (registrado) {
                response = "{\"status\":\"Usuario registrado\"}";
                statusCode = 201;
            } else {
                response = "{\"error\":\"Datos inválidos\"}";
                statusCode = 400;
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

