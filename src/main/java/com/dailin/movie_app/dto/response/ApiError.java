package com.dailin.movie_app.dto.response;

import java.time.LocalDateTime;
import java.util.List;

public record ApiError(

    int httpCode, // codigo de estado

    String ulr, // path que dio error

    String httpMethod, // metodo de solicitudad http
    
    String message, // mensaje para el cliente
    
    String backendMessage, // Mensaje mas tecnico
    
    LocalDateTime timestamp, // fecha y hora de la excepcion

    List<String> details // lista de detalles del error
    
) {

}
