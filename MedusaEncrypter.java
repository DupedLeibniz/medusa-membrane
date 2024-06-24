// MedusaEcnrypter Version 1.0 Portable
// This is the portable version of the encrypter
// Just compile it and execute it with java MedusaEncrypter
// Needs a lot of improvement but so far this works fine
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.EventQueue;
import java.awt.FocusTraversalPolicy;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class MedusaEncrypterPortable {

	private JFrame frame;
	private JTextField passwordField;
	private JTextArea txtAreaSource;
	private JTextArea txtAreaResult;
	private File selectedFile;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					MedusaEncrypterPortable window = new MedusaEncrypterPortable();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public MedusaEncrypterPortable() {
		initialize();
	}

	private void initialize() {

		frame = new JFrame("Medusa Encrypter Version 1.0 (Portable)");
		frame.setForeground(Color.WHITE);
		frame.setBackground(Color.BLACK);
		frame.getContentPane().setForeground(Color.WHITE);
		frame.getContentPane().setBackground(Color.BLACK);
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel texts = new JPanel();
		frame.getContentPane().add(texts, BorderLayout.CENTER);
		texts.setLayout(new GridLayout(0, 2, 0, 0));

		JScrollPane scrollPane = new JScrollPane();
		texts.add(scrollPane);

		txtAreaSource = new JTextArea();
		txtAreaSource.setForeground(Color.WHITE);
		txtAreaSource.setBackground(Color.BLACK);
		txtAreaSource.setFont(new Font("Dialog", Font.PLAIN, 15));
		scrollPane.setViewportView(txtAreaSource);

		JScrollPane scrollPane_1 = new JScrollPane();
		texts.add(scrollPane_1);

		txtAreaResult = new JTextArea();
		txtAreaResult.setForeground(Color.WHITE);
		txtAreaResult.setBackground(Color.BLACK);
		txtAreaResult.setFont(new Font("Dialog", Font.PLAIN, 15));
		txtAreaResult.setEditable(false);
		scrollPane_1.setViewportView(txtAreaResult);
		texts.setFocusTraversalPolicy(
				new FocusTraversalOnArray(new Component[] { txtAreaSource, scrollPane, scrollPane_1 }));

		JPanel upperTexts = new JPanel();
		upperTexts.setForeground(Color.WHITE);
		upperTexts.setBackground(Color.BLACK);
		frame.getContentPane().add(upperTexts, BorderLayout.NORTH);
		upperTexts.setLayout(new GridLayout(2, 2, 0, 0));

		JSeparator separator_2 = new JSeparator();
		separator_2.setForeground(Color.BLACK);
		separator_2.setBackground(Color.BLACK);
		upperTexts.add(separator_2);

		JSeparator separator_2_1 = new JSeparator();
		separator_2_1.setForeground(Color.BLACK);
		separator_2_1.setBackground(Color.BLACK);
		upperTexts.add(separator_2_1);

		JLabel lblSource = new JLabel("Your text");
		lblSource.setHorizontalAlignment(SwingConstants.CENTER);
		lblSource.setForeground(Color.WHITE);
		lblSource.setFont(new Font("Dialog", Font.BOLD, 14));
		upperTexts.add(lblSource);

		JLabel lblResult = new JLabel("Result");
		lblResult.setHorizontalAlignment(SwingConstants.CENTER);
		lblResult.setForeground(Color.WHITE);
		lblResult.setFont(new Font("Dialog", Font.BOLD, 14));
		upperTexts.add(lblResult);

		JPanel panelPassword = new JPanel();
		panelPassword.setBackground(Color.BLACK);
		frame.getContentPane().add(panelPassword, BorderLayout.SOUTH);
		panelPassword.setLayout(new GridLayout(5, 0, 0, 0));

		JSeparator separator_1 = new JSeparator();
		separator_1.setForeground(Color.BLACK);
		separator_1.setBackground(Color.BLACK);
		panelPassword.add(separator_1);

		JLabel lblPassword = new JLabel("Password");
		lblPassword.setForeground(Color.WHITE);
		lblPassword.setFont(new Font("Dialog", Font.BOLD, 14));
		panelPassword.add(lblPassword);

		passwordField = new JTextField();
		passwordField.setFont(new Font("Dialog", Font.PLAIN, 15));
		passwordField.setForeground(Color.GREEN);
		passwordField.setBackground(Color.BLACK);
		panelPassword.add(passwordField);
		passwordField.setColumns(10);

		JSeparator separator = new JSeparator();
		separator.setToolTipText("");
		separator.setBackground(Color.BLACK);
		separator.setForeground(Color.BLACK);
		panelPassword.add(separator);

		JPanel actions = new JPanel();
		actions.setBackground(Color.BLACK);
		panelPassword.add(actions);
		actions.setLayout(new GridLayout(0, 4, 0, 0));

		JButton chooseFile = new JButton("Choose file");
		chooseFile.setForeground(Color.WHITE);
		chooseFile.setBackground(Color.BLACK);
		chooseFile.setFont(new Font("Dialog", Font.BOLD, 14));
		chooseFile.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {

				int choice = JOptionPane.YES_OPTION;
				if (!txtAreaSource.getText().equals("")) {
					choice = JOptionPane.showConfirmDialog(null,
							"Your text panel contains text already. Do you want to proceed?");
				}
				if (!txtAreaResult.getText().equals("")) {
					choice = JOptionPane.showConfirmDialog(null,
							"The result panel contains text already. Do you want to proceed?");
				}

				if (choice == JOptionPane.YES_OPTION) {
					JFileChooser fileChooser = new JFileChooser();
					int result = fileChooser.showOpenDialog(null);
					if (result == JFileChooser.APPROVE_OPTION) {

						boolean fileIsValid = true;
						selectedFile = fileChooser.getSelectedFile();
						String name = selectedFile.getName();
						if (name.indexOf('.') >= 0) {
							if (!name.substring(name.indexOf('.') + 1).equals("txt")) {
								JOptionPane.showMessageDialog(null, "Please select a .txt file.");
								fileIsValid = false;
							}
						}

						if (fileIsValid) {
							String fileContents = "";
							try {
								fileContents = Files.readString(Paths.get(selectedFile.getAbsolutePath()));
							} catch (IOException e) {
								JOptionPane.showMessageDialog(null, e.getMessage());
							}
							frame.setTitle(frame.getTitle() + " " + selectedFile.getAbsolutePath());
							txtAreaSource.setText(fileContents);
						}
					}
				}

			}

		});
		actions.add(chooseFile);

		JButton encrypt = new JButton("Encrypt");
		encrypt.setForeground(Color.GREEN);
		encrypt.setBackground(Color.BLACK);
		encrypt.setFont(new Font("Dialog", Font.BOLD, 14));
		encrypt.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				String text = txtAreaSource.getText();
				if (!text.equals("")) {
					String password = passwordField.getText();
					if (!password.equals("")) {
						String[] splittedText = text.split("\n");
						String encryptedText = "";
						for (String row : splittedText) {
							try {
								String encryptedRow = cipher(row, password, Operation.ENCRYPT);
								encryptedText += encryptedRow + "\n";
							} catch (CharOutOfRangeException e) {
								JOptionPane.showMessageDialog(null, e.getMessage());
							}
						}
						txtAreaResult.setText(encryptedText);
					} else {
						JOptionPane.showMessageDialog(null, "No password was provided.");
					}
				} else {
					JOptionPane.showMessageDialog(null, "There is nothing to encrypt.");
				}

			}
		});
		actions.add(encrypt);

		JButton decrypt = new JButton("Decrypt");
		decrypt.setForeground(Color.RED);
		decrypt.setBackground(Color.BLACK);
		decrypt.setFont(new Font("Dialog", Font.BOLD, 14));
		decrypt.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String encryptedText = txtAreaSource.getText();
				if (!encryptedText.equals("")) {
					String password = passwordField.getText();
					if (!password.equals("")) {
						String[] splittedEncryptedText = encryptedText.split("\n");
						String decryptedText = "";
						for (String row : splittedEncryptedText) {
							try {
								String decryptedRow = cipher(row, password, Operation.DECRYPT);
								decryptedText += decryptedRow + "\n";
							} catch (CharOutOfRangeException e) {
								JOptionPane.showMessageDialog(null, e.getMessage());
							}
						}
						txtAreaResult.setText(decryptedText);
					} else {
						JOptionPane.showMessageDialog(null, "No password was provided.");
					}
				} else {
					JOptionPane.showMessageDialog(null, "There is nothing to decrypt.");
				}

			}
		});
		actions.add(decrypt);

		JButton writeToFile = new JButton("Write to file");
		writeToFile.setFont(new Font("Dialog", Font.BOLD, 14));
		writeToFile.setForeground(Color.WHITE);
		writeToFile.setBackground(Color.BLACK);
		writeToFile.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (selectedFile == null) {
					JOptionPane.showMessageDialog(null, "No file selected.");
				} else {
					int choice = JOptionPane.showConfirmDialog(null,
							"WARNING\nAll contents on file TODO will be replaced. Do you really want to proceed? >:)");
					if (choice == JOptionPane.YES_OPTION) {
						try (FileWriter writer = new FileWriter(selectedFile)) {
							writer.write(txtAreaResult.getText());
							JOptionPane.showMessageDialog(null, "File contents were updated.");
						} catch (IOException e) {
							JOptionPane.showMessageDialog(null, e.getMessage());
						}
					}
				}
			}
		});
		actions.add(writeToFile);
	}

	public static String cipher(String text, String password, Operation operation) throws CharOutOfRangeException {

		if (containsCharsOutOfRange(password)) {
			throw new CharOutOfRangeException("Password contains one or more chars out of range.");
		}

		String rotationKey = generateRotationKey(text, password);
		String rotatedText = "";
		Direction direction = operation.equals(Operation.ENCRYPT) ? Direction.POSITIVE : Direction.NEGATIVE;

		for (int i = 0; i < text.length(); i++) {
			if (32 <= text.charAt(i) && text.charAt(i) <= 126) {
				rotatedText += (char) rotate(text.charAt(i), rotationKey.charAt(i), direction);
			} else {
				// If the char is not within ASCII [32, 126] do not process it
				rotatedText += text.charAt(i);
			}
		}

		return rotatedText;
	}

	public static boolean containsCharsOutOfRange(String text) {

		for (byte asciiValue : text.getBytes()) {
			if (asciiValue < 32 || asciiValue > 126) {
				return true;
			}
		}

		return false;
	}

	public static String generateRotationKey(String text, String password) {

		int timesPasswordFitsInText = text.length() / password.length();
		int remainder = text.length() % password.length();
		String rotationKey = "";

		for (int i = 0; i < timesPasswordFitsInText; i++) {
			rotationKey += password;
		}

		if (remainder > 0) {
			rotationKey += foldText(password, remainder);
		}

		return rotationKey;

	}

	// Adds or subtracts char2's ASCII value from char1 wraping the value within the
	// range [32, 126]
	public static byte rotate(char char1, char char2, Direction direction) {

		final byte inferiorLimit = 32; // DO NOT MODIFY [!]
		final byte superiorLimit = 126; // DO NOT MODIFY [!]
		final byte modulo = superiorLimit - inferiorLimit + 1; // DO NOT MODIFY [!]

		// Positive rotation (encryption)
		switch (direction) {
		case POSITIVE:
			return (byte) (((char1 - inferiorLimit + char2) % modulo) + inferiorLimit);
		case NEGATIVE:
			// Negative rotation (decryption)
			// Determine how many times the modulo should be added
			// TODO determine better variable names
			int negativeNormalized = char1 - inferiorLimit - char2;
			int timesModuloShouldBeAdded = (int) Math.ceil(-negativeNormalized / (double) modulo);
			return (byte) (((negativeNormalized + (modulo * timesModuloShouldBeAdded)) % modulo) + inferiorLimit);
		default:
			return 0;
		}

	}

	// Reduces the text by using the excess part as a rotation key
	public static String foldText(String text, int desiredLength) {

		String subText = text.substring(0, desiredLength);
		String subRotationKey = text.substring(desiredLength, text.length());

		try {
			return cipher(subText, subRotationKey, Operation.ENCRYPT);
		} catch (CharOutOfRangeException e) {
			e.printStackTrace();
		}

		return null;

	}

	public static class CharOutOfRangeException extends Exception {

		public CharOutOfRangeException(String errorMessage) {
			super(errorMessage);
		}

	}

	public enum Direction {
		POSITIVE, NEGATIVE;
	}

	public enum Operation {
		ENCRYPT, DECRYPT;
	}

	public class FocusTraversalOnArray extends FocusTraversalPolicy {
		private final Component m_Components[];

		////////////////////////////////////////////////////////////////////////////
		//
		// Constructor
		//
		////////////////////////////////////////////////////////////////////////////
		public FocusTraversalOnArray(Component components[]) {
			m_Components = components;
		}

		////////////////////////////////////////////////////////////////////////////
		//
		// Utilities
		//
		////////////////////////////////////////////////////////////////////////////
		private int indexCycle(int index, int delta) {
			int size = m_Components.length;
			int next = (index + delta + size) % size;
			return next;
		}

		private Component cycle(Component currentComponent, int delta) {
			int index = -1;
			loop: for (int i = 0; i < m_Components.length; i++) {
				Component component = m_Components[i];
				for (Component c = currentComponent; c != null; c = c.getParent()) {
					if (component == c) {
						index = i;
						break loop;
					}
				}
			}
			// try to find enabled component in "delta" direction
			int initialIndex = index;
			while (true) {
				int newIndex = indexCycle(index, delta);
				if (newIndex == initialIndex) {
					break;
				}
				index = newIndex;
				//
				Component component = m_Components[newIndex];
				if (component.isEnabled() && component.isVisible() && component.isFocusable()) {
					return component;
				}
			}
			// not found
			return currentComponent;
		}

		////////////////////////////////////////////////////////////////////////////
		//
		// FocusTraversalPolicy
		//
		////////////////////////////////////////////////////////////////////////////
		@Override
		public Component getComponentAfter(Container container, Component component) {
			return cycle(component, 1);
		}

		@Override
		public Component getComponentBefore(Container container, Component component) {
			return cycle(component, -1);
		}

		@Override
		public Component getFirstComponent(Container container) {
			return m_Components[0];
		}

		@Override
		public Component getLastComponent(Container container) {
			return m_Components[m_Components.length - 1];
		}

		@Override
		public Component getDefaultComponent(Container container) {
			return getFirstComponent(container);
		}
	}

}
