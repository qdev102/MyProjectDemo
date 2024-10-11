package com.example.product_management.service.imagine;

import com.example.product_management.dto.ImagineDto;
import com.example.product_management.model.Imagine;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ImagineI {
    Imagine getImageById(Long id);
    void deleteImagineById(Long id);
    List<ImagineDto> saveImagine(List<MultipartFile> file, Long productId);
    void updateImagine(MultipartFile file, Long imagineID);

}
