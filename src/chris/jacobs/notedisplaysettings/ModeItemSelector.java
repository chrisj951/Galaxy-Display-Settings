package chris.jacobs.notedisplaysettings;

import android.content.Context;
import android.widget.Spinner;

public class ModeItemSelector extends DisplayItemSelector{

	public static String MODE_FILE_LOCATION = "/sys/class/mdnie/mdnie/mode";
	public ModeItemSelector(Context context,Spinner spinner) {
		super(context, spinner);
	}

	@Override
	protected String getFile() {
		return MODE_FILE_LOCATION;
	}

	@Override
	protected String getValue(Object spinnerItemAtPosition) {
		String spinnerItem = (String) spinnerItemAtPosition;
		return spinnerItem.substring(0,1);
	}

	@Override
	protected int getArrayRValue() {
		return R.array.modes;
	}

}
