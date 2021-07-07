
package com.leonard.healthmanager.WalkandStep;

import android.content.pm.PackageManager;

import com.leonard.healthmanager.WalkandStep.services.AbstractStepDetectorService;
import com.leonard.healthmanager.WalkandStep.services.AccelerometerStepDetectorService;
import com.leonard.healthmanager.WalkandStep.services.HardwareStepDetectorService;
import com.leonard.healthmanager.WalkandStep.utils.AndroidVersionHelper;




public class Factory {



    public static Class<? extends AbstractStepDetectorService> getStepDetectorServiceClass(PackageManager pm){
        if(pm != null && AndroidVersionHelper.supportsStepDetector(pm)) {
            return HardwareStepDetectorService.class;
        }else{
            return AccelerometerStepDetectorService.class;
        }
    }
}
