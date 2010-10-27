package com.osp.ide.resource.resourceexplorer;

import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Timer;
import java.util.TimerTask;

import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.Viewport;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.draw2d.parts.Thumbnail;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Display;

import com.osp.ide.resource.model.PanelFrame;
import com.osp.ide.resource.model.Popup;
import com.osp.ide.resource.model.Form;
import com.osp.ide.resource.part.PopupFramePart;
import com.osp.ide.resource.part.FormFramePart;
import com.osp.ide.resource.part.PanelFramePart;

public class ImageThumbnail extends Thumbnail {

	private static final int EFFECT_COUNT = 10;
	private static final int EFFECT_FAIDIN_NULL = 0;
	private static final int EFFECT_FAIDIN_LEFT = 1;
	private static final int EFFECT_FAIDIN_RIGHT = 2;
	private static final int EFFECT_FAIDIN_TOP = 3;
	private static final int EFFECT_FAIDIN_BOTTOM = 4;
	private static final int EFFECT_FAIDOUT_NULL = 5;
	private static final int EFFECT_FAIDOUT_LEFT = 6;
	private static final int EFFECT_FAIDOUT_RIGHT = 7;
	private static final int EFFECT_FAIDOUT_TOP = 8;
	private static final int EFFECT_FAIDOUT_BOTTOM = 9;
	private static final String[] strEffect = { "Effect Faidin NULL",
			"Effect Faidin Left", "Effect Faidin Right", "Effect Faidin Top",
			"Effect Faidin Bottom", "Effect Faidout NULL",
			"Effect Faidout Left", "Effect Faidout Right",
			"Effect Faidout Top", "Effect Faidout Bottom" };

	private static final int EFFECT_GAP = 4;
	public static final float THUMBNAIL_SCALE = 0.7f;

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

	public class Effect extends TimerTask {
		int type;
		private Timer timer;
		private boolean first;

		public Effect(Timer timer, int type) {
			this.type = type;
			this.timer = timer;
			this.first = true;
		}

		synchronized public void run() {
			switch (type) {
			case EFFECT_FAIDIN_NULL:
				Display.getDefault().syncExec(new Runnable() {
					@Override
					public void run() {
						resetLocation();
					}
				});
				timer.cancel();
				break;
			case EFFECT_FAIDIN_LEFT:
				Display.getDefault().syncExec(new Runnable() {
					@Override
					synchronized public void run() {
						if (first) {
							setDrawLocation(new Point(-size.x, 0));
							first = false;
						}
						Point loc = getDrawLocation();
						loc.x = loc.x + EFFECT_GAP;
						setDrawLocation(loc);
					}
				});
				if (getDrawLocation().x >= 0)
					timer.cancel();
				break;
			case EFFECT_FAIDIN_RIGHT:
				Display.getDefault().syncExec(new Runnable() {
					@Override
					synchronized public void run() {
						if (first) {
							setDrawLocation(new Point(size.x, 0));
							first = false;
						}
						Point loc = getDrawLocation();
						loc.x = loc.x - EFFECT_GAP;
						setDrawLocation(loc);
					}
				});
				if (getDrawLocation().x <= 0)
					timer.cancel();
				break;
			case EFFECT_FAIDIN_TOP:
				Display.getDefault().syncExec(new Runnable() {
					@Override
					synchronized public void run() {
						if (first) {
							setDrawLocation(new Point(0, -size.y));
							first = false;
						}
						Point loc = getDrawLocation();
						loc.y = loc.y + EFFECT_GAP;
						setDrawLocation(loc);
					}
				});
				if (getDrawLocation().y >= 0)
					timer.cancel();
				break;
			case EFFECT_FAIDIN_BOTTOM:
				Display.getDefault().syncExec(new Runnable() {
					@Override
					synchronized public void run() {
						if (first) {
							setDrawLocation(new Point(0, size.y));
							first = false;
						}
						Point loc = getDrawLocation();
						loc.y = loc.y - EFFECT_GAP;
						setDrawLocation(loc);
					}
				});
				if (getDrawLocation().y <= 0)
					timer.cancel();
				break;
			case EFFECT_FAIDOUT_NULL:
				Display.getDefault().syncExec(new Runnable() {
					@Override
					synchronized public void run() {
						setDrawLocation(new Point(size.x, 0));
					}
				});
				timer.cancel();
				break;
			case EFFECT_FAIDOUT_LEFT:
				Display.getDefault().syncExec(new Runnable() {
					@Override
					synchronized public void run() {
						if (first) {
							resetLocation();
							first = false;
						}
						Point loc = getDrawLocation();
						loc.x = loc.x - EFFECT_GAP;
						setDrawLocation(loc);
					}
				});
				if (getDrawLocation().x <= -size.x)
					timer.cancel();
				break;
			case EFFECT_FAIDOUT_RIGHT:
				Display.getDefault().syncExec(new Runnable() {
					@Override
					synchronized public void run() {
						if (first) {
							resetLocation();
							first = false;
						}
						Point loc = getDrawLocation();
						loc.x = loc.x + EFFECT_GAP;
						setDrawLocation(loc);
					}
				});
				if (getDrawLocation().x >= size.x)
					timer.cancel();
				break;
			case EFFECT_FAIDOUT_TOP:
				Display.getDefault().syncExec(new Runnable() {
					@Override
					synchronized public void run() {
						if (first) {
							resetLocation();
							first = false;
						}
						Point loc = getDrawLocation();
						loc.y = loc.y - EFFECT_GAP;
						setDrawLocation(loc);
					}
				});
				if (getDrawLocation().y <= -size.y)
					timer.cancel();
				break;
			case EFFECT_FAIDOUT_BOTTOM:
				Display.getDefault().syncExec(new Runnable() {
					@Override
					synchronized public void run() {
						if (first) {
							resetLocation();
							first = false;
						}
						Point loc = getDrawLocation();
						loc.y = loc.y + EFFECT_GAP;
						setDrawLocation(loc);
					}
				});
				if (getDrawLocation().y >= size.y)
					timer.cancel();
				break;
			default:
				timer.cancel();
			}
		}
	}

