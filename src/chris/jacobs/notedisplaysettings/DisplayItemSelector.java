package chris.jacobs.notedisplaysettings;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;

public abstract class DisplayItemSelector implements OnItemSelectedListener {

	Context context;
	Spinner spinner;
	
	public DisplayItemSelector(Context context,Spinner spinner) {
		this.context = context;
		this.spinner = spinner;
		this.readFileForValue();
	}

	protected abstract String getFile();
	protected abstract int getArrayRValue();

	protected abstract String getValue(Object spinnerItemAtPosition);

	public void setMode(String file, String value) throws InterruptedException,
			IOException {
		System.out.println("Writing " + value + " to " + file);

		String message = "echo " + value + " > " + file;
		Process process = Runtime.getRuntime().exec(
				new String[] { "su", "-c", message });
		process.waitFor();

	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
		try { 
			String item = (String) parent.getItemAtPosition(position);
			String fileLoc = this.getFile();
			String value = this.getValue(item);
			this.setMode(fileLoc, value);
		} catch (Exception e) {
			NoteDisplaySettings.displayException(context,e);
		}
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		this.readFileForValue(); 
	}
	
	private void readFileForValue(){
		try {
			this.setSpinnerValues(spinner, this.getFile());
		} catch (Exception e) {
			NoteDisplaySettings.displayException(context, e);

		}
	}
	
	
	private int getPositionOfSpinnerValue(String value){
		System.out.println("Looking for value : \"" + value +"\"");
		String[] valueArray = context.getResources().getStringArray(this.getArrayRValue());
		for(int i=0;i<valueArray.length;i++){
			System.out.println("arr["+i+"] = " + valueArray[i]);
			if(valueArray[i].contains(value)){
				return i;
			}
		}
		
		return 0;
	}
	
	private void setSpinnerValues(Spinner spinner, String fileToReadLocation) throws IOException, InterruptedException {
		String selection = this.getSelectionFromFile(fileToReadLocation);
		int colonLoc = selection.indexOf(":");
		selection = selection.substring(colonLoc+1).trim();
		int pos = this.getPositionOfSpinnerValue(selection.trim());
		spinner.setSelection(pos);
	}

	private String getSelectionFromFile(String fileToReadLocation) throws IOException, InterruptedException {
		String message = "cat " + fileToReadLocation;
		Process process = Runtime.getRuntime().exec(
				new String[] { "su", "-c", message });
		process.waitFor();

		BufferedReader fileReader = new BufferedReader(new InputStreamReader(
				process.getInputStream()));
		String line = fileReader.readLine();
		fileReader.close();
		return line;

	}
}
