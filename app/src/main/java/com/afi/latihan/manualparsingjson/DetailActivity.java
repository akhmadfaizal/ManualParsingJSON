package com.afi.latihan.manualparsingjson;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

public class DetailActivity extends AppCompatActivity {

    /*
       serves as a key to get the value of the data sent
       Sebagai kunci untuk mendapatkan nilai data yang dikirim
   */
    public static final String EXTRA_USER = "extra_user";

    // Variable
    TextView tvFirstNameDet;
    ImageView imgAvatarDet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // initialize variable with view
        tvFirstNameDet = findViewById(R.id.tv_first_name_det);
        imgAvatarDet = findViewById(R.id.img_avatar_det);

        // to get data from intent using parcelable
        User user = getIntent().getParcelableExtra(EXTRA_USER);

        // to display data object that received
        String firstNameDet = user.getFirstName();
        String avatarDet = user.getAvatar();

        tvFirstNameDet.setText(firstNameDet);

        Glide.with(this)
                .load(avatarDet)
                .apply(new RequestOptions().override(350,350))
                .into(imgAvatarDet);
    }
}
