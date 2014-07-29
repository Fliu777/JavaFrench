import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

//robot typing key

public class UserInteraction implements NativeKeyListener {

	public void RobotType(String twoletter) {
		String osName = System.getProperty("os.name");
		System.err.println(twoletter);
		System.err.println(currentword);
		boolean isMac = osName.startsWith("Mac OS X");
		boolean isWin = osName.startsWith("Windows");
		boolean isLin = osName.startsWith("Linux");

		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		StringSelection str = new StringSelection(twoletter);
		clipboard.setContents(str, str);
		Robot robot = null;
		try {
			robot = new Robot();
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// 2 when it's just 2
		// 3 when double tap
		robot.keyPress(KeyEvent.VK_BACK_SPACE);
		robot.keyRelease(KeyEvent.VK_BACK_SPACE);
		robot.keyPress(KeyEvent.VK_BACK_SPACE);
		robot.keyRelease(KeyEvent.VK_BACK_SPACE);
		robot.keyPress(KeyEvent.VK_BACK_SPACE);
		robot.keyRelease(KeyEvent.VK_BACK_SPACE);

		if (isMac) {

			// ⌘-V on Mac
			robot.keyPress(KeyEvent.VK_META);
			robot.keyPress(KeyEvent.VK_V);
			robot.keyRelease(KeyEvent.VK_V);
			robot.keyRelease(KeyEvent.VK_META);
		} else if (isWin) {
			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_V);
			robot.keyRelease(KeyEvent.VK_V);
			robot.keyRelease(KeyEvent.VK_CONTROL);
		} else if (isLin) {
			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_V);
			robot.keyRelease(KeyEvent.VK_V);
			robot.keyRelease(KeyEvent.VK_CONTROL);
		}

		else {
			throw new AssertionError("Not tested on " + osName);
		}

	}

	public char currentkey = ' ';
	public String currentword = "";
	public long start = System.currentTimeMillis();
	public long end = 0;
	public boolean keyheld = false;
	public double totaltime = 30000.0;
	public int defaultkeytime = 300;
	public int keypresses = 100;
	public boolean accentmode = false;
	public char lastkey = ' ';

	public void nativeKeyPressed(NativeKeyEvent e) {
		// System.out.println("Key Pressed: " +
		// NativeKeyEvent.getKeyText(e.getKeyCode()));

		if (e.getKeyCode() == NativeKeyEvent.VK_ESCAPE) {
			GlobalScreen.unregisterNativeHook();
		}
		if (e.getKeyCode() == NativeKeyEvent.VK_SPACE) {
			System.out.println(totaltime / keypresses);
			currentword = "";
		}
		if (currentword.length() > 0
				&& e.getKeyCode() == NativeKeyEvent.VK_BACK_SPACE) {
			// GlobalScreen.unregisterNativeHook();
			currentword = currentword.substring(0, currentword.length() - 1);
		}

	}

	public void nativeKeyReleased(NativeKeyEvent e) {
		// System.out.println("Key Released: " +
		// NativeKeyEvent.getKeyText(e.getKeyCode()));
		end = System.currentTimeMillis();
		keyheld = false;

		keypresses++;

		// adjust for holding keys
		if (end - start < 500) {
			totaltime = totaltime + end - start;
		} else {
			keypresses--;
		}

		// they want an accent okay let's give it to them ,change to dynamic
		// time
		/*
		 * if (end-start>250){ System.out.println("accent mode has started");
		 * accentmode=true; } else{ accentmode=false; }
		 */

	}

	public void nativeKeyTyped(NativeKeyEvent e) {

		char typed = e.getKeyChar();
		if (accentmode) {
			String query = "";
			if (!currentword.isEmpty()) {
				//take out last letter
				query = currentword.substring(0,currentword.length()-1);
				//query+=currentword.charAt(currentword.length()-1);
				//System.out.println(" sadlif<"+ query+">"+"<"+currentword+">");
			}
			// System.out.println("going into accent mode");
			// (GetAccents.GetBest(query,typed));
			// System.out.println(GetAccents.GetBest(query,typed));
			// System.out.println("between?");
			
			//System.out.println(" sadlif<"+ query+">"+"<"+currentword+">");

			String t = GetAccents.GetBest(query, typed);
			if (!t.isEmpty()) {
				RobotType(t);
			}

			accentmode = false;

		}

		if (typed == ' ' || typed == '\n') {
			currentword = "";
		} else if ((typed >= 'a' && typed <= 'z') || (typed >= 'A' && typed <= 'Z')) {
			currentword += typed;
		}
		currentkey = typed;

		// double tap version, cycles to next key type
		if (System.currentTimeMillis() - start < 200) {
			//System.out.println("well here it is <"+lastkey+"> <"+e.getKeyChar());
			if (lastkey == e.getKeyChar()
					&& (e.getKeyChar() == 'e' || e.getKeyChar() == 'a'
							|| e.getKeyChar() == 'i' || e.getKeyChar() == 'o' || e
							.getKeyChar() == 'u')) {
				accentmode = true;
			} else {
				accentmode = false;
			}
		} else {
			accentmode = false;
		}

		lastkey = e.getKeyChar();

		start = System.currentTimeMillis();

		// System.out.println("Key Typed: " +
		// NativeKeyEvent.getKeyText(e.getKeyCode()));
		
		
	}

	public static void main(String[] args) {

		GetAccents.BuildAccents();

		String osName = System.getProperty("os.name");
		System.out.println(osName);
		try {
			GlobalScreen.registerNativeHook();
		} catch (NativeHookException ex) {
			System.err
					.println("There was a problem registering the native hook.");
			System.err.println(ex.getMessage());

			System.exit(1);
		}

		// Construct the example object and initialze native hook.
		GlobalScreen.getInstance().addNativeKeyListener(new UserInteraction());
	}
}