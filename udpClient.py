from socket import *

class UDPClient:
    def __init__(self, host, port):
        self.host = host
        self.port = port
        self.client = socket(AF_INET, SOCK_DGRAM)
        self.client.settimeout(2)

    def sendRequest(self, obj):
        self.client.sendto(obj.encode('utf-8'), (self.host, self.port))

    def getResponse(self):
        try:
            data, addr = self.client.recvfrom(1024)
            return data.decode('utf-8')
        except timeout:
            return None
    
    def close(self):
        self.client.close()
