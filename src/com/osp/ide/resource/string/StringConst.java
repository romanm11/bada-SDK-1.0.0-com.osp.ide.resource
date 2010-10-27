package com.osp.ide.resource.string;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.FontData;

public interface StringConst {
	public static final int LANG_NUM = 184;

	public class _MyDefine {
		public String name;
		public String comment;

		public _MyDefine(String name, String comment) {
			this.name = name;
			this.comment = comment;
		};
	};

	// enum idxLanguage {ENG, KOR, CZE, DAN, DUT, FIN, FRE, GER, GRE, HUN, ITA,
	// NOR, POL, POR, ROM, RUS, SLB, SLV, SPA, SWE, TUR, UNS};
//	public static final _MyDefine[] cszLanguage = {
//			new _MyDefine("aa", "Afar"), new _MyDefine("ab", "Abkhaze"),
//			new _MyDefine("af", "Afrikaans"), new _MyDefine("ak", "Akan"),
//			new _MyDefine("sq", "Albanian"), new _MyDefine("am", "Amharic"),
//			new _MyDefine("ar", "Arabic"), new _MyDefine("an", "Aragonese"),
//			new _MyDefine("hy", "Armenian"), new _MyDefine("as", "Assamese"),
//			new _MyDefine("av", "Avaric"), new _MyDefine("ae", "Avestan"),
//			new _MyDefine("ay", "Aymara"), new _MyDefine("az", "Azerbaijani"),
//			new _MyDefine("ba", "Bashkir"), new _MyDefine("bm", "Bambara"),
//			new _MyDefine("eu", "Basque"), new _MyDefine("be", "Belarusian"),
//			new _MyDefine("bn", "Bengali"), new _MyDefine("bh", "Bihari"),
//			new _MyDefine("bi", "Bislama"), new _MyDefine("bo", "Tibetan"),
//			new _MyDefine("bs", "Bosnian"), new _MyDefine("br", "Breton"),
//			new _MyDefine("bg", "Bulgarian"), new _MyDefine("my", "Burmese"),
//			new _MyDefine("ca", "Catalan"), new _MyDefine("cs", "Czech"),
//			new _MyDefine("ch", "Chamorro"), new _MyDefine("ce", "Chechen"),
//			new _MyDefine("zh", "Chinese"),
//			new _MyDefine("cu", "ChurchSlavic"),
//			new _MyDefine("cv", "Chuvash"), new _MyDefine("kw", "Cornish"),
//			new _MyDefine("co", "Corsican"), new _MyDefine("cr", "Cree"),
//			new _MyDefine("cy", "Welsh"), new _MyDefine("cs", "Czech"),
//			new _MyDefine("da", "Danish"), new _MyDefine("de", "German"),
//			new _MyDefine("dv", "Divehi"), new _MyDefine("nl", "Dutch"),
//			new _MyDefine("dz", "Dzongkha"), new _MyDefine("el", "Greek"),
//			new _MyDefine("en", "English"), new _MyDefine("eo", "Esperanto"),
//			new _MyDefine("et", "Estonian"), new _MyDefine("ee", "Ewe"),
//			new _MyDefine("fo", "Faroese"), new _MyDefine("fa", "Persian"),
//			new _MyDefine("fj", "Fijian"), new _MyDefine("fi", "Finnish"),
//			new _MyDefine("fr", "French"),
//			new _MyDefine("fy", "WesternFrisian"),
//			new _MyDefine("ff", "Fulah"), new _MyDefine("ka", "Georgian"),
//			new _MyDefine("gd", "Gaelic"), new _MyDefine("ga", "Irish"),
//			new _MyDefine("gl", "Galician"), new _MyDefine("gv", "Manx"),
//			new _MyDefine("gn", "Guarani"), new _MyDefine("bn", "Bengali"),
//			new _MyDefine("bh", "Bihari"), new _MyDefine("gu", "Gujarati"),
//			new _MyDefine("ht", "Haitian"), new _MyDefine("ha", "Hausa"),
//			new _MyDefine("he", "Hebrew"), new _MyDefine("hz", "Herero"),
//			new _MyDefine("hi", "Hindi"), new _MyDefine("ho", "Hiri Motu"),
//			new _MyDefine("hr", "Croatian"), new _MyDefine("hu", "Hungarian"),
//			new _MyDefine("ig", "Igbo"), new _MyDefine("is", "Icelandic"),
//			new _MyDefine("io", "Ido"), new _MyDefine("ii", "SichuanYi"),
//			new _MyDefine("iu", "Inuktitut"),
//			new _MyDefine("ie", "Interlingue"),
//			new _MyDefine("ia", "Interlingua"),
//			new _MyDefine("id", "Indonesian"), new _MyDefine("ik", "Inupiaq"),
//			new _MyDefine("it", "Italian"), new _MyDefine("jv", "Javanese"),
//			new _MyDefine("ja", "Japanese"),
//			new _MyDefine("kl", "Kalaallisut"), new _MyDefine("kn", "Kannada"),
//			new _MyDefine("ks", "Kashmiri"), new _MyDefine("kr", "Kanuri"),
//			new _MyDefine("kk", "Kazakh"), new _MyDefine("km", "CentralKhmer"),
//			new _MyDefine("ki", "Kikuyu"), new _MyDefine("rw", "Kinyarwanda"),
//			new _MyDefine("ky", "Kirghiz"), new _MyDefine("kv", "Komi"),
//			new _MyDefine("kg", "Kongo"), new _MyDefine("ko", "Korean"),
//			new _MyDefine("kj", "Kuanyama"), new _MyDefine("ku", "Kurdish"),
//			new _MyDefine("lo", "Lao"), new _MyDefine("la", "Latin"),
//			new _MyDefine("lv", "Latvian"), new _MyDefine("li", "Limburgan"),
//			new _MyDefine("ln", "Lingala"), new _MyDefine("lt", "Lithuanian"),
//			new _MyDefine("lb", "Luxembourgish"),
//			new _MyDefine("lu", "Luba-Katanga"), new _MyDefine("lg", "Ganda"),
//			new _MyDefine("mk", "Macedonian"),
//			new _MyDefine("mh", "Marshallese"),
//			new _MyDefine("ml", "Malayalam"), new _MyDefine("mi", "Maori"),
//			new _MyDefine("mr", "Marathi"), new _MyDefine("ms", "Malay"),
//			new _MyDefine("mg", "Malagasy"), new _MyDefine("mt", "Maltese"),
//			new _MyDefine("mn", "Mongolian"), new _MyDefine("na", "Nauru"),
//			new _MyDefine("nv", "Navajo"),
//			new _MyDefine("nr", "SouthNdebele"),
//			new _MyDefine("nd", "NorthNdebele"),
//			new _MyDefine("ng", "Ndonga"), new _MyDefine("ne", "Nepali"),
//			new _MyDefine("nn", "NorwegianNynorsk"),
//			new _MyDefine("nb", "NorwegianBokmal"),
//			new _MyDefine("no", "Norwegian"), new _MyDefine("ny", "Chichewa"),
//			new _MyDefine("oc", "Occitan"), new _MyDefine("oj", "Ojibwa"),
//			new _MyDefine("or", "Oriya"), new _MyDefine("om", "Oromo"),
//			new _MyDefine("os", "Ossetian"), new _MyDefine("pa", "Panjabi"),
//			new _MyDefine("pi", "Pali"), new _MyDefine("pl", "Polish"),
//			new _MyDefine("pt", "Portuguese"), new _MyDefine("ps", "Pushto"),
//			new _MyDefine("qu", "Quechua"), new _MyDefine("rm", "Romansh"),
//			new _MyDefine("ro", "Romanian"), new _MyDefine("rn", "Rundi"),
//			new _MyDefine("ru", "Russian"), new _MyDefine("sg", "Sango"),
//			new _MyDefine("sa", "Sanskrit"), new _MyDefine("si", "Sinhala"),
//			new _MyDefine("sk", "Slovak"), new _MyDefine("sl", "Slovenian"),
//			new _MyDefine("se", "NorthernSami"), new _MyDefine("sm", "Samoan"),
//			new _MyDefine("sn", "Shona"), new _MyDefine("sd", "Sindhi"),
//			new _MyDefine("so", "Somali"),
//			new _MyDefine("st", "SouthernSotho"),
//			new _MyDefine("es", "Spanish"), new _MyDefine("sc", "Sardinian"),
//			new _MyDefine("sr", "Serbian"), new _MyDefine("ss", "Swati"),
//			new _MyDefine("su", "Sundanese"), new _MyDefine("sw", "Swahili"),
//			new _MyDefine("sv", "Swedish"), new _MyDefine("ty", "Tahitian"),
//			new _MyDefine("ta", "Tamil"), new _MyDefine("tt", "Tatar"),
//			new _MyDefine("te", "Telugu"), new _MyDefine("tg", "Tajik"),
//			new _MyDefine("tl", "Tagalog"), new _MyDefine("th", "Thai"),
//			new _MyDefine("ti", "Tigrinya"), new _MyDefine("to", "Tonga"),
//			new _MyDefine("tn", "Tswana"), new _MyDefine("ts", "Turkmen"),
//			new _MyDefine("tr", "Turkish"), new _MyDefine("tw", "Twi"),
//			new _MyDefine("ug", "Uighur"), new _MyDefine("uk", "Ukrainian"),
//			new _MyDefine("ur", "Urdu"), new _MyDefine("uz", "Uzbek"),
//			new _MyDefine("ve", "Venda"), new _MyDefine("vi", "Vietnamese"),
//			new _MyDefine("vo", "Volapuk"), new _MyDefine("wa", "Walloon"),
//			new _MyDefine("wo", "Wolof"), new _MyDefine("xh", "Xhosa"),
//			new _MyDefine("yi", "Yiddish"), new _MyDefine("yo", "Yoruba"),
//			new _MyDefine("za", "Zhuang"), new _MyDefine("zu", "Zulu") };

