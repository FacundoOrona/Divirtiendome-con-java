package org.example.handler;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.example.model.RegistroResultado;
import org.example.model.Usuario;
import org.example.dao.UsuarioDAO;
import org.example.service.UsuarioService;
import org.example.validator.UsuarioValidator;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class UsuarioHandler implements HttpHandler {
    private final UsuarioService service = new UsuarioService();

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if ("POST".equals(exchange.getRequestMethod())) {
            InputStream is = exchange.getRequestBody();
            String body = new String(is.readAllBytes(), StandardCharsets.UTF_8);

            String username = body.split("\"username\":\"")[1].split("\"")[0];
            String password = body.split("\"password\":\"")[1].split("\"")[0];

            Usuario usuario = new Usuario(username, password);

            RegistroResultado resultado = service.registrarUsuario(usuario);

            String response;
            int statusCode;

            switch (resultado) {
                case CREADO:
                    response = "{\"status\":\"Usuario registrado\"}";
                    statusCode = 201;
                    break;
                case DATOS_INVALIDOS:
                    response = "{\"error\":\"Datos inválidos\"}";
                    statusCode = 400;
                    break;
                case USUARIO_EXISTE:
                    response = "{\"error\":\"Usuario ya existe\"}";
                    statusCode = 409;
                    break;
                default:
                    response = "{\"error\":\"Error interno\"}";
                    statusCode = 500;
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


