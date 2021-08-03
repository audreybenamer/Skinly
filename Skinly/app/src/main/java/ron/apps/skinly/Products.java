package ron.apps.skinly;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.widget.Button;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import io.realm.Realm;
import io.realm.RealmResults;

@EActivity(R.layout.activity_products)
public class Products extends AppCompatActivity {

    @ViewById
    Button addproduct;
    @ViewById
    Button productsback;

    @ViewById
    RecyclerView productstable;

    Realm realm;

    @Click(R.id.addproduct)
    public void addproduct() { AddProduct_.intent(this).start(); }

    @Click(R.id.productsback)
    public void productsback() { MainScreen_.intent(this).start(); }

    public void predit( ProductsClass pr )
    {
        if (pr.isValid())
        {
//            Intent intent = new Intent(this, EditProduct_.class);
//            intent.putExtra("pruuid", pr.getUuid());
//            startActivity(intent);
        }
    }

    public void prdelete( ProductsClass pr )
    {
        if (pr.isValid())
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setCancelable(true);
            builder.setTitle("Delete Product");
            builder.setMessage("Are you sure you want to delete this product?");
            builder.setPositiveButton("Confirm",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            realm.beginTransaction();
                            pr.deleteFromRealm();
                            realm.commitTransaction();
                        }
                    });
            builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });

            AlertDialog dialog = builder.create();
            dialog.show();
        }
    }

    @AfterViews
    public void init()
    {
        LinearLayoutManager LayoutManager = new LinearLayoutManager(this);
        LayoutManager.setOrientation(RecyclerView.VERTICAL);
        productstable.setLayoutManager((LayoutManager));

        realm = Realm.getDefaultInstance();

        RealmResults<ProductsClass> list = realm.where(ProductsClass.class).findAll();

        ProductsAdapter adapter = new ProductsAdapter(this, list, true);
        productstable.setAdapter(adapter);
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
