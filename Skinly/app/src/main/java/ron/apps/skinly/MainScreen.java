package ron.apps.skinly;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import io.realm.Realm;
import io.realm.RealmResults;

@EActivity(R.layout.activity_main_screen)
public class MainScreen extends AppCompatActivity {

    @ViewById
    RecyclerView recyclerview_mainscreen;

    @ViewById
    TextView welcomemessage_mainscreen;

    Realm realm;

    SharedPreferences prefs;

    @ViewById
    FloatingActionButton adddaily_mainscreen;

    @AfterViews
    public void init_mainscreen() {

        realm = Realm.getDefaultInstance();
        LinearLayoutManager mlayoutmanager = new LinearLayoutManager(this);
        mlayoutmanager.setOrientation(RecyclerView.VERTICAL);
        recyclerview_mainscreen.setLayoutManager(mlayoutmanager);

        RealmResults<Daily> list = realm.where(Daily.class).findAll();
        DailyAdapter adapter = new DailyAdapter(this, list, true);
        recyclerview_mainscreen.setAdapter(adapter);

        prefs = getSharedPreferences("user_data", MODE_PRIVATE);
        String saved_uuid = prefs.getString("uuid", null);
        String username_get = realm.where(User.class).equalTo("uuid", saved_uuid).findFirst().getName();
        welcomemessage_mainscreen.setText("Welcome, "+username_get+"!");
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
        String formattedDate = df.format(c);


    }

    public void edit(String uuid, String date) {
        EditDailyScreen_.intent(this).uuid(uuid).date(date).start();
    }

    @Click(R.id.editprofile_mainscreen)
    public void edit_profile(){

    }

    @Click(R.id.products_mainscreen)
    public void viewproducts(){
        Products_.intent(this).start();
    }

    @Click(R.id.adddaily_mainscreen)
    public void add_dailyscreen(){
        DailyScreen_.intent(this).start();
    }

    public void onDestroy() {
        super.onDestroy();
        if (!realm.isClosed()) {
            realm.close();
        }
    }
}