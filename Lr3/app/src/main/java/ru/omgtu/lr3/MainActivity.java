package ru.omgtu.lr3;
import ru.omgtu.lr3.fragments.ImageFragment;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import ru.omgtu.lr3.fragments.ChatConstraintFragment;
import ru.omgtu.lr3.fragments.ChatLinearFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnLinear = findViewById(R.id.btnLinear);
        Button btnConstraint = findViewById(R.id.btnConstraint);

        btnLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFragment(new ChatLinearFragment());
            }
        });

        Button btnFrame = findViewById(R.id.btnFrame);

        btnFrame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFragment(new ImageFragment());
            }
        });

        btnConstraint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFragment(new ChatConstraintFragment());
            }
        });

        // Показываем LinearLayout чат по умолчанию
        if (savedInstanceState == null) {
            openFragment(new ChatLinearFragment());
        }
    }

    private void openFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
    }
}