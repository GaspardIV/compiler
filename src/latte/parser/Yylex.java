/* The following code was generated by JFlex 1.4.1 on 12/1/22, 3:42 AM */

// -*- Java -*- File generated by the BNF Converter (bnfc 2.9.4).

// Lexer definition for use with JFlex
package latte.parser;

import java_cup.runtime.*;

/**
 * This class is a scanner generated by 
 * <a href="http://www.jflex.de/">JFlex</a> 1.4.1
 * on 12/1/22, 3:42 AM from the specification file
 * <tt>latte/Yylex</tt>
 */
public class Yylex implements java_cup.runtime.Scanner {

  /** This character denotes the end of file */
  public static final int YYEOF = -1;

  /** initial size of the lookahead buffer */
  private static final int ZZ_BUFFERSIZE = 16384;

  /** lexical states */
  public static final int STRING = 3;
  public static final int ESCAPED = 4;
  public static final int YYINITIAL = 0;
  public static final int COMMENT = 1;
  public static final int CHAREND = 2;
  public static final int CHARESC = 2;
  public static final int CHAR = 2;

  /** 
   * Translates characters to character classes
   */
  private static final String ZZ_CMAP_PACKED = 
    "\11\0\1\61\1\55\1\0\1\61\1\60\22\0\1\61\1\21\1\56"+
    "\1\54\1\0\1\26\1\22\1\3\1\4\1\5\1\24\1\16\1\6"+
    "\1\17\1\15\1\25\12\2\1\20\1\11\1\27\1\12\1\30\2\0"+
    "\32\1\1\13\1\57\1\14\1\0\1\3\1\0\1\35\1\31\1\37"+
    "\1\43\1\34\1\44\1\51\1\53\1\46\2\1\1\33\1\1\1\36"+
    "\1\32\2\1\1\45\1\40\1\42\1\50\1\52\1\47\1\41\2\1"+
    "\1\7\1\23\1\10\102\0\27\1\1\0\7\1\30\1\1\0\10\1"+
    "\uff00\0";

  /** 
   * Translates characters to character classes
   */
  private static final char [] ZZ_CMAP = zzUnpackCMap(ZZ_CMAP_PACKED);

  /** 
   * Translates DFA states to action switch labels.
   */
  private static final int [] ZZ_ACTION = zzUnpackAction();

  private static final String ZZ_ACTION_PACKED_0 =
    "\5\0\1\1\1\2\1\3\1\4\1\5\1\6\1\7"+
    "\1\10\1\11\1\12\1\13\1\14\1\15\1\16\1\17"+
    "\1\20\1\21\2\1\1\22\1\23\1\24\1\25\1\26"+
    "\13\2\1\27\1\30\1\31\2\27\1\32\1\33\1\34"+
    "\1\35\1\32\1\36\1\37\1\40\1\41\1\42\1\43"+
    "\1\44\1\45\1\36\1\46\1\47\1\50\1\51\1\52"+
    "\1\53\1\54\1\55\1\56\1\57\14\2\1\60\2\2"+
    "\1\61\3\2\1\62\4\2\1\63\2\2\1\64\3\2"+
    "\1\65\1\2\1\66\2\2\1\67\3\2\1\70\2\2"+
    "\1\71\1\2\1\72\1\2\1\73\2\2\1\74\1\75"+
    "\1\76\1\77";

