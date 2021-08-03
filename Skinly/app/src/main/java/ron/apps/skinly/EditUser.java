package ron.apps.skinly;

import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;

import io.realm.Realm;

@EActivity(R.layout.activity_edit_user)
public class EditUser extends AppCompatActivity {

    @Extra
    String uuid;

    Realm realm;

    @ViewById
    EditText uname2, pw12, pw22;

    User currentuser;



    @Click(R.id.cancelbutton2)
    public void cancel2(){
        finish();
    }

    @Click(R.id.savebutton2)
    public void save2() {

        String uname_extract = uname2.getText().toString();

        User result = realm.where(User.class).equalTo("name", uname_extract).findFirst();

        if (result!=null && !result.getName().equals(currentuser.getName()))  {
            Toast t = Toast.makeText(this, "User already exists", Toast.LENGTH_LONG);
            t.show();
        }
        else{
            String pw1_extract = pw12.getText().toString();
            String pw2_extract = pw22.getText().toString();
            if (pw1_extract.equals(pw2_extract)) {
                realm.beginTransaction();
                currentuser.setName(uname_extract);
                currentuser.setPassword(pw1_extract);
                realm.commitTransaction();
                Toast t2 = Toast.makeText(this, "User Edit Saved", Toast.LENGTH_LONG);
                t2.show();
                finish();
            }
            else {
                Toast t1 = Toast.makeText(this, "Confirm password does not match", Toast.LENGTH_LONG);
                t1.show();
            }
        }

    }

    @AfterViews
    public void init_edituser(){
        realm = Realm.getDefaultInstance();
        currentuser = realm.where(User.class).equalTo("uuid", uuid).findFirst();
        uname2.setText(currentuser.getName());
        pw12.setText(currentuser.getPassword());
        pw22.setText(currentuser.getPassword());
    }

    public void onDestroy(){
        super.onDestroy();
        if(!realm.isClosed()){
            realm.close();
        }
    }
}