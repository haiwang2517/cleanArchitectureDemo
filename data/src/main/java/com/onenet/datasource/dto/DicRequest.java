package com.onenet.datasource.dto;

import java.util.List;

/**
 * DicRequest
 *
 * @author HaiYinLong
 * @version 2025/05/23 12:14
 **/
public class DicRequest {
    private List<String> codes;

    public DicRequest() {}

    public List<String> getCodes() {
        return codes;
    }

    public void setCodes(List<String> codes) {
        this.codes = codes;
    }
}
