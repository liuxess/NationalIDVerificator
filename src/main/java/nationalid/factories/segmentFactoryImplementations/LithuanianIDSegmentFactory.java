package nationalid.factories.segmentFactoryImplementations;

import java.util.ArrayList;
import java.util.List;

import nationalid.interfaces.IDSegmentFactory;
import nationalid.models.NationalID;
import nationalid.models.Segments.NationalIDSegment;
import nationalid.models.Segments.Lithuanian.BirthDateSegment;
import nationalid.models.Segments.Lithuanian.ControlNumberSegment;
import nationalid.models.Segments.Lithuanian.GenderSegment;
import nationalid.models.Segments.Lithuanian.RandomNumberSegment;

public class LithuanianIDSegmentFactory implements IDSegmentFactory {

    int numberOfSegments = 4;

    /*
     * (non-Javadoc)
     * 
     * @see interfaces.IDSegmentFactory#GenerateListOfIDSegments(int)
     */
    @Override
    public List<NationalIDSegment> GenerateListOfIDSegments(NationalID ID) {
        List<NationalIDSegment> segmentList = new ArrayList<NationalIDSegment>();

        segmentList.add(new GenderSegment(ID));
        segmentList.add(new BirthDateSegment(ID));
        segmentList.add(new RandomNumberSegment(ID));
        segmentList.add(new ControlNumberSegment(ID));

        return segmentList;
    }

    @Override
    public int getNumberOfSegments() {
        return numberOfSegments;
    }

}
