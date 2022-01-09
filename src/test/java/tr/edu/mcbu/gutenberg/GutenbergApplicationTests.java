package tr.edu.mcbu.gutenberg;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import tr.edu.mcbu.gutenberg.model.Book;
import tr.edu.mcbu.gutenberg.model.User;
import tr.edu.mcbu.gutenberg.model.UserBook;
import tr.edu.mcbu.gutenberg.repository.UserBookRepository;
import tr.edu.mcbu.gutenberg.service.BookService;
import tr.edu.mcbu.gutenberg.service.UserService;


import java.util.List;
import java.util.UUID;

@SpringBootTest
@Slf4j
@RequiredArgsConstructor
class GutenbergApplicationTests {

    private final UserBookRepository userBookRepository;

    private final UserService userService;

    private final BookService bookService;

    @Test
    void contextLoads() {


        UserBook userBook = userBookRepository.findUserBookByBookIdAndUserId(UUID.fromString("b439d804-94cf-45d8-bc6b-6c150beb41e7"), UUID.fromString("f41d2cc8-592d-44a7-9cc9-2e24fc702d4a")).get();

        log.info(userBook.getBookId().toString());

        log.info(userService.getUserInfoById(UUID.fromString("08dc2165-f623-4ef7-ad93-3a77072a6da8")).getMessage());
        log.info(userService.getUserInfoById(UUID.fromString("08dc2165-f623-4ef7-ad93-3a77072a6da8")).getSuccess().toString());


        List<User> users = userService.listAllUsers();

        users.forEach(u -> {
            log.info(u.getFirstName());
        });

        log.info(userService.getUserById(UUID.fromString("84a8c485-9215-41d8-aa7b-e8634b36c562")).getFirstName());

        userService.addBookToFavoriteBooks(UUID.fromString("f41d2cc8-592d-44a7-9cc9-2e24fc702d4a"), UUID.fromString("13dd764b-cb59-4885-80a0-9d9156f3a2c4"));
        userService.removeBookFromFavoriteBooks(UUID.fromString("f41d2cc8-592d-44a7-9cc9-2e24fc702d4a"), UUID.fromString("13dd764b-cb59-4885-80a0-9d9156f3a2c4"));

        log.info(userService.existsUserByUsername("test75").toString());

        userService.addBookToUserBooks(UUID.fromString("f41d2cc8-592d-44a7-9cc9-2e24fc702d4a"), UUID.fromString("13dd764b-cb59-4885-80a0-9d9156f3a2c4"));
        userService.removeBookFromUserBooks(UUID.fromString("f41d2cc8-592d-44a7-9cc9-2e24fc702d4a"), UUID.fromString("13dd764b-cb59-4885-80a0-9d9156f3a2c4"));


        for (UserBook book : userService.listAllUserBooks(UUID.fromString("f41d2cc8-592d-44a7-9cc9-2e24fc702d4a")).getData()) {
            log.info(book.getName());
        }

        for (UserBook b : userService.listAllFavoriteBooks(UUID.fromString("f41d2cc8-592d-44a7-9cc9-2e24fc702d4a")).getData()) {
            log.info(b.getName());
        }


        bookService.save(new Book(5L,"name", "author", 6, "url", "imageurl"));

        log.info(bookService.getBookById(UUID.fromString("0006eb10-1a20-423d-a455-ab0e3137f555")).getName());

        // Fetching all books in the Gutenberg Project

        /*
        for(int i=1; i<=2091; i++){

            String url = "https://gutendex.com/books/?page=" + i;
            URL obj;

            {
                try {
                    obj = new URL(url);
                    HttpURLConnection con = (HttpURLConnection) obj.openConnection();
                    con.setRequestMethod("GET");
                    con.setRequestProperty("User-Agent", "Mozilla/5.0");
                    BufferedReader in = new BufferedReader(
                            new InputStreamReader(con.getInputStream()));
                    String inputLine;
                    StringBuffer response = new StringBuffer();
                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }
                    in.close();
                    System.out.println(response.toString());
                    JSONObject jsonResponse = new JSONObject(response.toString());
                    JSONArray results = jsonResponse.getJSONArray("results");

                    for(int j=1; j < results.length(); j++){
                        JSONObject bookFromGutenbergApi = results.getJSONObject(j);

                        int gutenbergId = bookFromGutenbergApi.getInt("id");
                        String bookName = bookFromGutenbergApi.getString("title");

                        if(bookName.length() > 50){
                            bookName = bookName.substring(0,50);
                        }

                        JSONArray authorArray = bookFromGutenbergApi.getJSONArray("authors");

                        String author = "anonymous";

                        if(authorArray.length() > 0){
                            JSONObject authorObject = authorArray.getJSONObject(0);
                            author = authorObject.getString("name");
                        }

                        if(author.length() > 50){
                            author = author.substring(0,50);
                        }

                        JSONObject formatsObject = bookFromGutenbergApi.getJSONObject("formats");
                        String bookUrl = formatsObject.getString("text/html");

                        String imageUrl = formatsObject.getString("image/jpeg");

                        if(Objects.isNull(bookUrl)){
                            bookUrl = formatsObject.getString("text/plain");
                        }

                        Book tempBook = new Book((long) gutenbergId, bookName, author, 0, bookUrl, imageUrl);
                        bookRepository.save(tempBook);
                    }
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
            }
        }
 */
    }
}
