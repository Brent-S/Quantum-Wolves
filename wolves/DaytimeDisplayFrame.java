package wolves;

import java.awt.Dialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

@SuppressWarnings("serial")
public class DaytimeDisplayFrame extends JFrame {

	
	public DaytimeDisplayFrame(double[][] inProbs, String inRoles){
		
		final JDialog dialog = new JDialog(this, "Quantum Werewolves", Dialog.ModalityType.APPLICATION_MODAL);	
		dialog.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		dialog.setLocationRelativeTo(null);
		
		final String newline = "\n";
		
		String table = "<html>" + newline + "<table border=\"1\">" + newline;
		table += "<tr><td>Player</td><td>Good </td><td>Evil </td><td>Alive </td><td>Dead</td></tr>" + newline;
		
		for(int row = 0; row < inProbs.length; row++){
			table += "<tr>" + newline + "<td>" + (row + 1) + "</td>" + newline;
			for(int col = 0; col < 4; col++){
				table += "<td>" + Math.round(inProbs[row][col]) + "</td>" + newline;
			}
			table += "</tr>" + newline;
		}
		table += "</table>" + newline + "</html>";
		
		final JLabel ProbsLabel = new JLabel(table);
		ProbsLabel.setFont(ProbsLabel.getFont().deriveFont(40.0f));
		final JLabel Title = new JLabel("Known information:");
		Title.setFont(Title.getFont().deriveFont(30.0f));
		final JLabel roles = new JLabel(inRoles);
		roles.setFont(roles.getFont().deriveFont(30.0f));
		
		JButton button = new JButton("Ok");
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				dialog.setVisible(false);
			}				
		});
		
		JLabel SliderLabel = new JLabel("Font size");
		JSlider FontSlider = new JSlider(JSlider.HORIZONTAL, 10, 100, 40);
		FontSlider.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				JSlider source = (JSlider)e.getSource();
		       // if (!source.getValueIsAdjusting()) {
		            float fontSize = (float)source.getValue();
		    		ProbsLabel.setFont(ProbsLabel.getFont().deriveFont(fontSize));
		    		Title.setFont(Title.getFont().deriveFont(0.75f * fontSize));
		    		roles.setFont(roles.getFont().deriveFont(0.75f * fontSize));
		            dialog.pack();
		       // }				
			}
			
		});
		
		dialog.getContentPane().setLayout(new BoxLayout(dialog.getContentPane(), BoxLayout.Y_AXIS));	
		
		dialog.add(SliderLabel);
		dialog.add(FontSlider);
		dialog.add(Title);
		dialog.add(ProbsLabel);
		dialog.add(roles);
		dialog.add(button);
				
		dialog.pack();
		dialog.setLocationRelativeTo(null);
		dialog.setVisible(true);
	}
}