  private static int [] zzUnpackAction() {
    int [] result = new int[123];
    int offset = 0;
    offset = zzUnpackAction(ZZ_ACTION_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackAction(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }


  /** 
   * Translates a state to a row index in the transition table
   */
  private static final int [] ZZ_ROWMAP = zzUnpackRowMap();

  private static final String ZZ_ROWMAP_PACKED_0 =
    "\0\0\0\62\0\144\0\226\0\310\0\372\0\u012c\0\u015e"+
    "\0\372\0\372\0\372\0\372\0\372\0\372\0\u0190\0\u01c2"+
    "\0\372\0\372\0\u01f4\0\u0226\0\372\0\u0258\0\u028a\0\u02bc"+
    "\0\372\0\u02ee\0\372\0\u0320\0\u0352\0\u0384\0\u03b6\0\u03e8"+
    "\0\u041a\0\u044c\0\u047e\0\u04b0\0\u04e2\0\u0514\0\u0546\0\u0578"+
    "\0\u05aa\0\372\0\372\0\372\0\u05dc\0\372\0\372\0\372"+
    "\0\372\0\u060e\0\372\0\372\0\372\0\372\0\372\0\372"+
    "\0\372\0\372\0\u0640\0\372\0\372\0\372\0\372\0\372"+
    "\0\372\0\372\0\372\0\372\0\372\0\u0672\0\u06a4\0\u06d6"+
    "\0\u0708\0\u073a\0\u076c\0\u079e\0\u07d0\0\u0802\0\u0834\0\u0866"+
    "\0\u0898\0\u012c\0\u08ca\0\u08fc\0\372\0\u092e\0\u0960\0\u0992"+
    "\0\u012c\0\u09c4\0\u09f6\0\u0a28\0\u0a5a\0\u012c\0\u0a8c\0\u0abe"+
    "\0\u012c\0\u0af0\0\u0b22\0\u0b54\0\u012c\0\u0b86\0\u012c\0\u0bb8"+
    "\0\u0bea\0\u012c\0\u0c1c\0\u0c4e\0\u0c80\0\u012c\0\u0cb2\0\u0ce4"+
    "\0\u012c\0\u0d16\0\u012c\0\u0d48\0\u012c\0\u0d7a\0\u0dac\0\u012c"+
    "\0\u012c\0\u012c\0\u012c";

  private static int [] zzUnpackRowMap() {
    int [] result = new int[123];
    int offset = 0;
    offset = zzUnpackRowMap(ZZ_ROWMAP_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackRowMap(String packed, int offset, int [] result) {
    int i = 0;  /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int high = packed.charAt(i++) << 16;
      result[j++] = high | packed.charAt(i++);
    }
    return j;
  }

  /** 
   * The transition table of the DFA
   */
  private static final int [] ZZ_TRANS = zzUnpackTrans();

  private static final String ZZ_TRANS_PACKED_0 =
    "\1\6\1\7\1\10\1\6\1\11\1\12\1\13\1\14"+
    "\1\15\1\16\1\17\1\20\1\21\1\22\1\23\1\24"+
    "\1\25\1\26\1\27\1\30\1\31\1\32\1\33\1\34"+
    "\1\35\1\36\2\7\1\37\1\7\1\40\1\41\1\42"+
    "\1\7\1\43\1\7\1\44\1\45\1\46\1\47\2\7"+
    "\1\50\1\7\1\51\1\52\1\53\1\6\2\52\24\54"+
    "\1\55\35\54\55\6\1\0\4\6\55\56\1\57\1\60"+
    "\1\61\1\62\1\56\36\63\1\64\3\63\1\65\1\63"+
    "\1\66\1\67\7\63\1\70\1\71\1\72\1\73\1\63"+
    "\63\0\3\7\25\0\23\7\10\0\1\10\71\0\1\74"+
    "\63\0\1\75\63\0\1\76\62\0\1\77\54\0\1\100"+
    "\71\0\1\101\62\0\1\102\62\0\1\103\1\51\46\0"+
    "\1\104\61\0\1\105\50\0\3\7\25\0\1\7\1\106"+
    "\21\7\7\0\3\7\25\0\2\7\1\107\5\7\1\110"+
    "\12\7\7\0\3\7\25\0\3\7\1\111\13\7\1\112"+
    "\3\7\7\0\3\7\25\0\2\7\1\113\20\7\7\0"+
    "\3\7\25\0\11\7\1\114\11\7\7\0\3\7\25\0"+
    "\14\7\1\115\6\7\7\0\3\7\25\0\1\7\1\116"+
    "\2\7\1\117\16\7\7\0\3\7\25\0\3\7\1\120"+
    "\17\7\7\0\3\7\25\0\5\7\1\121\5\7\1\122"+
    "\7\7\7\0\3\7\25\0\22\7\1\123\7\0\3\7"+
    "\25\0\1\7\1\124\21\7\6\0\55\51\1\0\4\51"+
    "\25\0\1\125\111\0\1\57\61\0\1\70\5\0\3\7"+
    "\25\0\1\7\1\126\21\7\7\0\3\7\25\0\7\7"+
    "\1\127\13\7\7\0\3\7\25\0\11\7\1\130\11\7"+
    "\7\0\3\7\25\0\16\7\1\131\4\7\7\0\3\7"+
    "\25\0\2\7\1\132\20\7\7\0\3\7\25\0\4\7"+
    "\1\133\16\7\7\0\3\7\25\0\14\7\1\134\6\7"+
    "\7\0\3\7\25\0\17\7\1\135\3\7\7\0\3\7"+
    "\25\0\14\7\1\136\6\7\7\0\3\7\25\0\2\7"+
    "\1\137\20\7\7\0\3\7\25\0\11\7\1\140\11\7"+
    "\7\0\3\7\25\0\11\7\1\141\11\7\7\0\3\7"+
    "\25\0\15\7\1\142\5\7\7\0\3\7\25\0\15\7"+
    "\1\143\5\7\7\0\3\7\25\0\2\7\1\144\20\7"+
    "\7\0\3\7\25\0\3\7\1\145\17\7\7\0\3\7"+
    "\25\0\3\7\1\146\17\7\7\0\3\7\25\0\2\7"+
    "\1\147\20\7\7\0\3\7\25\0\7\7\1\150\13\7"+
    "\7\0\3\7\25\0\15\7\1\151\5\7\7\0\3\7"+
    "\25\0\3\7\1\152\17\7\7\0\3\7\25\0\7\7"+
    "\1\153\13\7\7\0\3\7\25\0\17\7\1\154\3\7"+
    "\7\0\3\7\25\0\2\7\1\155\20\7\7\0\3\7"+
    "\25\0\12\7\1\156\10\7\7\0\3\7\25\0\3\7"+
    "\1\157\17\7\7\0\3\7\25\0\5\7\1\160\15\7"+
    "\7\0\3\7\25\0\7\7\1\161\13\7\7\0\3\7"+
    "\25\0\5\7\1\162\15\7\7\0\3\7\25\0\3\7"+
    "\1\163\17\7\7\0\3\7\25\0\14\7\1\164\6\7"+
    "\7\0\3\7\25\0\3\7\1\165\17\7\7\0\3\7"+
    "\25\0\4\7\1\166\16\7\7\0\3\7\25\0\12\7"+
    "\1\167\10\7\7\0\3\7\25\0\20\7\1\170\2\7"+
    "\7\0\3\7\25\0\5\7\1\171\15\7\7\0\3\7"+
    "\25\0\5\7\1\172\15\7\7\0\3\7\25\0\7\7"+
    "\1\173\13\7\6\0";

  private static int [] zzUnpackTrans() {
    int [] result = new int[3550];
    int offset = 0;
    offset = zzUnpackTrans(ZZ_TRANS_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackTrans(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      value--;
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }


  /* error codes */
  private static final int ZZ_UNKNOWN_ERROR = 0;
  private static final int ZZ_NO_MATCH = 1;
  private static final int ZZ_PUSHBACK_2BIG = 2;

  /* error messages for the codes above */
  private static final String ZZ_ERROR_MSG[] = {
    "Unkown internal scanner error",
    "Error: could not match input",
    "Error: pushback value was too large"
  };

  /**
   * ZZ_ATTRIBUTE[aState] contains the attributes of state <code>aState</code>
   */
  private static final int [] ZZ_ATTRIBUTE = zzUnpackAttribute();

  private static final String ZZ_ATTRIBUTE_PACKED_0 =
    "\5\0\1\11\2\1\6\11\2\1\2\11\2\1\1\11"+
    "\3\1\1\11\1\1\1\11\16\1\3\11\1\1\4\11"+
    "\1\1\10\11\1\1\12\11\17\1\1\11\46\1";

  private static int [] zzUnpackAttribute() {
    int [] result = new int[123];
    int offset = 0;
    offset = zzUnpackAttribute(ZZ_ATTRIBUTE_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackAttribute(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }

  /** the input device */
  private java.io.Reader zzReader;

  /** the current state of the DFA */
  private int zzState;

  /** the current lexical state */
  private int zzLexicalState = YYINITIAL;

  /** this buffer contains the current text to be matched and is
      the source of the yytext() string */
  private char zzBuffer[] = new char[ZZ_BUFFERSIZE];

  /** the textposition at the last accepting state */
  private int zzMarkedPos;

  /** the textposition at the last state to be included in yytext */
  private int zzPushbackPos;

  /** the current text position in the buffer */
  private int zzCurrentPos;

  /** startRead marks the beginning of the yytext() string in the buffer */
  private int zzStartRead;

  /** endRead marks the last character in the buffer, that has been read
      from input */
  private int zzEndRead;

  /** number of newlines encountered up to the start of the matched text */
  private int yyline;

  /** the number of characters up to the start of the matched text */
  private int yychar;

  /**
   * the number of characters from the last newline up to the start of the 
   * matched text
   */
  private int yycolumn;

  /** 
   * zzAtBOL == true <=> the scanner is currently at the beginning of a line
   */
  private boolean zzAtBOL = true;

  /** zzAtEOF == true <=> the scanner is at the EOF */
  private boolean zzAtEOF;

  /** denotes if the user-EOF-code has already been executed */
  private boolean zzEOFDone;

  /* user code: */
  String pstring = new String();
  final int unknown = -1;
  ComplexSymbolFactory.Location left = new ComplexSymbolFactory.Location(unknown, unknown);
  ComplexSymbolFactory cf = new ComplexSymbolFactory();
  public SymbolFactory getSymbolFactory() { return cf; }

  public int line_num() { return (yyline+1); }
  public ComplexSymbolFactory.Location left_loc() {
    return new ComplexSymbolFactory.Location(yyline+1, yycolumn+1, yychar);
  }
  public ComplexSymbolFactory.Location right_loc() {
    ComplexSymbolFactory.Location left = left_loc();
  return new ComplexSymbolFactory.Location(left.getLine(), left.getColumn()+yylength(), left.getOffset()+yylength());
  }
  public String buff() {return new String(zzBuffer,zzCurrentPos,10).trim();}


  /**
   * Creates a new scanner
   * There is also a java.io.InputStream version of this constructor.
   *
   * @param   in  the java.io.Reader to read input from.
   */
  public Yylex(java.io.Reader in) {
    this.zzReader = in;
  }

  /**
   * Creates a new scanner.
   * There is also java.io.Reader version of this constructor.
   *
   * @param   in  the java.io.Inputstream to read input from.
   */
  public Yylex(java.io.InputStream in) {
    this(new java.io.InputStreamReader(in));
  }

  /** 
   * Unpacks the compressed character translation table.
   *
   * @param packed   the packed character translation table
   * @return         the unpacked character translation table
   */
  private static char [] zzUnpackCMap(String packed) {
    char [] map = new char[0x10000];
    int i = 0;  /* index in packed string  */
    int j = 0;  /* index in unpacked array */
    while (i < 142) {
      int  count = packed.charAt(i++);
      char value = packed.charAt(i++);
      do map[j++] = value; while (--count > 0);
    }
    return map;
  }


  /**
   * Refills the input buffer.
   *
   * @return      <code>false</code>, iff there was new input.
   * 
   * @exception   java.io.IOException  if any I/O-Error occurs
   */
  private boolean zzRefill() throws java.io.IOException {

    /* first: make room (if you can) */
    if (zzStartRead > 0) {
      System.arraycopy(zzBuffer, zzStartRead,
                       zzBuffer, 0,
                       zzEndRead-zzStartRead);

      /* translate stored positions */
      zzEndRead-= zzStartRead;
      zzCurrentPos-= zzStartRead;
      zzMarkedPos-= zzStartRead;
      zzPushbackPos-= zzStartRead;
      zzStartRead = 0;
    }

    /* is the buffer big enough? */
    if (zzCurrentPos >= zzBuffer.length) {
      /* if not: blow it up */
      char newBuffer[] = new char[zzCurrentPos*2];
      System.arraycopy(zzBuffer, 0, newBuffer, 0, zzBuffer.length);
      zzBuffer = newBuffer;
    }

    /* finally: fill the buffer with new input */
    int numRead = zzReader.read(zzBuffer, zzEndRead,
                                            zzBuffer.length-zzEndRead);

    if (numRead < 0) {
      return true;
    }
    else {
      zzEndRead+= numRead;
      return false;
    }
  }

    
  /**
   * Closes the input stream.
   */
  public final void yyclose() throws java.io.IOException {
    zzAtEOF = true;            /* indicate end of file */
    zzEndRead = zzStartRead;  /* invalidate buffer    */

    if (zzReader != null)
      zzReader.close();
  }


  /**
   * Resets the scanner to read from a new input stream.
   * Does not close the old reader.
   *
   * All internal variables are reset, the old input stream 
   * <b>cannot</b> be reused (internal buffer is discarded and lost).
   * Lexical state is set to <tt>ZZ_INITIAL</tt>.
   *
   * @param reader   the new input stream 
   */
  public final void yyreset(java.io.Reader reader) {
    zzReader = reader;
    zzAtBOL  = true;
    zzAtEOF  = false;
    zzEndRead = zzStartRead = 0;
    zzCurrentPos = zzMarkedPos = zzPushbackPos = 0;
    yyline = yychar = yycolumn = 0;
    zzLexicalState = YYINITIAL;
  }


  /**
   * Returns the current lexical state.
   */
  public final int yystate() {
    return zzLexicalState;
  }


  /**
   * Enters a new lexical state
   *
   * @param newState the new lexical state
   */
  public final void yybegin(int newState) {
    zzLexicalState = newState;
  }


  /**
   * Returns the text matched by the current regular expression.
   */
  public final String yytext() {
    return new String( zzBuffer, zzStartRead, zzMarkedPos-zzStartRead );
  }


  /**
   * Returns the character at position <tt>pos</tt> from the 
   * matched text. 
   * 
   * It is equivalent to yytext().charAt(pos), but faster
   *
   * @param pos the position of the character to fetch. 
   *            A value from 0 to yylength()-1.
   *
   * @return the character at position pos
   */
  public final char yycharat(int pos) {
    return zzBuffer[zzStartRead+pos];
  }


  /**
   * Returns the length of the matched text region.
   */
  public final int yylength() {
    return zzMarkedPos-zzStartRead;
  }


  /**
   * Reports an error that occured while scanning.
   *
   * In a wellformed scanner (no or only correct usage of 
   * yypushback(int) and a match-all fallback rule) this method 
   * will only be called with things that "Can't Possibly Happen".
   * If this method is called, something is seriously wrong
   * (e.g. a JFlex bug producing a faulty scanner etc.).
   *
   * Usual syntax/scanner level error handling should be done
   * in error fallback rules.
   *
   * @param   errorCode  the code of the errormessage to display
   */
  private void zzScanError(int errorCode) {
    String message;
    try {
      message = ZZ_ERROR_MSG[errorCode];
    }
    catch (ArrayIndexOutOfBoundsException e) {
      message = ZZ_ERROR_MSG[ZZ_UNKNOWN_ERROR];
    }

    throw new Error(message);
  } 


  /**
   * Pushes the specified amount of characters back into the input stream.
   *
   * They will be read again by then next call of the scanning method
   *
   * @param number  the number of characters to be read again.
   *                This number must not be greater than yylength()!
   */
  public void yypushback(int number)  {
    if ( number > yylength() )
      zzScanError(ZZ_PUSHBACK_2BIG);

    zzMarkedPos -= number;
  }


  /**
   * Contains user EOF-code, which will be executed exactly once,
   * when the end of file is reached
   */
  private void zzDoEOF() throws java.io.IOException {
    if (!zzEOFDone) {
      zzEOFDone = true;
      yyclose();
    }
  }


  /**
   * Resumes scanning until the next regular expression is matched,
   * the end of input is encountered or an I/O-Error occurs.
   *
   * @return      the next token
   * @exception   java.io.IOException  if any I/O-Error occurs
   */
  public java_cup.runtime.Symbol next_token() throws java.io.IOException {
    int zzInput;
    int zzAction;

    // cached fields:
    int zzCurrentPosL;
    int zzMarkedPosL;
    int zzEndReadL = zzEndRead;
    char [] zzBufferL = zzBuffer;
    char [] zzCMapL = ZZ_CMAP;

    int [] zzTransL = ZZ_TRANS;
    int [] zzRowMapL = ZZ_ROWMAP;
    int [] zzAttrL = ZZ_ATTRIBUTE;

    while (true) {
      zzMarkedPosL = zzMarkedPos;

      yychar+= zzMarkedPosL-zzStartRead;

      boolean zzR = false;
      for (zzCurrentPosL = zzStartRead; zzCurrentPosL < zzMarkedPosL;
                                                             zzCurrentPosL++) {
        switch (zzBufferL[zzCurrentPosL]) {
        case '\u000B':
        case '\u000C':
        case '\u0085':
        case '\u2028':
        case '\u2029':
          yyline++;
          yycolumn = 0;
          zzR = false;
          break;
        case '\r':
          yyline++;
          yycolumn = 0;
          zzR = true;
          break;
        case '\n':
          if (zzR)
            zzR = false;
          else {
            yyline++;
            yycolumn = 0;
          }
          break;
        default:
          zzR = false;
          yycolumn++;
        }
      }

      if (zzR) {
        // peek one character ahead if it is \n (if we have counted one line too much)
        boolean zzPeek;
        if (zzMarkedPosL < zzEndReadL)
          zzPeek = zzBufferL[zzMarkedPosL] == '\n';
        else if (zzAtEOF)
          zzPeek = false;
        else {
          boolean eof = zzRefill();
          zzEndReadL = zzEndRead;
          zzMarkedPosL = zzMarkedPos;
          zzBufferL = zzBuffer;
          if (eof) 
            zzPeek = false;
          else 
            zzPeek = zzBufferL[zzMarkedPosL] == '\n';
        }
        if (zzPeek) yyline--;
      }
      zzAction = -1;

      zzCurrentPosL = zzCurrentPos = zzStartRead = zzMarkedPosL;
  
      zzState = zzLexicalState;


      zzForAction: {
        while (true) {
    
          if (zzCurrentPosL < zzEndReadL)
            zzInput = zzBufferL[zzCurrentPosL++];
          else if (zzAtEOF) {
            zzInput = YYEOF;
            break zzForAction;
          }
          else {
            // store back cached positions
            zzCurrentPos  = zzCurrentPosL;
            zzMarkedPos   = zzMarkedPosL;
            boolean eof = zzRefill();
            // get translated positions and possibly new buffer
            zzCurrentPosL  = zzCurrentPos;
            zzMarkedPosL   = zzMarkedPos;
            zzBufferL      = zzBuffer;
            zzEndReadL     = zzEndRead;
            if (eof) {
              zzInput = YYEOF;
              break zzForAction;
            }
            else {
              zzInput = zzBufferL[zzCurrentPosL++];
            }
          }
          int zzNext = zzTransL[ zzRowMapL[zzState] + zzCMapL[zzInput] ];
          if (zzNext == -1) break zzForAction;
          zzState = zzNext;

          int zzAttributes = zzAttrL[zzState];
          if ( (zzAttributes & 1) == 1 ) {
            zzAction = zzState;
            zzMarkedPosL = zzCurrentPosL;
            if ( (zzAttributes & 8) == 8 ) break zzForAction;
          }

        }
      }

      // store back cached position
      zzMarkedPos = zzMarkedPosL;

      switch (zzAction < 0 ? zzAction : ZZ_ACTION[zzAction]) {
        case 23: 
          { /* skip */
          }
        case 64: break;
        case 4: 
          { return cf.newSymbol("", sym._SYMB_0, left_loc(), right_loc());
          }
        case 65: break;
        case 28: 
          { String foo = pstring; pstring = new String(); yybegin(YYINITIAL); return cf.newSymbol("", sym._STRING_, left, right_loc(), foo.intern());
          }
        case 66: break;
        case 32: 
          { pstring += "\t"; yybegin(STRING);
          }
        case 67: break;
        case 57: 
          { return cf.newSymbol("", sym._SYMB_29, left_loc(), right_loc());
          }
        case 68: break;
        case 1: 
          { throw new Error("Illegal Character <"+yytext()+"> at "+(yyline+1)+":"+(yycolumn+1)+"("+yychar+")");
          }
        case 69: break;
        case 14: 
          { return cf.newSymbol("", sym._SYMB_18, left_loc(), right_loc());
          }
        case 70: break;
        case 54: 
          { return cf.newSymbol("", sym._SYMB_37, left_loc(), right_loc());
          }
        case 71: break;
        case 38: 
          { return cf.newSymbol("", sym._SYMB_26, left_loc(), right_loc());
          }
        case 72: break;
        case 17: 
          { return cf.newSymbol("", sym._SYMB_15, left_loc(), right_loc());
          }
        case 73: break;
        case 24: 
          { /* ignore white space. */
          }
        case 74: break;
        case 36: 
          { pstring += "\""; yybegin(STRING);
          }
        case 75: break;
        case 11: 
          { return cf.newSymbol("", sym._SYMB_7, left_loc(), right_loc());
          }
        case 76: break;
        case 48: 
          { return cf.newSymbol("", sym._SYMB_34, left_loc(), right_loc());
          }
        case 77: break;
        case 16: 
          { return cf.newSymbol("", sym._SYMB_12, left_loc(), right_loc());
          }
        case 78: break;
        case 46: 
          { return cf.newSymbol("", sym._SYMB_23, left_loc(), right_loc());
          }
        case 79: break;
        case 59: 
          { return cf.newSymbol("", sym._SYMB_42, left_loc(), right_loc());
          }
        case 80: break;
        case 63: 
          { return cf.newSymbol("", sym._SYMB_31, left_loc(), right_loc());
          }
        case 81: break;
        case 8: 
          { return cf.newSymbol("", sym._SYMB_4, left_loc(), right_loc());
          }
        case 82: break;
        case 19: 
          { return cf.newSymbol("", sym._SYMB_20, left_loc(), right_loc());
          }
        case 83: break;
        case 34: 
          { pstring += "\r"; yybegin(STRING);
          }
        case 84: break;
        case 26: 
          { pstring += yytext();
          }
        case 85: break;
        case 49: 
          { yybegin(YYINITIAL);
          }
        case 86: break;
        case 5: 
          { return cf.newSymbol("", sym._SYMB_1, left_loc(), right_loc());
          }
        case 87: break;
        case 3: 
          { return cf.newSymbol("", sym._INTEGER_, left_loc(), right_loc(), Integer.valueOf(yytext()));
          }
        case 88: break;
        case 45: 
          { yybegin(COMMENT);
          }
        case 89: break;
        case 31: 
          { pstring +=  "\n"; yybegin(STRING);
          }
        case 90: break;
        case 18: 
          { return cf.newSymbol("", sym._SYMB_19, left_loc(), right_loc());
          }
        case 91: break;
        case 61: 
          { return cf.newSymbol("", sym._SYMB_38, left_loc(), right_loc());
          }
        case 92: break;
        case 42: 
          { return cf.newSymbol("", sym._SYMB_27, left_loc(), right_loc());
          }
        case 93: break;
        case 2: 
          { return cf.newSymbol("", sym._IDENT_, left_loc(), right_loc(), yytext().intern());
          }
        case 94: break;
        case 43: 
          { return cf.newSymbol("", sym._SYMB_16, left_loc(), right_loc());
          }
        case 95: break;
        case 29: 
          { yybegin(ESCAPED);
          }
        case 96: break;
        case 12: 
          { return cf.newSymbol("", sym._SYMB_8, left_loc(), right_loc());
          }
        case 97: break;
        case 22: 
          { return cf.newSymbol("", sym._SYMB_24, left_loc(), right_loc());
          }
        case 98: break;
        case 52: 
          { return cf.newSymbol("", sym._SYMB_35, left_loc(), right_loc());
          }
        case 99: break;
        case 39: 
          { return cf.newSymbol("", sym._SYMB_13, left_loc(), right_loc());
          }
        case 100: break;
        case 58: 
          { return cf.newSymbol("", sym._SYMB_32, left_loc(), right_loc());
          }
        case 101: break;
        case 9: 
          { return cf.newSymbol("", sym._SYMB_5, left_loc(), right_loc());
          }
        case 102: break;
        case 20: 
          { return cf.newSymbol("", sym._SYMB_21, left_loc(), right_loc());
          }
        case 103: break;
        case 40: 
          { return cf.newSymbol("", sym._SYMB_10, left_loc(), right_loc());
          }
        case 104: break;
        case 35: 
          { throw new Error("Unterminated string on line " + left.getLine() + " beginning at column " + left.getColumn());
          }
        case 105: break;
        case 33: 
          { pstring += "\f"; yybegin(STRING);
          }
        case 106: break;
        case 55: 
          { return cf.newSymbol("", sym._SYMB_40, left_loc(), right_loc());
          }
        case 107: break;
        case 6: 
          { return cf.newSymbol("", sym._SYMB_2, left_loc(), right_loc());
          }
        case 108: break;
        case 37: 
          { pstring += "\\"; yybegin(STRING);
          }
        case 109: break;
        case 30: 
          { pstring += yytext(); yybegin(STRING);
          }
        case 110: break;
        case 60: 
          { return cf.newSymbol("", sym._SYMB_39, left_loc(), right_loc());
          }
        case 111: break;
        case 62: 
          { return cf.newSymbol("", sym._SYMB_28, left_loc(), right_loc());
          }
        case 112: break;
        case 44: 
          { return cf.newSymbol("", sym._SYMB_17, left_loc(), right_loc());
          }
        case 113: break;
        case 13: 
          { return cf.newSymbol("", sym._SYMB_9, left_loc(), right_loc());
          }
        case 114: break;
        case 47: 
          { return cf.newSymbol("", sym._SYMB_25, left_loc(), right_loc());
          }
        case 115: break;
        case 50: 
          { return cf.newSymbol("", sym._SYMB_36, left_loc(), right_loc());
          }
        case 116: break;
        case 15: 
          { return cf.newSymbol("", sym._SYMB_14, left_loc(), right_loc());
          }
        case 117: break;
        case 27: 
          { throw new Error("Unterminated string on line " + left.getLine() + " begining at column " + left.getColumn());
          }
        case 118: break;
        case 51: 
          { return cf.newSymbol("", sym._SYMB_33, left_loc(), right_loc());
          }
        case 119: break;
        case 41: 
          { return cf.newSymbol("", sym._SYMB_11, left_loc(), right_loc());
          }
        case 120: break;
        case 10: 
          { return cf.newSymbol("", sym._SYMB_6, left_loc(), right_loc());
          }
        case 121: break;
        case 25: 
          { left = left_loc(); yybegin(STRING);
          }
        case 122: break;
        case 21: 
          { return cf.newSymbol("", sym._SYMB_22, left_loc(), right_loc());
          }
        case 123: break;
        case 56: 
          { return cf.newSymbol("", sym._SYMB_41, left_loc(), right_loc());
          }
        case 124: break;
        case 53: 
          { return cf.newSymbol("", sym._SYMB_30, left_loc(), right_loc());
          }
        case 125: break;
        case 7: 
          { return cf.newSymbol("", sym._SYMB_3, left_loc(), right_loc());
          }
        case 126: break;
        default: 
          if (zzInput == YYEOF && zzStartRead == zzCurrentPos) {
            zzAtEOF = true;
            zzDoEOF();
            switch (zzLexicalState) {
            case STRING: {
              throw new Error("Unterminated string at EOF, beginning at " + left.getLine() + ":" + left.getColumn());
            }
            case 124: break;
            case ESCAPED: {
              throw new Error("Unterminated string at EOF, beginning at " + left.getLine() + ":" + left.getColumn());
            }
            case 125: break;
            default:
              {
                return cf.newSymbol("EOF", sym.EOF, left_loc(), left_loc());
              }
            }
          } 
          else {
            zzScanError(ZZ_NO_MATCH);
          }
      }
    }
  }


}
