package uk.gov.service.notify;

import org.json.JSONObject;

import java.util.Optional;
import java.util.UUID;

public class SendLetterResponse {
    private UUID notificationId;
    private String reference;
    private UUID templateId;
    private int templateVersion;
    private String templateUri;
    private String body;
    private String subject;


    public SendLetterResponse(String response) {
        JSONObject data = new JSONObject(response);
        notificationId = UUID.fromString(data.getString("id"));
        reference = data.isNull("reference") ? null : data.getString("reference");
        JSONObject content = data.getJSONObject("content");
        body = tryToGetString(content, "body");
        subject = tryToGetString(content, "subject");
        JSONObject template = data.getJSONObject("template");
        templateId = UUID.fromString(template.getString("id"));
        templateVersion = template.getInt("version");
        templateUri = template.getString("uri");
    }

    public UUID getNotificationId() {
        return notificationId;
    }

    public Optional<String> getReference() {
        return Optional.ofNullable(reference);
    }

    public UUID getTemplateId() {
        return templateId;
    }

    public int getTemplateVersion() {
        return templateVersion;
    }

    public String getTemplateUri() {
        return templateUri;
    }

    public String getBody() {
        return body;
    }

    public String getSubject() {
        return subject;
    }

    @Override
    public String toString() {
        return "SendLetterResponse{" +
                "notificationId=" + notificationId +
                ", reference=" + reference +
                ", templateId=" + templateId +
                ", templateVersion=" + templateVersion +
                ", templateUri='" + templateUri + '\'' +
                ", body='" + body + '\'' +
                ", subject='" + subject +
                '}';
    }

    private String tryToGetString(JSONObject jsonObj, String key)
    {
        if (jsonObj.has(key))
        {
            if(jsonObj.opt(key).toString() == "null")
            {
                return null;
            }
            else
            {
                return jsonObj.opt(key).toString();
            }
        }

        return null;
    }
}
