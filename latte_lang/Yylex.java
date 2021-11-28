// -*- Java -*- File generated by the BNF Converter (bnfc 2.9.3).
// Lexer definition for use with JLex
package latte_lang;
import java_cup.runtime.*;


public class Yylex implements java_cup.runtime.Scanner {
	private final int YY_BUFFER_SIZE = 512;
	private final int YY_F = -1;
	private final int YY_NO_STATE = -1;
	private final int YY_NOT_ACCEPT = 0;
	private final int YY_START = 1;
	private final int YY_END = 2;
	private final int YY_NO_ANCHOR = 4;
	private final int YY_BOL = 65536;
	private final int YY_EOF = 65537;

  String pstring = new String();
  final int unknown = -1;
  ComplexSymbolFactory.Location left = new ComplexSymbolFactory.Location(unknown, unknown);
  ComplexSymbolFactory cf = new ComplexSymbolFactory();
  public SymbolFactory getSymbolFactory() { return cf; }
  int yycolumn = unknown - 1;
  public int line_num() { return (yyline+1); }
  public ComplexSymbolFactory.Location left_loc() {
    return new ComplexSymbolFactory.Location(yyline+1, yycolumn+1, yychar);
  }
  public ComplexSymbolFactory.Location right_loc() {
    ComplexSymbolFactory.Location left = left_loc();
  return new ComplexSymbolFactory.Location(left.getLine(), left.getColumn()+yylength(), left.getOffset()+yylength());
  }
  public String buff() {return new String(yy_buffer,yy_buffer_index,10).trim();}
	private java.io.BufferedReader yy_reader;
	private int yy_buffer_index;
	private int yy_buffer_read;
	private int yy_buffer_start;
	private int yy_buffer_end;
	private char yy_buffer[];
	private int yychar;
	private int yyline;
	private boolean yy_at_bol;
	private int yy_lexical_state;

	public Yylex (java.io.Reader reader) {
		this ();
		if (null == reader) {
			throw (new Error("Error: Bad input stream initializer."));
		}
		yy_reader = new java.io.BufferedReader(reader);
	}

	public Yylex (java.io.InputStream instream) {
		this ();
		if (null == instream) {
			throw (new Error("Error: Bad input stream initializer."));
		}
		yy_reader = new java.io.BufferedReader(new java.io.InputStreamReader(instream));
	}

	private Yylex () {
		yy_buffer = new char[YY_BUFFER_SIZE];
		yy_buffer_read = 0;
		yy_buffer_index = 0;
		yy_buffer_start = 0;
		yy_buffer_end = 0;
		yychar = 0;
		yyline = 0;
		yy_at_bol = true;
		yy_lexical_state = YYINITIAL;
	}

