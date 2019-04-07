package com.aeroways.ragnarok.aeroways.UI.flight_booking.data.polling;

import com.google.gson.annotations.SerializedName;

public class BookingDetailsLink {
    @SerializedName("Body")
    private final String body;
    @SerializedName("Method")
    private final String method;
    @SerializedName("Uri")
    private final String uri;

    public BookingDetailsLink(String body, String method, String uri) {
        this.body = body;
        this.method = method;
        this.uri = uri;
    }

    public String getBody() {
        return body;
    }

    public String getMethod() {
        return method;
    }

    public String getUri() {
        return uri;
    }
}
