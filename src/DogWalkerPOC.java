import components.map.Map;
import components.map.Map1L;
import components.sequence.Sequence;
import components.sequence.Sequence1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

public class DogWalkerPOC {

    private Map<String, Sequence<Integer>> locationAndTime;
    private Map<Integer, String> currentWalk;

    public DogWalkerPOC() {
        this.locationAndTime = new Map1L<>();
        this.currentWalk = new Map1L<>();
    };

    public void startWalk(int currentTime, String location) {
        if (this.currentWalk.size() == 0) {
            this.currentWalk = new Map1L<>();
            this.currentWalk.add(currentTime, location);
        }
    }

    public void endWalk(int currentTime) {
        Map.Pair<Integer, String> temp = this.currentWalk.removeAny();
        if (this.locationAndTime.hasKey(temp.value())) {
            this.locationAndTime.value(temp.value()).add(0,
                    currentTime - temp.key());
        } else {
            Sequence<Integer> seq = new Sequence1L<>();
            seq.add(0, currentTime - temp.key());
            this.locationAndTime.add(temp.value(), seq);
        }
    }

    public String favoriteLocation() {
        String favorite = "";
        int maxVisits = 0;

        for (Map.Pair<String, Sequence<Integer>> entry : this.locationAndTime) {
            String location = entry.key();
            Sequence<Integer> durations = entry.value();

            int visitCount = durations.length();

            if (visitCount > maxVisits) {
                favorite = location;
                maxVisits = visitCount;
            }
        }

        return favorite;
    }

    public int totalWalkDuration() {
        int totalDuration = 0;

        for (Map.Pair<String, Sequence<Integer>> entry : this.locationAndTime) {
            Sequence<Integer> durations = entry.value();

            for (int duration : durations) {
                totalDuration += duration;
            }
        }

        return totalDuration;
    }

    public static void main(String[] args) {
        DogWalkerPOC walker = new DogWalkerPOC();
        SimpleWriter out = new SimpleWriter1L();

        // Simulate walks
        walker.startWalk(1000, "Park");
        walker.endWalk(2000);

        walker.startWalk(3000, "Park");
        walker.endWalk(4500);

        walker.startWalk(5000, "Lake");
        walker.endWalk(6000);

        walker.startWalk(7000, "Park");
        walker.endWalk(8000);

        // Output results
        out.println("Favorite Location: " + walker.favoriteLocation());
        out.println("Total Walk Duration: " + walker.totalWalkDuration()
                + " seconds");

        out.close();
    }

}
