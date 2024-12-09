package com.pj.project.mine;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 煤矿表实体类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SpMine {
    private long mineId;

    private String mineName;
}