	private boolean yy_eof_done = false;
	private final int STRING = 5;
	private final int ESCAPED = 6;
	private final int YYINITIAL = 0;
	private final int COMMENT = 1;
	private final int CHAREND = 4;
	private final int CHARESC = 3;
	private final int CHAR = 2;
	private final int yy_state_dtrans[] = {
		0,
		67,
		73,
		73,
		73,
		76,
		78
	};
	private void yybegin (int state) {
		yy_lexical_state = state;
	}
	private int yy_advance ()
		throws java.io.IOException {
		int next_read;
		int i;
		int j;

		if (yy_buffer_index < yy_buffer_read) {
			return yy_buffer[yy_buffer_index++];
		}

		if (0 != yy_buffer_start) {
			i = yy_buffer_start;
			j = 0;
			while (i < yy_buffer_read) {
				yy_buffer[j] = yy_buffer[i];
				++i;
				++j;
			}
			yy_buffer_end = yy_buffer_end - yy_buffer_start;
			yy_buffer_start = 0;
			yy_buffer_read = j;
			yy_buffer_index = j;
			next_read = yy_reader.read(yy_buffer,
					yy_buffer_read,
					yy_buffer.length - yy_buffer_read);
			if (-1 == next_read) {
				return YY_EOF;
			}
			yy_buffer_read = yy_buffer_read + next_read;
		}

		while (yy_buffer_index >= yy_buffer_read) {
			if (yy_buffer_index >= yy_buffer.length) {
				yy_buffer = yy_double(yy_buffer);
			}
			next_read = yy_reader.read(yy_buffer,
					yy_buffer_read,
					yy_buffer.length - yy_buffer_read);
			if (-1 == next_read) {
				return YY_EOF;
			}
			yy_buffer_read = yy_buffer_read + next_read;
		}
		return yy_buffer[yy_buffer_index++];
	}
	private void yy_move_end () {
		if (yy_buffer_end > yy_buffer_start &&
		    '\n' == yy_buffer[yy_buffer_end-1])
			yy_buffer_end--;
		if (yy_buffer_end > yy_buffer_start &&
		    '\r' == yy_buffer[yy_buffer_end-1])
			yy_buffer_end--;
	}
	private boolean yy_last_was_cr=false;
	private void yy_mark_start () {
		int i;
		for (i = yy_buffer_start; i < yy_buffer_index; ++i) {
			if ('\n' == yy_buffer[i] && !yy_last_was_cr) {
				++yyline;
			}
			if ('\r' == yy_buffer[i]) {
				++yyline;
				yy_last_was_cr=true;
			} else yy_last_was_cr=false;
		}
		yychar = yychar
			+ yy_buffer_index - yy_buffer_start;
		yy_buffer_start = yy_buffer_index;
	}
	private void yy_mark_end () {
		yy_buffer_end = yy_buffer_index;
	}
	private void yy_to_mark () {
		yy_buffer_index = yy_buffer_end;
		yy_at_bol = (yy_buffer_end > yy_buffer_start) &&
		            ('\r' == yy_buffer[yy_buffer_end-1] ||
		             '\n' == yy_buffer[yy_buffer_end-1] ||
		             2028/*LS*/ == yy_buffer[yy_buffer_end-1] ||
		             2029/*PS*/ == yy_buffer[yy_buffer_end-1]);
	}
	private java.lang.String yytext () {
		return (new java.lang.String(yy_buffer,
			yy_buffer_start,
			yy_buffer_end - yy_buffer_start));
	}
	private int yylength () {
		return yy_buffer_end - yy_buffer_start;
	}
	private char[] yy_double (char buf[]) {
		int i;
		char newbuf[];
		newbuf = new char[2*buf.length];
		for (i = 0; i < buf.length; ++i) {
			newbuf[i] = buf[i];
		}
		return newbuf;
	}
	private final int YY_E_INTERNAL = 0;
	private final int YY_E_MATCH = 1;
	private java.lang.String yy_error_string[] = {
		"Error: Internal error.\n",
		"Error: Unmatched input.\n"
	};
	private void yy_error (int code,boolean fatal) {
		java.lang.System.out.print(yy_error_string[code]);
		java.lang.System.out.flush();
		if (fatal) {
			throw new Error("Fatal Error.\n");
		}
	}
	private int[][] unpackFromString(int size1, int size2, String st) {
		int colonIndex = -1;
		String lengthString;
		int sequenceLength = 0;
		int sequenceInteger = 0;

		int commaIndex;
		String workString;

		int res[][] = new int[size1][size2];
		for (int i= 0; i < size1; i++) {
			for (int j= 0; j < size2; j++) {
				if (sequenceLength != 0) {
					res[i][j] = sequenceInteger;
					sequenceLength--;
					continue;
				}
				commaIndex = st.indexOf(',');
				workString = (commaIndex==-1) ? st :
					st.substring(0, commaIndex);
				st = st.substring(commaIndex+1);
				colonIndex = workString.indexOf(':');
				if (colonIndex == -1) {
					res[i][j]=Integer.parseInt(workString);
					continue;
				}
				lengthString =
					workString.substring(colonIndex+1);
				sequenceLength=Integer.parseInt(lengthString);
				workString=workString.substring(0,colonIndex);
				sequenceInteger=Integer.parseInt(workString);
				res[i][j] = sequenceInteger;
				sequenceLength--;
			}
		}
		return res;
	}
	private int yy_acpt[] = {
		/* 0 */ YY_NOT_ACCEPT,
		/* 1 */ YY_NO_ANCHOR,
		/* 2 */ YY_NO_ANCHOR,
		/* 3 */ YY_NO_ANCHOR,
		/* 4 */ YY_NO_ANCHOR,
		/* 5 */ YY_NO_ANCHOR,
		/* 6 */ YY_NO_ANCHOR,
		/* 7 */ YY_NO_ANCHOR,
		/* 8 */ YY_NO_ANCHOR,
		/* 9 */ YY_NO_ANCHOR,
		/* 10 */ YY_NO_ANCHOR,
		/* 11 */ YY_NO_ANCHOR,
		/* 12 */ YY_NO_ANCHOR,
		/* 13 */ YY_NO_ANCHOR,
		/* 14 */ YY_NO_ANCHOR,
		/* 15 */ YY_NO_ANCHOR,
		/* 16 */ YY_NO_ANCHOR,
		/* 17 */ YY_NO_ANCHOR,
		/* 18 */ YY_NO_ANCHOR,
		/* 19 */ YY_NO_ANCHOR,
		/* 20 */ YY_NO_ANCHOR,
		/* 21 */ YY_NO_ANCHOR,
		/* 22 */ YY_NO_ANCHOR,
		/* 23 */ YY_NO_ANCHOR,
		/* 24 */ YY_NO_ANCHOR,
		/* 25 */ YY_NO_ANCHOR,
		/* 26 */ YY_NO_ANCHOR,
		/* 27 */ YY_NO_ANCHOR,
		/* 28 */ YY_NO_ANCHOR,
		/* 29 */ YY_NO_ANCHOR,
		/* 30 */ YY_NO_ANCHOR,
		/* 31 */ YY_NO_ANCHOR,
		/* 32 */ YY_NO_ANCHOR,
		/* 33 */ YY_NO_ANCHOR,
		/* 34 */ YY_NO_ANCHOR,
		/* 35 */ YY_NO_ANCHOR,
		/* 36 */ YY_NO_ANCHOR,
		/* 37 */ YY_NO_ANCHOR,
		/* 38 */ YY_NO_ANCHOR,
		/* 39 */ YY_NO_ANCHOR,
		/* 40 */ YY_NO_ANCHOR,
		/* 41 */ YY_NO_ANCHOR,
		/* 42 */ YY_NO_ANCHOR,
		/* 43 */ YY_NO_ANCHOR,
		/* 44 */ YY_NO_ANCHOR,
		/* 45 */ YY_NO_ANCHOR,
		/* 46 */ YY_NO_ANCHOR,
		/* 47 */ YY_NO_ANCHOR,
		/* 48 */ YY_NO_ANCHOR,
		/* 49 */ YY_NO_ANCHOR,
		/* 50 */ YY_NO_ANCHOR,
		/* 51 */ YY_NO_ANCHOR,
		/* 52 */ YY_NO_ANCHOR,
		/* 53 */ YY_NO_ANCHOR,
		/* 54 */ YY_NO_ANCHOR,
		/* 55 */ YY_NO_ANCHOR,
		/* 56 */ YY_NO_ANCHOR,
		/* 57 */ YY_NO_ANCHOR,
		/* 58 */ YY_NO_ANCHOR,
		/* 59 */ YY_NO_ANCHOR,
		/* 60 */ YY_NO_ANCHOR,
		/* 61 */ YY_NO_ANCHOR,
		/* 62 */ YY_NO_ANCHOR,
		/* 63 */ YY_NO_ANCHOR,
		/* 64 */ YY_NO_ANCHOR,
		/* 65 */ YY_NO_ANCHOR,
		/* 66 */ YY_NO_ANCHOR,
		/* 67 */ YY_NOT_ACCEPT,
		/* 68 */ YY_NO_ANCHOR,
		/* 69 */ YY_NO_ANCHOR,
		/* 70 */ YY_NO_ANCHOR,
		/* 71 */ YY_NO_ANCHOR,
		/* 72 */ YY_NO_ANCHOR,
		/* 73 */ YY_NOT_ACCEPT,
		/* 74 */ YY_NO_ANCHOR,
		/* 75 */ YY_NO_ANCHOR,
		/* 76 */ YY_NOT_ACCEPT,
		/* 77 */ YY_NO_ANCHOR,
		/* 78 */ YY_NOT_ACCEPT,
		/* 79 */ YY_NO_ANCHOR,
		/* 80 */ YY_NO_ANCHOR,
		/* 81 */ YY_NO_ANCHOR,
		/* 82 */ YY_NO_ANCHOR,
		/* 83 */ YY_NO_ANCHOR,
		/* 84 */ YY_NO_ANCHOR,
		/* 85 */ YY_NO_ANCHOR,
		/* 86 */ YY_NO_ANCHOR,
		/* 87 */ YY_NO_ANCHOR,
		/* 88 */ YY_NO_ANCHOR,
		/* 89 */ YY_NO_ANCHOR,
		/* 90 */ YY_NO_ANCHOR,
		/* 91 */ YY_NO_ANCHOR,
		/* 92 */ YY_NO_ANCHOR,
		/* 93 */ YY_NO_ANCHOR,
		/* 94 */ YY_NO_ANCHOR,
		/* 95 */ YY_NO_ANCHOR,
		/* 96 */ YY_NO_ANCHOR,
		/* 97 */ YY_NO_ANCHOR,
		/* 98 */ YY_NO_ANCHOR,
		/* 99 */ YY_NO_ANCHOR,
		/* 100 */ YY_NO_ANCHOR,
		/* 101 */ YY_NO_ANCHOR,
		/* 102 */ YY_NO_ANCHOR,
		/* 103 */ YY_NO_ANCHOR,
		/* 104 */ YY_NO_ANCHOR,
		/* 105 */ YY_NO_ANCHOR,
		/* 106 */ YY_NO_ANCHOR,
		/* 107 */ YY_NO_ANCHOR,
		/* 108 */ YY_NO_ANCHOR,
		/* 109 */ YY_NO_ANCHOR,
		/* 110 */ YY_NO_ANCHOR,
		/* 111 */ YY_NO_ANCHOR,
		/* 112 */ YY_NO_ANCHOR,
		/* 113 */ YY_NO_ANCHOR,
		/* 114 */ YY_NO_ANCHOR,
		/* 115 */ YY_NO_ANCHOR,
		/* 116 */ YY_NO_ANCHOR,
		/* 117 */ YY_NO_ANCHOR,
		/* 118 */ YY_NO_ANCHOR,
		/* 119 */ YY_NO_ANCHOR,
		/* 120 */ YY_NO_ANCHOR,
		/* 121 */ YY_NO_ANCHOR,
		/* 122 */ YY_NO_ANCHOR,
		/* 123 */ YY_NO_ANCHOR
	};
	private int yy_cmap[] = unpackFromString(1,65538,
"43:9,51,44,43,51,42,43:18,51,14,45,41,43,19,15,50,1,2,17,11,3,12,10,18,47:1" +
"0,13,6,20,7,21,43:2,48:26,8,46,9,43,50,43,26,22,28,32,25,33,38,40,35,49:2,2" +
"4,49,27,23,49:2,34,29,31,37,39,36,30,49:2,4,16,5,43:66,48:23,43,48:7,49:24," +
"43,49:8,43:65280,0:2")[0];

