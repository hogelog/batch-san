package org.hogel.batchsan;

public class BasicJobResult implements JobResult {
    private final boolean success;

    private final String body;

    public static BasicJobResult success(String body) {
        return new BasicJobResult(true, body);
    }

    public static BasicJobResult failure(String body) {
        return new BasicJobResult(false, body);
    }

    public BasicJobResult(boolean success, String body) {
        this.success = success;
        this.body = body;
    }

    @Override
    public boolean isSuccess() {
        return success;
    }

    @Override
    public String getBody() {
        return body;
    }
}
