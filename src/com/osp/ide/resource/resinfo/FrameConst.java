package com.osp.ide.resource.resinfo;

public interface FrameConst {

	public static final int BOOL_TRUE = 0;
	public static final int BOOL_FALSE = 1;
	public static final String[] BOOL = { "TRUE", "FALSE" };
	public static final int SOFTKEY_STYLE_NORMAL = 0;
	public static final int SOFTKEY_STYLE_BACK = 1;
	public static final String[] SOFTKEY_STYLE = { "Normal", "Back" };
	public static final String DEFAULT_COLOR = "Default";

	public static final int DEFAULT = 0;
	public static final int PORTRAIT = 1;
	public static final int LANDCAPE = 2;
	public static final String cszFrmMode[] = { "Default", "Portrait",
			"Landscape" };
	
	public static final int CTL_TYPE_NUM = 38;

	public static final int WINDOW_FORM = 0;
	public static final int WINDOW_BUTTON = 1;
	public static final int WINDOW_LIST = 2;
	public static final int WINDOW_COMBOBOX = 3;
	public static final int WINDOW_SPIN = 4;
	public static final int WINDOW_COLORSPIN = 5;
	public static final int WINDOW_POPUP = 6;
	public static final int WINDOW_PROGRESS = 7;
	public static final int WINDOW_EDITFIELD = 8;
	public static final int WINDOW_EDITAREA = 9;
	public static final int WINDOW_EDITTIME = 10;
	public static final int WINDOW_LABEL = 11;
	public static final int WINDOW_ICONLIST = 12;
	public static final int WINDOW_SLIDER = 13;
	public static final int WINDOW_RADIOBUTTON = 14;
	public static final int WINDOW_CHECKBUTTON = 15;
	public static final int WINDOW_GRID = 16;
	public static final int WINDOW_INDICATOR = 17;
	public static final int WINDOW_SCROLL = 18;
	public static final int WINDOW_TITLE = 19;
	public static final int WINDOW_SOFTKEY = 20;
	public static final int WINDOW_CUSTOMLIST = 21;
	public static final int WINDOW_BITMAPBUTTON = 22;
	public static final int WINDOW_SCENE = 23;
	public static final int WINDOW_SCROLLPANEL = 24;
	public static final int WINDOW_FLASHCONTROL = 25;
	public static final int WINDOW_COLORPICKER = 26;
	public static final int WINDOW_DATEPICKER = 27;
	public static final int WINDOW_EXPANDABLELIST = 28;
	public static final int WINDOW_GROUPEDLIST = 29;
	public static final int WINDOW_OVERLAYPANEL = 30;
	public static final int WINDOW_PANEL = 31;
	public static final int WINDOW_SLIDABLEGROUPEDLIST = 32;
	public static final int WINDOW_SLIDABLELIST = 33;
	public static final int WINDOW_TAB = 34;
	public static final int WINDOW_TIMEPICKER = 35;
	public static final int WINDOW_CONTROL = 36;
	public static final int WINDOW_EDITDATE = 37;

	public static final int[] defaultMinWidth = {480, 48, 92, 0, 0, 0, 
		/*WINDOW_POPUP : 6*/ 388, 100, 50, 50, 10, 1, 92, 200, 
		/*WINDOW_RADIOBUTTON : 14*/ 1, 92, 1, 1, 1, 1, 1, 92, 48, 
		/*WINDOW_SCENE : 23*/ 1, 1, 1, 1, 1, 92, 274, 1, 1, 274, 92, 
		/*WINDOW_TAB : 34*/ 1, 1, 1, 1};
	public static final int[] defaultMinHeight = {800, 48, 72, 0, 0, 0, 
		/*WINDOW_POPUP : 6*/ 130, 20,  80,  80, 16, 1, 72,  90, 
		/*WINDOW_RADIOBUTTON : 14*/ 1, 72, 1, 1, 1, 1, 1, 72, 48, 
		/*WINDOW_SCENE : 23*/ 1, 1, 1, 1, 1, 72, 148, 1, 1, 148, 72, 
		/*WINDOW_TAB : 34*/ 1, 1, 1, 1 };
	
