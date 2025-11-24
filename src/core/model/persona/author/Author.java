/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.model.persona.author;


import core.model.book.Book;
import core.model.persona.Person;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author edangulo
 */
public class Author extends Person {

    private final List<Book> books;

    public Author(long id, String firstname, String lastname) {
        super(id, firstname, lastname);
        this.books = new ArrayList<>();
    }

    public List<Book> getBooks() {
        return books;
    }

    public void addBook(Book book) {
        this.books.add(book);
    }

    public int getBookCount() {
        return this.books.size();
    }
}
