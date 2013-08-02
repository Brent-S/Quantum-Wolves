package androidWolves.quantumwerewolvesforandroid;

import android.os.Bundle;
import android.app.Activity;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.annotation.SuppressLint;
import android.content.Intent;

public class DisplayMessageActivity extends Activity {

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_message);
		this.setTitle("Quantum Werewolves");
        Intent intent = getIntent();
        final TextView textViewToChange = (TextView) findViewById(R.id.MainText);

        String message = intent.getStringExtra(MainActivity.ACTIVITY_MESSAGE);
        if(message != null){
            textViewToChange.setText(message);
        } else {
        	message = intent.getStringExtra(MainActivity.HTML_STORED);
        	if(message != null) textViewToChange.setText(Html.fromHtml(message));
        }
    }
    
    public void OnExit(View view){
    	Intent returnIntent = new Intent();
    	setResult(RESULT_OK, returnIntent);        
    	finish(); 
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
	
	@Override
	public void onBackPressed(){
		// Do nothing
	}
}