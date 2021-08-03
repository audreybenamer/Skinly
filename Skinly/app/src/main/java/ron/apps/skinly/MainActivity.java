package ron.apps.skinly;

import android.content.SharedPreferences;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import io.realm.Realm;

@EActivity(R.layout.activity_main)
public class MainActivity extends AppCompatActivity {

    @ViewById
    EditText usernamelogin, passwordlogin;

    @ViewById
    CheckBox rememberme;

    SharedPreferences prefs;
    Realm realm;

    @Click(R.id.loginbutton)
    public void login(){
        String username_extract = usernamelogin.getText().toString();
        String password_extract = passwordlogin.getText().toString();
        //search for user in realm
        User existing_user = realm.where(User.class).equalTo("name", username_extract).findFirst();

        if(existing_user == null) //no user
        {
            Toast t = Toast.makeText(this, "No User found", Toast.LENGTH_LONG);
            t.show();
        }
        else
        {

            if(password_extract.equals(existing_user.getPassword())) //get password from realm
            {
                SharedPreferences.Editor editor = prefs.edit();
                editor.putBoolean("rememberstatus", rememberme.isChecked());
                editor.putString("uuid", existing_user.getUuid());
                editor.apply();
                MainScreen_.intent(this).start();
            }
            else
            {
                Toast t1 = Toast.makeText(this, "Invalid Credentials", Toast.LENGTH_LONG);
                t1.show();
            }
        }
    }

    @Click(R.id.adminbutton)
    public void admin(){
        AdminScreen_.intent(this).start();
    }

    @Click(R.id.clearbutton)
    public void clear(){
        SharedPreferences.Editor edit = prefs.edit();
        edit.clear();
        edit.apply();
        Toast t2 = Toast.makeText(this, "Preferences Cleared", Toast.LENGTH_LONG);
        t2.show();
    }

    @AfterViews
    public void init(){
        realm = Realm.getDefaultInstance();
        prefs = getSharedPreferences("user_data", MODE_PRIVATE);
        if(prefs.getBoolean("rememberstatus", false)) {
            String saved_uuid = prefs.getString("uuid", null);
            //search from Realm
            User saved_user = realm.where(User.class).equalTo("uuid", saved_uuid).findFirst();

            usernamelogin.setText(saved_user.getName());
            passwordlogin.setText(saved_user.getPassword());
            rememberme.setChecked(true);
        }
    }

    public void onDestroy(){
        super.onDestroy();
        if(!realm.isClosed()){
            realm.close();
        }
    }
}