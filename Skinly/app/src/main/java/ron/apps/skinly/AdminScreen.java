package ron.apps.skinly;

import android.content.SharedPreferences;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import io.realm.Realm;
import io.realm.RealmResults;

@EActivity(R.layout.activity_admin_screen)
public class AdminScreen extends AppCompatActivity {

    @ViewById
    RecyclerView recyclerView;

    Realm realm;

    SharedPreferences prefs;

    @AfterViews
    public void admin_init(){
        LinearLayoutManager mlayoutmanager = new LinearLayoutManager(this);
        mlayoutmanager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(mlayoutmanager);

        realm = Realm.getDefaultInstance();

        RealmResults<User> list = realm.where(User.class).findAll();
        UserAdapter adapter = new UserAdapter(this, list, true);
        recyclerView.setAdapter(adapter);
    }

    public void delete(User u){
        if(u.isValid()){
            realm.beginTransaction();
            u.deleteFromRealm();
            realm.commitTransaction();
        }
    }

    public void edit(String uuid){
        EditUser_.intent(this).uuid(uuid).start();
    }

    @Click(R.id.addbutton)
    public void add(){
        RegisterScreen_.intent(this).start();
    }

    @Click(R.id.clearbutton2)
    public void clearRealm(){
        realm.beginTransaction();
        realm.deleteAll();
        realm.commitTransaction();

        prefs = getSharedPreferences("user_data", MODE_PRIVATE);
        SharedPreferences.Editor edit = prefs.edit();
        edit.clear();
        edit.apply();

        Toast t = Toast.makeText(this,"Realm cleared", Toast.LENGTH_LONG);
        t.show();
    }

    public void onDestroy(){
        super.onDestroy();
        if(!realm.isClosed()){
            realm.close();
        }
    }
}