	public static final String cszTag[] = { "Form", "Button", "List",
			"ComboBox", "Spin", "ColorSpin", "Popup", "Progress", "EditField",
			"EditArea", "EditTime", "Label", "IconList", "Slider",
			"RadioButton", "CheckButton", "Grid", "Indicator", "Scroll",
			"Title", "Softkey", "CustomList", "BitmapButton", "Scene",
			"ScrollPanel", "Flash", "ColorPicker", "DatePicker",
			"ExpandableList", "GroupedList", "OverlayPanel", "Panel", 
			"SlidableGroupedList", "SlidableList", "Tab", "TimePicker", "Control", 
			"EditDate"};
	public static final String cszCtlType[] = { "FORM", "BUTTON", "LIST",
			"COMBOBOX", "SPIN", "COLORSPIN", "POPUP", "PROGRESS", "EDITFIELD",
			"EDITAREA", "EDITTIME", "LABEL", "ICONLIST", "SLIDER",
			"RADIOBUTTON", "CHECKBUTTON", "GRID", "INDICATIOR", "SCROLL",
			"TITLE", "SOFTKEY", "CUSTOMLIST", "BITMAPBUTTON", "SCENE",
			"SCROLLPANEL", "FLASH", "COLORPICKER", "DATEPICKER",
			"EXPANDABLELIST", "GROUPEDLIST", "OVERLAYPANEL", "PANEL", 
			"SLIDABLEGROUPEDLIST", "SLIDABLELIST", "TAB", "TIMEPICKER", "CONTROL",
			"EDITDATE"};

