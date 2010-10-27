package com.osp.ide.resource.resourceexplorer;

import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Timer;
import java.util.TimerTask;

import org.eclipse.swt.SWTException;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;

public class PreviewCanvas extends Canvas {
	public static final int EFFECT_COUNT = 15;
	public static final int EFFECT_FAIDIN_NULL = 0;
	public static final int EFFECT_FAIDIN_LEFT = 1;
	public static final int EFFECT_FAIDIN_RIGHT = 2;
	public static final int EFFECT_FAIDIN_TOP = 3;
	public static final int EFFECT_FAIDIN_BOTTOM = 4;
	public static final int EFFECT_FAIDOUT_NULL = 5;
	public static final int EFFECT_FAIDOUT_LEFT = 6;
	public static final int EFFECT_FAIDOUT_RIGHT = 7;
	public static final int EFFECT_FAIDOUT_TOP = 8;
	public static final int EFFECT_FAIDOUT_BOTTOM = 9;
	public static final int EFFECT_SLIDE_NULL = 10;
	public static final int EFFECT_SLIDE_LEFT = 11;
	public static final int EFFECT_SLIDE_RIGHT = 12;
	public static final int EFFECT_SLIDE_TOP = 13;
	public static final int EFFECT_SLIDE_BOTTOM = 14;
	public static final String[] strEffect = { "Faidin NULL", "Faidin Left",
			"Faidin Right", "Faidin Top", "Faidin Bottom", "Faidout NULL",
			"Faidin Left", "Faidin Right", "Faidin Top", "Faidin Bottom",
			"Slide NULL", "Slide Left", "Slide Right", "Slide Top",
			"Slide Bottom" };

	public static final int EFFECT_GAP = 4;
	public static final int EFFECT_DELAY = 500;

	public static int getEffectIndex(String effect) {
		for (int i = 0; i < EFFECT_COUNT; i++) {
			if (effect.equals(strEffect[i]))
				return i;
		}
		return 0;
	}

	public static String getEffectString(int index) {
		if (index >= 0 && index < EFFECT_COUNT)
			return strEffect[index];

		return "";
	}

	public class EffectFaid extends TimerTask {
		int type;
		private Timer timer;
		private boolean first;
		private String imageName;
		private Point size;
		private int curPos;

		public EffectFaid(Timer timer, String imageName, int type) {
			this.timer = timer;
			this.imageName = imageName;
			this.type = type;
			this.first = true;
			this.size = getSize();
		}

