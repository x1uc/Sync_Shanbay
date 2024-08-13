package top.x1uc.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author li
 * @date 2024/06/19
 * response by get youdao's collection book
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class YouDaoListResponse {
    private int code;
    private String msg;
    private YouDaoData data;
}