	public static final int TITLE = 0;
	public static final int SOFTKEYSTYLE = 1;
	public static final int PROPERTY = 2;
	public static final int TEXT = 3;
	public static final int HALIGN = 4;
	public static final int VALIGN = 5;
	public static final int OUTLINE = 6;
	public static final int NOMALBGCOLOR = 7;
	public static final int NOMALTEXTCOLOR = 8;
	public static final int NOMALOUTLINE = 9;
	public static final int PRESSEDBGCOLOR = 10;
	public static final int PRESSEDTEXTCOLOR = 11;
	public static final int PRESSEDOUTLINE = 12;
	public static final int DISABLEBGCOLOR = 13;
	public static final int DISABLETEXTCOLOR = 14;
	public static final int DISABLEOUTLINE = 15;
	public static final int LISTITEMFORMAT = 16;
	public static final int TITLETEXT = 17;
	public static final int ITEMTEXT = 18;
	public static final int GUIDETEXT = 19;
	public static final int ITEMCOLOR = 20;
	public static final int VALUE = 21;
	public static final int MIN = 22;
	public static final int MAX = 23;
	public static final int STEP = 24;
	public static final int STEPSIZE = 25;
	public static final int TEXTCOLOR = 26;
	public static final int BGCOLOR = 27;
	public static final int LEFTTEXT = 28;
	public static final int CENTERTEXT = 29;
	public static final int RIGHTTEXT = 30;
	public static final int LEFTTEXTCOLOR = 31;
	public static final int CENTERTEXTCOLOR = 32;
	public static final int RIGHTTEXTCOLOR = 33;
	public static final int MAXTEXT = 34;
	public static final int MINTEXT = 35;
	public static final int GROUPID = 36;
	public static final int LAYOUT = 37;
	public static final int MODE = 38;
	public static final int STYLE = 39;
	public static final int X = 40;
	public static final int Y = 41;
	public static final int WIDTH = 42;
	public static final int HEIGHT = 43;
	public static final int ID = 44;
	public static final int DOCK = 45;
	public static final int FIT = 46;
	public static final int PARENT = 47;
	public static final int MAXDROPLINECOUNT = 48;
	public static final int CUSTOMCLASS = 49;
	public static final int SOFTKEY0TEXT = 50;
	public static final int SOFTKEY1TEXT = 51;
	public static final int SOFTKEY0NICON = 52;
	public static final int SOFTKEY1NICON = 53;
	public static final int LINE1HEIGHT = 54;
	public static final int LINE2HEIGHT = 55;
	public static final int BGBITMAPPATH = 56;
	public static final int NORMALBITMAPPATH = 57;
	public static final int NORMALBITMAPX = 58;
	public static final int NORMALBITMAPY = 59;
	public static final int PRESSEDBITMAPPATH = 60;
	public static final int PRESSEDBITMAPX = 61;
	public static final int PRESSEDBITMAPY = 62;
	public static final int DISABLEDBITMAPPATH = 63;
	public static final int DISABLEDBITMAPX = 64;
	public static final int DISABLEDBITMAPY = 65;
	public static final int NORMALBGBITMAPPATH = 66;
	public static final int PRESSEDBGBITMAPPATH = 67;
	public static final int LOCALFILEPATH = 68;
	public static final int REPEATMODE = 69;
	public static final int ORIENTATION = 70;
	public static final int QUALITY = 71;
	public static final int ITEMWIDTH = 72;
	public static final int ITEMHEIGHT = 73;
	public static final int TOPMARGIN = 74;
	public static final int LEFTMARGIN = 75;
	public static final int TEXTSIZE = 76;
	public static final int TEXTSTYLE = 77;
	public static final int HEADERHEIGHT = 78;
	public static final int TEXTOFEMPTYLIST = 79;
	public static final int TITLEICON = 80;
	public static final int BITMAP = 81;
	public static final int BSHOWVALUESTATE = 82;
	public static final int BSHOWSTATE = 83;
	public static final int BEDITMODEENABLE = 84;
	public static final int B24HOUR = 85;
	public static final int PANELID = 86;
	public static final int PANELLAYOUT = 87;
	public static final int SOFTKEY0PICON = 88;
	public static final int SOFTKEY1PICON = 89;
	public static final int LEFTICONBITMAPPATH = 90;
	public static final int RIGHTICONBITMAPPATH = 91;
	public static final int BACKGROUNDSTYLE = 92;
	public static final int LIMITLENGTH = 93;
	public static final int INPUTSTYLE = 94;
	public static final int URLFILEPATH = 95;
	public static final int DVERSION = 96;
	public static final int BGCOLOROPACITY = 97;
	public static final int ITEMDIVIDER = 98;
	public static final int FASTSCROLL = 99;
	public static final int ROW1HEIGHT = 100;
	public static final int ROW2HEIGHT = 101;
	public static final int COLUMN1WIDTH = 102;
	public static final int COLUMN2WIDTH = 103;
	public static final int TITLEALIGN = 104;
	public static final int SHOWTITLETEXT = 105;
	public static final int KEYPADENABLED = 106;
	public static final int COLOROFEMPTYLISTTEXT = 107;
	public static final int COLOROFTEXT = 108;
	public static final int COLOROFTITLETEXT = 109;
	public static final int BORDERSTYLE = 110;
	public static final int GROUPSTYLE = 111;
	public static final int BVERSION = 112;
	
	public static final String[] cszAttribName = { "title", "softKeyStyle",
	/* 2 */	"property", "text", "hAlign", "vAlign", "outline", "normalBGColor",
	/* 8 */	"normalTextColor", "normalOutline", "pressedBGColor",
	/* 11 */"pressedTextColor", "pressedOutline", "disableBGColor",
	/* 14 */"disableTextColor", "disableOutline", "ListItemFormat", "titleText",
	/* 18 */"itemText", "guideText", "itemColor", "value", "min", "max",
	/* 24 */"step", "stepSize", "textColor", "BGColor", "leftText", "centerText",
	/* 30 */"rightText", "leftTextColor", "centerTextColor", "rightTextColor",
	/* 34 */"maxText", "minText", "GroupID", "layout", "mode", "style", "x",
	/* 41 */"y", "width", "height", "id", "dock", "fit", "parent",
	/* 48 */"maxDropLineCount", "class", "softKey0Text", "softKey1Text",
	/* 52 */"softKey0NormalIcon", "softKey1NormalIcon", "line1Height", "line2Height",
	/* 56 */"BGBitmapPath", "NormalBitmapPath", "NormalBitmapX",
	/* 59 */"NormalBitmapY", "PressedBitmapPath", "PressedBitmapX",
	/* 62 */"PressedBitmapY", "DisabledBitmapPath", "DisabledBitmapX",
	/* 65 */"DisabledBitmapY", "NormalBGBitmapPath", "PressedBGBitmapPath",
	/* 68 */"contentsPath", "repeatMode", "Orientation",
	/* 71 */"Quality", "itemWidth", "itemHeight", "topMargin",
	/* 75 */"leftMargin", "textSize", "textStyle", "headerHeight", "textOfEmptyList", 
	/* 80 */"titleIcon", "bitmap", "bShowValueState", "bShowState", 
	/* 84 */"bEditModeEnable", "b24Hour", "panelId", "panelLayout", 
	/* 88 */"softKey0PressedIcon", "softKey1PressedIcon", "leftIconBitmapPath", 
	/* 91 */"rightIconBitmapPath",
	/* 92 */"BackgroundStyle",
	/* 93 */"LimitLength",
	/* 94 */"InputStyle",
	/* 95 */"UrlFilePath", 
	/* 96 */"Dversion", 
	/* 97 */"BGColorOpacity", "itemDivider"	, "fastScroll", 
	/* 100 */"row1Height", "row2Height", "column1Width", "column2Width", 
	/* 104 */"titleAlign",
	/* 105 */"bShowTitleText",
	/* 106 */"KeypadEnabled", "colorOfEmptyListText", 
	/* 108 */"colorOfText", "colorOfTitleText",
	/* 110 */"borderStyle", "GroupStyle", "Bversion"
	};