		synchronized public void run() {
			try {
				switch (type) {
				case EFFECT_FAIDIN_NULL:
					Display.getDefault().syncExec(new Runnable() {
						@Override
						synchronized public void run() {
							setToolTipText(imageName);
						}
					});
					timerList.remove(timer);
					timer.cancel();
					break;
				case EFFECT_FAIDIN_LEFT:
					Display.getDefault().syncExec(new Runnable() {
						@Override
						synchronized public void run() {
							if (first) {
								curPos = -size.x;
								first = false;
							}
							curPos = curPos + EFFECT_GAP;
							drawImage(imageName, curPos, 0);
							if (curPos >= 0) {
								setToolTipText(imageName);
								timerList.remove(timer);
								timer.cancel();
							}
						}
					});
					break;
				case EFFECT_FAIDIN_RIGHT:
					Display.getDefault().syncExec(new Runnable() {
						@Override
						synchronized public void run() {
							if (first) {
								curPos = size.x;
								first = false;
							}
							curPos = curPos - EFFECT_GAP;
							drawImage(imageName, curPos, 0);
							if (curPos <= 0) {
								setToolTipText(imageName);
								timerList.remove(timer);
								timer.cancel();
							}
						}
					});
					break;
				case EFFECT_FAIDIN_TOP:
					Display.getDefault().syncExec(new Runnable() {
						@Override
						synchronized public void run() {
							if (first) {
								curPos = -size.y;
								first = false;
							}
							curPos = curPos + EFFECT_GAP;
							drawImage(imageName, 0, curPos);
							if (curPos >= 0) {
								setToolTipText(imageName);
								timerList.remove(timer);
								timer.cancel();
							}
						}
					});
					break;
				case EFFECT_FAIDIN_BOTTOM:
					Display.getDefault().syncExec(new Runnable() {
						@Override
						synchronized public void run() {
							if (first) {
								curPos = size.y;
								first = false;
							}
							curPos = curPos - EFFECT_GAP;
							drawImage(imageName, 0, curPos);
							if (curPos <= 0) {
								setToolTipText(imageName);
								timerList.remove(timer);
								timer.cancel();
							}
						}
					});
					break;
				case EFFECT_FAIDOUT_NULL:
					Display.getDefault().syncExec(new Runnable() {
						@Override
						public void run() {
							setToolTipText(null);
						}
					});
					timerList.remove(timer);
					timer.cancel();
					break;
				case EFFECT_FAIDOUT_LEFT:
					Display.getDefault().syncExec(new Runnable() {
						@Override
						synchronized public void run() {
							if (first) {
								setToolTipText(null);
								curPos = 0;
								first = false;
							}
							curPos = curPos - EFFECT_GAP;
							redraw(getSize().x + curPos, 0, EFFECT_GAP,
									getSize().y, false);
							drawImage(imageName, curPos, 0);
							if (curPos <= -size.x) {
								timerList.remove(timer);
								timer.cancel();
							}
						}
					});
					break;
				case EFFECT_FAIDOUT_RIGHT:
					Display.getDefault().syncExec(new Runnable() {
						@Override
						synchronized public void run() {
							if (first) {
								setToolTipText(null);
								curPos = 0;
								first = false;
							}
							curPos = curPos + EFFECT_GAP;
							redraw(curPos - EFFECT_GAP, 0, EFFECT_GAP,
									getSize().y, false);
							drawImage(imageName, curPos, 0);
							if (curPos >= size.x) {
								timerList.remove(timer);
								timer.cancel();
							}
						}
					});
					break;
				case EFFECT_FAIDOUT_TOP:
					Display.getDefault().syncExec(new Runnable() {
						@Override
						synchronized public void run() {
							if (first) {
								setToolTipText(null);
								curPos = 0;
								first = false;
							}
							curPos = curPos - EFFECT_GAP;
							redraw(0, getSize().y + curPos, getSize().x,
									EFFECT_GAP, false);
							drawImage(imageName, 0, curPos);
							if (curPos <= -size.y) {
								timerList.remove(timer);
								timer.cancel();
							}
						}
					});
					break;
				case EFFECT_FAIDOUT_BOTTOM:
					Display.getDefault().syncExec(new Runnable() {
						@Override
						synchronized public void run() {
							if (first) {
								setToolTipText(null);
								curPos = 0;
								first = false;
							}
							curPos = curPos + EFFECT_GAP;
							redraw(0, curPos - EFFECT_GAP, getSize().x,
									EFFECT_GAP, false);
							drawImage(imageName, 0, curPos);
							if (curPos >= size.y) {
								timerList.remove(timer);
								timer.cancel();
							}
						}
					});
					break;
				default:
					timerList.remove(timer);
					timer.cancel();
				}
			} catch (SWTException e) {
				timerList.remove(timer);
				timer.cancel();
			}
		}
	}

	public class EffectSlide extends TimerTask {
		int type;
		private Timer timer;
		private boolean first;
		private String outImageName;
		private String inImageName;
		private Point size;
		private int curPos;

		public EffectSlide(Timer timer, String outImageName,
				String inImageName, int type) {
			this.timer = timer;
			this.outImageName = outImageName;
			this.inImageName = inImageName;
			this.type = type;
			this.first = true;
			this.size = getSize();
		}

		synchronized public void run() {
			try {
				switch (type) {
				case EFFECT_SLIDE_NULL:
					Display.getDefault().syncExec(new Runnable() {
						@Override
						synchronized public void run() {
							setToolTipText(inImageName);
						}
					});
					timerList.remove(timer);
					timer.cancel();
					break;
				case EFFECT_SLIDE_LEFT:
					Display.getDefault().syncExec(new Runnable() {
						@Override
						synchronized public void run() {
							if (first) {
								curPos = size.x;
								first = false;
							}
							curPos = curPos - EFFECT_GAP;
							drawImage(outImageName, inImageName, curPos, 0,
									EFFECT_SLIDE_LEFT);
							if (curPos <= 0) {
								setToolTipText(inImageName);
								timerList.remove(timer);
								timer.cancel();
							}
						}
					});
					break;
				case EFFECT_SLIDE_RIGHT:
					Display.getDefault().syncExec(new Runnable() {
						@Override
						synchronized public void run() {
							if (first) {
								curPos = -size.x;
								first = false;
							}
							curPos = curPos + EFFECT_GAP;
							drawImage(outImageName, inImageName, curPos, 0,
									EFFECT_SLIDE_RIGHT);
							if (curPos >= 0) {
								setToolTipText(inImageName);
								timerList.remove(timer);
								timer.cancel();
							}
						}
					});
					break;
				case EFFECT_SLIDE_TOP:
					Display.getDefault().syncExec(new Runnable() {
						@Override
						synchronized public void run() {
							if (first) {
								curPos = size.y;
								first = false;
							}
							curPos = curPos - EFFECT_GAP;
							drawImage(outImageName, inImageName, 0, curPos,
									EFFECT_SLIDE_TOP);
							if (curPos <= 0) {
								setToolTipText(inImageName);
								timerList.remove(timer);
								timer.cancel();
							}
						}
					});
					break;
				case EFFECT_SLIDE_BOTTOM:
					Display.getDefault().syncExec(new Runnable() {
						@Override
						synchronized public void run() {
							if (first) {
								curPos = -size.y;
								first = false;
							}
							curPos = curPos + EFFECT_GAP;
							drawImage(outImageName, inImageName, 0, curPos,
									EFFECT_SLIDE_BOTTOM);
							if (curPos >= 0) {
								setToolTipText(inImageName);
								timerList.remove(timer);
								timer.cancel();
							}
						}
					});
					break;
				default:
					timerList.remove(timer);
					timer.cancel();
				}
			} catch (SWTException e) {
				timerList.remove(timer);
				timer.cancel();
			}
		}
	}

