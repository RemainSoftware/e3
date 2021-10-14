package com.remainsoftware.e3.swt.base;

/*
 * Shell example snippet: create a non-rectangular window
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 *
 * @since 3.0
 */
import org.eclipse.swt.*;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.widgets.*;

public class Snippet134 {

static int[] circle(int r, int offsetX, int offsetY) {
	int[] polygon = new int[8 * r + 4];
	//x^2 + y^2 = r^2
	for (int i = 0; i < 2 * r + 1; i++) {
		int x = i - r;
		int y = (int)Math.sqrt(r*r - x*x);
		polygon[2*i] = offsetX + x;
		polygon[2*i+1] = offsetY + y;
		polygon[8*r - 2*i - 2] = offsetX + x;
		polygon[8*r - 2*i - 1] = offsetY - y;
	}
	return polygon;
}

public static void main(String[] args) {
	final Display display = new Display();
	//Shell must be created with style SWT.NO_TRIM
	final Shell shell = new Shell(display, SWT.NO_TRIM | SWT.ON_TOP);
	shell.setText("Snippet 134");
	shell.setBackground(display.getSystemColor(SWT.COLOR_RED));
	//define a region that looks like a key hole
	Region region = new Region();
	region.add(circle(67, 67, 67));
	region.subtract(circle(20, 67, 50));
	region.subtract(new int[]{67, 50, 55, 105, 79, 105});
	//define the shape of the shell using setRegion
	shell.setRegion(region);
	Rectangle size = region.getBounds();
	shell.setSize(size.width, size.height);
	//add ability to move shell around
	Listener l = new Listener() {
		/** The x/y of the MouseDown, relative to top-left of the shell. */
		Point origin;
		@Override
		public void handleEvent(Event e) {
			switch (e.type) {
				case SWT.MouseDown:
					Point point = shell.toDisplay(e.x, e.y);
					Point loc = shell.getLocation();
					origin = new Point(point.x - loc.x, point.y - loc.y);
					break;
				case SWT.MouseUp:
					origin = null;
					break;
				case SWT.MouseMove:
					if (origin != null) {
						Point p = display.map(shell, null, e.x, e.y);
						shell.setLocation(p.x - origin.x, p.y - origin.y);
					}
					break;
			}
		}
	};
	shell.addListener(SWT.MouseDown, l);
	shell.addListener(SWT.MouseUp, l);
	shell.addListener(SWT.MouseMove, l);
	//add ability to close shell
	Button b = new Button(shell, SWT.PUSH);
	b.setBackground(shell.getBackground());
	b.setText("close");
	b.pack();
	b.setLocation(10, 68);
	b.addListener(SWT.Selection, e -> shell.close());
	shell.open();
	while (!shell.isDisposed()) {
		if (!display.readAndDispatch())
			display.sleep();
	}
	region.dispose();
	display.dispose();
}
}
