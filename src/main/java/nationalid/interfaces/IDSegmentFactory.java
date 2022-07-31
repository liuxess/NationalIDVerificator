package nationalid.interfaces;

import java.util.List;

import nationalid.models.NationalID;
import nationalid.models.Segments.NationalIDSegmentBase;

public interface IDSegmentFactory {

    List<NationalIDSegmentBase> GenerateListOfIDSegments(NationalID ID);

    int getNumberOfSegments();

}
