package tr.edu.mcbu.gutenberg.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import tr.edu.mcbu.gutenberg.exception.ExceptionMessages;
import tr.edu.mcbu.gutenberg.exception.UserNotFoundException;
import tr.edu.mcbu.gutenberg.model.Book;
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
import tr.edu.mcbu.gutenberg.repository.UserRepository;
import tr.edu.mcbu.gutenberg.service.BookService;
import tr.edu.mcbu.gutenberg.service.StorageService;
import tr.edu.mcbu.gutenberg.service.UserBookService;
import tr.edu.mcbu.gutenberg.service.UserService;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;


@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository repository;

    private final BookService bookService;

    private final UserBookService userBookService;
    private final StorageService storageService;


    @Override
    public void save(User user) {
        repository.save(user);
    }

    @Override
    public MessageResponse registerUser(UserRequest userRequest) {

        if (Boolean.TRUE.equals(existsUserByUsername(userRequest.getUsername()))) {
            return new MessageResponse(false, "This username is already taken.");
        }

        User user = User.builder()
                .firstName(userRequest.getFirstName())
                .lastName(userRequest.getLastName())
                .city(userRequest.getCity())
                .username(userRequest.getUsername())
                .password(userRequest.getPassword())
                .build();

        save(user);
        return new MessageResponse(true, "User registered.");
    }

    @Override
    public MessageDataResponse<UserResponse> getUserInfoById(UUID userId) {

        User user = getUserById(userId);

        if (Objects.isNull(user)) {
            return new MessageDataResponse<>(false, null, "There is no such user in the system.");
        }

        UserResponse response = UserResponse.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .username(user.getUsername())
                .city(user.getCity())
                .build();

        return new MessageDataResponse<>(true, response, "User info sent successfully.");
    }

    @Override
    public MessageResponse uploadUserImage(String id, MultipartFile multipartFile) {
        File image;

        if (!Objects.isNull(multipartFile)) {
            try {
                image = storageService.store(multipartFile);
                uploadProfileImage(UUID.fromString(id), image);

            } catch (IOException e) {
                log.error(e.getMessage());
                return new MessageResponse(false, "Could not upload profile picture.");
            } catch (IllegalArgumentException e) {
                log.error("UUID failed to parse");
                return new MessageResponse(false, "Invalid id please try again.");
            }

        }
        return new MessageResponse(true, "Profile picture uploaded.");

    }

    @Override
    public Boolean existsUserByUsername(String username) {
        return repository.existsUserByUsername(username);
    }

    @Override
    public MessageResponse updateUser(UserUpdateRequest userUpdateRequest) {


        User user = getUserById(userUpdateRequest.getUserId());

        if (Objects.isNull(user)) {
            return new MessageResponse(false, "There is no such user in the system");
        }

        user.setFirstName(userUpdateRequest.getFirstName());
        user.setLastName(userUpdateRequest.getLastName());

        if (Boolean.TRUE.equals(existsUserByUsername(userUpdateRequest.getUsername()))) {
            return new MessageResponse(false, "This username is already taken.");
        }
        user.setUsername(userUpdateRequest.getUsername());
        user.setCity(userUpdateRequest.getCity());
        user.setPassword(userUpdateRequest.getPassword());

        save(user);
        return new MessageResponse(true, "User updated.");
    }

    @Override
    public void uploadProfileImage(UUID id, File image) {

        try {
            User user = repository.findById(id).orElseThrow(() -> new UserNotFoundException(ExceptionMessages.USER_NOT_FOUND));
            user.setImage(image);
            save(user);
        } catch (UserNotFoundException e) {
            log.error(ExceptionMessages.USER_NOT_FOUND);
        }
        log.info("Profile image uploaded. User id: {}", id);
    }

    @Override
    public List<User> listAllUsers() {

        return repository.findAll();
    }

    @Override
    public ResponseEntity<LoginResponse> login(LoginRequest loginRequest) {

        if (Objects.isNull(loginRequest.getUsername())) {
            return ResponseEntity.badRequest().body(new LoginResponse(null, false, "Username cannot be empty."));
        }

        User user = repository.findUserByUsername(loginRequest.getUsername());

        if (Objects.isNull(user)) {
            return ResponseEntity.badRequest().body(new LoginResponse(null, false, "Wrong username or password. Please try again."));
        }

        return checkUserValidity(user, loginRequest.getPassword());
    }

    @Override
    public MessageDataResponse<Set<UserBook>> listAllUserBooks(UUID userId) {

        User user = getUserById(userId);

        if (Objects.isNull(user)) {
            return new MessageDataResponse<>(false, null, ExceptionMessages.USER_NOT_FOUND);
        }

        return new MessageDataResponse<>(true, userBookService.findAllByUserId(userId), "User book's listed.");
    }

    @Override
    public MessageDataResponse<Set<UserBook>> listAllFavoriteBooks(UUID userId) {

        User user = getUserById(userId);

        if (Objects.isNull(user)) {
            return new MessageDataResponse<>(false, null, ExceptionMessages.USER_NOT_FOUND);
        }

        return new MessageDataResponse<>(true, user.getFavoriteBooks(), "User favorite book's listed.");
    }

    @Override
    public MessageResponse addBookToUserBooks(UUID userId, UUID bookId) {

        User user = getUserById(userId);
        Book book = bookService.getBookById(bookId);

        if (Boolean.FALSE.equals(validateUserAndBook(user, book).getSuccess())) {
            return validateUserAndBook(user, book);
        }

        if (Boolean.TRUE.equals(userBookService.existsByBookIdAndUserId(bookId, userId))) {
            return new MessageResponse(false, "This user already has this book.");
        }

        UserBook userBook = UserBook.builder()
                .bookId(bookId)
                .userId(userId)
                .name(book.getName())
                .author(book.getAuthor())
                .currentPage(book.getCurrentPage())
                .gutenbergId(book.getGutenbergId())
                .url(book.getUrl())
                .imageUrl(book.getImageUrl())
                .build();

        userBookService.save(userBook);
        return new MessageResponse(true, "Book added to user books.");
    }

    @Override
    public MessageResponse removeBookFromUserBooks(UUID userId, UUID bookId) {

        User user = getUserById(userId);
        UserBook userBook = userBookService.getUserBookByBookIdAndUserId(bookId, userId);

        if (Boolean.FALSE.equals(validateUserAndBook(user, userBook).getSuccess())) {
            return validateUserAndBook(user, userBook);
        }

        return userBookService.removeUserBook(bookId, userId);

    }

    @Override
    public MessageResponse addBookToFavoriteBooks(UUID userId, UUID bookId) {

        User user = getUserById(userId);
        Book book = bookService.getBookById(bookId);

        if (Boolean.FALSE.equals(validateUserAndBook(user, book).getSuccess())) {
            return validateUserAndBook(user, book);
        }

        AtomicReference<Boolean> result = new AtomicReference<>(true);
        user.getFavoriteBooks().forEach(b -> {
            if (b.getUserId().equals(userId) && b.getBookId().equals(bookId)) {
                result.set(false);
            }
        });

        if (Boolean.FALSE.equals(result.get())) {
            return new MessageResponse(false, "This book is already added to favorites");
        }

        UserBook userBook = UserBook.builder()
                .bookId(bookId)
                .userId(userId)
                .name(book.getName())
                .author(book.getAuthor())
                .currentPage(book.getCurrentPage())
                .gutenbergId(book.getGutenbergId())
                .url(book.getUrl())
                .imageUrl(book.getImageUrl())
                .build();

        user.getFavoriteBooks().add(userBook);

        if (Boolean.TRUE.equals(userBookService.existsByBookIdAndUserId(bookId, userId))) {
            userBookService.removeUserBook(bookId, userId);
        }
        save(user);
        return new MessageResponse(true, "Book added to user's favorite books.");

    }

    @Override
    public MessageResponse removeBookFromFavoriteBooks(UUID userId, UUID bookId) {

        User user = getUserById(userId);
        UserBook userBook = userBookService.getUserBookByBookIdAndUserId(bookId, userId);

        if (Boolean.FALSE.equals(validateUserAndBook(user, userBook).getSuccess())) {
            return validateUserAndBook(user, userBook);
        }

        if (!user.getFavoriteBooks().contains(userBook)) {
            return new MessageResponse(false, "There is no such book in the user's favorite books.");
        }

        user.getFavoriteBooks().remove(userBook);
        save(user);
        return new MessageResponse(true, "Book removed from user's favorite books.");
    }

    @Override
    public MessageResponse updateCurrentPage(UpdatePageRequest updatePageRequest) {


        UserBook userBook = userBookService.getUserBookByBookIdAndUserId(updatePageRequest.getBookId(), updatePageRequest.getUserId());

        if (Objects.isNull(userBook)) {
            return new MessageResponse(false, "This user hasn't this book.");
        }

        userBook.setCurrentPage(updatePageRequest.getCurrentPage());
        userBookService.save(userBook);

        return new MessageResponse(true, "Page updated.");

    }


    private ResponseEntity<LoginResponse> checkUserValidity(User user, String password) {

        if (!user.getPassword().equals(password)) {
            return ResponseEntity.badRequest().body(new LoginResponse(null, false, "Username and password not matched."));

        } else {
            return ResponseEntity.ok(new LoginResponse(user.getId(), true, "Login successful."));
        }
    }

    public User getUserById(UUID id) {

        User user = null;
        try {
            user = repository.findById(id).orElseThrow(() -> new UserNotFoundException(ExceptionMessages.USER_NOT_FOUND));
        } catch (UserNotFoundException e) {
            log.info(e.getMessage());
        }

        return user;
    }

    public MessageResponse validateUserAndBook(Object user, Object book) {
        if (Objects.isNull(user)) {
            return new MessageResponse(false, ExceptionMessages.USER_NOT_FOUND);
        }

        if (Objects.isNull(book)) {
            return new MessageResponse(false, ExceptionMessages.BOOK_NOT_FOUND);
        }

        return new MessageResponse(true, "Validation successful.");
    }

}
