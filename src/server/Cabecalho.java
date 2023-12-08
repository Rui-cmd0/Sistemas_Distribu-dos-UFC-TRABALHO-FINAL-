package org.example;
import org.json.JSONObject;

public class Cabecalho {
    private int messageType;
    private String objectReference;
    private int methodId;
    private String arguments;

    public Cabecalho(int messageType, String objectReference, int methodId, String arguments) {
        this.messageType = messageType;
        this.objectReference = objectReference;
        this.methodId = methodId;
        this.arguments = arguments;
    }

    public String toJson() {
        JSONObject headerData = new JSONObject();
        headerData.put("messageType", messageType);
        headerData.put("objectreference", objectReference);
        headerData.put("methodId", methodId);
        headerData.put("arguments", arguments);
        return headerData.toString();
    }

    public static Cabecalho fromJson(String jsonStr) {
        JSONObject headerData = new JSONObject(jsonStr);
        return new Cabecalho(
                headerData.getInt("messageType"),
                headerData.getString("objectreference"),
                headerData.getInt("methodId"),
                headerData.get("arguments").toString()
        );
    }

    public String getObjectReference() {
        return objectReference;
    }

    public int getMethodId() {
        return methodId;
    }

    public String getArguments() {
        return arguments;
    }
}