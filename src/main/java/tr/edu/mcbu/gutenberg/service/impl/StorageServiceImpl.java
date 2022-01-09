package tr.edu.mcbu.gutenberg.service.impl;

import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.tasks.UnsupportedFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import tr.edu.mcbu.gutenberg.model.DocumentExtension;
import tr.edu.mcbu.gutenberg.model.File;
import tr.edu.mcbu.gutenberg.model.ImageExtension;
import tr.edu.mcbu.gutenberg.repository.FileRepository;
import tr.edu.mcbu.gutenberg.service.StorageService;
import net.coobird.thumbnailator.Thumbnails;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.UUID;

@Service
@Slf4j
public class StorageServiceImpl implements StorageService {

    public static final String FOLDER_PATH =
            new java.io.File("").getAbsolutePath() + "/uploaded_files/";

    private FileRepository fileRepository;


    @Autowired
    public StorageServiceImpl(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    @Override
    public File load(UUID id) throws IOException {
        File file = fileRepository.findById(id).get();
        Path path = Paths.get(file.getLocation());
        byte[] bytes = Files.readAllBytes(path);
        file.setSize(path.toFile().length());
        file.setContent(bytes);
        return file;
    }

    @Override
    public File store(File file) throws IOException {
        fileRepository.save(file);
        file.setLocation(FOLDER_PATH);
        fileRepository.save(file);
        Path savePath = Paths.get(file.getLocation());
        if (!savePath.getParent().toFile().exists()) {
            Files.createDirectory(savePath.getParent());
        }
        Files.write(savePath, file.getContent());

        if (Arrays.stream(ImageExtension.values()).anyMatch(extension -> extension.toString().equals(file.getName()))) {
            try {
                Thumbnails.of(savePath.toFile()).width(1000).height(1000)
                        .toFile(new java.io.File(FOLDER_PATH + "big." + savePath.getFileName()));
                Thumbnails.of(savePath.toFile()).width(200).height(200)
                        .toFile(new java.io.File(FOLDER_PATH + "small." + savePath.getFileName()));
            } catch (UnsupportedFormatException e) {
                log.error("Unsupported file format!", e);
            }
        }
        return file;
    }

    @Override
    public void delete(File file) {
        fileRepository.delete(file);
    }

    @Override
    public File createFile(MultipartFile multipartFile) throws IOException {

        File file = new File();
        file.setName(multipartFile.getOriginalFilename());
        file.setContentType(multipartFile.getContentType());
        file.setContent(multipartFile.getBytes());
        file.setSize(multipartFile.getSize());
        return file;
    }

    @Override
    public File store(MultipartFile multipartFile) throws IOException {

        File file = createFile(multipartFile);
        return store(file);
    }


    @Override
    public Boolean checkExtensionValidityForImages(String imageName) {

        String extension = imageName.substring(imageName.lastIndexOf(".") + 1).toLowerCase();
        return Arrays.stream(ImageExtension.values()).anyMatch(e -> e.toString().toLowerCase().equals(extension));

    }

    @Override
    public Boolean checkExtensionValidityForDocuments(String documentName) {

        String extension = documentName.substring(documentName.lastIndexOf(".") + 1).toLowerCase();
        return Arrays.stream(DocumentExtension.values()).anyMatch(e -> e.toString().toLowerCase().equals(extension));
    }


    @Override
    public Boolean checkImageAndExtension(MultipartFile photo) {

        if (photo != null) {
            return !Boolean.FALSE.equals(checkExtensionValidityForImages(photo.getOriginalFilename()));
        }
        return true;
    }

    @Override
    public Boolean checkDocumentAndExtension(MultipartFile resume) {
        if (resume != null) {
            return !Boolean.FALSE.equals(checkExtensionValidityForDocuments(resume.getOriginalFilename()));
        }
        return true;
    }

}
