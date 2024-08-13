package top.x1uc.Entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author li
 * @date 2024/06/19
 * wordId is word's id , word is word itself
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShanBayWordPair {
    private String id;
    private String word;
}
