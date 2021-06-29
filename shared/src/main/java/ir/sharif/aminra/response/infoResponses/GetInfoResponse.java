package ir.sharif.aminra.response.infoResponses;

import ir.sharif.aminra.response.Response;
import ir.sharif.aminra.response.ResponseVisitor;

public class GetInfoResponse extends Response {
    String username;
    int numberOfWins, numberOfLoses;

    public GetInfoResponse(String username, int numberOfWins, int numberOfLoses) {
        this.username = username;
        this.numberOfWins = numberOfWins;
        this.numberOfLoses = numberOfLoses;
    }

    @Override
    public void visit(ResponseVisitor responseVisitor) {
        responseVisitor.applyInfo(username, numberOfWins, numberOfLoses);
    }
}