	private String key;
	private FormFramePart twframepart;
	private int degree;
	private Point point = new Point(0, 0);
	private Point size = new Point(0, 0);
	private Viewport viewPort;
	private Hashtable<String, Image> sceneImageList;
	private PopupFramePart twpopuppart;
	private PanelFramePart twpanelpart;

	public ImageThumbnail(Viewport figure) {
		super(figure);
	}

	public ImageThumbnail(Viewport figure, AbstractGraphicalEditPart part) {
		super(figure);
		if(part instanceof FormFramePart) {
			twframepart = (FormFramePart) part;
			viewPort = figure;
			key = ((Form) part.getModel()).getName();
		} else if(part instanceof PopupFramePart) {
			this.twpopuppart = (PopupFramePart) part;
			this.viewPort = figure;
			key = ((Popup) part.getModel()).getName();
		} else if(part instanceof PanelFramePart) {
			this.twpanelpart = (PanelFramePart) part;
			this.viewPort = figure;
			key = ((PanelFrame) part.getModel()).getName();
		}
	}

	@Override
	public void deactivate() {
		if(sceneImageList != null) {
			Enumeration<String> keys = sceneImageList.keys();
			while(keys.hasMoreElements()) {
				Image image = sceneImageList.get(keys.nextElement());
				if(image != null && !image.isDisposed()) {
					image.dispose();
					image = null;
				}
			}
		}
		
		super.deactivate();
	}

	public ImageThumbnail(Viewport figure, FormFramePart twframepart,
			Point size, Hashtable<String, Image> imageList) {
		super(figure);
		this.twframepart = twframepart;
		this.viewPort = figure;
		this.size = new Point(size.x, size.y);
		this.sceneImageList = imageList;
		key = ((Form) twframepart.getModel()).getName();
	}
	
	public String getId(){
		return key;
	}

	public FormFramePart getTWPart() {
		return twframepart;
	}
	
	public Viewport getViewPort() {
		return viewPort;
	}

	@Override
	public float getScaleX() {
		// TODO Auto-generated method stub
		return super.getScaleX();
	}

	@Override
	public float getScaleY() {
		// TODO Auto-generated method stub
		return super.getScaleY();
	}

	@Override
	public Rectangle getSourceRectangle() {
		return super.getSourceRectangle();
		// return framepart.getFigure().getBounds();
	}

	@Override
	public Image getThumbnailImage() {
		// TODO Auto-generated method stub
		return super.getThumbnailImage();
	}

	@Override
	protected void paintFigure(Graphics graphics) {
		Image thumbnail = getThumbnailImage();

		if (thumbnail == null || thumbnail.getBounds().width == 0) {
			if(twframepart != null)
				System.err.println(((Form) twframepart.getModel()).getName()
						+ "] image is null!");
			else if(twpopuppart != null)
				System.err.println(((Popup) twpopuppart.getModel()).getName()
						+ "] image is null!");
			else if(twpanelpart != null)
				System.err.println(((PanelFrame) twpanelpart.getModel()).getName()
						+ "] image is null!");
			else
				return;

			return;
		}
		if (sceneImageList != null) {
			Image image = sceneImageList.remove(key);
			if(image != null && !image.isDisposed())
				image.dispose();
			image = new Image(null, thumbnail, SWT.IMAGE_COPY);
			sceneImageList.put(key, image);
		}

		Rectangle rect;
		if(twframepart != null)
			rect = twframepart.getFigure().getBounds();
		else if(twpopuppart != null)
			rect = twpopuppart.getFigure().getBounds();
		else if(twpanelpart != null)
			rect = twpanelpart.getFigure().getBounds();
		else
			return;
		
		if (rect.x < 0)
			rect.x = 0;
		if (rect.y < 0)
			rect.y = 0;

		if (degree != 0) {
			graphics.rotate(degree);
		}

		if (size.x == 0 || size.y == 0) {
			size.x = (int) (rect.width * THUMBNAIL_SCALE);
			size.y = (int) (rect.height * THUMBNAIL_SCALE);
		}

		graphics.drawImage(thumbnail, (int) 0, 0, thumbnail.getBounds().width,
				thumbnail.getBounds().height, point.x, point.y, size.x, size.y);
	}

