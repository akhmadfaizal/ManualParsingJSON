package com.afi.latihan.manualparsingjson;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

/*
    Create the basic adapter extending from RecyclerView.Adapter
    Membuat dasar adapter extending dari RecyclerView.Adapter
*/
/*
    Note that i specify the custom ViewHolder which gives me access to my views
    Perhatikan bahwa saya menentukan ViewHolder khusus, yang memberi saya akses ke tampilan saya
*/
public class ListUserAdapter extends RecyclerView.Adapter<ListUserAdapter.ListViewHolder> {

    /*
       Store variable group for user.
       Simpan kelompok variabel untuk user
    */
    private ArrayList<User> listUser;

    /*
        put array user to constructor.
        Menempatkan user array ke kontruktor.
    */
    public ListUserAdapter(ArrayList<User> list) {
        this.listUser = list;
    }

    /*
         Usually involves inflating a layout from XML and returning the holder
         Biasanya melibatkan menggembungkan tata letak dari XML dan mengembalikan holder
    */
    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        // inflate the custom layout
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);

        // Returning a new holder instance
        return new ListViewHolder(view);
    }

    // involves populating data into the item through holder
    @Override
    public void onBindViewHolder(@NonNull ListViewHolder holder, int position) {
        // Get the data model based on position
        final User user = listUser.get(position);

        // Set item views based on your views and data model
        holder.tvFirstName.setText(user.getFirstName());
        holder.tvEmail.setText(user.getEmail());

        // Used to load an image
        Glide.with(holder.itemView.getContext())
                .load(user.getAvatar())
                .apply(new RequestOptions().override(350,350))
                .into(holder.imgAvatar);

        // Listener
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // To carry out an Activity
                Intent intent = new Intent(v.getContext(), DetailActivity.class);
                // Send object data to class with key and value
                intent.putExtra(DetailActivity.EXTRA_USER, user);
                // start activity
                v.getContext().startActivity(intent);
            }
        });
    }

    // Return the total count of items in the list
    @Override
    public int getItemCount() {
        return listUser.size();
    }

    /*
        Provide a direct reference to each of the views within a data item
        Menyediakan referensi langsung ke setiap tampilan dalam item data
    */
    /*
        Used to cache the views within the item layout for fast access
        Digunakan untuk me-cache tampilan dalam tata letak item untuk akses cepat
    */
    public class ListViewHolder extends RecyclerView.ViewHolder {
        /*
            Your holder should contain a member variable
            holder anda harus berisi variabel anggota
        */
        /*
            for any view that will be set as you render a row
            untuk tampilan apapun yang akan ditetapkan saat anda merender baris
        */
        TextView tvFirstName, tvEmail;
        ImageView imgAvatar;

        /*
            i also create a constructor that accepts the entire item row
            saya juga membuat constructor ynag menerima semua baris item
        */
        /*
             and does the view lookups to find each subview
             dan apakah tampilan pencarian untuk menemukan setiap subview
         */
        public ListViewHolder(@NonNull View itemView) {

            /*
                Stores the itemView in a public final member variable that can be used
                menyimpan itemView di public final member yang akan bisa digunakan
            */
            /*
                to access the context from any ViewHolder instance.
                untuk mengakses context dari turunan ViewHolder apapun.
            */
            super(itemView);
            tvFirstName = itemView.findViewById(R.id.tv_first_name);
            tvEmail = itemView.findViewById(R.id.tv_email);
            imgAvatar = itemView.findViewById(R.id.img_avatar);
        }
    }
}