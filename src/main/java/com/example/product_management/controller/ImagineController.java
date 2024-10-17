package com.example.product_management.controller;

import com.example.product_management.dto.ImagineDto;
import com.example.product_management.exception.ResourceNotFoundException;
import com.example.product_management.model.Imagine;
import com.example.product_management.model.Product;
import com.example.product_management.response.ApiResponse;
import com.example.product_management.service.ImagineImpService;
//import com.example.product_management.service.imagine.ImagineI;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.sql.SQLException;
import java.util.List;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/imagine")
public class ImagineController {
    private final ImagineImpService imagineservice;

    @PostMapping("/upload")
    public ResponseEntity<ApiResponse> saveImagine(@RequestParam List<MultipartFile> files, @RequestParam Long productId) {
        try {
            List<ImagineDto> imagineDtos = imagineservice.saveImagine(files, productId);
            return ResponseEntity.ok(new ApiResponse("Up loadsuccess", imagineDtos));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("Upload Faild!", e.getMessage()));
        }

    }

//    @GetMapping("/image/download/{imageId}")
//    public ResponseEntity<Resource> downloadImage(@PathVariable Long imageId) throws SQLException {
//        Imagine image = imagineservice.getImageById(imageId);
//        ByteArrayResource resource = new ByteArrayResource(image.getImage().getBytes(1, (int) image.getImage().length()));
//        return  ResponseEntity.ok().contentType(MediaType.parseMediaType(image.getFileType()))
//                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" +image.getFileName() + "\"")
//                .body(resource);
//    }

    @PutMapping("/image/{imageId}/update")
    public ResponseEntity<ApiResponse> updateImage(@PathVariable Long imageId, @RequestBody MultipartFile file) {
        try {
            Imagine image = imagineservice.getImageById(imageId);
            if (image != null) {
                imagineservice.updateImagine(file, imageId);
                return ResponseEntity.ok(new ApiResponse("Update success!", null));
            }
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("Update failed!", INTERNAL_SERVER_ERROR));
    }


    @DeleteMapping("/image/{imageId}/delete")
    public ResponseEntity<ApiResponse> deleteImage(@PathVariable Long imageId) {
        try {
            Imagine image = imagineservice.getImageById(imageId);
            if (image != null) {
                imagineservice.deleteImagineById(imageId);
                return ResponseEntity.ok(new ApiResponse("Delete success!", null));
            }
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("Delete failed!", INTERNAL_SERVER_ERROR));
    }
}


