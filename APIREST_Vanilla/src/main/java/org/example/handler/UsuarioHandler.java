package org.example.handler;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.example.model.RegistroResultado;
import org.example.model.Usuario;
import org.example.service.UsuarioService;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

import com.google.gson.Gson;

public class UsuarioHandler implements HttpHandler {
    private final UsuarioService service = new UsuarioService();
    private final Gson gson = new Gson();

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if ("POST".equals(exchange.getRequestMethod())) {
            InputStream is = exchange.getRequestBody();
            String body = new String(is.readAllBytes(), StandardCharsets.UTF_8);

            // Parseo limpio con Gson
            Usuario usuario = gson.fromJson(body, Usuario.class);

            RegistroResultado resultado = service.registrarUsuario(usuario);

            // Manejo de respuesta centralizado
            RespuestaHttp respuesta = RespuestaHttp.fromResultado(resultado);

            exchange.getResponseHeaders().set("Content-Type", "application/json");
            exchange.sendResponseHeaders(respuesta.getCodigo(), respuesta.getMensaje().getBytes().length);
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(respuesta.getMensaje().getBytes());
            }
        } else {
            exchange.sendResponseHeaders(405, -1);
        }
    }
}



