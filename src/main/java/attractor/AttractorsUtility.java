package attractor;

import interfaces.attractor.AttractorInfo;
import interfaces.attractor.LabelledOrderedAttractor;
import interfaces.state.State;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class AttractorsUtility {
    private AttractorsUtility(){}

    public static <T extends State> List<LabelledOrderedAttractor<T>> fromInfoToAttractors(Collection<AttractorInfo<T>> infoCollection) {
        infoCollection.stream().forEach(x -> Collections.sort(x.getStates())); //ordino gli stati dell'attroctorInfo

        List<AttractorInfo<T>> ordered = infoCollection.stream()
                .sorted((x, y) -> x.getStates().get(0).compareTo(y.getStates().get(0)))
                .collect(Collectors.toList());
        List<LabelledOrderedAttractor<T>> temp = new ArrayList<>();
        Integer counter = 1;
        for (AttractorInfo<T> aInfo : ordered) {
            temp.add(new AttractorImpl<>(aInfo, counter));
            counter++;
        }
        return new ImmutableListImpl<>(temp);
    }
}