	// TEXTDIRECTION CONST.
	public static final int TEXT_DIRECTION_HORIZONTAL = 0;
	public static final int TEXT_DIRECTION_VERTICAL = 1;
	public static final String[] cszTextDirection = {
			"TEXT_DIRECTION_HORIZONTAL", "TEXT_DIRECTION_VERTICAL" };

	// ALIGN CONST.
	public static final int ALIGN_LEFT = 0;
	public static final int ALIGN_CENTER = 1;
	public static final int ALIGN_RIGHT = 2;
	public static final String[] cszHAlign = { "ALIGN_LEFT",
			"ALIGN_CENTER", "ALIGN_RIGHT" };
	
    public static final int SHOWTAB_NONE = 0;
    public static final int SHOWTAB_ICON = 1;
    public static final int SHOWTAB_TEXT = 2;
	public static final String[] sShowTab = { "NONE", 
	    "Icon Tab", "Text Tab"};

	public static final int ALIGN_TOP = 0;
	public static final int ALIGN_MIDDLE = 1;
	public static final int ALIGN_BOTTOM = 2;
	public static final String[] cszVAlign = { "ALIGN_TOP",
			"ALIGN_MIDDLE", "ALIGN_BOTTOM" };

	// BACKGROUND STYLE
	public static final int BACKGROUND_STYLE_NONE = 0;
	public static final int BACKGROUND_STYLE_DEFAULT = 1;
	
	public static final String[] cszBgStyle = { "BACKGROUND_STYLE_NONE", "BACKGROUND_STYLE_DEFAULT" };
	
	public static final String[] cszBorderStyle = { "BORDER_STYLE_NONE",
			"BORDER_STYLE_OUTLINE", "BORDER_STYLE_ROUND_TOP",
			"BORDER_STYLE_ROUND_BOTTOM", "BORDER_STYLE_ROUND" };
	
	
    public static final int GROUP_STYLE_NONE = 0;
    public static final int GROUP_STYLE_SINGLE = 1;
    public static final int GROUP_STYLE_TOP = 2;
    public static final int GROUP_STYLE_MIDDLE = 3;
    public static final int GROUP_STYLE_BOTTOM = 4;
    public static final String[] cszGroupStyle = { "GROUP_STYLE_NONE",
        "GROUP_STYLE_SINGLE", "GROUP_STYLE_TOP",
        "GROUP_STYLE_MIDDLE", "GROUP_STYLE_BOTTOM" };
    
	// STYLE CONST.
	public static final int STYLE_FORM = 0;
	public static final int FORM_STYLE_NORMAL = 0;
	public static final int FORM_STYLE_INDICATOR = 1;
	public static final int FORM_STYLE_TITLE = 2;
	public static final int FORM_STYLE_SOFTKEY0 = 3;
	public static final int FORM_STYLE_SOFTKEY1 = 4;
	public static final int FORM_STYLE_OPTIONKEY = 5;
	public static final int FORM_STYLE_TEXTTAB = 6;
	public static final int FORM_STYLE_ICONTAB = 7;

