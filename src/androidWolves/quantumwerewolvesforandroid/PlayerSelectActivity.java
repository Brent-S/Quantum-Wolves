package androidWolves.quantumwerewolvesforandroid;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

public class PlayerSelectActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_player_select);
		this.setTitle("Quantum Werewolves");
		Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.ACTIVITY_MESSAGE);
        final TextView textViewToChange = (TextView) findViewById(R.id.textView1);
        textViewToChange.setText(message);
		
		
		
		Spinner spinner = (Spinner) findViewById(R.id.spinner1);
		String[] Names = intent.getStringArrayExtra(MainActivity.NAME_ARRAY);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, Names);
		spinner.setAdapter(adapter);
	}
	
	@Override
	public void onBackPressed(){
		// Do nothing
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.reset:
			Intent returnIntent = new Intent();
			setResult(MainActivity.RESULT_CANCELED, returnIntent);        
			finish();
			return true;
		}
		return false;
	}

    public void OnExit(View view){
    	Intent returnIntent = this.getIntent();
		Spinner spinner = (Spinner) findViewById(R.id.spinner1);
		String ChosenName = (String) spinner.getItemAtPosition(spinner.getSelectedItemPosition());
		int ChosenID;
		if(ChosenName.equals("NONE")) {
			ChosenID = 0;
		} else {
			ChosenID = MainActivity.getPlayerIDFromName(ChosenName);
		}		
		returnIntent.putExtra(MainActivity.NUMBER_STORED, ChosenID);
    	setResult(RESULT_OK, returnIntent);        
    	finish(); 
    }
}
