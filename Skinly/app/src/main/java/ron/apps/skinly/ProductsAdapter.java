package ron.apps.skinly;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import javax.annotation.Nullable;

import io.realm.OrderedRealmCollection;
import io.realm.RealmRecyclerViewAdapter;

public class ProductsAdapter extends RealmRecyclerViewAdapter<ProductsClass, ProductsAdapter.ViewHolder> {

    Products activity;

    public ProductsAdapter(Products activity,
                           @Nullable OrderedRealmCollection<ProductsClass> data,
                           boolean autoUpdate) {
        super(data, autoUpdate);

        this.activity = activity;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView prname;
        TextView prtype;

        ImageButton predit;
        ImageButton prdelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            prname = itemView.findViewById(R.id.prname);
            prtype = itemView.findViewById(R.id.prtype);

            predit = itemView.findViewById(R.id.predit);
            prdelete = itemView.findViewById(R.id.prdelete);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View prview = activity.getLayoutInflater().inflate(R.layout.products_row, parent, false);

        ViewHolder prvh = new ViewHolder(prview);
        return prvh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder prholder, int position) {

        ProductsClass pr = getItem(position);

        prholder.prname.setText(pr.getProductname());
        prholder.prtype.setText(pr.getProducttype());

        prholder.predit.setTag(pr);
        prholder.predit.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick(View view) { activity.predit((ProductsClass) view.getTag()); }
        });

        prholder.prdelete.setTag(pr);
        prholder.prdelete.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick(View view) { activity.prdelete((ProductsClass) view.getTag()); }
        });
    }

}
