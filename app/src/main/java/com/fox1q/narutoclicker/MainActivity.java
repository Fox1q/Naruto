package com.fox1q.narutoclicker;

import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    ImageButton enemy, restart;
    Button nextLocation;
    TextView timer, healthView, clickDamage, goldView, locationEnemies;
    int dpc = 5; // damage per click - урон с 1 клика
    int hp = 10; // начальное здоровье обычных врагов
    int hpBoost = 30; // увеличение здоровья обычных врагов
    int bossHP = 450;
    int currentHP = hp; // текущее здоровье врага
    int gold = 0; // начальное золото
    int currentLocation = 0; // текущая локация
    int currentEnemy = 0; // текущий враг в данной локации (картинка)
    int enemyCount = 0;

    private int[] defeatedEnemies = new int[101];
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

        timer = (TextView) findViewById(R.id.timer);
        clickDamage = (TextView) findViewById(R.id.clickDamage);
        clickDamage.setText("Урон: " + dpc);
        goldView = (TextView) findViewById(R.id.goldView);
        goldView.setText("Золото: " + gold);
        healthView = (TextView) findViewById(R.id.healthView);
        healthView.setText("Здоровье: " + hp + "/" + hp);
        enemy = (ImageButton) findViewById(R.id.enemy);
        restart = (ImageButton) findViewById(R.id.restart);
        nextLocation = (Button) findViewById(R.id.nextLocation);

        locationEnemies = (TextView) findViewById(R.id.locationEnemies);
        locationEnemies.setText(defeatedEnemies[currentLocation] + " / " + checkCountOfEnemies(enemies, currentLocation));

        final CountDownTimer bossTimer = new CountDownTimer(30000, 1000) {

            public void onTick(long millisUntilFinished) {
                timer.setText("" + millisUntilFinished / 1000);
            }

            public void onFinish() {
                currentHP = hp;
                healthView.setText("Здоровье: " + currentHP + "/" + hp);
                timer.setText("Осталось: 0");
                start();
            }
        };

        restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dpc = 5;
                hp = 10;
                currentHP = hp;
                gold = 0;
                currentLocation = 0;
                currentEnemy = 0;
                enemyCount = 0;
                defeatedEnemies = new int[101];
                locationEnemies.setText(defeatedEnemies[currentLocation] + " / " + checkCountOfEnemies(enemies, currentLocation));
                healthView.setText("Здоровье: " + currentHP + "/" + hp);
                goldView.setText("Золото: " + gold);
                clickDamage.setText("Урон: " + dpc);
                enemy.setImageResource(enemies[currentLocation][0]);
                bossTimer.cancel();
            }
        });

        nextLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nextLocation(bossTimer);
            }
        });

        enemy.setOnClickListener(new View.OnClickListener() {

            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View view) {
                click(bossTimer);
            }
        });
    }

    public int checkCountOfEnemies(int[][] mass, int a) {
        if (mass[a].length == 1)
            return 1;

        return 10;
    }

    public void enemyImage() {
        if (enemies[currentLocation].length == 1) {
            currentEnemy = 0;
        } else {
            Random kek = new Random();
            currentEnemy = kek.nextInt(enemies[currentLocation].length - 1) + 1;
        }
    }

    public void isLocationCleared() {
        if (defeatedEnemies[currentLocation] < checkCountOfEnemies(enemies, currentLocation)) {
            ++defeatedEnemies[currentLocation];
        }
    }

    public void bossTimer(CountDownTimer bossTimer) {
        if (isBoss()) {
            timer.setVisibility(View.VISIBLE);
            if (bossTimer != null) {
                bossTimer.cancel();
                bossTimer.start();
            } else {
                bossTimer.start();
                currentHP = hp;
                healthView.setText("Здоровье: " + currentHP + "/" + hp);
            }
        }
    }

    public void nextLocation(CountDownTimer bossTimer) {
        if (defeatedEnemies[currentLocation] >= checkCountOfEnemies(enemies, currentLocation)) {
            ++currentLocation;
            checkLocationForBoss(bossTimer);
            dpc = dpc + 3;
            currentHP = hp;
            enemyCount = 0;
            healthView.setText("Здоровье: " + currentHP + "/" + hp);
            locationEnemies.setText(defeatedEnemies[currentLocation] + " / " + checkCountOfEnemies(enemies, currentLocation));
            enemy.setImageResource(enemies[currentLocation][0]);
        }
    }

    public void checkLocationForBoss(CountDownTimer bossTimer) {
        if (isBoss()) {
            timer.setVisibility(View.VISIBLE);
            bossTimer.start();
            hp = bossHP * currentLocation;
        } else {
            bossTimer.cancel();
            timer.setVisibility(View.GONE);
            hp = hpBoost * currentLocation;
        }
    }

    public boolean isBoss() {
        return ((currentLocation + 1) % 5 == 0);
    }

    public void click(CountDownTimer bossTimer) {
        currentHP = currentHP - dpc;
        isDead(bossTimer);
        locationEnemies.setText(defeatedEnemies[currentLocation] + " / " + checkCountOfEnemies(enemies, currentLocation));
        healthView.setText("Здоровье: " + currentHP + "/" + hp);
        goldView.setText("Золото: " + gold);
        clickDamage.setText("Урон: " + dpc);
    }

    public void isDead(CountDownTimer bossTimer) {
        if (currentHP <= 0) {
            bossTimer(bossTimer);
            enemyImage();
            isLocationCleared();
            currentHP = hp;
            gold = gold + 10;
            enemy.setImageResource(enemies[currentLocation][currentEnemy]);
        }
    }
}