	private int yy_rmap[] = unpackFromString(1,124,
"0,1:7,2,3,1:2,4,5,1,6,7,1,8,1,9,10,11,12,1:2,13,1:8,14,1:2,15:14,1:4,16,1:7" +
",17,1:2,18,19,20,21,1:2,22,1,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,3" +
"8,39,40,41,42,43,44,45,46,47,48,49,50,51,52,53,54,55,56,57,58,59,60,61,62,6" +
"3,64,65,66,67,68,15,69,15")[0];

	private int yy_nxt[][] = unpackFromString(70,52,
"1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,68,17,18,19,20,21,22,123:2,102,123,9" +
"0,112,118,123,103,123,91,119,69,113,123:2,104,123,23,24,74,24,25,74,26,123:" +
"2,74,24,-1:59,27,-1:53,28,-1:53,29,-1:52,30,-1:46,31,-1:59,32,-1:53,34,35,-" +
"1:40,36,-1:51,37,-1:66,123,120,123:17,-1:6,121:2,123,121,-1:2,23:43,-1,23:7" +
",-1:47,26,-1:5,35:43,-1,35:7,-1:22,123:19,-1:6,121:2,123,121,-1:45,71,-1:51" +
",72,-1:7,1,52:16,70,52:24,-1,52,53,52:7,-1:16,33,-1:57,123:5,79,123:5,38,12" +
"3:7,-1:6,121:2,123,121,-1:19,54,-1:33,1,74:41,-1,74,-1,74:7,-1:22,123:14,39" +
",123:4,-1:6,121:2,123,121,-1,1,55:41,56,55,71,57,58,55:5,-1:22,123:12,40,12" +
"3:6,-1:6,121:2,123,121,-1,1,59:26,60,59:3,61,59,62,63,59:7,64,59,72,65,66,5" +
"9:5,-1:22,123:9,41,123:9,-1:6,121:2,123,121,-1:23,123:3,42,123:15,-1:6,121:" +
"2,123,121,-1:23,123:3,43,123:15,-1:6,121:2,123,121,-1:23,123:10,44,123:8,-1" +
":6,121:2,123,121,-1:23,123:7,45,123:11,-1:6,121:2,123,121,-1:23,123:3,46,12" +
"3:15,-1:6,121:2,123,121,-1:23,123:3,47,123:15,-1:6,121:2,123,121,-1:23,123:" +
"16,48,123:2,-1:6,121:2,123,121,-1:23,123:5,49,123:13,-1:6,121:2,123,121,-1:" +
"23,123:5,50,123:13,-1:6,121:2,123,121,-1:23,123:7,51,123:11,-1:6,121:2,123," +
"121,-1:23,123:3,75,123:15,-1:6,121:2,123,121,-1:23,123,77,123:2,106,123:14," +
"-1:6,121:2,123,121,-1:23,123:7,80,123:11,-1:6,121:2,123,121,-1:23,123:15,81" +
",123:3,-1:6,121:2,123,121,-1:23,123:13,82,123:5,-1:6,121:2,123,121,-1:23,12" +
"3:7,83,123:11,-1:6,121:2,123,121,-1:23,123:7,84,123:11,-1:6,121:2,123,121,-" +
"1:23,123:2,85,123:16,-1:6,121:2,123,121,-1:23,123:5,86,123:13,-1:6,121:2,12" +
"3,121,-1:23,123:12,87,123:6,-1:6,121:2,123,121,-1:23,123:4,88,123:14,-1:6,1" +
"21:2,123,121,-1:23,123:10,89,123:8,-1:6,121:2,123,121,-1:23,123:2,92,123:5," +
"122,123:10,-1:6,121:2,123,121,-1:23,123:12,93,123:6,-1:6,121:2,123,121,-1:2" +
"3,123,94,123:17,-1:6,121:2,123,121,-1:23,123:4,95,123:14,-1:6,121:2,123,121" +
",-1:23,123:2,96,123:16,-1:6,121:2,123,121,-1:23,123:13,97,123:5,-1:6,121:2," +
"123,121,-1:23,123:13,98,123:5,-1:6,121:2,123,121,-1:23,123:15,99,123:3,-1:6" +
",121:2,123,121,-1:23,123:3,100,123:15,-1:6,121:2,123,121,-1:23,123:5,101,12" +
"3:13,-1:6,121:2,123,121,-1:23,123:2,105,123:16,-1:6,121:2,123,121,-1:23,123" +
":18,107,-1:6,121:2,123,121,-1:23,123:12,108,123:6,-1:6,121:2,123,121,-1:23," +
"123:9,109,123:9,-1:6,121:2,123,121,-1:23,123:2,110,123:16,-1:6,121:2,123,12" +
"1,-1:23,123:3,111,123:15,-1:6,121:2,123,121,-1:23,123:9,114,123:9,-1:6,121:" +
"2,123,121,-1:23,123:3,115,123:15,-1:6,121:2,123,121,-1:23,123,116,123:17,-1" +
":6,121:2,123,121,-1:23,123:9,117,123:9,-1:6,121:2,123,121,-1");

