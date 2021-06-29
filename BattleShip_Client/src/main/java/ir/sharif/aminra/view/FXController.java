package ir.sharif.aminra.view;

import ir.sharif.aminra.listeners.RequestListener;

public abstract class FXController {

    public RequestListener requestListener;

    public void setRequestListener(RequestListener requestListener) {
        this.requestListener = requestListener;
    }
}
