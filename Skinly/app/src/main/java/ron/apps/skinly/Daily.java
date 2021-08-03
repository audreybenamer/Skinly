package ron.apps.skinly;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Daily extends RealmObject {


    private String uuid;
    @PrimaryKey
    private String date;
    private String routine_am, routine_pm, dailyimagepath, notes;
    private boolean am_checked, pm_checked;

    public Daily(){}

    public Daily(String uuid, String date, String routine_am, String routine_pm, String dailyimagepath, String notes, boolean am_checked, boolean pm_checked) {
        this.uuid = uuid;
        this.date = date;
        this.routine_am = routine_am;
        this.routine_pm = routine_pm;
        this.dailyimagepath = dailyimagepath;
        this.notes = notes;
        this.am_checked = am_checked;
        this.pm_checked = pm_checked;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getRoutine_am() {
        return routine_am;
    }

    public void setRoutine_am(String routine_am) {
        this.routine_am = routine_am;
    }

    public String getRoutine_pm() {
        return routine_pm;
    }

    public void setRoutine_pm(String routine_pm) {
        this.routine_pm = routine_pm;
    }

    public String getDailyimagepath() {
        return dailyimagepath;
    }

    public void setDailyimagepath(String dailyimagepath) {
        this.dailyimagepath = dailyimagepath;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public boolean isAm_checked() {
        return am_checked;
    }

    public void setAm_checked(boolean am_checked) {
        this.am_checked = am_checked;
    }

    public boolean isPm_checked() {
        return pm_checked;
    }

    public void setPm_checked(boolean pm_checked) {
        this.pm_checked = pm_checked;
    }
}
