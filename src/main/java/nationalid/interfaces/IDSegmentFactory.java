package nationalid.interfaces;

import java.util.List;

import nationalid.models.NationalID;
import nationalid.models.Segments.NationalIDSegment;

public interface IDSegmentFactory {

    List<NationalIDSegment> GenerateListOfIDSegments(NationalID ID);

    int getNumberOfSegments();

}