	// enum idxFonts {FONT_12N, FONT_12B, FONT_14N, FONT_14B, FONT_14I,
	// FONT_16N, FONT_16B, FONT_16I, FONT_18N, FONT_18B, FONT_18I,
	// FONT_20N, FONT_20B, FONT_20I, FONT_24N, FONT_24B, FONT_24I,
	// FONT_28N, FONT_28B, FONT_28I, FONT_32B};
	public static final int FONT_12N = 0;
	public static final int FONT_12B = 1;
	public static final int FONT_14N = 2;
	public static final int FONT_14B = 3;
	public static final int FONT_14I = 4;
	public static final int FONT_16N = 5;
	public static final int FONT_16B = 6;
	public static final int FONT_16I = 7;
	public static final int FONT_18N = 8;
	public static final int FONT_18B = 9;
	public static final int FONT_18I = 10;
	public static final int FONT_20N = 11;
	public static final int FONT_20B = 12;
	public static final int FONT_20I = 13;
	public static final int FONT_24N = 14;
	public static final int FONT_24B = 15;
	public static final int FONT_24I = 16;
	public static final int FONT_28N = 17;
	public static final int FONT_28B = 18;
	public static final int FONT_28I = 19;
	public static final int FONT_32B = 20;
	public static final String cszFont[] = { "FONT_12_NORMAL", "FONT_12_BOLD",
			"FONT_14_NORMAL", "FONT_14_BOLD", "FONT_14_ITALIC",
			"FONT_16_NORMAL", "FONT_16_BOLD", "FONT_16_ITALIC",
			"FONT_18_NORMAL", "FONT_18_BOLD", "FONT_18_ITALIC",
			"FONT_20_NORMAL", "FONT_20_BOLD", "FONT_20_ITALIC",
			"FONT_24_NORMAL", "FONT_24_BOLD", "FONT_24_ITALIC",
			"FONT_28_NORMAL", "FONT_28_BOLD", "FONT_28_ITALIC", "FONT_32_BOLD" };
	public static final FontData[] Font = {
			new FontData("∞ÌµÒ√º", 18, SWT.NORMAL),
			new FontData("∞ÌµÒ√º", 18, SWT.BOLD),
			new FontData("∞ÌµÒ√º", 22, SWT.NORMAL),
			new FontData("∞ÌµÒ√º", 22, SWT.BOLD),
			new FontData("∞ÌµÒ√º", 22, SWT.ITALIC),
			new FontData("∞ÌµÒ√º", 26, SWT.NORMAL),
			new FontData("∞ÌµÒ√º", 26, SWT.BOLD),
			new FontData("∞ÌµÒ√º", 26, SWT.ITALIC),
			new FontData("∞ÌµÒ√º", 30, SWT.NORMAL),
			new FontData("∞ÌµÒ√º", 30, SWT.BOLD),
			new FontData("∞ÌµÒ√º", 30, SWT.ITALIC),
			new FontData("∞ÌµÒ√º", 34, SWT.NORMAL),
			new FontData("∞ÌµÒ√º", 34, SWT.BOLD),
			new FontData("∞ÌµÒ√º", 34, SWT.ITALIC),
			new FontData("∞ÌµÒ√º", 38, SWT.NORMAL),
			new FontData("∞ÌµÒ√º", 38, SWT.BOLD),
			new FontData("∞ÌµÒ√º", 38, SWT.ITALIC),
			new FontData("∞ÌµÒ√º", 42, SWT.NORMAL),
			new FontData("∞ÌµÒ√º", 42, SWT.BOLD),
			new FontData("∞ÌµÒ√º", 42, SWT.ITALIC),
			new FontData("∞ÌµÒ√º", 50, SWT.BOLD) };

