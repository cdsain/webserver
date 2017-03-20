package nl.sogyo.webserver;

public class HeaderParameter {
    private String nameOfParameter;
    private String value = "";

    public String getNameOfParameter() {
        return this.nameOfParameter;
    }

    public String getValue() {
        return this.value;
    }

    public HeaderParameter (String input) {
        String[] splittedLines = input.split(": ");
        this.nameOfParameter = splittedLines[0];
        if(splittedLines.length == 2){
            this.value = splittedLines[1];
        }
    }
}
