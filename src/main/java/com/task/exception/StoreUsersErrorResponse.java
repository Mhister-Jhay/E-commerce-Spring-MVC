package com.task.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StoreUsersErrorResponse {
    @JsonFormat(pattern = "yyyy:MM:dd HH:mm:ss")
    private LocalDateTime localDateTime;
    private int statusCode;
    private String path;
    private String message;
}
