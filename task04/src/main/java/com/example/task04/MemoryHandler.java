package com.example.task04;

public class MemoryHandler implements MessageHandler {

    private final MessageHandler targetHandler;
    private final int bufferSize;
    private String[] buffer;

    public MemoryHandler(MessageHandler targetHandler, int bufferSize) {
        this.targetHandler = targetHandler;
        this.bufferSize = bufferSize;
        this.buffer = new String[0];
    }

    @Override
    public void handle(String message) {
        int currentLength = buffer.length;

        if (currentLength >= bufferSize) {
            flush();
            currentLength = 0;
        }

        String[] newBuffer = new String[currentLength + 1];
        System.arraycopy(buffer, 0, newBuffer, 0, currentLength);
        newBuffer[currentLength] = message;
        buffer = newBuffer;

        if (buffer.length >= bufferSize) {
            flush();
        }
    }

    public void flush() {
        if (buffer.length > 0) {

            for (String loggedMessage : buffer) {
                targetHandler.handle(loggedMessage);
            }

            buffer = new String[0];
        }
    }
}
