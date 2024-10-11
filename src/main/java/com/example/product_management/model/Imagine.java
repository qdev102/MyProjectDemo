package com.example.product_management.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Base64;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Imagine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fileName;
    private String fileType;
    @Lob
    private Blob image;
    private String downloadUrl;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
    // Phương thức để trả về chuỗi Base64 từ Blob
    public String getImageBase64() throws SQLException, IOException {
        byte[] imageBytes = this.image.getBinaryStream().readAllBytes(); // Lấy dữ liệu nhị phân từ Blob
        return Base64.getEncoder().encodeToString(imageBytes); // Mã hóa thành Base64
    }
}
