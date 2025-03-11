package com.example.projectfx;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;

public class PasswordGeneratorController {
    @FXML
    private TextField passwordField;
    @FXML
    private ComboBox<Integer> lengthComboBox;
    @FXML
    private CheckBox uppercaseCheckBox, lowercaseCheckBox, digitsCheckBox, specialCharCheckBox;

    private PasswordGenerator passwordGenerator = new PasswordGenerator();

    public void initialize() {
        lengthComboBox.getItems().addAll(8, 10, 12, 16);
        lengthComboBox.setValue(12);

        // Añadir listeners a todos los checkboxes
        uppercaseCheckBox.selectedProperty().addListener((obs, oldVal, newVal) -> updateSecurityLevel());
        lowercaseCheckBox.selectedProperty().addListener((obs, oldVal, newVal) -> updateSecurityLevel());
        digitsCheckBox.selectedProperty().addListener((obs, oldVal, newVal) -> updateSecurityLevel());
        specialCharCheckBox.selectedProperty().addListener((obs, oldVal, newVal) -> updateSecurityLevel());
    }

    private void updateSecurityLevel() {
        // Contar cuántos checkboxes están seleccionados
        int selectedCount = 0;
        if (uppercaseCheckBox.isSelected()) selectedCount++;
        if (lowercaseCheckBox.isSelected()) selectedCount++;
        if (digitsCheckBox.isSelected()) selectedCount++;
        if (specialCharCheckBox.isSelected()) selectedCount++;

        // Limpiar clases anteriores
        passwordField.getStyleClass().removeAll(
                "security-level-1", "security-level-2", "security-level-3", "security-level-4"
        );

        // Aplicar la clase correspondiente
        if (selectedCount > 0) {
            passwordField.getStyleClass().add("security-level-" + selectedCount);
        }
    }

    @FXML
    private void onGeneratePassword() {
        int length = lengthComboBox.getValue();
        boolean includeUppercase = uppercaseCheckBox.isSelected();
        boolean includeLowercase = lowercaseCheckBox.isSelected();
        boolean includeDigits = digitsCheckBox.isSelected();
        boolean includeSpecialChars = specialCharCheckBox.isSelected();

        String password = passwordGenerator.generatePassword(length, includeUppercase, includeLowercase, includeDigits, includeSpecialChars);
        passwordField.setText(password);
        updateSecurityLevel();
    }

    @FXML
    private void onCopyPassword() {
        String password = passwordField.getText();

        if (password != null && !password.isEmpty()) {

            Clipboard clipboard = Clipboard.getSystemClipboard();
            ClipboardContent content = new ClipboardContent();
            content.putString(password);
            clipboard.setContent(content);

            System.out.println("Password copied: " + password);
        }
    }
}