	// enum depth {TREE_ROOT, TREE_GROUP, TREE_TABLE, TREE_ITEM, TREE_NONE};
	public static final int TREE_ROOT = 0;
	public static final int TREE_GROUP = 1;
	public static final int TREE_TABLE = 2;
	public static final int TREE_ITEM = 3;
	public static final int TREE_NONE = 4;
	public static final int KIND_NUM = 4;
	// enum kind {KIND_STRING, KIND_MENU, KIND_DIALOG, KIND_BITMAP, KIND_NONE};
	public static final int KIND_STRING = 0;
	public static final int KIND_MENU = 1;
	public static final int KIND_DIALOG = 2;
	public static final int KIND_BITMAP = 3;
	public static final int KIND_NONE = 4;
	public static final String cszKindName[] = { "String", "Menu", "Dialog",
			"Bitmap" };
	public static final String cszKind[] = { "STRING", "MENU", "DIALOG",
			"BITMAP" };

	public static final int STRING_TAG_NUM = 2;

	public static final int TAG_TABLE = 0;
	public static final int TAG_TEXT = 1;

	public static final String cszTag[] = { "string_table", "text" };

	public static final int STRING_ATTR_NUM = 2;

	public static final int ATTR_LANGUAGE = 0;
	public static final int ATTR_ID = 1;

	public static final String cszAttr[] = { "language", "id" };
}
