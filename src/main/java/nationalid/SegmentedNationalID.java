package nationalid;

import java.util.ArrayList;
import java.util.List;

import nationalid.enums.NationalIDSegmentType;
import nationalid.factories.NationalIDSegmentFactory;
import nationalid.models.NationalID;
import nationalid.models.Segments.NationalIDSegment;

public class SegmentedNationalID {
    NationalID ID;
    List<NationalIDSegment> nationalIDSegments;

    public SegmentedNationalID(long ID) {
        setID(ID);
    }

    private void setID(long ID) {
        setID(new NationalID(ID));
    }

    private void setID(NationalID ID) {
        this.ID = ID;
        setNationalIDSegments(NationalIDSegmentFactory.GenerateListOfIDSegments(ID));
    }

    public NationalID getID() {
        return ID;
    }

    private void setNationalIDSegments(List<NationalIDSegment> segmentList) {
        nationalIDSegments = segmentList;
    }

    public Boolean VerifyIntegrity() {
        for (NationalIDSegment segment : nationalIDSegments) {
            if (!segment.Verify()) {
                return false;
            }
        }

        return true;
    }

    public NationalIDSegment getSegment(NationalIDSegmentType type) throws Exception {
        for (NationalIDSegment segment : nationalIDSegments) {
            if (segment.isOfType(type))
                return segment;
        }

        throw new Exception("This ID does not contain such a segment");
    }

    public List<String> getProblemList() {
        List<String> problemList = new ArrayList<>();
        nationalIDSegments.forEach(segment -> {
            if (!segment.Verify())
                problemList.add(segment.getProblemMessage());
        });
        return problemList;
    }

}
