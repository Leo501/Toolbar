package com.leo.sleep.component.table;

import com.leo.sleep.modules.serializable.City;
import com.litesuits.orm.db.annotation.NotNull;
import com.litesuits.orm.db.annotation.PrimaryKey;
import com.litesuits.orm.db.annotation.Table;
import com.litesuits.orm.db.enums.AssignType;

/**
 * Created by leo70 on 2016/10/5.
 */

@Table("sleep_city")
public class CityORM {

    @PrimaryKey(AssignType.AUTO_INCREMENT)
    private int id;

    @NotNull
    private String name;

    public CityORM(String name){
        this.name=name;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }
}
