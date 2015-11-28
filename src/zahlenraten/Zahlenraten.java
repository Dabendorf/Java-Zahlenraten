package zahlenraten;

import java.text.NumberFormat;
import java.util.Random;

import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.text.NumberFormatter;

/**
 * Dies ist die einzige Klasse des Programmes. Sie steuert alle Ablaeufe des Spiels Zahlenraten.
 * @author Lukas Schramm
 * @version 1.0
 */
public class Zahlenraten {
	
	private NumberFormat format = NumberFormat.getInstance(); 
	private NumberFormatter formatter = new NumberFormatter(format);
	private int zufallszahl;
	private int ratezahl;
	private int versuche = 0;
	
	public Zahlenraten() {
		format.setGroupingUsed(false); 
		formatter.setAllowsInvalid(false);
		Random hundert = new Random();
		zufallszahl = hundert.nextInt(100)+1;
		zahleneingabe();
	}
	
	/**
	 * Diese Methode nimmt die Zahl in einem Eingabefeld an, die der Spieler als Tipp abgibt.<br>
	 * Bei falscher Antwort wird die Anzahl der verbrauchten Versuche um 1 nach oben gesetzt.<br>
	 * Wenn der Spieler nichts eingibt, wird eine Fehlermeldung ausgegeben.<br>
	 * Die Methode leitet direkt zu rechnen() weiter, wo ermittelt wird, was dem Nutzer ausgegeben wird.
	 */
	private void zahleneingabe() {
		JFormattedTextField nummernfeld = new JFormattedTextField(formatter);
		Object[] zahlenfrage = {"Bitte gib eine Zahl zwischen 0 und 100 ein.", nummernfeld};
		JOptionPane pane = new JOptionPane(zahlenfrage, JOptionPane.QUESTION_MESSAGE, JOptionPane.DEFAULT_OPTION);
		pane.createDialog(null, "Errate die Zahl!").setVisible(true);
		String zahlStr = nummernfeld.getText();
		try {
			if(zahlStr.equals("")) {
				JOptionPane.showMessageDialog(null, "Bitte gib eine Zahl ein!", "Ungültige Eingabe", JOptionPane.ERROR_MESSAGE);
				zahleneingabe();
			} else if(zahlStr.equals("1804")) {
				JOptionPane.showMessageDialog(null, "Die korrekte Zahl ist "+zufallszahl+". \nVerrate es nicht weiter.", "Napoléonmodus", JOptionPane.INFORMATION_MESSAGE);
				zahleneingabe();
			} else {
				ratezahl = Integer.parseInt(zahlStr);
				versuche += 1;
				rechnen();
			}
		} catch(Exception e) {
			JOptionPane.showMessageDialog(null, "Bitte gib eine natürliche Zahl ein!", "Ungültige Eingabe", JOptionPane.ERROR_MESSAGE);
			zahleneingabe();
		}
	}
	
	/**
	 * Diese Methode nimmt den Rateversuch des Spielers auf und sagt ihm, ob die Zahl zu klein, zu gross oder genau richtig ist.<br>
	 * Sollte er die Anzahl der Versuche ueberschreiten, wird sein Spiel beendet.
	 */
	private void rechnen() {
		if(versuche > 10) {
			JOptionPane.showMessageDialog(null, "Du hast mehr als 10 Versuche gebraucht. \nDu hast verloren.", "Verloren", JOptionPane.PLAIN_MESSAGE);
			neuepartie();
		} else {
			if(ratezahl>zufallszahl) {
				JOptionPane.showMessageDialog(null, "Deine Zahl "+ratezahl+" ist zu groß!", "Zu groß", JOptionPane.PLAIN_MESSAGE);
				zahleneingabe();
			} else if (ratezahl<zufallszahl) {
				JOptionPane.showMessageDialog(null, "Deine Zahl "+ratezahl+" ist zu klein!", "Zu klein", JOptionPane.PLAIN_MESSAGE);
				zahleneingabe();
			} else {
				if(versuche == 1) {
					JOptionPane.showMessageDialog(null, "Deine Zahl "+ratezahl+" ist die gesuchte Zahl! \nDu gewinnst nach einem Versuch!", "Gewonnen!", JOptionPane.PLAIN_MESSAGE);
				} else {
					JOptionPane.showMessageDialog(null, "Deine Zahl "+ratezahl+" ist die gesuchte Zahl! \nDu gewinnst nach "+versuche+" Versuchen!", "Gewonnen!", JOptionPane.PLAIN_MESSAGE);
				}
				neuepartie();
			}
		}
	}
	
	/**
	 * Diese Methode fragt den Spieler, ob er eine weitere Zahl erraten moechte und startet je nach Nutzereingabe entweder ein neues Spiel oder beendet das Programm.
	 */
	private void neuepartie() {
		int dialogneustart = JOptionPane.showConfirmDialog(null, "Möchtest Du eine neue Zahl erraten?", "Neue Runde?", JOptionPane.YES_NO_OPTION);
        if(dialogneustart == 0) {
        	new Zahlenraten();
        } else {
        	System.exit(0);
        }
	}
	
	public static void main(String[] args) {
		new Zahlenraten();
	}
}