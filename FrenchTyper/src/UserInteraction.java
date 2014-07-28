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
	
	
	
	public  void RobotType(String twoletter){
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
	        throw new AssertionError("Not tested on "+osName);
	    }
	   
	}
	
	
	
	
	
		public char currentkey=' ';
		public String currentword="";
		public long start=System.currentTimeMillis();
		public long end=0;
		public boolean keyheld=false;
		public double totaltime=30000.0;
		public int defaultkeytime=300;
		public int keypresses=100;
		public boolean accentmode=false;
	
        public void nativeKeyPressed(NativeKeyEvent e) {
                //System.out.println("Key Pressed: " + NativeKeyEvent.getKeyText(e.getKeyCode()));

                if (e.getKeyCode() == NativeKeyEvent.VK_ESCAPE) {
                        GlobalScreen.unregisterNativeHook();
                }
                if (e.getKeyCode() == NativeKeyEvent.VK_SPACE) {
                    //GlobalScreen.unregisterNativeHook();
                    System.out.println(totaltime/keypresses);
                }
                if (currentword.length()>0 && e.getKeyCode() == NativeKeyEvent.VK_BACK_SPACE) {
                    //GlobalScreen.unregisterNativeHook();
                    currentword=currentword.substring(0, currentword.length()-1);
                }
                start=System.currentTimeMillis();

                
        }

        public void nativeKeyReleased(NativeKeyEvent e) {
              //  System.out.println("Key Released: " + NativeKeyEvent.getKeyText(e.getKeyCode()));
            end=System.currentTimeMillis();
            keyheld=false;

        	keypresses++;
        	
        	//adjust for holding keys
        	if (end-start<500){
            	totaltime=totaltime+end-start;
        	}
        	else{
        		keypresses--;
        	}

        	//they want an accent okay let's give it to them ,change to dynamic time
        	if (end-start>200){
        		System.out.println("accent mode has started");
        		accentmode=true;
        	}
        	else{
        		accentmode=false;
        	}
        }

        public void nativeKeyTyped(NativeKeyEvent e) {
            char typed=e.getKeyChar();
            String query=currentword;
            if (accentmode){
            	//System.out.println("going into accent mode");
                //(GetAccents.GetBest(query,typed));
            	//System.out.println(GetAccents.GetBest(query,typed));
            	//System.out.println("between?");
            	RobotType(GetAccents.GetBest(query,typed));
            	accentmode=false;
            	
            }


            if (typed==' ' || typed=='\n'){
                System.out.println(currentword);
            	currentword="";
            }
            else{
            	currentword+=typed;
            }
            currentkey=typed;

           


            


            //start=System.currentTimeMillis();
    
        	//System.out.println("Key Typed: " + NativeKeyEvent.getKeyText(e.getKeyCode()));
        }

        public static void main(String[] args) {
        	
        	GetAccents.BuildAccents();
        	
    	    String osName = System.getProperty("os.name");
    	    System.out.println(osName);
                try {
                        GlobalScreen.registerNativeHook();
                }
                catch (NativeHookException ex) {
                        System.err.println("There was a problem registering the native hook.");
                        System.err.println(ex.getMessage());

                        System.exit(1);
                }

                //Construct the example object and initialze native hook.
                GlobalScreen.getInstance().addNativeKeyListener(new UserInteraction());
        }
}