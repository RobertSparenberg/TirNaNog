package net.frozenchaos.TirNaNog.capabilities;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OwnCapabilityApplicationsService {
    private final List<CapabilityThread> runningCapabilities = new ArrayList<>();
    private boolean stopRequested = false;

    public synchronized List<CapabilityApplication> getCapabilityApplications() {
        synchronized(runningCapabilities) {
            List<CapabilityApplication> capabilityApplications = new ArrayList<>(runningCapabilities.size());
            for(CapabilityThread thread : runningCapabilities) {
                capabilityApplications.add(thread.getProfile());
            }
            return capabilityApplications;
        }
    }

    synchronized List<CapabilityThread> getRunningCapabilities() {
        return new ArrayList<>(runningCapabilities);
    }

    synchronized void removeCapabilityApplication(CapabilityThread capability) {
        runningCapabilities.remove(capability);
    }

    synchronized void addCapabilityApplication(CapabilityThread capability) {
        if(!stopRequested) {
            runningCapabilities.add(capability);
        }
    }

    public void setStopRequested() {
        stopRequested = true;
    }
}
