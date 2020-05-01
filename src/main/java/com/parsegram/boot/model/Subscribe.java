package com.parsegram.boot.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Subscribe {

    private UUID id;
    private Long sum;
    private Date created;
    private Date cancel;
}
