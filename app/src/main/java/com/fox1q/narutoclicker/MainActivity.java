package com.fox1q.narutoclicker;

import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    ImageButton enemy;
    TextView healthView;
    TextView clickDamage;
    TextView goldView;
    int damage = 2;
    int hp = 100;
    int currentHP = hp;
    int gold = 0;
    int x = 0;

    private Integer[] imageIDs = {
            R.drawable.enemy_1,
            R.drawable.enemy_2,
            R.drawable.enemy_3,
            R.drawable.jirobo,
            R.drawable.sakon_ukon,
            R.drawable.kidomaru,
            R.drawable.tayuya,
            R.drawable.jirobo_cursed,
            R.drawable.sakon_ukon_cursed,
            R.drawable.kidomaru_cursed,
            R.drawable.tayuya_cursed,
            R.drawable.kimimaru,
            R.drawable.kimimaru_cursed,
            R.drawable.orochimaru,
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView clickDamage = (TextView) findViewById(R.id.clickDamage);
        clickDamage.setText("Урон: " + damage);
        final TextView goldView = (TextView) findViewById(R.id.goldView);
        goldView.setText("Золото: " + gold);
        final TextView healthView = (TextView) findViewById(R.id.healthView);
        healthView.setText("Здоровье: " + hp + "/" + hp);
        final ImageButton enemy = (ImageButton) findViewById(R.id.enemy);
        enemy.setOnClickListener(new View.OnClickListener() {

            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "-" + damage, Toast.LENGTH_SHORT).show();
                currentHP = currentHP - damage;
                healthView.setText("Здоровье: " + currentHP + "/" + hp);
                clickDamage.setText("Урон: " + damage);
                if (currentHP <= 0) {
                    Toast.makeText(MainActivity.this, "GJ", Toast.LENGTH_SHORT).show();
                    ++x;
                    damage = damage + 3;
                    hp = hp + 100;
                    currentHP = hp;
                    gold = gold + 10;
                    goldView.setText("Золото: " + gold);
                    healthView.setText("Здоровье: " + currentHP + "/" + hp);
                    clickDamage.setText("Урон: " + damage);
                    enemy.setImageResource(imageIDs[x]);
                }
            }
        });
    }
}