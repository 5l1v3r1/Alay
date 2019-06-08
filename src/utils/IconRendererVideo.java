package utils;

import javax.swing.Icon;
import javax.swing.table.DefaultTableCellRenderer;

@SuppressWarnings("serial")
public class IconRendererVideo extends DefaultTableCellRenderer {
	public IconRendererVideo() {
		super();
	}

	public void setValue(Object value) {
		if (value == null) {
			setIcon(null);
		} else {
			setIcon((Icon) value);
		}
	}
}