	public static final int STYLE_LABEL = 1;
	public static final int LABEL_STYLE_SCROLL = 2;
	public static final int STYLE_SPIN = 2;
	public static final int STYLE_EDITFIELD = 3;
	public static final int STYLE_EDITTIME = 4;
	public static final int STYLE_PROGRESS = 5;
	public static final int STYLE_LIST = 6;
	public static final int STYLE_ICONLIST = 7;
	public static final int STYLE_FLASHCONTROL = 8;
	public static final int STYLE_CUSTOMLIST = 9;
	public static final int STYLE_LABEL_TEXT = 10;
	public static final int STYLE_TAB = 11;
	public static final int STYLE_CHECK = 12;
	public static final int STYLE_DATEPICKER = 13;
	public static final int STYLE_TIMEPICKER = 14;
	
	public static final int CHECK_BUTTON_STYLE_MARK = 0;
	public static final int CHECK_BUTTON_STYLE_MARK_WITH_DIVIDER = 1;
	public static final int CHECK_BUTTON_STYLE_ONOFF = 2;
	public static final int CHECK_BUTTON_STYLE_ONOFF_WITH_DIVIDER = 3;
	public static final int CHECK_BUTTON_STYLE_RADIO = 4;
	public static final int CHECK_BUTTON_STYLE_RADIO_WITH_DIVIDER = 5;
	
	public static final int LIST_STYLE_NORMAL = 0;
	public static final int LIST_STYLE_NUMBER = 1;
	public static final int LIST_STYLE_RADIO = 2;
	public static final int LIST_STYLE_RADIO_WITH_DIVIDER = 3;
	public static final int LIST_STYLE_MARK = 4;
	public static final int LIST_STYLE_MARK_WITH_DIVIDER = 5;
	public static final int LIST_STYLE_ONOFF = 6;
	public static final int LIST_STYLE_ONOFF_WITH_DIVIDER = 7;
		
public static final String[][] cszStyle = {
			/* STYLE_FORM */{ "FORM_STYLE_NORMAL", "FORM_STYLE_INDICATOR", "FORM_STYLE_TITLE",
					"FORM_STYLE_SOFTKEY_0", "FORM_STYLE_SOFTKEY_1", "FORM_STYLE_OPTIONKEY",
					"FORM_STYLE_TEXT_TAB", "FORM_STYLE_ICON_TAB" },
			/* STYLE_LABEL */{ "LABEL_STYLE_NONE", "LABEL_STYLE_ONELINE",
					"LABEL_STYLE_SCROLL", "LABEL_STYLE_TITLE_LEFT",
					"LABEL_STYLE_TITLE_TOP" },
			/* STYLE_SPIN */{ "STYLE_TITLE_NONE", "STYLE_TITLE_LEFT",
					"STYLE_TITLE_TOP" },
			/* STYLE_EDITFIELD */{ "EDIT_FIELD_STYLE_NORMAL","EDIT_FIELD_STYLE_PASSWORD",
						"EDIT_FIELD_STYLE_NORMAL_SMALL","EDIT_FIELD_STYLE_PASSWORD_SMALL", 
						"EDIT_FIELD_STYLE_EMAIL", "EDIT_FIELD_STYLE_URL", 
						"EDIT_FIELD_STYLE_EMAIL_SMALL", "EDIT_FIELD_STYLE_URL_SMALL"},
			/* STYLE_EDITTIME */{ "STYLE_NORMAL", "STYLE_TITLE_LEFT",
					"STYLE_TITLE_TOP" },
			/* STYLE_PROGRESS */{ "PROGRESS_STYLE_NONE" },
			/* STYLE_LIST */{ "LIST_STYLE_NORMAL","LIST_STYLE_NUMBER","LIST_STYLE_RADIO",
				"LIST_STYLE_RADIO_WITH_DIVIDER","LIST_STYLE_MARK","LIST_STYLE_MARK_WITH_DIVIDER",
				"LIST_STYLE_ONOFF","LIST_STYLE_ONOFF_WITH_DIVIDER" },
			/* STYLE_ICONLIST */{ "ICON_LIST_STYLE_NORMAL",
					"ICON_LIST_STYLE_RADIO","ICON_LIST_STYLE_MARK"},
			/* STYLE_FLASHCONTROL */{ "FLASH_STYLE_NORMAL",
					"FLASH_STYLE_PLAY_WITHOUT_FOCUS" },
			/* STYLE_CUSTOMLIST */{ "CUSTOM_LIST_STYLE_NORMAL", 
					    "CUSTOM_LIST_STYLE_RADIO", "CUSTOM_LIST_STYLE_RADIO_WITH_DIVIDER", 
					"CUSTOM_LIST_STYLE_MARK", "CUSTOM_LIST_STYLE_MARK_WITH_DIVIDER", 
					"CUSTOM_LIST_STYLE_ONOFF", "CUSTOM_LIST_STYLE_ONOFF_WITH_DIVIDER" },
			/* STYLE_LABEL_TEXT */{ "LABEL_TEXT_STYLE_NORMAL",
					"LABEL_TEXT_STYLE_BOLD", "LABEL_TEXT_STYLE_ITALIC" }, 
			/* STYLE_TAB */{ "TAB_MODE_NORMAL", "TAB_MODE_EDIT" }, 
			/* STYLE_CHECK */{ "CHECK_BUTTON_STYLE_MARK", "CHECK_BUTTON_STYLE_MARK_WITH_DIVIDER", "CHECK_BUTTON_STYLE_ONOFF", 
				"CHECK_BUTTON_STYLE_ONOFF_WITH_DIVIDER", "CHECK_BUTTON_STYLE_RADIO", "CHECK_BUTTON_STYLE_RADIO_WITH_DIVIDER"},
			/* STYLE_DATEPICKER */{ "INPUT_STYLE_FULLSCREEN", "INPUT_STYLE_OVERLAY"}, 
			/* STYLE_TIMEPICKER */{ "INPUT_STYLE_FULLSCREEN", "INPUT_STYLE_OVERLAY"} 
	};

