/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.model.persona.narrator;

import core.model.persona.Person;
import core.model.book.audioBook.AudioBook;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author edangulo
 */
public class Narrator extends Person {

    private final List<AudioBook> books;

    public Narrator(long id, String firstname, String lastname) {
        super(id, firstname, lastname);
        this.books = new ArrayList<>();
    }

    public List<AudioBook> getBooks() {
        return books;
    }

    public void addAudioBook(AudioBook book) {
        this.books.add(book);
    }

    public int getAudioBookCount() {
        return this.books.size();
    }

    void addAudioBook(AudioBook book) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
