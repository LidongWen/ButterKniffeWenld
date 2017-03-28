package wenld.github.butterkniffewenld;

import android.app.Activity;

/**
 * <p/>
 * Author: 温利东 on 2017/3/28 14:43.
 * blog: http://blog.csdn.net/sinat_15877283
 * github: https://github.com/LidongWen
 */

public class InjectView {
    public static void bind(Activity activity) {
        String className = activity.getClass().getName();
        try {
            Class<?> viewClass = Class.forName(className + "$$ViewBinder");
            ViewBinder binder = (ViewBinder) viewClass.newInstance();
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
