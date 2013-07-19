package wolves;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

@SuppressWarnings("serial")
public class DaytimeDisplayFrame extends JFrame {

	
	public DaytimeDisplayFrame(int inRound, double[][] inProbs, String inRoles){
		this.getContentPane().setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));		
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setLocationRelativeTo(null);
		
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
		
		JLabel ProbsLabel = new JLabel(table);
		ProbsLabel.setFont(ProbsLabel.getFont().deriveFont(50.0f));
		JLabel Title = new JLabel("Round " + inRound + " known information:");
		Title.setFont(Title.getFont().deriveFont(40.0f));
		JLabel roles = new JLabel(inRoles);
		roles.setFont(roles.getFont().deriveFont(40.0f));
		
		JButton button = new JButton("Ok");
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO something here...
			}				
		});
		
		this.add(Title);
		this.add(ProbsLabel);
		this.add(roles);
		this.add(button);

		this.pack();
		this.setVisible(true);
	}
}
