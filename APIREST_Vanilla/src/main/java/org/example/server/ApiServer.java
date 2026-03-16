package org.example.server;

import com.sun.net.httpserver.HttpServer;
import org.example.handler.UsuarioHandler;

import java.net.InetSocketAddress;

//ApiServer: levanta un servidor HTTP en el puerto 8080.
public class ApiServer {
    public static void main(String[] args) throws Exception {
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);

        // asocia la ruta /usuarios con tu lógica de registro.
        server.createContext("/usuarios", new UsuarioHandler());

        server.setExecutor(null); //usa el executor por defecto

        server.start();
        System.out.println("Servidor iniciado en http://localhost:8080");
    }
}
