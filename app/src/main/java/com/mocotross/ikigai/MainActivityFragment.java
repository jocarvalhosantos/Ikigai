package com.mocotross.ikigai;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

public class MainActivityFragment extends Fragment {
    private DoodleView doodleView;
    private float acceleration;
    private float lastAcceleration;
    private float currentAcceleration;
    private boolean dialogOnScreen = false;
    private static final int ACCELERATION_THRESHOLD = 1000000;
    private static final int SAVE_IMAGE_PERMISSION_REQUEST_CODE = 1;


    @Override
    public void onResume() {
        super.onResume();
        enableAccelerometerListening();
    }

    @Override
    public void onPause() {
        super.onPause();
        disableAccelerometerListening();
    }

    private void disableAccelerometerListening (){
        SensorManager manager = (SensorManager)
                getActivity().getSystemService(Context.SENSOR_SERVICE);
        manager.
                unregisterListener(sensonEventListener,
                        manager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) );
    }

    private void enableAccelerometerListening(){
        SensorManager manager = (SensorManager)
                getActivity().getSystemService(Context.SENSOR_SERVICE);

        manager.registerListener(
                sensonEventListener,
                manager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_NORMAL
        );

    }





    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View v = inflater.
                inflate(R.layout.fragment_main, container, false);
        setHasOptionsMenu(true);
        doodleView = v.findViewById(R.id.doodleView);
        currentAcceleration = SensorManager.GRAVITY_EARTH;
        lastAcceleration = SensorManager.GRAVITY_EARTH;
        acceleration = 0.0f;
        return v;

    }
    private final SensorEventListener sensonEventListener =
            new SensorEventListener() {
                @Override
                public void onSensorChanged(SensorEvent event) {
                    if (!dialogOnScreen){
                        String TAG = "TESTEACELEROMETRO";
                        float x = event.values[0];
                        float y = event.values[1];
                        float z = event.values[2];
                        Log.i(TAG, "x: " + x);
                        Log.i(TAG, "y: " + y);
                        Log.i(TAG, "z: " + z);

                        lastAcceleration = currentAcceleration;
                        currentAcceleration =
                                x * x + y * y + z * z;
                        acceleration =
                                currentAcceleration *
                                        (currentAcceleration - lastAcceleration);

                        if (acceleration > ACCELERATION_THRESHOLD){
                            //confirmErase();
                        }
                    }
                }

                @Override
                public void onAccuracyChanged(Sensor sensor, int accuracy) {

                }
            };

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

    }



    private void saveImage() {
        if (getContext().
                checkSelfPermission(
                        Manifest.permission.WRITE_EXTERNAL_STORAGE) !=
                PackageManager.PERMISSION_GRANTED){
            //permissão ainda não concedida
            //usuário já disse não uma vez? se sim, explicar
            if (shouldShowRequestPermissionRationale(
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
            )){
                //constroi um (builder de) diálogo
                AlertDialog.Builder builder =
                        new AlertDialog.Builder(getActivity());
                builder.setMessage(R.string.permission_explanation);
                builder.setPositiveButton(
                        android.R.string.ok,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                //pede a permissão
                                requestPermissions(new String []{

                                                Manifest.permission.WRITE_EXTERNAL_STORAGE},

                                        SAVE_IMAGE_PERMISSION_REQUEST_CODE);
                            }
                        });
                //mostra
                builder.create().show();
            }
            else{
                //usuário ainda não disse não, só pede a permissão
                requestPermissions(new String[]{
                                Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        SAVE_IMAGE_PERMISSION_REQUEST_CODE);
            }
        }
        else{
            //permissão já concedida, salva

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        switch (requestCode){
            case SAVE_IMAGE_PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){

                }
        }
    }

    public DoodleView getDoodleView(){
        return this.doodleView;
    }

    public void setDialogOnScreen(boolean dialogOnScreen) {
        this.dialogOnScreen = dialogOnScreen;
    }

}