	private Hashtable<String, Image> sceneImageList = new Hashtable<String, Image>();
	private GC gc;
	private Hashtable<Timer, Timer> timerList = new Hashtable<Timer, Timer>();

	public PreviewCanvas(Composite parent, int style) {
		super(parent, style);
		gc = new GC(this);
	}

	public Hashtable<String, Image> getImageList() {
		return sceneImageList;
	}

	@Override
	public void setToolTipText(String key) {
		if(getToolTipText() != null && getToolTipText().equals(key))
			setBackgroundImage(null);
		Image image = null;
		if (key != null)
			image = sceneImageList.get(key);

		setBackgroundImage(image);
		super.setToolTipText(key);
	}

	public void drawImage(String imageName, int x, int y) {
		Image image = sceneImageList.get(imageName);
		if (image == null)
			return;

		gc.drawImage(image, x, y);
	}

	public void effectFaid(String imageName, int delay, int interval,
			String effect) {
		Date start = new Date();
		int length;
		start.setTime(start.getTime() + delay);
		Timer timer = new Timer(); // 타이머 생성
		if (getEffectIndex(effect) == EFFECT_FAIDIN_TOP
				|| getEffectIndex(effect) == EFFECT_FAIDIN_BOTTOM
				|| getEffectIndex(effect) == EFFECT_FAIDOUT_TOP
				|| getEffectIndex(effect) == EFFECT_FAIDOUT_BOTTOM)
			length = getSize().y;
		else
			length = getSize().x;

		timer.scheduleAtFixedRate(new EffectFaid(timer, imageName,
				getEffectIndex(effect)), start, interval * EFFECT_GAP / length);
		timerList.put(timer, timer);
	}

	public void effectSlide(String outImageName, String inImageName, int delay,
			int interval, String effect) {
		Date start = new Date();
		int length;
		start.setTime(start.getTime() + delay);
		Timer timer = new Timer(); 
		if (getEffectIndex(effect) == EFFECT_SLIDE_TOP
				|| getEffectIndex(effect) == EFFECT_SLIDE_BOTTOM)
			length = getSize().y;
		else
			length = getSize().x;

		if (inImageName == null || inImageName.isEmpty())
			timer.scheduleAtFixedRate(new EffectFaid(timer, outImageName,
					getEffectIndex(effect) - 5), start, interval * EFFECT_GAP
					/ length);
		else
			timer.scheduleAtFixedRate(new EffectSlide(timer, outImageName,
					inImageName, getEffectIndex(effect)), start, interval
					* EFFECT_GAP / length);
		timerList.put(timer, timer);
	}

	@Override
	public void dispose() {
		Enumeration<Timer> keys = timerList.keys();
		while (keys.hasMoreElements()) {
			Timer timer = keys.nextElement();
			timer.cancel();
		}

		super.dispose();
	}

	protected void drawImage(String outImageName, String inImageName, int x,
			int y, int type) {
		Image inImage = sceneImageList.get(inImageName);
		Image outImage = sceneImageList.get(outImageName);
		if (inImage == null)
			return;
		gc.drawImage(inImage, x, y);
		switch (type) {
		case EFFECT_SLIDE_LEFT:
			gc.drawImage(outImage, x - getSize().x + EFFECT_GAP * 2, y);
			break;
		case EFFECT_SLIDE_RIGHT:
			gc.drawImage(outImage, x + getSize().x - EFFECT_GAP * 2, y);
			break;
		case EFFECT_SLIDE_TOP:
			gc.drawImage(outImage, x, y - getSize().y + EFFECT_GAP * 2);
			break;
		case EFFECT_SLIDE_BOTTOM:
			gc.drawImage(outImage, x, y + getSize().y - EFFECT_GAP * 2);
			break;
		}
	}
}
