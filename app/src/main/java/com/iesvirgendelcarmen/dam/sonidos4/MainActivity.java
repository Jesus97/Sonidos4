package com.iesvirgendelcarmen.dam.sonidos4;

import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private MediaRecorder miGrabadora;
    private String salida = null;

    ImageButton grabar;
    ImageButton pausa;
    ImageButton play;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        salida = Environment.getExternalStorageDirectory().getAbsolutePath() + "/grabado.3gp";
        miGrabadora=new MediaRecorder();
        miGrabadora.setAudioSource(MediaRecorder.AudioSource.MIC);
        miGrabadora.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        miGrabadora.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
        miGrabadora.setOutputFile(salida);

        grabar = (ImageButton) findViewById(R.id.grabar);
        pausa = (ImageButton) findViewById(R.id.pausa);
        play = (ImageButton) findViewById(R.id.play);

        grabar.setClickable(true);
        pausa.setClickable(false);
        play.setClickable(false);

        grabar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                        miGrabadora.prepare();
                       miGrabadora.start();
                        }
                    catch (IllegalStateException e) {
                    e.printStackTrace();
                    }
                catch (IOException e) {
                e.printStackTrace();
                }
                grabar.setClickable(false);
                pausa.setClickable(true);
                Toast toast = Toast.makeText(getApplicationContext(), "ESTAMOS GRABANDO", Toast.LENGTH_SHORT);
                toast.show();
            }
        });

        pausa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                miGrabadora.stop();
                miGrabadora.release();
                miGrabadora = null;
                play.setClickable(true);
                pausa.setClickable(false);
                Toast toast = Toast.makeText(getApplicationContext(), "GRABACION FINALIZADA", Toast.LENGTH_SHORT);
                toast.show();
            }
        });

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) throws IllegalArgumentException,SecurityException,IllegalStateException {
                MediaPlayer repro = new MediaPlayer();
                try {
                    repro.setDataSource(salida);
                    }catch (IOException e){
                        e.printStackTrace();
                }
                try {
                    repro.prepare();
                }catch (IOException e) {
                    e.printStackTrace();
                }
                repro.start();
                Toast toast = Toast.makeText(getApplicationContext(), "REPRODUCIENDO GRABACION", Toast.LENGTH_SHORT);
                toast.show();
            }
        });

    }

}
