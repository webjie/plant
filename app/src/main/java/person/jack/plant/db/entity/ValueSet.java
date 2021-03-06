package person.jack.plant.db.entity;

/**
 * 阈值设置类
 * Created by yanxu on 2018/6/4.
 */

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;


@DatabaseTable (tableName = "value_record")
public class ValueSet {
    /**
     * id
     */
    @DatabaseField (columnName = "id",generatedId = true)
    private int id;

    /**
     *植物名称
     */
    @DatabaseField (columnName = "name")
    private String name;
    /**
     * 最小值
     */
    @DatabaseField (columnName = "min")
    private Integer min;
    /**
     * 最大值
     */
    @DatabaseField (columnName = "max")
    private Integer max;

    public ValueSet() {
    }

    public ValueSet(int id, String name, Integer min, Integer max) {
        this.id = id;
        this.name = name;
        this.min = min;
        this.max = max;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getMin() {
        return min;
    }

    public void setMin(Integer min) {
        this.min = min;
    }

    public Integer getMax() {
        return max;
    }

    public void setMax(Integer max) {
        this.max = max;
    }
}
