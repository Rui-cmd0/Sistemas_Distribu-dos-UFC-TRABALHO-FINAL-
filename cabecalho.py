from json import *

class Cabecalho:

    def __init__(self, messageType, objectreference, methodId, arguments, requestId):
        self.messageType = messageType
        self.objectreference = objectreference
        self.methodId = methodId
        self.arguments = arguments
        self.requestId = requestId

    def to_json(self):
        headerData = {
            "messageType": self.messageType,
            "objectreference": self.objectreference,
            "methodId": self.methodId,
            "arguments": self.arguments,
            "requestId": self.requestId
        }
        return dumps(headerData)

    @classmethod
    def from_json(cls, json_str):
        headerData = loads(json_str)
        return cls(
            headerData["messageType"],
            headerData["objectreference"],
            headerData["methodId"],
            headerData["arguments"],
            headerData.get("requestId", 0)
        )