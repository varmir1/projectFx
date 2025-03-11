package com.example.projectfx;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;

public class PasswordGeneratorController {
    //declaracion de los componentes de la interfaz
    @FXML
    private TextField passwordField;
    @FXML
    private ComboBox<Integer> lengthComboBox;
    @FXML
    private CheckBox uppercaseCheckBox, lowercaseCheckBox, digitsCheckBox, specialCharCheckBox;
    @FXML
    private Label strengthLabel;
    //declaracion del generador de contraseñas
    private PasswordGenerator passwordGenerator = new PasswordGenerator();

    public void initialize() {
        lengthComboBox.getItems().addAll(8, 10, 12, 16);
        lengthComboBox.setValue(12);

        //actualizar el nivel de seguridad
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
    //generar la contraseña
    @FXML
    private void onGeneratePassword() {
        int length = lengthComboBox.getValue();
        boolean includeUppercase = uppercaseCheckBox.isSelected();
        boolean includeLowercase = lowercaseCheckBox.isSelected();
        boolean includeDigits = digitsCheckBox.isSelected();
        boolean includeSpecialChars = specialCharCheckBox.isSelected();

        // Si no hay ninguna opción seleccionada, seleccionar todas
        if (!includeUppercase && !includeLowercase && !includeDigits && !includeSpecialChars) {
            includeUppercase = includeLowercase = includeDigits = includeSpecialChars = true;
            // Actualizar visualmente los checkboxes
            uppercaseCheckBox.setSelected(true);
            lowercaseCheckBox.setSelected(true);
            digitsCheckBox.setSelected(true);
            specialCharCheckBox.setSelected(true);
        }

        String password;
        boolean isValid;
        
        do {
            password = passwordGenerator.generatePassword(length, includeUppercase, includeLowercase, includeDigits, includeSpecialChars);
            
            // Verificar que la contraseña contiene al menos un carácter de cada tipo seleccionado
            isValid = true;
            if (includeUppercase && !PasswordValidator.hasUpperCase(password)) isValid = false;
            if (includeLowercase && !PasswordValidator.hasLowerCase(password)) isValid = false;
            if (includeDigits && !PasswordValidator.hasNumber(password)) isValid = false;
            if (includeSpecialChars && !PasswordValidator.hasSpecialChar(password)) isValid = false;
            
        } while (!isValid);

        passwordField.setText(password);
        updateSecurityLevel();

        // Validar si la contraseña cumple todos los requisitos
        if (PasswordValidator.validatePassword(password)) {
            strengthLabel.setText("PASSWORD TOO STRONG");
            strengthLabel.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
        } else {
            strengthLabel.setText("");
        }
    }
    // funcion paracopiar la contraseña
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