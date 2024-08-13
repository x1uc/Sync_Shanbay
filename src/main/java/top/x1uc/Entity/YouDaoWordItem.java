package top.x1uc.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * @author li
 * @date 2024/06/19
 * YOUDAO word item
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class YouDaoWordItem {
    private String itemId;
    private String word;
    private String lanFrom;
    private String lanTo;
    private String trans;
    private String usphone;
    private String ukphone;
    private String createTime;
}
