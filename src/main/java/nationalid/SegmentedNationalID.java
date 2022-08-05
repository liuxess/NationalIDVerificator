package nationalid;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import nationalid.enums.NationalIDSegmentType;
import nationalid.factories.NationalIDSegmentFactory;
import nationalid.models.NationalID;
import nationalid.models.Segments.NationalIDSegmentBase;

/**
 * Stores and Segments the National ID which allows for part-by-part
 * verification
 * 
 * @see nationalid.models.NationalID
 * @see nationalid.models.Segments.NationalIDSegmentBase
 */
public class SegmentedNationalID {
    private NationalID nationalID;
    private List<NationalIDSegmentBase> nationalIDSegments;

    /**
     * @param ID that this Segmented National ID is based upon
     */
    public SegmentedNationalID(String ID) {
        setNationalID(ID);
    }

    /**
     * @param nationalID to segment
     */
    public SegmentedNationalID(NationalID nationalID) {
        setNationalID(nationalID);
    }

    // region: public methods

    /**
     * Compiles a list of problems. If NationalID has no issues, checks for issues
     * on each segment
     * 
     * @return List of issues
     */
    public List<String> getProblemList() {
        List<String> problemList = new ArrayList<>();
        Optional<String> globalIssue = nationalID.getProblemMessage();

        if (globalIssue.isPresent()) {
            problemList.add(globalIssue.get());
            return problemList;
        }

        nationalIDSegments.forEach(segment -> {
            Optional<String> problem = segment.getProblemMessage();

            if (!problem.isEmpty()) {
                problemList.add(problem.get());
            }
        });
        return problemList;
    }

    /**
     * Validates against the national ID and then against each of the segments
     * 
     * @return whether the Segmented National ID has valid integrity
     */
    public Boolean VerifyIntegrity() {
        if (!nationalID.Validate())
            return false;

        for (NationalIDSegmentBase segment : nationalIDSegments) {
            if (!segment.Verify()) {
                return false;
            }
        }

        return true;
    }

    /**
     * @param type of segment to retun
     * @return segment of the type; If doesn't exist, retuns empty
     */
    public Optional<NationalIDSegmentBase> getSegment(NationalIDSegmentType type) {
        for (NationalIDSegmentBase segment : nationalIDSegments) {
            if (segment.isOfType(type))
                return Optional.of(segment);
        }

        return Optional.empty();
    }

    // endregion

    // region: Getters & Setters

    public NationalID getNationalID() {
        return nationalID;
    }

    /**
     * @param ID for the NationalID to segment
     */
    private void setNationalID(String ID) {
        setNationalID(new NationalID(ID));
    }

    /**
     * @param ID to be segmented
     */
    private void setNationalID(NationalID ID) {
        this.nationalID = ID;
        setNationalIDSegments(NationalIDSegmentFactory.GenerateListOfIDSegments(ID));
    }

    /**
     * @param segmentList to store
     */
    private void setNationalIDSegments(List<NationalIDSegmentBase> segmentList) {
        nationalIDSegments = segmentList;
    }

    // endregion
}
