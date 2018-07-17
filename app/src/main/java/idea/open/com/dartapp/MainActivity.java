package idea.open.com.dartapp;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.widget.LinearLayout;
import android.widget.TextView;

import idea.open.com.dartlib.Dartlib;

public class MainActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        ((TextView)findViewById(R.id.username)).setText(Dartlib.getInstance().getStringValueWithAlt("login_username_text","User ID"));

        ((TextView)findViewById(R.id.username)).setTextColor(Color.parseColor(Dartlib.getInstance().getStringValueWithAlt("login_username_text_color","#000000")));

        ((TextView)findViewById(R.id.password)).setText(Dartlib.getInstance().getStringValueWithAlt("login_password_text","Password"));

        ((TextView)findViewById(R.id.password)).setTextColor(Color.parseColor(Dartlib.getInstance().getStringValueWithAlt("login_password_text_color","#000000")));

        ((LinearLayout)findViewById(R.id.bg)).setBackgroundColor(Color.parseColor(Dartlib.getInstance().getStringValueWithAlt("login_bg_color","#FFFFFFFF")));


    }
}
