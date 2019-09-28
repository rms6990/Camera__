package com.example.uiapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import android.support.v7.app.AppCompatActivity;

public class TodayLuckChoice extends AppCompatActivity {

    int values = 0;


    class ImageBtnOnClickListener implements ImageButton.OnClickListener {


        @Override
        public void onClick(View view) {
            switch(view.getId()) {
                case R.id.luck_mice : values = 1; break;
                case R.id.luck_cow : values = 2; break;
                case R.id.luck_tiger : values = 3; break;
                case R.id.luck_rabbit : values = 4; break;
                case R.id.luck_dragon : values = 5; break;
                case R.id.luck_snake : values = 6; break;
                case R.id.luck_horse : values = 7; break;
                case R.id.luck_sheep : values = 8; break;
                case R.id.luck_monkey : values = 9; break;
                case R.id.luck_chick : values = 10; break;
                case R.id.luck_dog : values = 11; break;
                case R.id.luck_pig : values = 12; break;
                default : values = 0;
            }

            Intent intent = new Intent(TodayLuckChoice.this, TodayLuck.class);
            intent.putExtra("animal", values);
            startActivity(intent);

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_today_luck_choice);


        checkAnimalList();

    }


    public void checkAnimalList() {
        ImageBtnOnClickListener onClickListener = new ImageBtnOnClickListener();

        ImageButton mice = (ImageButton) findViewById(R.id.luck_mice);
        mice.setOnClickListener(onClickListener);
        ImageButton cow = (ImageButton) findViewById(R.id.luck_cow);
        cow.setOnClickListener(onClickListener);
        ImageButton tiger = (ImageButton) findViewById(R.id.luck_tiger);
        tiger.setOnClickListener(onClickListener);
        ImageButton rabbit = (ImageButton) findViewById(R.id.luck_rabbit);
        rabbit.setOnClickListener(onClickListener);
        ImageButton dragon = (ImageButton) findViewById(R.id.luck_dragon);
        dragon.setOnClickListener(onClickListener);
        ImageButton snake = (ImageButton) findViewById(R.id.luck_snake);
        snake.setOnClickListener(onClickListener);
        ImageButton horse = (ImageButton) findViewById(R.id.luck_horse);
        horse.setOnClickListener(onClickListener);
        ImageButton sheep = (ImageButton) findViewById(R.id.luck_sheep);
        sheep.setOnClickListener(onClickListener);
        ImageButton monkey = (ImageButton) findViewById(R.id.luck_monkey);
        monkey.setOnClickListener(onClickListener);
        ImageButton chick = (ImageButton) findViewById(R.id.luck_chick);
        chick.setOnClickListener(onClickListener);
        ImageButton dog = (ImageButton) findViewById(R.id.luck_dog);
        dog.setOnClickListener(onClickListener);
        ImageButton pig = (ImageButton) findViewById(R.id.luck_pig);
        pig.setOnClickListener(onClickListener);
    }

}
