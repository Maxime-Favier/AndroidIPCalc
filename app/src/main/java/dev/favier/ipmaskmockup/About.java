package dev.favier.ipmaskmockup;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * The about activity class show the licence of the app
 *
 * @author Maxime Favier
 */
public class About extends AppCompatActivity {

    /**
     * Button widget variable
     */
    Button buttonAboutBack;

    /**
     * Load activity layout
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        buttonAboutBack = findViewById(R.id.buttonAboutBack);
        // set eventListener for button
        buttonAboutBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
