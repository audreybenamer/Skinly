package ron.apps.skinly;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.UUID;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

@EActivity(R.layout.activity_add_product)
public class AddProduct extends AppCompatActivity {

    @ViewById
    EditText productname;
    @ViewById
    EditText producttype;
    @ViewById
    EditText productschedule;
    @ViewById
    EditText producttips;
    @ViewById
    Button addprsave;
    @ViewById
    Button addprcancel;

    Realm realm;

    @Click(R.id.addprsave)
    public void addprsave()
    {
        realm = Realm.getDefaultInstance();

        String prodname = productname.getText().toString();
        String prodtype = producttype.getText().toString();
        //String prodschedule = productschedule.getText().toString();
        //String prodtips = producttips.getText().toString();

        if (prodname.equals("") || prodtype.equals(""))
        {
            Toast t = Toast.makeText(this,
                    "Please make sure product name and product type aren't blank",
                    Toast.LENGTH_LONG);
            t.show();
        }
        else
        {
            ProductsClass pr = new ProductsClass();

            realm.beginTransaction();
            pr.setUuid(UUID.randomUUID().toString());
            pr.setProductname(productname.getText().toString());
            pr.setProducttype(producttype.getText().toString());
            pr.setSchedule(productschedule.getText().toString());
            pr.setTips(producttips.getText().toString());
            realm.copyToRealmOrUpdate(pr);
            realm.commitTransaction();

            RealmResults<ProductsClass> results = realm.where(ProductsClass.class).findAll();

            results = results.sort("productname", Sort.DESCENDING);

            for (ProductsClass i : results)
            {
                Log.d("ProductsRealm", i.toString());
            }


            long prcount = realm.where(ProductsClass.class).count();

            Toast saved = Toast.makeText(this, "New product saved. Total: " + prcount, Toast.LENGTH_LONG);
            saved.show();

            Products_.intent(this).start();
        }
    }

    @Click(R.id.addprcancel)
    public void addprcancel()
    {
        MainActivity_.intent(this).start();
    }

    public void onDestroy()
    {
        super.onDestroy();
        if (!realm.isClosed())
        {
            realm.close();
        }
    }



}
