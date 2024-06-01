package com.artofcode.artofcodebck.config;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
public class CloudinaryService {
    Cloudinary cloudinary;

    public CloudinaryService() {
        Map<String, String> valuesMap = new HashMap<>();
        valuesMap.put("cloud_name", "dfyftzxnc");
        valuesMap.put("api_key", "884917534899757");
        valuesMap.put("api_secret", "1X785_3ostlZ5p9ULQHrBEVY_9w");
        cloudinary = new Cloudinary(valuesMap);
    }
    public String uploadImage(MultipartFile file) throws IOException {
        // Convertissez le MultipartFile en File
        File convertedFile = convert(file);

        // Chargez le fichier vers Cloudinary
        Map uploadResult = cloudinary.uploader().upload(convertedFile, ObjectUtils.emptyMap());

        // Récupérez l'URL de l'image uploadée depuis la réponse de Cloudinary
        return (String) uploadResult.get("url");
    }

    public Map upload(MultipartFile multipartFile) throws IOException {
        File file = convert(multipartFile);
        Map result = cloudinary.uploader().upload(file, ObjectUtils.emptyMap());
        if (!Files.deleteIfExists(file.toPath())) {
            throw new IOException("Failed to delete temporary file: " + file.getAbsolutePath());
        }
        return result;
    }

    private File convert(MultipartFile multipartFile) throws IOException {
        File file = new File(Objects.requireNonNull(multipartFile.getOriginalFilename()));
        FileOutputStream fo = new FileOutputStream(file);
        fo.write(multipartFile.getBytes());
        fo.close();
        return file;
    }

}
