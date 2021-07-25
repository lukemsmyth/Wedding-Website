package ie.lukeandella.wedding.models;

import java.util.ArrayList;
import java.util.List;

public class ReservationRequest {

    private Long giftId;
    private Integer percentage;

    public ReservationRequest() {
    }

    public ReservationRequest(Long giftId, Integer percentage) {
        this.giftId = giftId;
        this.percentage = percentage;
    }

    public Long getGiftId() {
        return giftId;
    }

    public void setGiftId(Long giftId) {
        this.giftId = giftId;
    }

    public Integer getPercentage() {
        return percentage;
    }

    public void setPercentage(Integer percentage) {
        this.percentage = percentage;
    }
}
