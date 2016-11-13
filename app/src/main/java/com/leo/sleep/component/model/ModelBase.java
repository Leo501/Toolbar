package com.leo.sleep.component.model;

import com.litesuits.orm.db.annotation.Column;
import com.litesuits.orm.db.annotation.PrimaryKey;
import com.litesuits.orm.db.enums.AssignType;

/**
 * Created by leo70 on 2016/11/6.
 */

public class ModelBase {

    //指定主键，并自增
    @PrimaryKey(AssignType.AUTO_INCREMENT)
    // 取名为“_id”,如果此处不重新命名,就采用属性名称
    @Column("_id")
    protected int id;
}
