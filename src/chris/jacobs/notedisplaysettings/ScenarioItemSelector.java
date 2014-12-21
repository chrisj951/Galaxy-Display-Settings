package chris.jacobs.notedisplaysettings;

import android.content.Context;
import android.widget.Spinner;

public class ScenarioItemSelector extends DisplayItemSelector{

	public static String SCENARIO_FILE_LOCATION = "/sys/class/mdnie/mdnie/scenario";
	
	public ScenarioItemSelector(Context context,Spinner spinner) {
		super(context, spinner);
	}

	@Override
	protected String getFile() {
		return SCENARIO_FILE_LOCATION;
	}

	@Override
	protected String getValue(Object spinnerItemAtPosition) {
		String spinnerItem = (String) spinnerItemAtPosition;
		return spinnerItem.substring(0,1);
	}

	@Override
	protected int getArrayRValue() {
		return R.array.scenario;
	}

}
