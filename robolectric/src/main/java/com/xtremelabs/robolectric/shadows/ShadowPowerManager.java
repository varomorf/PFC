package com.xtremelabs.robolectric.shadows;

import android.content.Context;
import android.os.PowerManager;
import com.xtremelabs.robolectric.Robolectric;
import com.xtremelabs.robolectric.internal.Implementation;
import com.xtremelabs.robolectric.internal.Implements;

/**
 * Shadows the {@code android.os.PowerManager} class.
 */
@Implements(PowerManager.class)
public class ShadowPowerManager {

	private boolean isScreenOn = true;

    @Implementation
    public PowerManager.WakeLock newWakeLock(int flags, String tag) {
    	PowerManager.WakeLock wl = Robolectric.newInstanceOf(PowerManager.WakeLock.class);
    	Robolectric.getShadowApplication().addWakeLock(wl);
        return wl;
    }

    @Implementation
    public boolean isScreenOn() {
    	return isScreenOn;
    }

    public void setIsScreenOn(boolean screenOn) {
    	isScreenOn = screenOn;
    }
    
    /**
     * Non-Android accessor that discards the most recent {@code PowerManager.WakeLock}s
     */
    public static void reset() {
    	Robolectric.getShadowApplication().clearWakeLocks();
    }
    
    /**
     * Non-Android accessor retrieves the most recent wakelock registered
     * by the application
     * 
     * @return
     */
    public PowerManager.WakeLock getLatestWakeLock() {
    	return Robolectric.getShadowApplication().getLatestWakeLock();
    }

    @Implements(PowerManager.WakeLock.class)
    public static class ShadowWakeLock {
        private boolean refCounted =  true;
        private int refCount;
        private boolean locked;

        @Implementation
        public void acquire() {
            acquire(0);

        }

        @Implementation
        public synchronized void acquire(long timeout) {
            if (refCounted) {
                refCount++;
            } else {
                locked = true;
            }
        }

        @Implementation
        public synchronized void release() {
            if (refCounted) {
                if (--refCount < 0) throw new RuntimeException("WakeLock under-locked");
            } else {
                locked = false;
            }
        }

        @Implementation
        public synchronized boolean isHeld() {
            return refCounted ? refCount > 0 : locked;
        }

        @Implementation
        public void setReferenceCounted(boolean value) {
            refCounted = value;
        }
    }
}
