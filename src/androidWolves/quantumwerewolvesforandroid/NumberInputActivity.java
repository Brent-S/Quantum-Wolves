package androidWolves.quantumwerewolvesforandroid;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class NumberInputActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_number_input);
		this.setTitle("Quantum Werewolves");
		Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.ACTIVITY_MESSAGE);
        final TextView textViewToChange = (TextView) findViewById(R.id.textView1);
        textViewToChange.setText(message);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.number_input, menu);
		return true;
	}

	public void ButtonClicked(View view){
		Intent returnIntent = this.getIntent();
		int EnteredNumber = 0;
		try{
			EnteredNumber = Integer.parseInt( ((EditText) findViewById(R.id.editText1)).getText().toString());
			returnIntent.putExtra(MainActivity.NUMBER_STORED, EnteredNumber);
			setResult(RESULT_OK, returnIntent);        
			finish(); 
		} catch (NumberFormatException e){
			setResult(MainActivity.RESULT_ERROR, returnIntent);        
			finish(); 
		}
		
	}
}
