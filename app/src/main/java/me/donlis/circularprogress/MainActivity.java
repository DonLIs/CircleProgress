package me.donlis.circularprogress;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private int progress = 0;
    private Button startBtn;
    private Button resetBtn;
    private CircularProgress circularProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startBtn = (Button) findViewById(R.id.start);
        resetBtn = (Button) findViewById(R.id.reset);
        circularProgress = (CircularProgress) findViewById(R.id.progress);

        circularProgress.setMax(100);
        circularProgress.setText(getProgressText(progress));
        circularProgress.setProgress(progress);


        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(progress > 100){
                    return;
                }
                progress += 10;
                circularProgress.setText(getProgressText(progress));
                circularProgress.setProgress(progress);
            }
        });

        resetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progress = 0;
                circularProgress.setText(getProgressText(progress));
                circularProgress.setProgress(progress);
            }
        });
    }

    private String getProgressText(int i){
        return String.format("%s%%",i);
    }
}
