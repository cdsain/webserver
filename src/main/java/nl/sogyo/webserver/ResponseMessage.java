package nl.sogyo.webserver;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class ResponseMessage implements Response {
    private HttpStatusCode status;
    private ZonedDateTime date;
    private String content;

    public ResponseMessage() {

        this.setStatus(HttpStatusCode.OK);
        this.date = ZonedDateTime.now();
    }

    public void generateContent (RequestMessage request) {
        StringBuilder requestContent = new StringBuilder();
        requestContent.append("<html><body>");
        requestContent.append("You did an HTTP " + request.getHTTPMethod() + " request and you requested the following resource: " + request.getResourcePath() + "<br/><br/>");
        requestContent.append("The following headerparameters were passed:<br/>");
        for (String name: request.getHeaderParameterNames()) {
            requestContent.append(name + ": " + request.getHeaderParameterValue(name)+"<br/>");
        }
        requestContent.append("<br/>The following parameters were passed:<br/>");
        for (String name : request.getParameterNames()) {
            requestContent.append(name + ": " + request.getParameterValue(name) + "<br/>");
        }
        requestContent.append("</html></body>");

        StringBuilder responseContent = new StringBuilder();

        responseContent.append("HTTP/1.1 " + status.getCode() + " " + status.getDescription() + "\n");
        responseContent.append("Date: " + date.format(DateTimeFormatter.RFC_1123_DATE_TIME) + "\n");
        responseContent.append("Server: myOwnServer\n");
        responseContent.append("Connection: close\n");
        responseContent.append("Content-Type: text/html; charset=UTF-8\n");
        responseContent.append("Content-Length: " + requestContent.length() + "\n");
        responseContent.append("\n");
        responseContent.append(requestContent);

        String contentResponse = responseContent.toString();
        this.setContent(contentResponse);
    }

    public HttpStatusCode getStatus() {
        return status;
    }

    public void setStatus(HttpStatusCode status) {
        this.status = status;
    }

    public ZonedDateTime getDate() {
        return date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