	public void addDegree() {
		degree++;
		repaint();
	}

	public void resetLocation() {
		point.x = 0;
		point.y = 0;
		repaint();
	}

	public void setLocalSize(Point point) {
		size = new Point(point.x, point.y);
		repaint();
	}

	public Point getLocalSize() {
		return new Point(size.x, size.y);
	}

	public void setDrawLocation(Point point) {
		this.point.x = point.x;
		this.point.y = point.y;
		repaint();
	}

	public Point getDrawLocation() {
		return new Point(point.x, point.y);
	}

	public void subDegree() {
		degree--;
		repaint();
	}

	public void faidinLeft(int delay, int milli) {
		Date start = new Date();
		start.setTime(start.getTime() + delay);
		Timer timer = new Timer(); // timer create
		timer.scheduleAtFixedRate(new Effect(timer, EFFECT_FAIDIN_LEFT), start,
				milli * EFFECT_GAP / size.x); // millisecond
	}

	public void faidinRight(int delay, int milli) {
		setDrawLocation(new Point(size.x, 0));
		Date start = new Date();
		start.setTime(start.getTime() + delay);
		Timer timer = new Timer(); // timer create
		timer.scheduleAtFixedRate(new Effect(timer, EFFECT_FAIDIN_RIGHT),
				start, milli * EFFECT_GAP / size.x); // millisecond
	}

	public void faidinTop(int delay, int milli) {
		setDrawLocation(new Point(0, -size.y));
		Date start = new Date();
		start.setTime(start.getTime() + delay);
		Timer timer = new Timer(); // timer create
		timer.scheduleAtFixedRate(new Effect(timer, EFFECT_FAIDIN_TOP), start,
				milli * EFFECT_GAP / size.y); // millisecond
	}

	public void faidinBottom(int delay, int milli) {
		setDrawLocation(new Point(0, size.y));
		Date start = new Date();
		start.setTime(start.getTime() + delay);
		Timer timer = new Timer(); // timer create
		timer.scheduleAtFixedRate(new Effect(timer, EFFECT_FAIDIN_BOTTOM),
				start, milli * EFFECT_GAP / size.y); // millisecond
	}

	public void faidoutLeft(int delay, int milli) {
		resetLocation();
		Date start = new Date();
		start.setTime(start.getTime() + delay);
		Timer timer = new Timer(); // timer create
		timer.scheduleAtFixedRate(new Effect(timer, EFFECT_FAIDOUT_LEFT),
				start, milli * EFFECT_GAP / size.x); // millisecond
	}

	public void faidoutRight(int delay, int milli) {
		resetLocation();
		Date start = new Date();
		start.setTime(start.getTime() + delay);
		Timer timer = new Timer(); // timer create
		timer.scheduleAtFixedRate(new Effect(timer, EFFECT_FAIDOUT_RIGHT),
				start, milli * EFFECT_GAP / size.x); // millisecond
	}

	public void faidoutTop(int delay, int milli) {
		resetLocation();
		Date start = new Date();
		start.setTime(start.getTime() + delay);
		Timer timer = new Timer(); // timer create
		timer.scheduleAtFixedRate(new Effect(timer, EFFECT_FAIDOUT_TOP), start,
				milli * EFFECT_GAP / size.y); // millisecond
	}

	public void faidoutBottom(int delay, int milli) {
		resetLocation();
		Date start = new Date();
		start.setTime(start.getTime() + delay);
		Timer timer = new Timer(); // timer create
		timer.scheduleAtFixedRate(new Effect(timer, EFFECT_FAIDOUT_BOTTOM),
				start, milli * EFFECT_GAP / size.y); // millisecond
	}

	public void effect(int delay, int milli, String effect) {
		Timer timer;
		Date curDate = new Date();
		curDate.setTime(curDate.getTime() + milli);
		switch (getEffectIndex(effect)) {
		case EFFECT_FAIDIN_NULL:
			timer = new Timer(); // timer create
			timer.schedule(new Effect(timer, EFFECT_FAIDIN_NULL), curDate);
			break;
		case EFFECT_FAIDIN_LEFT:
			faidinLeft(delay, milli);
			break;
		case EFFECT_FAIDIN_RIGHT:
			faidinRight(delay, milli);
			break;
		case EFFECT_FAIDIN_TOP:
			faidinTop(delay, milli);
			break;
		case EFFECT_FAIDIN_BOTTOM:
			faidinBottom(delay, milli);
			break;
		case EFFECT_FAIDOUT_NULL:
			timer = new Timer(); // timer create
			timer.schedule(new Effect(timer, EFFECT_FAIDOUT_NULL), curDate);
			break;
		case EFFECT_FAIDOUT_LEFT:
			faidoutLeft(delay, milli);
			break;
		case EFFECT_FAIDOUT_RIGHT:
			faidoutRight(delay, milli);
			break;
		case EFFECT_FAIDOUT_TOP:
			faidoutTop(delay, milli);
			break;
		case EFFECT_FAIDOUT_BOTTOM:
			faidoutBottom(delay, milli);
			break;
		}
	}
}
