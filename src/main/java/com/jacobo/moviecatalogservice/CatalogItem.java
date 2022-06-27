package com.jacobo.moviecatalogservice;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CatalogItem {
    private String title;
    private String desc;
    private int rating;
}
