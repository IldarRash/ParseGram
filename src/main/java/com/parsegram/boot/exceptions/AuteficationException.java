package com.parsegram.boot.exceptions;

public class AuteficationException extends RuntimeException {

    private final String id;

    public AuteficationException(String id) {
        super(id);
        this.id = id;
    }

    public AuteficationException(ID id) {
        super(id.getBaseText());
        this.id = id.name();
    }

    public AuteficationException(String id, String message) {
        super(message);
        this.id = id;
    }

    public enum ID {
        DATA_NOT_VALID("Вы ввели не верный логи или пароль"),
        UNAUTHORIZ("Нужно войти в систему"),
        ;

        private final String baseText;

        ID(String baseText) {
            this.baseText = baseText;
        }

        public String getBaseText() {
            return baseText;
        }
    }
}
