package com.example.qrcode.controllers;

import com.example.qrcode.BarCodeGenerator;
import com.google.zxing.WriterException;
import com.google.zxing.qrcode.QRCodeWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Objects;

@RestController
@RequestMapping("/api")
public class QRController {
    @GetMapping("/health")
    public ResponseEntity<Object> checker() {
        return ResponseEntity.ok().build();
    }

    @GetMapping("/qrcode")
    public ResponseEntity<BufferedImage> qrCode(@RequestParam String contents, @RequestParam(defaultValue = "250") int size, @RequestParam(defaultValue = "png") String type, @RequestParam(defaultValue = "L") String correction) {
        if(contents.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Content is empty");
        }
        if(size < 150 || size > 350) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Image size must be between 150 and 350 pixels");
        }
        else if(!Objects.equals(type, "png") && !Objects.equals(type, "jpeg") && !Objects.equals(type, "gif")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "");
        }
        BufferedImage image = null;
        try {
            image = BarCodeGenerator.generator(contents, size, correction);
        } catch (WriterException e) {
            throw new RuntimeException(e);
        }
        if(type.equals("gif")) {
            return ResponseEntity.ok().contentType(MediaType.IMAGE_GIF).body(image);
        }
        else if(type.equals("png")) {
            return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(image);
        }
        else return ResponseEntity
                .ok()
                .contentType(MediaType.IMAGE_JPEG).body(image);

    }
}
