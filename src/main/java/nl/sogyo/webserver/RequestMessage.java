package nl.sogyo.webserver;

import java.util.ArrayList;

public class RequestMessage implements Request {
    private String resourcePath = "";
    private HttpMethod httpMethod;
    private ArrayList<Parameter> parameters = new ArrayList<>();
    private ArrayList<HeaderParameter> headParameters = new ArrayList<>();

    public void handleInput(String method) {
        if (method.toLowerCase().contains("get")||method.toLowerCase().contains("post")){
            this.setHttpMethod(method);
        }
        else if (method.contains(": ")) {
            this.headParameters.add(new HeaderParameter(method));
        }
    }

    public void setHttpMethod(String method) {
        if (method.toLowerCase().contains("get")) {
            this.httpMethod = HttpMethod.GET;
        }
        else if (method.toLowerCase().contains("post")) {
            this.httpMethod = HttpMethod.POST;
        }
        String[] resource = method.split("\\?");
        this.resourcePath = "/" + resource[0].split("/")[1];
        if (resource.length == 2) {
            String parameterSplit = resource[1].split(" ")[0];
            for (String param : parameterSplit.split("\\&")) {
                this.parameters.add(new Parameter(param));
            }
        }
    }

    public HttpMethod getHTTPMethod() {
        return this.httpMethod;
    }
    public String getResourcePath() {
        return this.resourcePath;
    }
    public ArrayList<String> getHeaderParameterNames() {
        ArrayList<String> headerNames = new ArrayList<>();
        for (HeaderParameter headerParameter: this.headParameters) {
            headerNames.add(headerParameter.getNameOfParameter());
        }
        return headerNames;
    }
    public String getHeaderParameterValue(String name) {
        for (HeaderParameter headerParameter: this.headParameters) {
            if (name.equals(headerParameter.getNameOfParameter())){
                return headerParameter.getValue();
            }
        }
        return null;
    }
    public ArrayList<String> getParameterNames() {
        ArrayList<String> parameterNames = new ArrayList<>();
        for (Parameter param: this.parameters) {
            parameterNames.add(param.getNameOfParameter());
        }
        return parameterNames;
    }
    public String getParameterValue(String name) {
        for (Parameter param: this.parameters) {
            if (name.equals(param.getNameOfParameter())){
                return param.getValue();
            }
        }
        return null;
    }
}
