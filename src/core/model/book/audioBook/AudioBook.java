
package core.model.book.audioBook;

import core.model.persona.author.Author;
import core.model.persona.narrator.Narrator;
import core.Publisher;
import core.model.book.Book;
import java.util.List;

/**
 *
 * @author edangulo
 */
public class AudioBook extends Book {

    private final int duration;
    private final Narrator narrator;

    public AudioBook(
            String title, 
            List<Author> authors, 
            String isbn, 
            String genre, 
            String format, 
            double value, 
            Publisher publisher,
            int duration, 
            Narrator narrator
    ) {
        super(title, authors, isbn, genre, format, value, publisher);
        this.duration = duration;
        this.narrator = narrator;
    }

    public int getDuration() {
        return duration;
    }

    public Narrator getNarrator() {
        return narrator;
    }
}


