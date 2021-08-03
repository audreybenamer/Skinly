package ron.apps.skinly;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import io.realm.OrderedRealmCollection;
import io.realm.RealmRecyclerViewAdapter;

// the parameterization <type of the RealmObject, ViewHolder type)
public class UserAdapter extends RealmRecyclerViewAdapter<ron.apps.skinly.User, UserAdapter.ViewHolder> {

    // IMPORTANT
    // THE CONTAINING ACTIVITY NEEDS TO BE PASSED SO YOU CAN GET THE LayoutInflator(see below)
    AdminScreen activity;

    public UserAdapter(AdminScreen activity, @Nullable OrderedRealmCollection<ron.apps.skinly.User> data, boolean autoUpdate) {
        super(data, autoUpdate);

        // THIS IS TYPICALLY THE ACTIVITY YOUR RECYCLERVIEW IS IN
        this.activity = activity;
    }

    // THIS DEFINES WHAT VIEWS YOU ARE FILLING IN
    public class ViewHolder extends RecyclerView.ViewHolder {

        // have a field for each one
        TextView username_1, password_1;

        ImageButton deletebutton, editbutton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            // initialize them from the itemView using standard style
            username_1 = itemView.findViewById(R.id.username_1);
            password_1 = itemView.findViewById(R.id.password_1);

            // initialize the buttons in the layout
            deletebutton = itemView.findViewById(R.id.deletebutton);
            editbutton = itemView.findViewById(R.id.editbutton);
        }
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        // create the raw view for this ViewHolder
        View v = activity.getLayoutInflater().inflate(R.layout.layout, parent, false);  // VERY IMPORTANT TO USE THIS STYLE

        // assign view to the viewholder
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        // gives you the data object at the given position
        ron.apps.skinly.User u = getItem(position);


        // copy all the values needed to the appropriate views
        holder.username_1.setText(u.getName());
        holder.password_1.setText(u.getPassword());



        // NOTE: MUST BE A STRING NOT INTs, etc.
        // String.valueOf() converts most types to a string
        // holder.age.setText(String.valueOf(u.getAge()));

        // do any other initializations here as well,  e.g. Button listeners
        holder.deletebutton.setTag(u);
        holder.deletebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.delete((ron.apps.skinly.User) view.getTag());
            }
        });

        holder.editbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.edit(u.getUuid());
            }
        });

    }

}
