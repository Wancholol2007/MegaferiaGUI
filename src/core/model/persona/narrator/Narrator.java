package core.model.persona.narrator;

import core.model.book.audioBook.AudioBook;
import core.model.persona.Person;
import java.util.ArrayList;

public class Narrator extends Person {

    private ArrayList<AudioBook> booksNarrated;

    public Narrator(long id, String nombres, String apellidos) {
        super(id, nombres, apellidos);
        booksNarrated = new ArrayList<>();
    }

    public void addAudioBook(AudioBook book) {
        this.booksNarrated.add(book);
    }

    public int getAudioBookCount() {
        return booksNarrated.size();
    }

    public ArrayList<AudioBook> getAudioBooks() {
        return booksNarrated;
    }
}
