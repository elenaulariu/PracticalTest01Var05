package ro.pub.cs.systems.eim.practicaltest01var05;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import ro.pub.cs.systems.eim.practicaltest01var05.databinding.ActivityPracticalTest01Var05SecondaryBinding;

public class PracticalTest01Var05SecondaryActivity extends AppCompatActivity {

    private Button verify_button;
    private Button cancel_button;
    private TextView view;

    private ButtonClickListener buttonClickListener = new ButtonClickListener();
    private class ButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            if(view.getId() == R.id.verify_button) {
                setResult(RESULT_OK, null);
            }
            else if(view.getId()==R.id.cancel_button) {
                setResult(RESULT_CANCELED, null);
            }
            finish();
        }

    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test01_var05_secondary);
        verify_button = findViewById(R.id.verify_button);
        cancel_button = findViewById(R.id.cancel_button);
        view = findViewById(R.id.text_view2);
        verify_button.setOnClickListener(buttonClickListener);
        cancel_button.setOnClickListener(buttonClickListener);

        Intent intent = getIntent();
        if (intent != null && intent.getExtras().containsKey(Constants.TextView)) {
            String numberOfClicks = intent.getStringExtra(Constants.TextView);
            view.setText(numberOfClicks);
        }
    }
}