package com.example.product_management.service.imagine;

import com.example.product_management.dto.ImagineDto;
import com.example.product_management.exception.ResourceNotFoundException;
import com.example.product_management.model.Imagine;
import com.example.product_management.model.Product;
import com.example.product_management.repository.ImagineResponsitory;
import com.example.product_management.service.product.ProductI;
import com.example.product_management.service.product.ProductImp;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ImagineImp implements ImagineI {
    private final ImagineResponsitory imagineResponsitory;
    private final ProductI productIService;

    @Override
    public Imagine getImageById(Long id) {
        return imagineResponsitory.findById(id).orElseThrow(() -> new ResourceNotFoundException("No imagine with id: " + id));
    }

    @Override
    public void deleteImagineById(Long id) {
        imagineResponsitory.findById(id).ifPresentOrElse(imagineResponsitory::delete, () -> {
            throw new ResourceNotFoundException("No imagine found with id: " + id);
        });
    }

    @Override
    public List<ImagineDto> saveImagine(List<MultipartFile> file, Long productId) {
        Product product = productIService.getProductById(productId);
        List<ImagineDto> saveImagineDto = new ArrayList<>();
        for (MultipartFile file1 : file) {
            try {
                Imagine imagine = new Imagine();
                imagine.setFileName(file1.getOriginalFilename());
                imagine.setFileType(file1.getContentType());
                imagine.setImage(new SerialBlob(file1.getBytes()));
                imagine.setProduct(product);


                String buildownloadURL = "/api/v1/imagines/imagine/download/";
                String downloadURL = buildownloadURL + imagine.getId();
                imagine.setDownloadUrl(downloadURL);
                Imagine savedImagine = imagineResponsitory.save(imagine);

                savedImagine.setDownloadUrl(buildownloadURL + savedImagine.getId());
                imagineResponsitory.save(savedImagine);

                ImagineDto imagineDto = new ImagineDto();
                imagineDto.setImagineId(savedImagine.getId());
                imagineDto.setImagineName(savedImagine.getFileName());
                imagineDto.setDownloadURL(savedImagine.getDownloadUrl());
                saveImagineDto.add(imagineDto);

            } catch (IOException | SQLException e) {
                throw new RuntimeException(e.getMessage());
            }
        }
        return saveImagineDto;
    }

    @Override
    public void updateImagine(MultipartFile file, Long imagineID) {
        Imagine imagine = getImageById(imagineID);
        try {
            imagine.setFileName(file.getOriginalFilename());
            imagine.setFileName(file.getOriginalFilename());
            imagine.setImage(new SerialBlob(file.getBytes()));
            imagineResponsitory.save(imagine);
        } catch (IOException | SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