	public java_cup.runtime.Symbol next_token ()
		throws java.io.IOException {
		int yy_lookahead;
		int yy_anchor = YY_NO_ANCHOR;
		int yy_state = yy_state_dtrans[yy_lexical_state];
		int yy_next_state = YY_NO_STATE;
		int yy_last_accept_state = YY_NO_STATE;
		boolean yy_initial = true;
		int yy_this_accept;

		yy_mark_start();
		yy_this_accept = yy_acpt[yy_state];
		if (YY_NOT_ACCEPT != yy_this_accept) {
			yy_last_accept_state = yy_state;
			yy_mark_end();
		}
		while (true) {
			if (yy_initial && yy_at_bol) yy_lookahead = YY_BOL;
			else yy_lookahead = yy_advance();
			yy_next_state = YY_F;
			yy_next_state = yy_nxt[yy_rmap[yy_state]][yy_cmap[yy_lookahead]];
			if (YY_EOF == yy_lookahead && true == yy_initial) {

  return cf.newSymbol("EOF", sym.EOF, left_loc(), left_loc());
			}
			if (YY_F != yy_next_state) {
				yy_state = yy_next_state;
				yy_initial = false;
				yy_this_accept = yy_acpt[yy_state];
				if (YY_NOT_ACCEPT != yy_this_accept) {
					yy_last_accept_state = yy_state;
					yy_mark_end();
				}
			}
			else {
				if (YY_NO_STATE == yy_last_accept_state) {
					throw (new Error("Lexical Error: Unmatched Input."));
				}
				else {
					yy_anchor = yy_acpt[yy_last_accept_state];
					if (0 != (YY_END & yy_anchor)) {
						yy_move_end();
					}
					yy_to_mark();
					switch (yy_last_accept_state) {
					case 1:
						
					case -2:
						break;
					case 2:
						{ return cf.newSymbol("", sym._SYMB_0, left_loc(), right_loc()); }
					case -3:
						break;
					case 3:
						{ return cf.newSymbol("", sym._SYMB_1, left_loc(), right_loc()); }
					case -4:
						break;
					case 4:
						{ return cf.newSymbol("", sym._SYMB_2, left_loc(), right_loc()); }
					case -5:
						break;
					case 5:
						{ return cf.newSymbol("", sym._SYMB_3, left_loc(), right_loc()); }
					case -6:
						break;
					case 6:
						{ return cf.newSymbol("", sym._SYMB_4, left_loc(), right_loc()); }
					case -7:
						break;
					case 7:
						{ return cf.newSymbol("", sym._SYMB_5, left_loc(), right_loc()); }
					case -8:
						break;
					case 8:
						{ return cf.newSymbol("", sym._SYMB_6, left_loc(), right_loc()); }
					case -9:
						break;
					case 9:
						{ return cf.newSymbol("", sym._SYMB_7, left_loc(), right_loc()); }
					case -10:
						break;
					case 10:
						{ return cf.newSymbol("", sym._SYMB_8, left_loc(), right_loc()); }
					case -11:
						break;
					case 11:
						{ return cf.newSymbol("", sym._SYMB_9, left_loc(), right_loc()); }
					case -12:
						break;
					case 12:
						{ return cf.newSymbol("", sym._SYMB_18, left_loc(), right_loc()); }
					case -13:
						break;
					case 13:
						{ return cf.newSymbol("", sym._SYMB_14, left_loc(), right_loc()); }
					case -14:
						break;
					case 14:
						{ return cf.newSymbol("", sym._SYMB_12, left_loc(), right_loc()); }
					case -15:
						break;
					case 15:
						{ return cf.newSymbol("", sym._SYMB_15, left_loc(), right_loc()); }
					case -16:
						break;
					case 16:
						{ throw new Error("Illegal Character <"+yytext()+"> at "+(yyline+1)); }
					case -17:
						break;
					case 17:
						{ return cf.newSymbol("", sym._SYMB_19, left_loc(), right_loc()); }
					case -18:
						break;
					case 18:
						{ return cf.newSymbol("", sym._SYMB_20, left_loc(), right_loc()); }
					case -19:
						break;
					case 19:
						{ return cf.newSymbol("", sym._SYMB_21, left_loc(), right_loc()); }
					case -20:
						break;
					case 20:
						{ return cf.newSymbol("", sym._SYMB_22, left_loc(), right_loc()); }
					case -21:
						break;
					case 21:
						{ return cf.newSymbol("", sym._SYMB_24, left_loc(), right_loc()); }
					case -22:
						break;
					case 22:
						{ return cf.newSymbol("", sym._IDENT_, left_loc(), right_loc(), yytext().intern()); }
					case -23:
						break;
					case 23:
						{ /* skip */ }
					case -24:
						break;
					case 24:
						{ /* ignore white space. */ }
					case -25:
						break;
					case 25:
						{ left = left_loc(); yybegin(STRING); }
					case -26:
						break;
					case 26:
						{ return cf.newSymbol("", sym._INTEGER_, left_loc(), right_loc(), new Integer(yytext())); }
					case -27:
						break;
					case 27:
						{ return cf.newSymbol("", sym._SYMB_26, left_loc(), right_loc()); }
					case -28:
						break;
					case 28:
						{ return cf.newSymbol("", sym._SYMB_13, left_loc(), right_loc()); }
					case -29:
						break;
					case 29:
						{ return cf.newSymbol("", sym._SYMB_10, left_loc(), right_loc()); }
					case -30:
						break;
					case 30:
						{ return cf.newSymbol("", sym._SYMB_11, left_loc(), right_loc()); }
					case -31:
						break;
					case 31:
						{ return cf.newSymbol("", sym._SYMB_27, left_loc(), right_loc()); }
					case -32:
						break;
					case 32:
						{ return cf.newSymbol("", sym._SYMB_16, left_loc(), right_loc()); }
					case -33:
						break;
					case 33:
						{ return cf.newSymbol("", sym._SYMB_17, left_loc(), right_loc()); }
					case -34:
						break;
					case 34:
						{ yybegin(COMMENT); }
					case -35:
						break;
					case 35:
						{ /* skip */ }
					case -36:
						break;
					case 36:
						{ return cf.newSymbol("", sym._SYMB_23, left_loc(), right_loc()); }
					case -37:
						break;
					case 37:
						{ return cf.newSymbol("", sym._SYMB_25, left_loc(), right_loc()); }
					case -38:
						break;
					case 38:
						{ return cf.newSymbol("", sym._SYMB_34, left_loc(), right_loc()); }
					case -39:
						break;
					case 39:
						{ return cf.newSymbol("", sym._SYMB_36, left_loc(), right_loc()); }
					case -40:
						break;
					case 40:
						{ return cf.newSymbol("", sym._SYMB_33, left_loc(), right_loc()); }
					case -41:
						break;
					case 41:
						{ return cf.newSymbol("", sym._SYMB_35, left_loc(), right_loc()); }
					case -42:
						break;
					case 42:
						{ return cf.newSymbol("", sym._SYMB_30, left_loc(), right_loc()); }
					case -43:
						break;
					case 43:
						{ return cf.newSymbol("", sym._SYMB_39, left_loc(), right_loc()); }
					case -44:
						break;
					case 44:
						{ return cf.newSymbol("", sym._SYMB_40, left_loc(), right_loc()); }
					case -45:
						break;
					case 45:
						{ return cf.newSymbol("", sym._SYMB_29, left_loc(), right_loc()); }
					case -46:
						break;
					case 46:
						{ return cf.newSymbol("", sym._SYMB_32, left_loc(), right_loc()); }
					case -47:
						break;
					case 47:
						{ return cf.newSymbol("", sym._SYMB_41, left_loc(), right_loc()); }
					case -48:
						break;
					case 48:
						{ return cf.newSymbol("", sym._SYMB_38, left_loc(), right_loc()); }
					case -49:
						break;
					case 49:
						{ return cf.newSymbol("", sym._SYMB_37, left_loc(), right_loc()); }
					case -50:
						break;
					case 50:
						{ return cf.newSymbol("", sym._SYMB_28, left_loc(), right_loc()); }
					case -51:
						break;
					case 51:
						{ return cf.newSymbol("", sym._SYMB_31, left_loc(), right_loc()); }
					case -52:
						break;
					case 52:
						{ /* skip */ }
					case -53:
						break;
					case 53:
						{ /* skip */ }
					case -54:
						break;
					case 54:
						{ yybegin(YYINITIAL); }
					case -55:
						break;
					case 55:
						{ pstring += yytext(); }
					case -56:
						break;
					case 56:
						{ throw new Error("Unterminated string on line " + left.getLine() ); }
					case -57:
						break;
					case 57:
						{ String foo = pstring; pstring = new String(); yybegin(YYINITIAL); return cf.newSymbol("", sym._STRING_, left, right_loc(), foo.intern()); }
					case -58:
						break;
					case 58:
						{ yybegin(ESCAPED); }
					case -59:
						break;
					case 59:
						{ pstring += yytext(); yybegin(STRING); }
					case -60:
						break;
					case 60:
						{ pstring +=  "\n"; yybegin(STRING); }
					case -61:
						break;
					case 61:
						{ pstring += "\t"; yybegin(STRING); }
					case -62:
						break;
					case 62:
						{ pstring += "\f"; yybegin(STRING); }
					case -63:
						break;
					case 63:
						{ pstring += "\r"; yybegin(STRING); }
					case -64:
						break;
					case 64:
						{ throw new Error("Unterminated string on line " + left.getLine() ); }
					case -65:
						break;
					case 65:
						{ pstring += "\""; yybegin(STRING); }
					case -66:
						break;
					case 66:
						{ pstring += "\\"; yybegin(STRING); }
					case -67:
						break;
					case 68:
						{ throw new Error("Illegal Character <"+yytext()+"> at "+(yyline+1)); }
					case -68:
						break;
					case 69:
						{ return cf.newSymbol("", sym._IDENT_, left_loc(), right_loc(), yytext().intern()); }
					case -69:
						break;
					case 70:
						{ /* skip */ }
					case -70:
						break;
					case 71:
						{ throw new Error("Unterminated string on line " + left.getLine() ); }
					case -71:
						break;
					case 72:
						{ throw new Error("Unterminated string on line " + left.getLine() ); }
					case -72:
						break;
					case 74:
						{ throw new Error("Illegal Character <"+yytext()+"> at "+(yyline+1)); }
					case -73:
						break;
					case 75:
						{ return cf.newSymbol("", sym._IDENT_, left_loc(), right_loc(), yytext().intern()); }
					case -74:
						break;
					case 77:
						{ return cf.newSymbol("", sym._IDENT_, left_loc(), right_loc(), yytext().intern()); }
					case -75:
						break;
					case 79:
						{ return cf.newSymbol("", sym._IDENT_, left_loc(), right_loc(), yytext().intern()); }
					case -76:
						break;
					case 80:
						{ return cf.newSymbol("", sym._IDENT_, left_loc(), right_loc(), yytext().intern()); }
					case -77:
						break;
					case 81:
						{ return cf.newSymbol("", sym._IDENT_, left_loc(), right_loc(), yytext().intern()); }
					case -78:
						break;
					case 82:
						{ return cf.newSymbol("", sym._IDENT_, left_loc(), right_loc(), yytext().intern()); }
					case -79:
						break;
					case 83:
						{ return cf.newSymbol("", sym._IDENT_, left_loc(), right_loc(), yytext().intern()); }
					case -80:
						break;
					case 84:
						{ return cf.newSymbol("", sym._IDENT_, left_loc(), right_loc(), yytext().intern()); }
					case -81:
						break;
					case 85:
						{ return cf.newSymbol("", sym._IDENT_, left_loc(), right_loc(), yytext().intern()); }
					case -82:
						break;
					case 86:
						{ return cf.newSymbol("", sym._IDENT_, left_loc(), right_loc(), yytext().intern()); }
					case -83:
						break;
					case 87:
						{ return cf.newSymbol("", sym._IDENT_, left_loc(), right_loc(), yytext().intern()); }
					case -84:
						break;
					case 88:
						{ return cf.newSymbol("", sym._IDENT_, left_loc(), right_loc(), yytext().intern()); }
					case -85:
						break;
					case 89:
						{ return cf.newSymbol("", sym._IDENT_, left_loc(), right_loc(), yytext().intern()); }
					case -86:
						break;
					case 90:
						{ return cf.newSymbol("", sym._IDENT_, left_loc(), right_loc(), yytext().intern()); }
					case -87:
						break;
					case 91:
						{ return cf.newSymbol("", sym._IDENT_, left_loc(), right_loc(), yytext().intern()); }
					case -88:
						break;
					case 92:
						{ return cf.newSymbol("", sym._IDENT_, left_loc(), right_loc(), yytext().intern()); }
					case -89:
						break;
					case 93:
						{ return cf.newSymbol("", sym._IDENT_, left_loc(), right_loc(), yytext().intern()); }
					case -90:
						break;
					case 94:
						{ return cf.newSymbol("", sym._IDENT_, left_loc(), right_loc(), yytext().intern()); }
					case -91:
						break;
					case 95:
						{ return cf.newSymbol("", sym._IDENT_, left_loc(), right_loc(), yytext().intern()); }
					case -92:
						break;
					case 96:
						{ return cf.newSymbol("", sym._IDENT_, left_loc(), right_loc(), yytext().intern()); }
					case -93:
						break;
					case 97:
						{ return cf.newSymbol("", sym._IDENT_, left_loc(), right_loc(), yytext().intern()); }
					case -94:
						break;
					case 98:
						{ return cf.newSymbol("", sym._IDENT_, left_loc(), right_loc(), yytext().intern()); }
					case -95:
						break;
					case 99:
						{ return cf.newSymbol("", sym._IDENT_, left_loc(), right_loc(), yytext().intern()); }
					case -96:
						break;
					case 100:
						{ return cf.newSymbol("", sym._IDENT_, left_loc(), right_loc(), yytext().intern()); }
					case -97:
						break;
					case 101:
						{ return cf.newSymbol("", sym._IDENT_, left_loc(), right_loc(), yytext().intern()); }
					case -98:
						break;
					case 102:
						{ return cf.newSymbol("", sym._IDENT_, left_loc(), right_loc(), yytext().intern()); }
					case -99:
						break;
					case 103:
						{ return cf.newSymbol("", sym._IDENT_, left_loc(), right_loc(), yytext().intern()); }
					case -100:
						break;
					case 104:
						{ return cf.newSymbol("", sym._IDENT_, left_loc(), right_loc(), yytext().intern()); }
					case -101:
						break;
					case 105:
						{ return cf.newSymbol("", sym._IDENT_, left_loc(), right_loc(), yytext().intern()); }
					case -102:
						break;
					case 106:
						{ return cf.newSymbol("", sym._IDENT_, left_loc(), right_loc(), yytext().intern()); }
					case -103:
						break;
					case 107:
						{ return cf.newSymbol("", sym._IDENT_, left_loc(), right_loc(), yytext().intern()); }
					case -104:
						break;
					case 108:
						{ return cf.newSymbol("", sym._IDENT_, left_loc(), right_loc(), yytext().intern()); }
					case -105:
						break;
					case 109:
						{ return cf.newSymbol("", sym._IDENT_, left_loc(), right_loc(), yytext().intern()); }
					case -106:
						break;
					case 110:
						{ return cf.newSymbol("", sym._IDENT_, left_loc(), right_loc(), yytext().intern()); }
					case -107:
						break;
					case 111:
						{ return cf.newSymbol("", sym._IDENT_, left_loc(), right_loc(), yytext().intern()); }
					case -108:
						break;
					case 112:
						{ return cf.newSymbol("", sym._IDENT_, left_loc(), right_loc(), yytext().intern()); }
					case -109:
						break;
					case 113:
						{ return cf.newSymbol("", sym._IDENT_, left_loc(), right_loc(), yytext().intern()); }
					case -110:
						break;
					case 114:
						{ return cf.newSymbol("", sym._IDENT_, left_loc(), right_loc(), yytext().intern()); }
					case -111:
						break;
					case 115:
						{ return cf.newSymbol("", sym._IDENT_, left_loc(), right_loc(), yytext().intern()); }
					case -112:
						break;
					case 116:
						{ return cf.newSymbol("", sym._IDENT_, left_loc(), right_loc(), yytext().intern()); }
					case -113:
						break;
					case 117:
						{ return cf.newSymbol("", sym._IDENT_, left_loc(), right_loc(), yytext().intern()); }
					case -114:
						break;
					case 118:
						{ return cf.newSymbol("", sym._IDENT_, left_loc(), right_loc(), yytext().intern()); }
					case -115:
						break;
					case 119:
						{ return cf.newSymbol("", sym._IDENT_, left_loc(), right_loc(), yytext().intern()); }
					case -116:
						break;
					case 120:
						{ return cf.newSymbol("", sym._IDENT_, left_loc(), right_loc(), yytext().intern()); }
					case -117:
						break;
					case 121:
						{ return cf.newSymbol("", sym._IDENT_, left_loc(), right_loc(), yytext().intern()); }
					case -118:
						break;
					case 122:
						{ return cf.newSymbol("", sym._IDENT_, left_loc(), right_loc(), yytext().intern()); }
					case -119:
						break;
					case 123:
						{ return cf.newSymbol("", sym._IDENT_, left_loc(), right_loc(), yytext().intern()); }
					case -120:
						break;
					default:
						yy_error(YY_E_INTERNAL,false);
					case -1:
					}
					yy_initial = true;
					yy_state = yy_state_dtrans[yy_lexical_state];
					yy_next_state = YY_NO_STATE;
					yy_last_accept_state = YY_NO_STATE;
					yy_mark_start();
					yy_this_accept = yy_acpt[yy_state];
					if (YY_NOT_ACCEPT != yy_this_accept) {
						yy_last_accept_state = yy_state;
						yy_mark_end();
					}
				}
			}
		}
	}
}
