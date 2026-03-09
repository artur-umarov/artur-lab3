package ru.omgtu.lr3.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import ru.omgtu.lr3.R;

public class ImageFragment extends Fragment {

    private ImageView imageView;
    private ImageButton btnChangeImage;
    private int currentImageIndex = 0;

    private int[] images = {
            R.drawable.image1,
            R.drawable.image2,
            R.drawable.image3,
            R.drawable.image4
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_image, container, false);

        imageView = view.findViewById(R.id.imageView);
        btnChangeImage = view.findViewById(R.id.btnChangeImage);

        imageView.setImageResource(images[0]);

        btnChangeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentImageIndex = (currentImageIndex + 1) % images.length;
                imageView.setImageResource(images[currentImageIndex]);
            }
        });

        return view;
    }
}