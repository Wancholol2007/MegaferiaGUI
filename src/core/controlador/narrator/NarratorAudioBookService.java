package core.controlador.narrator;

import core.Audiobook;
import core.Narrator;


public class NarratorAudioBookService {

    public static void assignNarration(Narrator narrator, Audiobook audioBook) {
        narrator.addAudioBook(audioBook);
    }
}
