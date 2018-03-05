package com.fox1q.narutoclicker;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    ImageButton enemy;
    Button nextLocation;
    TextView healthView;
    TextView clickDamage;
    TextView goldView;
    TextView locationEnemies;
    int dpc = 5; // damage per click - урон с 1 клика
    int hp = 10; // начальное здоровье обычных врагов
    int hpBoost = 30; // увеличение здоровья обычных врагов при
    int bossHP = 250;
    int bossHPBoost = 450;
    int currentHP = hp; // текущее здоровье врага
    int gold = 0; // начальное золото
    int currentLocation = 0; // текущая локация
    int currentEnemy = 0; // текущий враг в данной локации (картинка)
    int enemyCount = 0;

    private int[] defeatedEnemies = new int[6];
    private int[][] enemies = {{
            R.drawable.sasuke,
            R.drawable.neji,
            R.drawable.rock_lee
    }, {
            R.drawable.enemy_1,
            R.drawable.enemy_2,
            R.drawable.enemy_3
    }, {
            R.drawable.jirobo,
            R.drawable.sakon_ukon,
            R.drawable.kidomaru,
            R.drawable.tayuya
    }, {
            R.drawable.temari,
            R.drawable.gaara,
            R.drawable.kankuro
    }, {
            R.drawable.orochimaru
    }, {
            R.drawable.enemy_1,
            R.drawable.enemy_2,
            R.drawable.enemy_3
    }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView clickDamage = (TextView) findViewById(R.id.clickDamage);
        clickDamage.setText("Урон: " + dpc);
        final TextView goldView = (TextView) findViewById(R.id.goldView);
        goldView.setText("Золото: " + gold);
        final TextView healthView = (TextView) findViewById(R.id.healthView);
        healthView.setText("Здоровье: " + hp + "/" + hp);
        final ImageButton enemy = (ImageButton) findViewById(R.id.enemy);
        final Button nextLocation = (Button) findViewById(R.id.nextLocation);

        final TextView locationEnemies = (TextView) findViewById(R.id.locationEnemies);
        locationEnemies.setText(defeatedEnemies[currentLocation] + " / " + checkCountOfEnemies(enemies, currentLocation));

        nextLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (defeatedEnemies[currentLocation] >= checkCountOfEnemies(enemies, currentLocation)) {
                    ++currentLocation;
                    hp = hp + hpBoost;
                    ++dpc;
                    currentHP = hp;
                    enemyCount = 0;
                    healthView.setText("Здоровье: " + currentHP + "/" + hp);
                    locationEnemies.setText(defeatedEnemies[currentLocation] + " / " + checkCountOfEnemies(enemies, currentLocation));
                    enemy.setImageResource(enemies[currentLocation][0]);
                }
            }
        });

        enemy.setOnClickListener(new View.OnClickListener() {

            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View view) {
                currentHP = currentHP - dpc;
                if (currentHP <= 0) {
                    checkMassLength();
                    isLocationCleared();
                    currentHP = hp;
                    gold = gold + 10;
                    enemy.setImageResource(enemies[currentLocation][currentEnemy]);
                }
                locationEnemies.setText(defeatedEnemies[currentLocation] + " / " + checkCountOfEnemies(enemies, currentLocation));
                healthView.setText("Здоровье: " + currentHP + "/" + hp);
                goldView.setText("Золото: " + gold);
                clickDamage.setText("Урон: " + dpc);
            }
        });
    }

    public int checkCountOfEnemies(int[][] mass, int a) {
        if (mass[a].length == 1) {
            return 1;
        } else {
            return 10;
        }
    }

    public void checkMassLength() {
        if (enemies[currentLocation].length == 1) {
            currentEnemy = 0;
        } else {
            Random kek = new Random();
            currentEnemy = kek.nextInt(enemies[currentLocation].length - 1) + 1;
        }
    }

    public void isLocationCleared() {
        if (defeatedEnemies[currentLocation] < 10) {
            ++defeatedEnemies[currentLocation];
        }
    }
}