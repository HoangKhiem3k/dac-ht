package jp.co.hakuhodo.technologies.dto.common;

import lombok.Data;

@Data
public class ValidateInfo {

    /*
     * メッセージ内容
     */
    private String msg;

    /*
     * 項目物理名
     */
    private String fieldName;
}
