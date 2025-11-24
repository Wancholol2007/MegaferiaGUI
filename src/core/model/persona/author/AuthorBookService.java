/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.model.persona.author;


import core.model.book.Book;
import core.Publisher;

import java.util.HashSet;
import java.util.Set;

public class AuthorBookService {

    // Agregar un libro al autor
    public static void addBookToAuthor(Author author, Book book) {
        author.addBook(book);
    }

    // Contar la cantidad de editoriales distintas
    public static int countDistinctPublishers(Author author) {
        Set<Publisher> publishers = new HashSet<>();

        for (Book book : author.getBooks()) {
            publishers.add((Publisher) book.getPublisher());
        }

        return publishers.size();
    }

    // Obtener los libros del autor (si se necesita)
    public static int getAuthorBookCount(Author author) {
        return author.getBookCount();
    }
}
