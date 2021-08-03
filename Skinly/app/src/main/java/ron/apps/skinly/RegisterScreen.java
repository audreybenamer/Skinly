package ron.apps.skinly;

import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.UUID;

import io.realm.Realm;

@EActivity(R.layout.activity_register_screen)
public class RegisterScreen extends AppCompatActivity {


    Realm realm;

    @ViewById
    EditText uname, pw1, pw2;

    @Click(R.id.savebutton)
    public void save(){
        String uname_extract = uname.getText().toString();
        //find in realm
        User existing_user = realm.where(User.class).equalTo("name", uname_extract).findFirst();

        if(existing_user != null){
            Toast t = Toast.makeText(this, "User already exists", Toast.LENGTH_LONG);
            t.show();
        }
        else{
            String pw1_extract = pw1.getText().toString();
            String pw2_extract = pw2.getText().toString();
            if(pw1_extract.equals(pw2_extract)){
                User u = new User();
                u.setUuid(UUID.randomUUID().toString());
                u.setName(uname_extract);
                u.setPassword(pw1_extract);

                realm.beginTransaction();
                realm.copyToRealmOrUpdate(u);
                realm.commitTransaction();

                long count = realm.where(User.class).count();

                Toast t2 = Toast.makeText(this, "New User Saved. Total: "+count, Toast.LENGTH_LONG);
                t2.show();
                finish();
            }
            else{
                Toast t1 = Toast.makeText(this, "Confirm password does not match", Toast.LENGTH_LONG);
                t1.show();
            }
        }
    }

    @Click(R.id.cancelbutton)
    public void cancel(){
        finish();
    }

    @AfterViews
    public void init_register(){
        realm = Realm.getDefaultInstance();

    }

    public void onDestroy(){
        super.onDestroy();
        if(!realm.isClosed()){
            realm.close();
        }
    }
}