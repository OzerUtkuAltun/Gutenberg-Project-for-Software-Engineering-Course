package tr.edu.mcbu.gutenberg.service;

import org.springframework.web.multipart.MultipartFile;
import tr.edu.mcbu.gutenberg.model.File;

import java.io.IOException;
import java.util.UUID;

public interface StorageService {

    File load(UUID id) throws IOException;

    File store(File file) throws IOException;

    void delete(File file);

    File createFile(MultipartFile multipartFile) throws IOException;

    File store(MultipartFile multipartFile) throws IOException;

    Boolean checkExtensionValidityForImages(String imageName);

    Boolean checkExtensionValidityForDocuments(String documentName);

    Boolean checkImageAndExtension(MultipartFile photo);

    Boolean checkDocumentAndExtension(MultipartFile resume);

}
