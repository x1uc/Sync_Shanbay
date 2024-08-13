package top.x1uc.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *  send data to the Server , while will then add the word to the notebook
 *  vocal_id is the word ID, but it is not the word identifier.
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ShanBayPostWord {
    int business_id = 6;
    String vocab_id;

    public ShanBayPostWord(String wordId) {
        this.vocab_id = wordId;
    }
}
