package ron.apps.skinly;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import io.realm.Realm;
import io.realm.RealmResults;

@EActivity(R.layout.activity_edit_daily_screen)
public class EditDailyScreen extends AppCompatActivity {

    Realm realm;
    SharedPreferences prefs;

    @Extra
    String uuid;

    @Extra
    String date;

    @ViewById
    TextView date_today_edit, note_editdailyscreen;

    @ViewById
    CheckBox amchecked_edit, pmchecked_edit;

    Daily existing_record;

    @ViewById
    RecyclerView amproducts_edit, pmproducts_edit;

    List<String> productscheckedam=new ArrayList<String>();
    List<String> productscheckedpm=new ArrayList<String>();

    List<String> am_placeholder;
    List<String> pm_placeholder;

    @AfterViews
    public void init_editdailyscreen(){
        realm = Realm.getDefaultInstance();

        prefs = getSharedPreferences("user_data", MODE_PRIVATE);
        String saved_uuid = prefs.getString("uuid", null);


        User saved_user = realm.where(User.class).equalTo("uuid", saved_uuid).findFirst();
        existing_record = realm.where(Daily.class)
                .equalTo("uuid",uuid)
                .equalTo("date",date)
                .findFirst();

        date_today_edit.setText(existing_record.getDate());
        note_editdailyscreen.setText(existing_record.getNotes());
        amchecked_edit.setChecked(existing_record.isAm_checked());
        pmchecked_edit.setChecked(existing_record.isPm_checked());
        productscheckedam = Arrays.asList(existing_record.getRoutine_am().split("\n"));
        productscheckedpm = Arrays.asList(existing_record.getRoutine_pm().split("\n"));
        am_placeholder=new ArrayList<String>(productscheckedam);
        pm_placeholder=new ArrayList<String>(productscheckedpm);


        LinearLayoutManager mlayoutmanager = new LinearLayoutManager(this);
        mlayoutmanager.setOrientation(RecyclerView.VERTICAL);
        pmproducts_edit.setLayoutManager(mlayoutmanager);

        LinearLayoutManager mmlayoutmanager = new LinearLayoutManager(this);
        mmlayoutmanager.setOrientation(RecyclerView.VERTICAL);
        amproducts_edit.setLayoutManager(mmlayoutmanager);

        RealmResults<ProductsClass> list = realm.where(ProductsClass.class).findAll();
        EditRoutineAdapterAM adapter = new EditRoutineAdapterAM(this, list, true);
        amproducts_edit.setAdapter(adapter);

        RealmResults<ProductsClass> list1 = realm.where(ProductsClass.class).findAll();
        EditRoutineAdapterPM adapter1 = new EditRoutineAdapterPM(this, list1, true);
        pmproducts_edit.setAdapter(adapter1);
    }

    @Click(R.id.save_editdailyscreen)
    public void editdaily(){

        String delim = "\n";
        StringBuilder sb = new StringBuilder();
        int i = 0;
        while (i < pm_placeholder.size() - 1)
        {
            sb.append(pm_placeholder.get(i));
            sb.append(delim);
            i++;
        }
        sb.append(pm_placeholder.get(i));
        String res = sb.toString();

        StringBuilder sb2 = new StringBuilder();
        int i2 = 0;
        while (i2 < am_placeholder.size() - 1)
        {
            sb2.append(am_placeholder.get(i2));
            sb2.append(delim);
            i2++;
        }
        sb2.append(am_placeholder.get(i2));

        String res2 = sb2.toString();
        realm.beginTransaction();
        existing_record.setAm_checked(amchecked_edit.isChecked());
        existing_record.setPm_checked(pmchecked_edit.isChecked());
        existing_record.setNotes(note_editdailyscreen.getText().toString());
        existing_record.setRoutine_am(res2);
        existing_record.setRoutine_pm(res);

        realm.copyToRealmOrUpdate(existing_record);
        realm.commitTransaction();

        Toast t = Toast.makeText(this, "Entry Saved!", Toast.LENGTH_LONG);
        t.show();
        finish();
    }

    public void checkproductAM(String name){
        am_placeholder.add(name);
    }

    public void uncheckproductAM(String name){
        am_placeholder.remove(name);
    }

    public void checkproductPM(String name){
        pm_placeholder.add(name);
    }

    public void uncheckproductPM(String name){
        pm_placeholder.remove(name);
    }

    @Click(R.id.delete_editdaily)
    public void deletedaily(){
        if(existing_record.isValid()){
            realm.beginTransaction();
            existing_record.deleteFromRealm();
            realm.commitTransaction();
            Toast t5 = Toast.makeText(this, "Record deleted!", Toast.LENGTH_LONG);
            t5.show();
            finish();
        }
    }

    @Click(R.id.cancel_editdailyscreen)
    public void canceledit(){
        finish();
    }

    public void onDestroy(){
        super.onDestroy();
        if (!realm.isClosed()) {
            realm.close();
        }
    }
}