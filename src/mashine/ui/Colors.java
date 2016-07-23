/**
 *  Static definig of several colors including Google's material colors
 *
 *	@author procsynth - Antoine Pintout
 *	@since  13-02-2016`
 */

package mashine.ui;

import mashine.utils.FlatColor;

public final class Colors {

	private Colors () {}

	public static final FlatColor WHITE = new FlatColor(0xFF);
	public static final FlatColor BLACK = new FlatColor(0x00);
	public static final FlatColor RED = new FlatColor(0xFF,0xFF,0xFF);

	public static final class MATERIAL{
		private MATERIAL () {}

		public static final class RED{
			public static final FlatColor _50 = new FlatColor(0xFF, 0xEB, 0xEE);
			public static final FlatColor _100 = new FlatColor(0xFF, 0xCD, 0xD2);
			public static final FlatColor _200 = new FlatColor(0xEF, 0x9A, 0x9A);
			public static final FlatColor _300 = new FlatColor(0xE5, 0x73, 0x73);
			public static final FlatColor _400 = new FlatColor(0xEF, 0x53, 0x50);
			public static final FlatColor _500 = new FlatColor(0xF4, 0x43, 0x36);
			public static final FlatColor _600 = new FlatColor(0xE5, 0x39, 0x35);
			public static final FlatColor _700 = new FlatColor(0xD3, 0x2F, 0x2F);
			public static final FlatColor _800 = new FlatColor(0xC6, 0x28, 0x28);
			public static final FlatColor _900 = new FlatColor(0xB7, 0x1C, 0x1C);
			public static final FlatColor A100 = new FlatColor(0xFF, 0x8A, 0x80);
			public static final FlatColor A200 = new FlatColor(0xFF, 0x52, 0x52);
			public static final FlatColor A400 = new FlatColor(0xFF, 0x17, 0x44);
			public static final FlatColor A700 = new FlatColor(0xD5, 0x00, 0x00);
		}
		public static final class PINK{
			public static final FlatColor _50 = new FlatColor(0xFC, 0xE4, 0xEC);
			public static final FlatColor _100 = new FlatColor(0xF8, 0xBB, 0xD0);
			public static final FlatColor _200 = new FlatColor(0xF4, 0x8F, 0xB1);
			public static final FlatColor _300 = new FlatColor(0xF0, 0x62, 0x92);
			public static final FlatColor _400 = new FlatColor(0xEC, 0x40, 0x7A);
			public static final FlatColor _500 = new FlatColor(0xE9, 0x1E, 0x63);
			public static final FlatColor _600 = new FlatColor(0xD8, 0x1B, 0x60);
			public static final FlatColor _700 = new FlatColor(0xC2, 0x18, 0x5B);
			public static final FlatColor _800 = new FlatColor(0xAD, 0x14, 0x57);
			public static final FlatColor _900 = new FlatColor(0x88, 0x0E, 0x4F);
			public static final FlatColor A100 = new FlatColor(0xFF, 0x80, 0xAB);
			public static final FlatColor A200 = new FlatColor(0xFF, 0x40, 0x81);
			public static final FlatColor A400 = new FlatColor(0xF5, 0x00, 0x57);
			public static final FlatColor A700 = new FlatColor(0xC5, 0x11, 0x62);
		}
		public static final class PURPLE{
			public static final FlatColor _50 = new FlatColor(0xF3, 0xE5, 0xF5);
			public static final FlatColor _100 = new FlatColor(0xE1, 0xBE, 0xE7);
			public static final FlatColor _200 = new FlatColor(0xCE, 0x93, 0xD8);
			public static final FlatColor _300 = new FlatColor(0xBA, 0x68, 0xC8);
			public static final FlatColor _400 = new FlatColor(0xAB, 0x47, 0xBC);
			public static final FlatColor _500 = new FlatColor(0x9C, 0x27, 0xB0);
			public static final FlatColor _600 = new FlatColor(0x8E, 0x24, 0xAA);
			public static final FlatColor _700 = new FlatColor(0x7B, 0x1F, 0xA2);
			public static final FlatColor _800 = new FlatColor(0x6A, 0x1B, 0x9A);
			public static final FlatColor _900 = new FlatColor(0x4A, 0x14, 0x8C);
			public static final FlatColor A100 = new FlatColor(0xEA, 0x80, 0xFC);
			public static final FlatColor A200 = new FlatColor(0xE0, 0x40, 0xFB);
			public static final FlatColor A400 = new FlatColor(0xD5, 0x00, 0xF9);
			public static final FlatColor A700 = new FlatColor(0xAA, 0x00, 0xFF);
		}
		public static final class DEEP_PURPLE{
			public static final FlatColor _50 = new FlatColor(0xED, 0xE7, 0xF6);
			public static final FlatColor _100 = new FlatColor(0xD1, 0xC4, 0xE9);
			public static final FlatColor _200 = new FlatColor(0xB3, 0x9D, 0xDB);
			public static final FlatColor _300 = new FlatColor(0x95, 0x75, 0xCD);
			public static final FlatColor _400 = new FlatColor(0x7E, 0x57, 0xC2);
			public static final FlatColor _500 = new FlatColor(0x67, 0x3A, 0xB7);
			public static final FlatColor _600 = new FlatColor(0x5E, 0x35, 0xB1);
			public static final FlatColor _700 = new FlatColor(0x51, 0x2D, 0xA8);
			public static final FlatColor _800 = new FlatColor(0x45, 0x27, 0xA0);
			public static final FlatColor _900 = new FlatColor(0x31, 0x1B, 0x92);
			public static final FlatColor A100 = new FlatColor(0xB3, 0x88, 0xFF);
			public static final FlatColor A200 = new FlatColor(0x7C, 0x4D, 0xFF);
			public static final FlatColor A400 = new FlatColor(0x65, 0x1F, 0xFF);
			public static final FlatColor A700 = new FlatColor(0x62, 0x00, 0xEA);
		}
		public static final class INDIGO{
			public static final FlatColor _50 = new FlatColor(0xE8, 0xEA, 0xF6);
			public static final FlatColor _100 = new FlatColor(0xC5, 0xCA, 0xE9);
			public static final FlatColor _200 = new FlatColor(0x9F, 0xA8, 0xDA);
			public static final FlatColor _300 = new FlatColor(0x79, 0x86, 0xCB);
			public static final FlatColor _400 = new FlatColor(0x5C, 0x6B, 0xC0);
			public static final FlatColor _500 = new FlatColor(0x3F, 0x51, 0xB5);
			public static final FlatColor _600 = new FlatColor(0x39, 0x49, 0xAB);
			public static final FlatColor _700 = new FlatColor(0x30, 0x3F, 0x9F);
			public static final FlatColor _800 = new FlatColor(0x28, 0x35, 0x93);
			public static final FlatColor _900 = new FlatColor(0x1A, 0x23, 0x7E);
			public static final FlatColor A100 = new FlatColor(0x8C, 0x9E, 0xFF);
			public static final FlatColor A200 = new FlatColor(0x53, 0x6D, 0xFE);
			public static final FlatColor A400 = new FlatColor(0x3D, 0x5A, 0xFE);
			public static final FlatColor A700 = new FlatColor(0x30, 0x4F, 0xFE);
		}
		public static final class BLUE{
			public static final FlatColor _50 = new FlatColor(0xE3, 0xF2, 0xFD);
			public static final FlatColor _100 = new FlatColor(0xBB, 0xDE, 0xFB);
			public static final FlatColor _200 = new FlatColor(0x90, 0xCA, 0xF9);
			public static final FlatColor _300 = new FlatColor(0x64, 0xB5, 0xF6);
			public static final FlatColor _400 = new FlatColor(0x42, 0xA5, 0xF5);
			public static final FlatColor _500 = new FlatColor(0x21, 0x96, 0xF3);
			public static final FlatColor _600 = new FlatColor(0x1E, 0x88, 0xE5);
			public static final FlatColor _700 = new FlatColor(0x19, 0x76, 0xD2);
			public static final FlatColor _800 = new FlatColor(0x15, 0x65, 0xC0);
			public static final FlatColor _900 = new FlatColor(0x0D, 0x47, 0xA1);
			public static final FlatColor A100 = new FlatColor(0x82, 0xB1, 0xFF);
			public static final FlatColor A200 = new FlatColor(0x44, 0x8A, 0xFF);
			public static final FlatColor A400 = new FlatColor(0x29, 0x79, 0xFF);
			public static final FlatColor A700 = new FlatColor(0x29, 0x62, 0xFF);
		}
		public static final class LIGHT_BLUE{
			public static final FlatColor _50 = new FlatColor(0xE1, 0xF5, 0xFE);
			public static final FlatColor _100 = new FlatColor(0xB3, 0xE5, 0xFC);
			public static final FlatColor _200 = new FlatColor(0x81, 0xD4, 0xFA);
			public static final FlatColor _300 = new FlatColor(0x4F, 0xC3, 0xF7);
			public static final FlatColor _400 = new FlatColor(0x29, 0xB6, 0xF6);
			public static final FlatColor _500 = new FlatColor(0x03, 0xA9, 0xF4);
			public static final FlatColor _600 = new FlatColor(0x03, 0x9B, 0xE5);
			public static final FlatColor _700 = new FlatColor(0x02, 0x88, 0xD1);
			public static final FlatColor _800 = new FlatColor(0x02, 0x77, 0xBD);
			public static final FlatColor _900 = new FlatColor(0x01, 0x57, 0x9B);
			public static final FlatColor A100 = new FlatColor(0x80, 0xD8, 0xFF);
			public static final FlatColor A200 = new FlatColor(0x40, 0xC4, 0xFF);
			public static final FlatColor A400 = new FlatColor(0x00, 0xB0, 0xFF);
			public static final FlatColor A700 = new FlatColor(0x00, 0x91, 0xEA);
		}
		public static final class CYAN{
			public static final FlatColor _50 = new FlatColor(0xE0, 0xF7, 0xFA);
			public static final FlatColor _100 = new FlatColor(0xB2, 0xEB, 0xF2);
			public static final FlatColor _200 = new FlatColor(0x80, 0xDE, 0xEA);
			public static final FlatColor _300 = new FlatColor(0x4D, 0xD0, 0xE1);
			public static final FlatColor _400 = new FlatColor(0x26, 0xC6, 0xDA);
			public static final FlatColor _500 = new FlatColor(0x00, 0xBC, 0xD4);
			public static final FlatColor _600 = new FlatColor(0x00, 0xAC, 0xC1);
			public static final FlatColor _700 = new FlatColor(0x00, 0x97, 0xA7);
			public static final FlatColor _800 = new FlatColor(0x00, 0x83, 0x8F);
			public static final FlatColor _900 = new FlatColor(0x00, 0x60, 0x64);
			public static final FlatColor A100 = new FlatColor(0x84, 0xFF, 0xFF);
			public static final FlatColor A200 = new FlatColor(0x18, 0xFF, 0xFF);
			public static final FlatColor A400 = new FlatColor(0x00, 0xE5, 0xFF);
			public static final FlatColor A700 = new FlatColor(0x00, 0xB8, 0xD4);
		}
		public static final class TEAL{
			public static final FlatColor _50 = new FlatColor(0xE0, 0xF2, 0xF1);
			public static final FlatColor _100 = new FlatColor(0xB2, 0xDF, 0xDB);
			public static final FlatColor _200 = new FlatColor(0x80, 0xCB, 0xC4);
			public static final FlatColor _300 = new FlatColor(0x4D, 0xB6, 0xAC);
			public static final FlatColor _400 = new FlatColor(0x26, 0xA6, 0x9A);
			public static final FlatColor _500 = new FlatColor(0x00, 0x96, 0x88);
			public static final FlatColor _600 = new FlatColor(0x00, 0x89, 0x7B);
			public static final FlatColor _700 = new FlatColor(0x00, 0x79, 0x6B);
			public static final FlatColor _800 = new FlatColor(0x00, 0x69, 0x5C);
			public static final FlatColor _900 = new FlatColor(0x00, 0x4D, 0x40);
			public static final FlatColor A100 = new FlatColor(0xA7, 0xFF, 0xEB);
			public static final FlatColor A200 = new FlatColor(0x64, 0xFF, 0xDA);
			public static final FlatColor A400 = new FlatColor(0x1D, 0xE9, 0xB6);
			public static final FlatColor A700 = new FlatColor(0x00, 0xBF, 0xA5);
		}
		public static final class GREEN{
			public static final FlatColor _50 = new FlatColor(0xE8, 0xF5, 0xE9);
			public static final FlatColor _100 = new FlatColor(0xC8, 0xE6, 0xC9);
			public static final FlatColor _200 = new FlatColor(0xA5, 0xD6, 0xA7);
			public static final FlatColor _300 = new FlatColor(0x81, 0xC7, 0x84);
			public static final FlatColor _400 = new FlatColor(0x66, 0xBB, 0x6A);
			public static final FlatColor _500 = new FlatColor(0x4C, 0xAF, 0x50);
			public static final FlatColor _600 = new FlatColor(0x43, 0xA0, 0x47);
			public static final FlatColor _700 = new FlatColor(0x38, 0x8E, 0x3C);
			public static final FlatColor _800 = new FlatColor(0x2E, 0x7D, 0x32);
			public static final FlatColor _900 = new FlatColor(0x1B, 0x5E, 0x20);
			public static final FlatColor A100 = new FlatColor(0xB9, 0xF6, 0xCA);
			public static final FlatColor A200 = new FlatColor(0x69, 0xF0, 0xAE);
			public static final FlatColor A400 = new FlatColor(0x00, 0xE6, 0x76);
			public static final FlatColor A700 = new FlatColor(0x00, 0xC8, 0x53);
		}
		public static final class LIGHT_GREEN{
			public static final FlatColor _50 = new FlatColor(0xF1, 0xF8, 0xE9);
			public static final FlatColor _100 = new FlatColor(0xDC, 0xED, 0xC8);
			public static final FlatColor _200 = new FlatColor(0xC5, 0xE1, 0xA5);
			public static final FlatColor _300 = new FlatColor(0xAE, 0xD5, 0x81);
			public static final FlatColor _400 = new FlatColor(0x9C, 0xCC, 0x65);
			public static final FlatColor _500 = new FlatColor(0x8B, 0xC3, 0x4A);
			public static final FlatColor _600 = new FlatColor(0x7C, 0xB3, 0x42);
			public static final FlatColor _700 = new FlatColor(0x68, 0x9F, 0x38);
			public static final FlatColor _800 = new FlatColor(0x55, 0x8B, 0x2F);
			public static final FlatColor _900 = new FlatColor(0x33, 0x69, 0x1E);
			public static final FlatColor A100 = new FlatColor(0xCC, 0xFF, 0x90);
			public static final FlatColor A200 = new FlatColor(0xB2, 0xFF, 0x59);
			public static final FlatColor A400 = new FlatColor(0x76, 0xFF, 0x03);
			public static final FlatColor A700 = new FlatColor(0x64, 0xDD, 0x17);
		}
		public static final class LIME{
			public static final FlatColor _50 = new FlatColor(0xF9, 0xFB, 0xE7);
			public static final FlatColor _100 = new FlatColor(0xF0, 0xF4, 0xC3);
			public static final FlatColor _200 = new FlatColor(0xE6, 0xEE, 0x9C);
			public static final FlatColor _300 = new FlatColor(0xDC, 0xE7, 0x75);
			public static final FlatColor _400 = new FlatColor(0xD4, 0xE1, 0x57);
			public static final FlatColor _500 = new FlatColor(0xCD, 0xDC, 0x39);
			public static final FlatColor _600 = new FlatColor(0xC0, 0xCA, 0x33);
			public static final FlatColor _700 = new FlatColor(0xAF, 0xB4, 0x2B);
			public static final FlatColor _800 = new FlatColor(0x9E, 0x9D, 0x24);
			public static final FlatColor _900 = new FlatColor(0x82, 0x77, 0x17);
			public static final FlatColor A100 = new FlatColor(0xF4, 0xFF, 0x81);
			public static final FlatColor A200 = new FlatColor(0xEE, 0xFF, 0x41);
			public static final FlatColor A400 = new FlatColor(0xC6, 0xFF, 0x00);
			public static final FlatColor A700 = new FlatColor(0xAE, 0xEA, 0x00);
		}
		public static final class YELLOW{
			public static final FlatColor _50 = new FlatColor(0xFF, 0xFD, 0xE7);
			public static final FlatColor _100 = new FlatColor(0xFF, 0xF9, 0xC4);
			public static final FlatColor _200 = new FlatColor(0xFF, 0xF5, 0x9D);
			public static final FlatColor _300 = new FlatColor(0xFF, 0xF1, 0x76);
			public static final FlatColor _400 = new FlatColor(0xFF, 0xEE, 0x58);
			public static final FlatColor _500 = new FlatColor(0xFF, 0xEB, 0x3B);
			public static final FlatColor _600 = new FlatColor(0xFD, 0xD8, 0x35);
			public static final FlatColor _700 = new FlatColor(0xFB, 0xC0, 0x2D);
			public static final FlatColor _800 = new FlatColor(0xF9, 0xA8, 0x25);
			public static final FlatColor _900 = new FlatColor(0xF5, 0x7F, 0x17);
			public static final FlatColor A100 = new FlatColor(0xFF, 0xFF, 0x8D);
			public static final FlatColor A200 = new FlatColor(0xFF, 0xFF, 0x00);
			public static final FlatColor A400 = new FlatColor(0xFF, 0xEA, 0x00);
			public static final FlatColor A700 = new FlatColor(0xFF, 0xD6, 0x00);
		}
		public static final class AMBER{
			public static final FlatColor _50 = new FlatColor(0xFF, 0xF8, 0xE1);
			public static final FlatColor _100 = new FlatColor(0xFF, 0xEC, 0xB3);
			public static final FlatColor _200 = new FlatColor(0xFF, 0xE0, 0x82);
			public static final FlatColor _300 = new FlatColor(0xFF, 0xD5, 0x4F);
			public static final FlatColor _400 = new FlatColor(0xFF, 0xCA, 0x28);
			public static final FlatColor _500 = new FlatColor(0xFF, 0xC1, 0x07);
			public static final FlatColor _600 = new FlatColor(0xFF, 0xB3, 0x00);
			public static final FlatColor _700 = new FlatColor(0xFF, 0xA0, 0x00);
			public static final FlatColor _800 = new FlatColor(0xFF, 0x8F, 0x00);
			public static final FlatColor _900 = new FlatColor(0xFF, 0x6F, 0x00);
			public static final FlatColor A100 = new FlatColor(0xFF, 0xE5, 0x7F);
			public static final FlatColor A200 = new FlatColor(0xFF, 0xD7, 0x40);
			public static final FlatColor A400 = new FlatColor(0xFF, 0xC4, 0x00);
			public static final FlatColor A700 = new FlatColor(0xFF, 0xAB, 0x00);
		}
		public static final class ORANGE{
			public static final FlatColor _50 = new FlatColor(0xFF, 0xF3, 0xE0);
			public static final FlatColor _100 = new FlatColor(0xFF, 0xE0, 0xB2);
			public static final FlatColor _200 = new FlatColor(0xFF, 0xCC, 0x80);
			public static final FlatColor _300 = new FlatColor(0xFF, 0xB7, 0x4D);
			public static final FlatColor _400 = new FlatColor(0xFF, 0xA7, 0x26);
			public static final FlatColor _500 = new FlatColor(0xFF, 0x98, 0x00);
			public static final FlatColor _600 = new FlatColor(0xFB, 0x8C, 0x00);
			public static final FlatColor _700 = new FlatColor(0xF5, 0x7C, 0x00);
			public static final FlatColor _800 = new FlatColor(0xEF, 0x6C, 0x00);
			public static final FlatColor _900 = new FlatColor(0xE6, 0x51, 0x00);
			public static final FlatColor A100 = new FlatColor(0xFF, 0xD1, 0x80);
			public static final FlatColor A200 = new FlatColor(0xFF, 0xAB, 0x40);
			public static final FlatColor A400 = new FlatColor(0xFF, 0x91, 0x00);
			public static final FlatColor A700 = new FlatColor(0xFF, 0x6D, 0x00);
		}
		public static final class DEEP_ORANGE{
			public static final FlatColor _50 = new FlatColor(0xFB, 0xE9, 0xE7);
			public static final FlatColor _100 = new FlatColor(0xFF, 0xCC, 0xBC);
			public static final FlatColor _200 = new FlatColor(0xFF, 0xAB, 0x91);
			public static final FlatColor _300 = new FlatColor(0xFF, 0x8A, 0x65);
			public static final FlatColor _400 = new FlatColor(0xFF, 0x70, 0x43);
			public static final FlatColor _500 = new FlatColor(0xFF, 0x57, 0x22);
			public static final FlatColor _600 = new FlatColor(0xF4, 0x51, 0x1E);
			public static final FlatColor _700 = new FlatColor(0xE6, 0x4A, 0x19);
			public static final FlatColor _800 = new FlatColor(0xD8, 0x43, 0x15);
			public static final FlatColor _900 = new FlatColor(0xBF, 0x36, 0x0C);
			public static final FlatColor A100 = new FlatColor(0xFF, 0x9E, 0x80);
			public static final FlatColor A200 = new FlatColor(0xFF, 0x6E, 0x40);
			public static final FlatColor A400 = new FlatColor(0xFF, 0x3D, 0x00);
			public static final FlatColor A700 = new FlatColor(0xDD, 0x2C, 0x00);
		}
		public static final class BROWN{
			public static final FlatColor _50 = new FlatColor(0xEF, 0xEB, 0xE9);
			public static final FlatColor _100 = new FlatColor(0xD7, 0xCC, 0xC8);
			public static final FlatColor _200 = new FlatColor(0xBC, 0xAA, 0xA4);
			public static final FlatColor _300 = new FlatColor(0xA1, 0x88, 0x7F);
			public static final FlatColor _400 = new FlatColor(0x8D, 0x6E, 0x63);
			public static final FlatColor _500 = new FlatColor(0x79, 0x55, 0x48);
			public static final FlatColor _600 = new FlatColor(0x6D, 0x4C, 0x41);
			public static final FlatColor _700 = new FlatColor(0x5D, 0x40, 0x37);
			public static final FlatColor _800 = new FlatColor(0x4E, 0x34, 0x2E);
			public static final FlatColor _900 = new FlatColor(0x3E, 0x27, 0x23);
		}
		public static final class GREY{
			public static final FlatColor _50 = new FlatColor(0xFA, 0xFA, 0xFA);
			public static final FlatColor _100 = new FlatColor(0xF5, 0xF5, 0xF5);
			public static final FlatColor _200 = new FlatColor(0xEE, 0xEE, 0xEE);
			public static final FlatColor _300 = new FlatColor(0xE0, 0xE0, 0xE0);
			public static final FlatColor _400 = new FlatColor(0xBD, 0xBD, 0xBD);
			public static final FlatColor _500 = new FlatColor(0x9E, 0x9E, 0x9E);
			public static final FlatColor _600 = new FlatColor(0x75, 0x75, 0x75);
			public static final FlatColor _700 = new FlatColor(0x61, 0x61, 0x61);
			public static final FlatColor _800 = new FlatColor(0x42, 0x42, 0x42);
			public static final FlatColor _900 = new FlatColor(0x21, 0x21, 0x21);
		}
		public static final class BLUE_GREY{
			public static final FlatColor _50 = new FlatColor(0xEC, 0xEF, 0xF1);
			public static final FlatColor _100 = new FlatColor(0xCF, 0xD8, 0xDC);
			public static final FlatColor _200 = new FlatColor(0xB0, 0xBE, 0xC5);
			public static final FlatColor _300 = new FlatColor(0x90, 0xA4, 0xAE);
			public static final FlatColor _400 = new FlatColor(0x78, 0x90, 0x9C);
			public static final FlatColor _500 = new FlatColor(0x60, 0x7D, 0x8B);
			public static final FlatColor _600 = new FlatColor(0x54, 0x6E, 0x7A);
			public static final FlatColor _700 = new FlatColor(0x45, 0x5A, 0x64);
			public static final FlatColor _800 = new FlatColor(0x37, 0x47, 0x4F);
			public static final FlatColor _900 = new FlatColor(0x26, 0x32, 0x38);
		}
	}

}