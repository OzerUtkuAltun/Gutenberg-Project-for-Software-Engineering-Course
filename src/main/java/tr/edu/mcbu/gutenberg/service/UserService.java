package tr.edu.mcbu.gutenberg.service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import tr.edu.mcbu.gutenberg.model.File;
import tr.edu.mcbu.gutenberg.model.User;
import tr.edu.mcbu.gutenberg.model.UserBook;
import tr.edu.mcbu.gutenberg.model.request.LoginRequest;
import tr.edu.mcbu.gutenberg.model.request.UpdatePageRequest;
import tr.edu.mcbu.gutenberg.model.request.UserRequest;
import tr.edu.mcbu.gutenberg.model.request.UserUpdateRequest;
import tr.edu.mcbu.gutenberg.model.response.LoginResponse;
import tr.edu.mcbu.gutenberg.model.response.MessageDataResponse;
import tr.edu.mcbu.gutenberg.model.response.MessageResponse;
import tr.edu.mcbu.gutenberg.model.response.UserResponse;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface UserService {


    void save(User user);

    MessageResponse registerUser(UserRequest userRequest);

    MessageDataResponse<UserResponse> getUserInfoById(UUID userId);

    MessageResponse uploadUserImage(String id, MultipartFile multipartFile);

    Boolean existsUserByUsername(String username);

    void uploadProfileImage(UUID id, File image);

    List<User> listAllUsers();

    User getUserById(UUID id);

    MessageResponse updateUser(UserUpdateRequest userUpdateRequest);

    ResponseEntity<LoginResponse> login(LoginRequest loginRequest);

    MessageDataResponse<Set<UserBook>> listAllUserBooks(UUID userId);

    MessageDataResponse<Set<UserBook>> listAllFavoriteBooks(UUID userId);

    MessageResponse addBookToUserBooks(UUID userId, UUID bookId);

    MessageResponse removeBookFromUserBooks(UUID userId, UUID bookId);

    MessageResponse addBookToFavoriteBooks(UUID userId, UUID bookId);

    MessageResponse removeBookFromFavoriteBooks(UUID userId, UUID bookId);

    MessageResponse validateUserAndBook(Object user, Object book);

    MessageResponse updateCurrentPage(UpdatePageRequest updatePageRequest);

}
