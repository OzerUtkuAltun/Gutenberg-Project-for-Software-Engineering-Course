package tr.edu.mcbu.gutenberg.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tr.edu.mcbu.gutenberg.model.User;
import tr.edu.mcbu.gutenberg.model.UserBook;
import tr.edu.mcbu.gutenberg.model.request.*;
import tr.edu.mcbu.gutenberg.model.response.LoginResponse;
import tr.edu.mcbu.gutenberg.model.response.MessageDataResponse;
import tr.edu.mcbu.gutenberg.model.response.MessageResponse;
import tr.edu.mcbu.gutenberg.model.response.UserResponse;
import tr.edu.mcbu.gutenberg.service.UserService;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@CrossOrigin
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Slf4j
public class UsersController {

    private final UserService userService;

    @GetMapping("/info/{userId}")
    public ResponseEntity<MessageDataResponse<UserResponse>> getUserInfoById(@PathVariable UUID userId) {

        MessageDataResponse<UserResponse> response = userService.getUserInfoById(userId);

        if (Boolean.FALSE.equals(response.getSuccess())) {
            return ResponseEntity.badRequest().body(response);
        }

        return ResponseEntity.ok(response);
    }


    @PostMapping("/register")
    public ResponseEntity<MessageResponse> register(@RequestBody @Valid UserRequest request) {

        MessageResponse response = userService.registerUser(request);

        if (Boolean.FALSE.equals(response.getSuccess())) {
            return ResponseEntity.badRequest().body(response);
        }

        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {

        return userService.login(loginRequest);
    }

    @PutMapping
    public ResponseEntity<MessageResponse> updateUser(@RequestBody UserUpdateRequest userUpdateRequest) {

        MessageResponse response = userService.updateUser(userUpdateRequest);

        if (Boolean.FALSE.equals(response.getSuccess())) {
            return ResponseEntity.badRequest().body(response);
        }
        return ResponseEntity.ok(response);
    }


    @PostMapping("/profile-image")
    public ResponseEntity<MessageResponse> uploadUserImage(String id, MultipartFile multipartFile) {

        MessageResponse response = userService.uploadUserImage(id, multipartFile);

        if (Boolean.FALSE.equals(response.getSuccess())) {
            ResponseEntity.badRequest().body(response);
        }

        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<MessageDataResponse<List<User>>> listAllUsers() {

        return ResponseEntity.ok(new MessageDataResponse<>(true, userService.listAllUsers(), "All users listed."));
    }

    @GetMapping("/books/{userId}")
    public ResponseEntity<MessageDataResponse<Set<UserBook>>> listAllUserBooks(@PathVariable UUID userId) {

        MessageDataResponse<Set<UserBook>> response = userService.listAllUserBooks(userId);

        if (Boolean.FALSE.equals(response.getSuccess())) {
            return ResponseEntity.badRequest().body(response);
        }
        return ResponseEntity.ok(response);
    }

    @PostMapping("/books")
    public ResponseEntity<MessageResponse> addBookToUserBooks(@RequestBody UsersBookRequest usersBookRequest) {

        MessageResponse response = userService.addBookToUserBooks(usersBookRequest.getUserId(), usersBookRequest.getBookId());

        if (Boolean.FALSE.equals(response.getSuccess())) {
            return ResponseEntity.badRequest().body(response);
        }
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/books")
    public ResponseEntity<MessageResponse> removeBookFromUserBooks(@RequestBody UsersBookRequest usersBookRequest) {

        MessageResponse response = userService.removeBookFromUserBooks(usersBookRequest.getUserId(), usersBookRequest.getBookId());

        if (Boolean.FALSE.equals(response.getSuccess())) {
            return ResponseEntity.badRequest().body(response);
        }
        return ResponseEntity.ok(response);
    }


    @GetMapping("/favorite-books/{userId}")
    public ResponseEntity<MessageDataResponse<Set<UserBook>>> listAllFavoriteBooks(@PathVariable UUID userId) {

        MessageDataResponse<Set<UserBook>> response = userService.listAllFavoriteBooks(userId);

        if (Boolean.FALSE.equals(response.getSuccess())) {
            return ResponseEntity.badRequest().body(response);
        }
        return ResponseEntity.ok(response);
    }

    @PostMapping("/favorite-books")
    public ResponseEntity<MessageResponse> addBookToFavoriteBooks(@RequestBody UsersBookRequest usersBookRequest) {

        MessageResponse response = userService.addBookToFavoriteBooks(usersBookRequest.getUserId(), usersBookRequest.getBookId());

        if (Boolean.FALSE.equals(response.getSuccess())) {
            return ResponseEntity.badRequest().body(response);
        }
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/favorite-books/bookId={bookId}/userId={userId}")
    public ResponseEntity<MessageResponse> removeBookFromFavoriteBooks(@PathVariable UUID bookId, @PathVariable UUID userId) {

        MessageResponse response = userService.removeBookFromFavoriteBooks(userId, bookId);

        if (Boolean.FALSE.equals(response.getSuccess())) {
            return ResponseEntity.badRequest().body(response);
        }
        return ResponseEntity.ok(response);
    }

    @PostMapping("/update-page")
    public ResponseEntity<MessageResponse> updateCurrentPage(@RequestBody UpdatePageRequest updatePageRequest) {

        MessageResponse response = userService.updateCurrentPage(updatePageRequest);

        if (Boolean.FALSE.equals(response.getSuccess())) {
            return ResponseEntity.badRequest().body(response);
        }

        return ResponseEntity.ok(response);

    }

}
