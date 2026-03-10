package ru.omgtu.lr4.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import ru.omgtu.lr4.R;

public class MainActivity extends AppCompatActivity {

    private EditText editName;
    private TextView tvResult;
    private ImageView imageView;
    private Button btnOpenSecond;
    private Button btnTestShare;
    private Button btnPlayMarket;
    private Button btnCamera;

    private ActivityResultLauncher<Intent> secondActivityLauncher;

    private static final int CAMERA_PERMISSION_CODE = 100;
    private static final int CAMERA_REQUEST_CODE = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editName = findViewById(R.id.editName);
        tvResult = findViewById(R.id.tvResult);
        imageView = findViewById(R.id.imageView);
        btnOpenSecond = findViewById(R.id.btnOpenSecond);
        btnTestShare = findViewById(R.id.btnTestShare);
        btnPlayMarket = findViewById(R.id.btnPlayMarket);
        btnCamera = findViewById(R.id.btnCamera);

        // Лаунчер для SecondActivity
        secondActivityLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        long date = result.getData().getLongExtra("selected_date", 0);
                        tvResult.setText("Выбрана дата: " + date);
                    }
                }
        );

        // Кнопка открытия SecondActivity
        btnOpenSecond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editName.getText().toString().trim();
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                intent.putExtra("user_name", name);
                secondActivityLauncher.launch(intent);
            }
        });

        // Кнопка теста шаринга
        btnTestShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri testUri = Uri.parse("android.resource://" + getPackageName() + "/" + R.mipmap.ic_launcher);
                tvResult.setText("Тест: " + testUri.toString());
                Toast.makeText(MainActivity.this, "Тест сработал", Toast.LENGTH_SHORT).show();

                imageView.setVisibility(View.VISIBLE);
                imageView.setImageURI(testUri);
            }
        });

        // Кнопка Play Market
        btnPlayMarket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("market://details?id=" + getPackageName()));
                intent.setPackage("com.android.vending");
                try {
                    startActivity(intent);
                } catch (Exception e) {
                    Intent webIntent = new Intent(Intent.ACTION_VIEW);
                    webIntent.setData(Uri.parse("https://play.google.com/store/apps/details?id=" + getPackageName()));
                    startActivity(webIntent);
                }
            }
        });

        // Кнопка камеры
        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkCameraPermission();
            }
        });
    }

    // Проверка разрешения на камеру
    private void checkCameraPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED) {
            openCamera();
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA},
                    CAMERA_PERMISSION_CODE);
        }
    }

    // Открытие камеры
    private void openCamera() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, CAMERA_REQUEST_CODE);
    }

    // Обработка результата с камеры
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CAMERA_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            Bundle extras = data.getExtras();
            if (extras != null) {
                android.graphics.Bitmap photo = (android.graphics.Bitmap) extras.get("data");
                if (photo != null) {
                    imageView.setVisibility(View.VISIBLE);
                    imageView.setImageBitmap(photo);
                    tvResult.setText("Фото получено с камеры");
                }
            }
        }
    }

    // Обработка ответа на запрос разрешения
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == CAMERA_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openCamera();
            } else {
                Toast.makeText(this, "Нет разрешения на камеру", Toast.LENGTH_SHORT).show();
            }
        }
    }

    // Обработка входящего Intent (для картинок)
    @Override
    protected void onStart() {
        super.onStart();

        Intent intent = getIntent();
        String action = intent.getAction();
        String type = intent.getType();

        if (Intent.ACTION_VIEW.equals(action) && type != null && type.startsWith("image/")) {
            Uri imageUri = intent.getData();
            if (imageUri != null) {
                tvResult.setText("Получена картинка: " + imageUri.toString());
                imageView.setVisibility(View.VISIBLE);
                imageView.setImageURI(imageUri);
            }
        }
    }
}