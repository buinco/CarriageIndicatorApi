package de.deutschebahn.traincarriageindicator;

import de.db.train.carriage.indicator.services.CarriageIndicatorService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

public class CarriageIndicatorServiceTest {

    @InjectMocks
    private CarriageIndicatorService carriageIndicatorService;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        carriageIndicatorService = Mockito.mock(CarriageIndicatorService.class);
    }

    @Test
    public void getTrackSectionTest() {
        List<String> expected = Arrays.asList("B");
        String stationShortcodeInput = "FF";
        int trainNumberInput = 2310;
        int wagonNumberInput = 10;

        Mockito.when(carriageIndicatorService.getTrackSection(stationShortcodeInput, trainNumberInput, wagonNumberInput)).thenReturn(expected);

        List<String> actual = carriageIndicatorService.getTrackSection(stationShortcodeInput, trainNumberInput, wagonNumberInput);
        Assertions.assertEquals(expected, actual);
        Assertions.assertFalse(actual.isEmpty());
        Assertions.assertEquals(1, actual.size());
    }
}
