package org.example;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.sql.SQLException;
import java.util.TreeMap;

public class UDPServer {
    private DatagramSocket serverSocket;
    private Database db;
    private TreeMap<String, String> lastRequest;

    public UDPServer(int port) throws IOException, SQLException {
        this.serverSocket = new DatagramSocket(port);
        this.db = new Database();
        this.lastRequest = new TreeMap<>();
    }

    public void getRequest() throws IOException {
        byte[] receiveData = new byte[1024];
        DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
        serverSocket.receive(receivePacket);

        String data = new String(receivePacket.getData(), 0, receivePacket.getLength(), "UTF-8");

        int PORT = receivePacket.getPort();
        String requestId = data.substring(data.length() - 2, data.length() - 1);
        String ADDR = receivePacket.getAddress().toString();
        ADDR = ADDR.substring(1);
        int methodId = Integer.parseInt(data.substring(59, 60));
        String msg = "Recebido: " + data + " do cliente com IP: " + ADDR + " e PORTA: " + PORT;

        System.out.println(msg);

        if (isDuplicateMessage(PORT, requestId, ADDR, methodId)) {
            System.out.println("Mensagem duplicada. Reenviando última requisição...");
            sendLastResponse(PORT, ADDR);
            return;
        }

        lastRequest.put("PORT", Integer.toString(PORT));
        lastRequest.put("requestId", requestId);
        lastRequest.put("ADDR", ADDR);
        lastRequest.put("methodId", Integer.toString(methodId));

        Despachante despachante = new Despachante(db);
        Cabecalho responseCabecalho = despachante.invoke(data);

        lastRequest.put("lastResponse", responseCabecalho.toJson());

        //simula um atraso de 3 segundos no envio da resposta
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        sendResponse(responseCabecalho, receivePacket);
    }

    private boolean isDuplicateMessage(int PORT, String requestId, String ADDR, int methodId) {
        return lastRequest.containsKey("PORT") && lastRequest.containsKey("requestId") &&
                lastRequest.containsKey("ADDR") && lastRequest.containsKey("methodId") &&
                lastRequest.get("PORT").equals(Integer.toString(PORT)) &&
                lastRequest.get("requestId").equals(requestId) &&
                lastRequest.get("ADDR").equals(ADDR) &&
                lastRequest.get("methodId").equals(Integer.toString(methodId));
    }

    private void sendLastResponse(int PORT, String ADDR) throws IOException {
        String lastResponse = lastRequest.get("lastResponse");
        byte[] sendData = lastResponse.getBytes("UTF-8");
        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, InetAddress.getByName(ADDR), PORT);
        serverSocket.send(sendPacket);
        System.out.println("Enviado: " + lastResponse);
    }

    private void sendResponse(Cabecalho responseCabecalho, DatagramPacket receivePacket) throws IOException {
        String responseStr = responseCabecalho.toJson();
        byte[] sendData = responseStr.getBytes("UTF-8");
        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, receivePacket.getAddress(), receivePacket.getPort());
        serverSocket.send(sendPacket);
        System.out.println("Enviado: " + responseStr);
    }

    public static void main(String[] args) throws Exception {
        UDPServer server = new UDPServer(8080);
        System.out.println("Servidor UDP iniciado. Aguardando conexões...");
        while (true) {
            server.getRequest();
        }
    }
}