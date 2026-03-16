package org.example.handler;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.example.model.Usuario;
import org.example.dao.UsuarioDAO;
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
    private final UsuarioDAO dao = new UsuarioDAO();

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        //Identifica el metodo de la request
        if("POST".equals(exchange.getRequestMethod())) {
            //Lee el cuerpo de la peticion
            InputStream is = exchange.getRequestBody();
            String body = new String(is.readAllBytes(), StandardCharsets.UTF_8);

            //Parse basico del JSON
            String username = body.split("\"username\":\"")[1].split("\"")[0];
            String password = body.split("\"password\":\"")[1].split("\"")[0];

            Usuario usuario = new Usuario(username, password);

            // Validación antes de registrar
            if (!UsuarioValidator.validar(usuario)) {
                String response = "{\"error\":\"Datos inválidos\"}";
                exchange.getResponseHeaders().set("Content-Type", "application/json");
                exchange.sendResponseHeaders(400, response.getBytes().length);
                try (OutputStream os = exchange.getResponseBody()) {
                    os.write(response.getBytes());
                }
                return; // corta el flujo aquí
            }

            dao.registrarUsuario(usuario);

            String response = "{\"status\":\"Usuario registrado\"}";
            exchange.getResponseHeaders().set("Content-Type", "application/json");
            exchange.sendResponseHeaders(200, response.getBytes().length);
            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        } else {
            exchange.sendResponseHeaders(405, -1);
        }
    }
}
