package com.example.ikigai;

import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import yuku.ambilwarna.AmbilWarnaDialog;

public class DrawYourself extends AppCompatActivity {
    ConstraintLayout mLayout;
    int cor = Color.argb(255, 0, 0, 0);

    int mDefaultColor;
    Button mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PaintView paintView = new PaintView(this);
        setContentView(paintView);
    }


    public void openColorPicker() {
        AmbilWarnaDialog colorPicker = new AmbilWarnaDialog(this, mDefaultColor, new AmbilWarnaDialog.OnAmbilWarnaListener() {
            @Override
            public void onCancel(AmbilWarnaDialog dialog) {

            }

            @Override
            public void onOk(AmbilWarnaDialog dialog, int color) {
                mDefaultColor = color;
                Toast.makeText(getApplicationContext(),""+ color, Toast.LENGTH_LONG).show();
                mLayout.setBackgroundColor(mDefaultColor);
            }
        });

        colorPicker.show();
    }
}
