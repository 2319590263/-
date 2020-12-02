package com.dl.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class XmlPojo {
    private String tagName;
    private Integer level;
    private Map<String,String> labelProperties = new HashMap<>();
    private String value;
    private List<XmlPojo> subTags = new ArrayList<>();

    public Boolean isLowest() {
        return subTags.size() == 0;
    }

    public Boolean isTop() {
        return level == 0;
    }
}
