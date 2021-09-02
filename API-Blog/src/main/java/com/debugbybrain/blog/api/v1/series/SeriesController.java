package hovanvydut.apiblog.api.v1.series;

import org.springframework.web.bind.annotation.*;

/**
 * @author hovanvydut
 * Created on 8/29/21
 */

@RestController
@RequestMapping("/api/v1")
public class SeriesController {

    @GetMapping("/series")
    public void getAllSeries() {

    }

    @PostMapping("/series")
    public void createSeries() {

    }

    @PutMapping("/series/{id}")
    public void updateSeries() {

    }

    @DeleteMapping("/series/{id}")
    public void deleteSeries() {

    }
}
