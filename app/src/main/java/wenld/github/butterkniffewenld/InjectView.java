package wenld.github.butterkniffewenld;

import android.app.Activity;

import com.wenld.ButterKnifeException;
import com.wenld.ViewBinder;

import java.util.HashMap;
import java.util.Map;

/**
 * <p/>
 * Author: wenld on 2017/3/28 14:43.
 * blog: http://www.jianshu.com/u/99f514ea81b3
 * github: https://github.com/LidongWen
 */

public class InjectView {
    private static Map<Activity, ViewBinder> map = new HashMap<>();

    public static void bind(Activity activity) {
        String className = activity.getClass().getName();
        try {
            Class<?> viewClass = Class.forName(className + "$$ViewBinder");

            ViewBinder binder = map.get(activity);
            if (binder != null) {
                throw new ButterKnifeException("重复");
            }
            binder = (ViewBinder) viewClass.newInstance();
            binder.bind(activity);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
