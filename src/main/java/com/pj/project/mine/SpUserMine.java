package com.pj.project.mine;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SpUserMine {

    private Long id;

    private Long userId;

    private Long mineId;

    //额外字段
    private List<Long> mineIds;
}
