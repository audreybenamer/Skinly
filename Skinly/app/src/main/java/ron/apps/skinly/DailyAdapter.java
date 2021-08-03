package ron.apps.skinly;

import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import io.realm.OrderedRealmCollection;
import io.realm.RealmRecyclerViewAdapter;

// the parameterization <type of the RealmObject, ViewHolder type)
public class DailyAdapter extends RealmRecyclerViewAdapter<Daily, DailyAdapter.ViewHolder> {

    // IMPORTANT
    // THE CONTAINING ACTIVITY NEEDS TO BE PASSED SO YOU CAN GET THE LayoutInflator(see below)
    MainScreen activity;

    public DailyAdapter(MainScreen activity, @Nullable OrderedRealmCollection<Daily> data, boolean autoUpdate) {
        super(data, autoUpdate);

        // THIS IS TYPICALLY THE ACTIVITY YOUR RECYCLERVIEW IS IN
        this.activity = activity;
    }

    // THIS DEFINES WHAT VIEWS YOU ARE FILLING IN
    public class ViewHolder extends RecyclerView.ViewHolder {

        // have a field for each one
        TextView date_mainscreen, amproducts_main, pmproducts_main, notes_main;
        CheckBox am_main, pm_main;

        ImageButton editdaily_main;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            // initialize them from the itemView using standard style
            date_mainscreen = itemView.findViewById(R.id.date_mainscreen);
            amproducts_main = itemView.findViewById(R.id.amproducts_main);
            am_main = itemView.findViewById(R.id.am_main);
            pm_main = itemView.findViewById(R.id.pm_main);
            pmproducts_main = itemView.findViewById(R.id.pmproducts_main);
            notes_main = itemView.findViewById(R.id.notes_main);
            // initialize the buttons in the layout
            editdaily_main = itemView.findViewById(R.id.editdaily_main);
        }
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        // create the raw view for this ViewHolder
        View v = activity.getLayoutInflater().inflate(R.layout.mainscreen_layout, parent, false);  // VERY IMPORTANT TO USE THIS STYLE

        // assign view to the viewholder
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        // gives you the data object at the given position
        Daily d = getItem(position);


        // copy all the values needed to the appropriate views
        holder.date_mainscreen.setText(d.getDate());



        holder.pmproducts_main.setText(d.getRoutine_pm());


        holder.amproducts_main.setText(d.getRoutine_am());

        if(d.getNotes()!=null) {
            holder.notes_main.setText("Note: "+d.getNotes());
        }
        holder.am_main.setChecked(d.isAm_checked());
        holder.pm_main.setChecked(d.isPm_checked());
        holder.am_main.setEnabled(false);
        holder.pm_main.setEnabled(false);


        // do any other initializations here as well,  e.g. Button listeners

        holder.editdaily_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.edit(d.getUuid(), d.getDate());
            }
        });

    }

}
