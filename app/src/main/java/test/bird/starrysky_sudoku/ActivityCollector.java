package test.bird.starrysky_sudoku;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 18-7-27.
 */

public class ActivityCollector {
    public static List<Activity> activities = new ArrayList<>();
    public static void addActivity(Activity activity){
        activities.add(activity);
    }
    public static void removeAvtivity(Activity activity){
        activities.remove(activity);
    }
    public static void finishAll(){
        for (Activity activity :activities){
            if (!activity.isFinishing()){
                activity.finish();
            }
        }
        activities.clear();
    }
}
