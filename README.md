# Library API - BizDigitalIT
A simple Java Spring Boot REST API for managing a library’s book catalog using in-memory storage.
### Features
- Add a new book
- Get all books
- Get book by ID
- Delete book by ID
- Update availability of a book (PATCH)

### How to run:
1. Clone the repository:
```bash
git clone https://github.com/nikhilmangali1/LibraryAPI.git
```
2. Go to Project directory:
```bash
cd LibraryAPI
```
3. Build and run the application:
```bash
./mvnw spring-boot:run
```

## API Endpoints - postman
1. Add a book:
```bash
{
  "title": "Design Patterns: Elements of Reusable Object-Oriented Software",
  "author": "Erich Gamma, Richard Helm, Ralph Johnson, John Vlissides",
  "isbn": "9780201633610",
  "available": true
}
```
![POST - http://localhost:8080/api/books](/images/addBook.png)
2. Get All books:
![GET - http://localhost:8080/api/books](/images/getAllBooks.png)
3. Get Book by ID
![Get - http://localhost:8080/api/books/1](/images/getBookById.png)
4. Delete Book by ID
![DELETE - http://localhost:8080/api/books/4](/images/delete.png)
5. Update availability
```bash
{
  "available": false
}
```
![PATCH - http://localhost:8080/api/books/3/availability](/images/availability.png)
```bash
{
  "available": true
}
```
![PATCH - http://localhost:8080/api/books/3/availability](/images/availability.png)
