package org.example.server;

import com.sun.net.httpserver.HttpServer;
import org.example.handler.LoginHandler;
import org.example.handler.UsuarioHandler;

import java.net.InetSocketAddress;

//ApiServer: levanta un servidor HTTP en el puerto 8080.
public class ApiServer {
    public static void main(String[] args) throws Exception {
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);

        // asocia la ruta /usuarios con tu lógica de registro.
        server.createContext("/registro", new UsuarioHandler());   // POST registro
        server.createContext("/login", new LoginHandler());        // POST login
//        server.createContext("/usuarios/list", new UsuarioListHandler()); // GET lista
//        server.createContext("/usuarios/get", new UsuarioGetHandler());   // GET por id


        server.setExecutor(null); //usa el executor por defecto

        server.start();
        System.out.println("Servidor iniciado en http://localhost:8080");
    }
}
