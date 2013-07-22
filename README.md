Quantum-Wolves
==============

A java implementation of the game 'Quantum Werewolves".

I'm trying to get a 
    JOptionPane OptPane = new JOptionPane(prompt, JOptionPane.QUESTION_MESSAGE);
    OptPane.setWantsInput(true);
    JDialog dialog = OptPane.createDialog("Quantum Werewolves");
     dialog.setVisible(true);
    Object output = OptPane.getValue();
to return a String, but it won't, and I don't know why...

I don't know if there is any point in having a jar uploaded, but I thought it would make it easier for windows-y peeps to run without much effort.

I managed to break it (eliminating all possible gamestates) but I don't know how, and can't reproduce it...  If anyone has any wise ideas, let me know.
