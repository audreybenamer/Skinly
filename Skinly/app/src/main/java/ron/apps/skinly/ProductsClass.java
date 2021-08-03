package ron.apps.skinly;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class ProductsClass extends RealmObject {

    @PrimaryKey
    private String uuid;

    private String productname;
    private String producttype;
    private String details;
    private String schedule;
    private String tips;
    //private String productimagepath;

    public ProductsClass()
    {
    }

    public ProductsClass(String uuid, String productname, String producttype, String details,
                         String schedule, String tips) {
        this.uuid = uuid;
        this.productname = productname;
        this.producttype = producttype;
        this.details = details;
        this.schedule = schedule;
        this.tips = tips;
    }

    public String getUuid() { return uuid; }

    public void setUuid(String uuid) { this.uuid = uuid; }

    public String getProductname() { return productname; }

    public void setProductname(String productname) { this.productname = productname; }

    public String getProducttype() { return producttype; }

    public void setProducttype(String producttype) { this.producttype = producttype; }

    public String getDetails() { return details; }

    public void setDetails(String details) { this.details = details; }

    public String getSchedule() { return schedule; }

    public void setSchedule(String schedule) { this.schedule = schedule; }

    public String getTips() { return tips; }

    public void setTips(String tips) { this.tips = tips; }

    @Override
    public String toString() {
        return "ProductsClass{" +
                "uuid='" + uuid + '\'' +
                ", productname='" + productname + '\'' +
                ", producttype='" + producttype + '\'' +
                ", details='" + details + '\'' +
                ", schedule='" + schedule + '\'' +
                ", tips='" + tips + '\'' +
                '}';
    }
}

