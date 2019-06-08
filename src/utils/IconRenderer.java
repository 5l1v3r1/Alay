package utils;

import javax.swing.ImageIcon;
import javax.swing.table.DefaultTableCellRenderer;

@SuppressWarnings("serial")
public class IconRenderer extends DefaultTableCellRenderer {
	public IconRenderer() {
		super();
	}

	public void setValue(Object value) {
		if (value == null) {
			setIcon(null);
		} else {
			setIcon(new ImageIcon((String) value));
		}
	}
}