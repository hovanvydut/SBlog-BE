package hovanvydut.apiblog.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author hovanvydut
 * Created on 7/28/21
 */

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class ExpectedSizeImage {
    private Integer width;
    private Integer height;

    public void setWidth(int width) {
        if (width <= 0) {
            throw new RuntimeException("Invalid width");
        }

        this.width = width;
    }

    public void setHeight(int width) {
        if (width <= 0) {
            throw new RuntimeException("Invalid height");
        }

        this.height = height;
    }
}
