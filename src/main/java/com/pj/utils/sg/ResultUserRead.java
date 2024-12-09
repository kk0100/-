package com.pj.utils.sg;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultUserRead {

    private Long userId;

    private String name;

    private boolean isRead;
}
