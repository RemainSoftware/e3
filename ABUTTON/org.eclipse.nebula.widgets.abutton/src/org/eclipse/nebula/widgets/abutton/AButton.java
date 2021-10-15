/*******************************************************************************
 * Copyright (c) 2021, 2021 Remain Software and others
 *
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Wim Jongman - initial API and implementation
 *******************************************************************************/
package org.eclipse.nebula.widgets.abutton;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;

/**
 * 
 * A timer button inspired by the NetFlix automatic button.
 *
 */
public class AButton extends Button {

	/**
	 * Create a automatic button.
	 * 
	 * @param parent
	 * @param style
	 */
	public AButton(Composite parent, int style) {
		super(parent, style);
		addPaintListener(event -> draw(event));
	}

	private int fAlpha = 100;
	private int fDelay = 5000;
	private int fBorderSize = 2;
	private boolean fIsDrawing;
	private boolean fIsDamaged;
	private boolean fCancelled;
	private boolean fIsDone;
	private boolean fIsStarted;

	protected boolean isCancelled() {
		return fCancelled;
	}

	/**
	 * Sets the transparency of the animation.
	 * 
	 * @param alpha the transparency value between 0 and 255l
	 * @return this
	 */
	public AButton setAlpha(int alpha) {
		fAlpha = alpha;
		return this;
	}

	/**
	 * @return the transparency value of the animation overlay.
	 */
	public int getAlpha() {
		return fAlpha;
	}

	/**
	 * Cancel the countdown of the widget.
	 * 
	 * @return this
	 */
	public AButton cancel() {
		fCancelled = true;
		return this;
	}

	/**
	 * Restart the countdown.
	 * 
	 * @return this
	 */
	public AButton start() {
		fCancelled = false;
		fIsDone = false;
		fIsStarted = true;
		redraw();
		return this;
	}

	/**
	 * Sets the delay after which the button will automatically press itself.
	 * 
	 * @param delay the delay in milliseconds
	 * @return this
	 */
	public AButton setDelay(int delay) {
		fDelay = delay;
		return this;
	}

	/**
	 * @return the delay to the automatic press in milliseconds.
	 */
	public int getDelay() {
		return fDelay;
	}

	public AButton setBorderSize(int borderSize) {
		fBorderSize = borderSize;
		return this;
	}

	public int getBorderSize() {
		return fBorderSize;
	}

	private void draw(PaintEvent event) {
		GC gc = new GC((AButton) event.widget);
		AButton button = (AButton) event.widget;
		Rectangle bounds = button.getBounds();
		Thread drawThread = new Thread(() -> {
			doDraw(gc, button, bounds);
		});
		drawThread.start();
	}

	private void doDraw(GC gc, AButton button, Rectangle bounds) {

		gc.setAlpha(button.getAlpha());
		int sleepTime = button.getDelay() / bounds.width;
		int borderWidth = getBorderSize();

		if (fIsDone || !fIsStarted) {
			if (fIsStarted) {
				drawDamagedArea(gc, bounds, borderWidth, bounds.width);
			}
			return;
		}

		if (fIsDrawing) {
			fIsDamaged = true;
			return;
		}
		try {
			fIsDrawing = true;

			for (int i = borderWidth; i < bounds.width - borderWidth; i++) {
				if (fCancelled) {
					break;
				}
				if (fIsDamaged && i > borderWidth) {
					drawDamagedArea(gc, bounds, borderWidth, i);
					fIsDamaged = false;
				}
				gc.drawLine(i, borderWidth, i, bounds.height - borderWidth);
				sleep(sleepTime);
			}
			fire();
		} finally {
			fIsDrawing = false;
			fIsDone = true;
		}
	}

	private void drawDamagedArea(GC gc, Rectangle bounds, int borderWidth, int width) {
		Color background = gc.getBackground();
		gc.setBackground(gc.getForeground());
		gc.fillRectangle(borderWidth, borderWidth, width - 2, bounds.height - borderWidth);
		gc.setBackground(background);
	}

	private void fire() {
		getDisplay().asyncExec(() -> {
			// setSelection(true);
			notifyListeners(SWT.Selection, new Event());
		});
	}

	private static void sleep(int sleepTimeMillis) {
		try {
			Thread.sleep(sleepTimeMillis);
		} catch (InterruptedException e) {
			// nop
		}
	}

	@Override
	protected void checkSubclass() {
	}
}
