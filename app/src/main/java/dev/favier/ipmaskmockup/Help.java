package dev.favier.ipmaskmockup;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * The Help activity show information about the requirements for the app
 *
 * @author Maxime Favier
 */
public class Help extends AppCompatActivity {

    /**
     * Button widget variable
     */
    Button ButtonHelpBack;

    /**
     * Load activity layout - creation lifecycle
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        ButtonHelpBack = findViewById(R.id.buttonHelpBack);
        // set eventListener for button
        ButtonHelpBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