	public static final int LABEL_TEXT_STYLE_NORMAL = 0;
	public static final int LABEL_TEXT_STYLE_BOLD = 1;
	public static final int LABEL_TEXT_STYLE_ITALIC = 2;

	public static final int LIST_ITEM_SINGLE_IMAGE = 0;
	public static final int LIST_ITEM_SINGLE_TEXT = 1;
	public static final int LIST_ITEM_SINGLE_IMAGE_TEXT = 2;
	public static final int LIST_ITEM_SINGLE_TEXT_IMAGE = 3;
	public static final int LIST_ITEM_SINGLE_IMAGE_TEXT_IMAGE = 4;
	public static final int LIST_ITEM_DOUBLE_IMAGE_TEXT_FULLTEXT = 5;
	public static final int LIST_ITEM_DOUBLE_FULLTEXT_IMAGE_TEXT = 6;
	public static final int LIST_ITEM_DOUBLE_TEXT_IMAGE_FULLTEXT = 7;
	public static final int LIST_ITEM_DOUBLE_FULLTEXT_TEXT_IMAGE = 8;
	public static final int LIST_ITEM_DOUBLE_IMAGE_TEXT_TEXT = 9;
	public static final int LIST_ITEM_DOUBLE_TEXT_TEXT_IMAGE = 10;
	public static final String[] cszListItemFormat = {
			"LIST_ITEM_SINGLE_IMAGE", "LIST_ITEM_SINGLE_TEXT",
			"LIST_ITEM_SINGLE_IMAGE_TEXT", "LIST_ITEM_SINGLE_TEXT_IMAGE",
			"LIST_ITEM_SINGLE_IMAGE_TEXT_IMAGE",
			"LIST_ITEM_DOUBLE_IMAGE_TEXT_FULLTEXT",
			"LIST_ITEM_DOUBLE_FULLTEXT_IMAGE_TEXT",
			"LIST_ITEM_DOUBLE_TEXT_IMAGE_FULLTEXT",
			"LIST_ITEM_DOUBLE_FULLTEXT_TEXT_IMAGE",
			"LIST_ITEM_DOUBLE_IMAGE_TEXT_TEXT",
			"LIST_ITEM_DOUBLE_TEXT_TEXT_IMAGE" };

