package ron.apps.skinly;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import io.realm.Realm;
import io.realm.RealmResults;

@EActivity(R.layout.activity_daily_screen)
public class DailyScreen extends AppCompatActivity {

    Realm realm;
    SharedPreferences prefs;
    String formattedDate;

    List<String> productscheckedam=new ArrayList<String>();
    List<String> productscheckedpm=new ArrayList<String>();

    DatePicker datePicker;
    Calendar calendar;
    TextView dateView;
    int year, month, day;

    @ViewById
    RecyclerView am_products, pm_products;

    @ViewById
    CheckBox amchecked_daily, pmchecked_daily;

    @ViewById
    TextView note_dailyscreen;

    @ViewById
    Button date_today, save_dailyscreen;

    @Click(R.id.save_dailyscreen)
    public void adddailyscreen() {
        String delim = "\n";
        String res = "", res2 = "";
        if (!productscheckedpm.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            int i = 0;
            while (i < productscheckedpm.size() - 1) {
                sb.append(productscheckedpm.get(i));
                sb.append(delim);
                i++;
            }
            sb.append(productscheckedpm.get(i));
           res = sb.toString();
        }
        if(!productscheckedam.isEmpty())
        {
            StringBuilder sb2 = new StringBuilder();
            int i2 = 0;
            while (i2 < productscheckedam.size() - 1) {
                sb2.append(productscheckedam.get(i2));
                sb2.append(delim);
                i2++;
            }
            sb2.append(productscheckedam.get(i2));

            res2 = sb2.toString();
        }
        Daily d = new Daily();
        d.setAm_checked(amchecked_daily.isChecked());
        d.setPm_checked(pmchecked_daily.isChecked());
        d.setDate(formattedDate);
        d.setRoutine_am(res2);
        d.setRoutine_pm(res);
        d.setUuid(prefs.getString("uuid", null).toString());
        d.setNotes(note_dailyscreen.getText().toString());

        realm.beginTransaction();
        realm.copyToRealmOrUpdate(d);
        realm.commitTransaction();

        Toast t = Toast.makeText(this, "Entry Saved!", Toast.LENGTH_LONG);
        t.show();

        finish();
    }

    @Click(R.id.date_today)
    public void change_date(){
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        //String dateholder = (monthOfYear + 1) + "-" + dayOfMonth + "-" + year;
                        //Daily check_date = realm.where(Daily.class).equalTo("date", dateholder).equalTo("uuid",prefs.getString("uuid", null)).findFirst();
                        date_today.setText((monthOfYear + 1) + "-" + dayOfMonth + "-" + year);
                        formattedDate=(monthOfYear + 1) + "-" + dayOfMonth + "-" + year;
                        save_dailyscreen.setClickable(true);

                    }
                }, year, month, day);
        datePickerDialog.show();
    }

    public void toasterror(){
        Toast t2 = Toast.makeText(this, "An Entry for this date already exists!", Toast.LENGTH_LONG);
        t2.show();
    }

    @Click(R.id.cancel_dailyscreen)
    public void cancel_dailyscreen(){
        finish();
    }

    @Click(R.id.addproduct_daily)
    public void addproduct_dailyscreen(){ Products_.intent(this).start();}

    @AfterViews
    public void init_dailyscreen(){
        realm = Realm.getDefaultInstance();

        LinearLayoutManager mlayoutmanager = new LinearLayoutManager(this);
        mlayoutmanager.setOrientation(RecyclerView.VERTICAL);
        pm_products.setLayoutManager(mlayoutmanager);

        LinearLayoutManager mmlayoutmanager = new LinearLayoutManager(this);
        mmlayoutmanager.setOrientation(RecyclerView.VERTICAL);
        am_products.setLayoutManager(mmlayoutmanager);

        RealmResults<ProductsClass> list = realm.where(ProductsClass.class).findAll();
        RoutineAdapterAM adapter = new RoutineAdapterAM(this, list, true);
        am_products.setAdapter(adapter);

        RealmResults<ProductsClass> list1 = realm.where(ProductsClass.class).findAll();
        RoutineAdapterPM adapter1 = new RoutineAdapterPM(this, list1, true);
        pm_products.setAdapter(adapter1);

        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("MM-dd-yyyy", Locale.getDefault());
        formattedDate = df.format(c);
        date_today.setText(formattedDate);

        prefs = getSharedPreferences("user_data", MODE_PRIVATE);
        String saved_uuid = prefs.getString("uuid", null);

    }

    public void checkproductAM(String name){
        productscheckedam.add(name);
    }

    public void uncheckproductAM(String name){
        productscheckedam.remove(name);
    }

    public void checkproductPM(String name){
        productscheckedpm.add(name);
    }

    public void uncheckproductPM(String name){
        productscheckedpm.remove(name);
    }

    public void onDestroy(){
        super.onDestroy();
        if (!realm.isClosed()) {
            realm.close();
        }
    }
}