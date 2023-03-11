package com.teleri.userapi.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Filter {
    private String name;
    private String lastname;
    private String email;
    private String nationality;
    private Integer page;
    private Integer size;
}
