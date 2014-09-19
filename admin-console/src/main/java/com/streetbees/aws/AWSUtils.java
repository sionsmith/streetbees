package com.streetbees.aws;

import com.amazonaws.regions.Region;
import com.amazonaws.regions.RegionUtils;

/**
 * Created by streetbees on 13/09/2014.
 */
public class AWSUtils {

    /**
     * Creates a Region object corresponding to the AWS Region. If an invalid region is passed in
     * then the JVM is terminated with an exit code of 1.
     *
     * @param regionStr the common name of the region for e.g. 'us-east-1'.
     * @return A Region object corresponding to regionStr.
     */
    public static Region parseRegion(String regionStr) {
        Region region = RegionUtils.getRegion(regionStr);

        if (region == null) {
            System.err.println(regionStr + " is not a valid AWS region.");
            System.exit(1);
        }
        return region;
    }
}
