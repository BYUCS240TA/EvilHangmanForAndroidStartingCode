package cs240.byu.edu.evilhangman_android;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.NumberPicker;

public class MainActivity extends AppCompatActivity
{
    public static String wordLengthKey = "WORD_LEN";
    public static String guessAmountKey = "NUM_GUESSES";
    public static String fileSelectedKey = "FILE_SELECTED";
    private String fileSelected = null;

    int currentSetWordLength;
    int currentSetNumberGuesses;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NumberPicker wordLength = (NumberPicker)findViewById(R.id.length_of_word);
        NumberPicker numberGuesses = (NumberPicker)findViewById(R.id.number_guess);

        wordLength.setMaxValue(15);
        wordLength.setMinValue(2);
        wordLength.setValue(4);
        currentSetWordLength = 4; //default values

        //this prevents the wheel from wrapping from 15 to 1
        wordLength.setWrapSelectorWheel(false);

        numberGuesses.setMaxValue(30);
        numberGuesses.setMinValue(1);
        numberGuesses.setValue(6);
        currentSetNumberGuesses = 6; //default values

        //this prevents the wheel from wrapping from 30 to 1
        numberGuesses.setWrapSelectorWheel(false);

        wordLength.setOnValueChangedListener(new NumberPicker.OnValueChangeListener()
        {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal)
            {
                currentSetWordLength = newVal;
                Log.d("MainActivity", "Word length set to " + String.valueOf(newVal));
            }
        });

        numberGuesses.setOnValueChangedListener(new NumberPicker.OnValueChangeListener()
        {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal)
            {
                currentSetNumberGuesses = newVal;
                Log.d("MainActivity", "Number of guesses set to " + String.valueOf(newVal));
            }
        });
    }

    public void StartGame(View v)
    {
        Intent i = new Intent(this, GamePlay.class);
        Bundle bundle = new Bundle();
        bundle.putInt(wordLengthKey, currentSetWordLength);
        bundle.putInt(guessAmountKey, currentSetNumberGuesses);
        bundle.putString(fileSelectedKey, fileSelected);
        i.putExtras(bundle);
        startActivity(i);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.game_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.pick_file:
                Intent intent = new Intent(this, SelectDictionary.class);
                startActivityForResult(intent, 0);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0 && resultCode == RESULT_OK && data != null) {
            fileSelected = data.getStringExtra(fileSelectedKey);
        }
    }
}
