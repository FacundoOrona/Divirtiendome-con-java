package org.example.handler;

import org.example.model.RegistroResultado;

public class RespuestaHttp {
    private final int codigo;
    private final String mensaje;

    public RespuestaHttp(int codigo, String mensaje) {
        this.codigo = codigo;
        this.mensaje = mensaje;
    }

    public int getCodigo() { return codigo; }
    public String getMensaje() { return mensaje; }

    public static RespuestaHttp fromResultado(RegistroResultado resultado) {
        switch (resultado) {
            case CREADO:
                return new RespuestaHttp(201, "{\"status\":\"Usuario registrado\"}");
            case DATOS_INVALIDOS:
                return new RespuestaHttp(400, "{\"error\":\"Datos inválidos\"}");
            case USUARIO_EXISTE:
                return new RespuestaHttp(409, "{\"error\":\"Usuario ya existe\"}");
            default:
                return new RespuestaHttp(500, "{\"error\":\"Error interno\"}");
        }
    }
}

