package org.example;


import DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UsuarioDAO {

    public void registrarUsuario(Usuario usuario) {
        String sql = "INSERT INTO usuarios (username, password) VALUES (?, ?)";

        try (Connection conn = DatabaseConnection.getConection();
             PreparedStatement statement = conn.prepareStatement(sql))
        {
            statement.setString(1, usuario.getUsername());
            statement.setString(2, usuario.getPassword());

            int filas = statement.executeUpdate();
            if (filas > 0) {
                System.out.println("Usuario registrado correctamente");
            }

        } catch (SQLException e) {
            System.out.println("Error al registrar ususario: " + e.getMessage());;
        }
    }

}