	// FRAME CONST.
	public static final int ORIENTATION_AUTOMATIC2D = 0;
	public static final int ORIENTATION_AUTOMATIC4D = 1;
	public static final int ORIENTATION_LANDSCAPE = 2;
	public static final int ORIENTATION_RLANDSCAPE = 3;
	public static final int ORIENTATION_PORTRAIT = 4;
	public static final int ORIENTATION_RPORTRAIT = 5;
	public static final String[] cszOrientation = { "Automatic",
			"Automatic:4Dir", "Landscape", "Landscape:Reverse", "Portrait",
			"Portrait:Reverse" };

	// SPIN CONST.
	public static final int SPIN_STYLE_TITLE_NODE = 0;
	public static final int SPIN_STYLE_TITLE_LEFT = 1;

	// EDITFIELD CONST.
	public static final int EDIT_FIELD_STYLE_NORMAL = 0;
	public static final int EDIT_FIELD_STYLE_PASSWORD = 1;
	public static final int EDIT_FIELD_STYLE_NORMAL_SMALL = 2;
	public static final int EDIT_FIELD_STYLE_PASSWORD_SMALL = 3;
	public static final int EDIT_FIELD_STYLE_EMAIL = 4;
	public static final int EDIT_FIELD_STYLE_URL = 5; 
	public static final int EDIT_FIELD_STYLE_EMAIL_SMALL = 6; 
	public static final int EDIT_FIELD_STYLE_URL_SMALL = 7;

	
	// EDITFILED,EDITAREA INPUTSTYLE
	public static final String[] cszInputStyle = { "INPUT_STYLE_FULLSCREEN",
		"INPUT_STYLE_OVERLAY" };
	

	// EDITTIME CONST.
	public static final int EDITTIME_STYLE_NORMAL = 0;
	public static final int EDITTIME_STYLE_TITLE_LEFT = 1;
	public static final int EDITTIME_STYLE_TITLE_TOP = 2;
	public static final String[] cszEditTimeStyle = { "STYLE_NORMAL",
			"STYLE_TITLE_LEFT", "STYLE_TITLE_TOP" };

	// ICONLIST CONST.
	public static final int ICONLIST_STYLE_NORMAL = 0;
	public static final int ICONLIST_STYLE_TITLE_LEFT = 1;
	public static final String[] cszIconListStyle = { "STYLE_NORMAL",
			"STYLE_TITLE_LEFT" };

	// FLASH REPEATMODE CONST.
	public static final int FLASH_REPEAT_NONE = 0;
	public static final int FLASH_REPEAT_LOOP = 1;
	public static final String[] cszRepeatMode = { "FLASH_REPEAT_NONE",
			"FLASH_REPEAT_LOOP" };
	// FLASH QUALITY CONST.
	public static final int FLASH_QUALITY_LOW = 0;
	public static final int FLASH_QUALITY_MEDIUM = 1;
	public static final int FLASH_QUALITY_HIGH = 2;
	public static final String[] cszQuality = { "FLASH_QUALITY_LOW",
			"FLASH_QUALITY_MEDIUM", "FLASH_QUALITY_HIGH" };

	// Docking
	public static final int DOCK_NONE = 0;
	public static final int DOCK_LEFT = 1;
	public static final int DOCK_TOP = 2;
	public static final int DOCK_RIGHT = 3;
	public static final int DOCK_BOTTOM = 4;
	public static final int DOCK_LEFTTOP = 5;
	public static final int DOCK_RIGHTTOP = 6;
	public static final int DOCK_RIGHTBOTTOM = 7;
	public static final int DOCK_LEFTBOTTOM = 8;

	public static final String cszDock[] = { "None", "Left", "Top", "Right",
			"Bottom", "Left|Top", "Right|Top", "Right|Bottom", "Left|Bottom" };
	public static final int FONT_VWIDTH = 20;
	public static final float FONT_RATE = 0.9f;
}
