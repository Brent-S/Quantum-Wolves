package wolves;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

@SuppressWarnings("serial")
public class PlayerSelectFrame extends JFrame {
	
	private String[] players;
	private String selection;
	private JLabel label;
	
	public static String choosePlayer(String prompt, String[] players) {
		PlayerSelectFrame frame = new PlayerSelectFrame("<html>" + prompt + "</html>", players);
		String val = frame.getSelection();
		frame.setVisible(false);
		frame.dispose();
		return val;
	}
	
	private synchronized String getSelection() {
		while (selection == null) {
			try {
				this.wait();
			} catch (InterruptedException e) {
				throw (new RuntimeException(e));
				//Seriously when is it ever correct to actually handle this error
			}
		}
		return selection;
		
	}

	private PlayerSelectFrame(String prompt, String[] players) {
		this.getContentPane().setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS)); 
		
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.players = players;
		this.label = new JLabel(prompt);
		this.add(label);

		for (String i : this.players) {
			final String j = i;
			JButton button = new JButton(i);
			button.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					PlayerSelectFrame.this.choose(j);
				}				
			});
			this.add(button);
		}
		this.pack();
		this.setVisible(true);
	}

	
	private synchronized void choose(String s) {
		this.selection = s;
		this.notify();
	}
	

}
