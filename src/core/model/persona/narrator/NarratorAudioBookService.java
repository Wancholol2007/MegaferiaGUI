/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.model.persona.narrator;

import core.model.persona.narrator.Narrator;
import core.model.book.audioBook.AudioBook;


public class NarratorAudioBookService {

    // Añadir audiolibros
    public void assignAudioBook(Narrator narrator, AudioBook book) {
        narrator.addAudioBook(book);
    }

    // Obtener cantidad
    public int countAudioBooks(Narrator narrator) {
        return narrator.getAudioBookCount();
    }

    // Ejemplo de futura lógica extendida:
    public int totalAudioDuration(Narrator narrator) {
        int total = 0;
        for (AudioBook book : narrator.getAudioBooks()) {
            total += book.getDuration();
        }
        return total;
    }
}