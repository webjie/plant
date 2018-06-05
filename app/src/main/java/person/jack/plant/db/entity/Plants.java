package person.jack.plant.db.entity;

import android.graphics.Bitmap;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.sql.Blob;
import java.util.Date;

/**
 * Created by yanxu on 2018/6/4.
 */
@DatabaseTable(tableName = "plants")
public class Plants {
    /**
     * id
     */
    @DatabaseField(columnName = "id",generatedId = true)
    private Integer id;

    /**
     * 图片
     */
    @DatabaseField (columnName = "image")
    private Integer Image;
    /**
     * 植物名称
     */
    @DatabaseField (columnName = "name")
    private String name;
    /**
     * 种植日期
     */
    @DatabaseField (columnName = "plantingDate")
    private Date plantingDate;
    /**
     * 生长阶段
     */
    @DatabaseField (columnName = "growthStage")
    private String growthStage;
    /**
     * 湿度
     */
    @DatabaseField (columnName = "hum")
    private Integer hum;
    /**
     * 光照
     */
    @DatabaseField (columnName = "light")
    private Integer light;
    /**
     * 温度
     */
    @DatabaseField (columnName = "temp")
    private Integer temp;


    public Plants() {
    }

    public Plants(Integer id, Integer image, String name, String growthStage, Date plantingDate) {
        this.id = id;
        this.Image = image;
        this.name = name;
        this.plantingDate = plantingDate;
        this.growthStage = growthStage;
    }

    public Plants(Integer id, Integer image, String name, Date plantingDate, String growthStage, Integer hum, Integer light, Integer temp) {
        this.id = id;
        Image = image;
        this.name = name;
        this.plantingDate = plantingDate;
        this.growthStage = growthStage;
        this.hum = hum;
        this.light = light;
        this.temp = temp;
    }

    public Integer getHum() {
        return hum;
    }

    public void setHum(Integer hum) {
        this.hum = hum;
    }

    public Integer getLight() {
        return light;
    }

    public void setLight(Integer light) {
        this.light = light;
    }

    public Integer getTemp() {
        return temp;
    }

    public void setTemp(Integer temp) {
        this.temp = temp;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getImage() {
        return Image;
    }

    public void setImage(Integer image) {
        Image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getPlantingDate() {
        return plantingDate;
    }

    public void setPlantingDate(Date plantingDate) {
        this.plantingDate = plantingDate;
    }

    public String getGrowthStage() {
        return growthStage;
    }

    public void setGrowthStage(String growthStage) {
        this.growthStage = growthStage;
    }
}
