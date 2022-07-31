package nationalid;

import java.util.ArrayList;
import java.util.List;

import nationalid.enums.NationalIDSegmentType;
import nationalid.factories.NationalIDSegmentFactory;
import nationalid.models.NationalID;
import nationalid.models.Segments.NationalIDSegmentBase;

public class SegmentedNationalID {
    private NationalID ID;
    private List<NationalIDSegmentBase> nationalIDSegments;

    public SegmentedNationalID(long ID) {
        setID(ID);
    }

    public SegmentedNationalID(NationalID ID) {
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

    private void setNationalIDSegments(List<NationalIDSegmentBase> segmentList) {
        nationalIDSegments = segmentList;
    }

    public Boolean VerifyIntegrity() {
        for (NationalIDSegmentBase segment : nationalIDSegments) {
            if (!segment.Verify()) {
                return false;
            }
        }

        return true;
    }

    public NationalIDSegmentBase getSegment(NationalIDSegmentType type) throws Exception {
        for (NationalIDSegmentBase segment : nationalIDSegments) {
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
