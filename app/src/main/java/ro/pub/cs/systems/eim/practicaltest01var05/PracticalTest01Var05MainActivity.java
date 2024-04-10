package ro.pub.cs.systems.eim.practicaltest01var05;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class PracticalTest01Var05MainActivity extends AppCompatActivity {

    private Button navigateToSecondaryActivityButton;
    private IntentFilter intentFilter= new IntentFilter();
    private Button topLeftButton;
    private Button topRightButton;
    private Button bottomLeftButton;
    private Button bottomRightButton;
    private Button centerButton;
    private int serviceStatus =0;

    private TextView textView;

    private int buttons_pressed = 0;

    private MessageBroadcastReceiver messageBroadcastReceiver = new MessageBroadcastReceiver();

    private class MessageBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d("[SERVICE]", intent.getStringExtra("message"));
        }
    }
    private ButtonClickListener buttonClickListener = new ButtonClickListener();
    private class ButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            if (view.getId() == R.id.top_left_button) {
                if (textView.getText().toString().equals("")) {
                    textView.setText("Top Left");
                } else {
                    textView.setText(textView.getText().toString() + ", Top Left");
                }
                buttons_pressed++;
            } else if (view.getId() == R.id.top_right_button) {
                if (textView.getText().toString().equals("")) {
                    textView.setText("Top Right");
                } else {
                    textView.setText(textView.getText().toString() + ", Top Right");
                }
                buttons_pressed++;
            }
            else if (view.getId() == R.id.center_button) {
                if (textView.getText().toString().equals("")) {
                    textView.setText("Center");
                } else {
                    textView.setText(textView.getText().toString() + ", Center");
                }
                buttons_pressed++;
            }
            else if (view.getId() == R.id.bottom_right_button) {
                if (textView.getText().toString().equals("")) {
                    textView.setText("Bottom Right");
                } else {
                    textView.setText(textView.getText().toString() + ", Bottom Right");
                }
                buttons_pressed++;
            }
            else if (view.getId() == R.id.bottom_left_button) {
                if (textView.getText().toString().equals("")) {
                    textView.setText("Bottom Left");
                } else {
                    textView.setText(textView.getText().toString() + ", Bottom Left");
                }
                buttons_pressed++;
            }
            else if(view.getId()==R.id.navigate_to_second_activity_button) {
                Intent intent = new Intent(getApplicationContext(), PracticalTest01Var05SecondaryActivity.class);
                String istoric = textView.getText().toString();
                intent.putExtra(Constants.TextView, istoric);
                startActivityForResult(intent, Constants.REQUEST_CODE);
            }
            if (buttons_pressed >= 5 && serviceStatus == 0){
                Log.d("DA", "AM Intrat sa pornim serviciul");
                Intent intent = new Intent(getApplicationContext(), PracticalTest01Var05Service.class);
                intent.putExtra(Constants.TextView, textView.getText().toString());
                getApplicationContext().startService(intent);
                serviceStatus = 1;
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (requestCode == Constants.REQUEST_CODE) {
            Toast.makeText(this, "The activity returned with result " + resultCode, Toast.LENGTH_LONG).show();
        }
    }
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt(Constants.BUTTON_PRESSED, buttons_pressed);
    }
    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState.containsKey(Constants.BUTTON_PRESSED)) {
            buttons_pressed = savedInstanceState.getInt(Constants.BUTTON_PRESSED);
            Toast.makeText(this, "The saved value is: " + buttons_pressed, Toast.LENGTH_LONG).show();
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test01_var05_main);
        navigateToSecondaryActivityButton = findViewById(R.id.navigate_to_second_activity_button);
        topLeftButton = findViewById(R.id.top_left_button);
        topRightButton = findViewById(R.id.top_right_button);
        bottomLeftButton = findViewById(R.id.bottom_left_button);
        bottomRightButton = findViewById(R.id.bottom_right_button);
        centerButton = findViewById(R.id.center_button);
        textView = findViewById(R.id.text_view);
        topLeftButton.setOnClickListener(buttonClickListener);
        topRightButton.setOnClickListener(buttonClickListener);
        bottomLeftButton.setOnClickListener(buttonClickListener);
        bottomRightButton.setOnClickListener(buttonClickListener);
        centerButton.setOnClickListener(buttonClickListener);
        navigateToSecondaryActivityButton = (Button)findViewById(R.id.navigate_to_second_activity_button);
        navigateToSecondaryActivityButton.setOnClickListener(buttonClickListener);

    }
    @Override
    protected void onResume() {
        super.onResume();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            registerReceiver(messageBroadcastReceiver, intentFilter, RECEIVER_EXPORTED);
        }
        else {
            registerReceiver(messageBroadcastReceiver, intentFilter);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(messageBroadcastReceiver);
    }

    @Override
    protected void onDestroy() {
        Intent intent = new Intent(this, PracticalTest01Var05Service.class);
        stopService(intent);
        serviceStatus = 0;
        super.onDestroy();

    }

}