package lib.kalu.banner;

import android.support.v4.view.ViewPager;
import android.view.View;

/**
 * Created by kalu on 2017/12/4.
 */

class TransformerScaleY implements ViewPager.PageTransformer {

    private static final float MIN_SCALE = 0.9F;

    @Override
    public void transformPage(View page, float position) {

        if (position < -1) {
            page.setScaleY(MIN_SCALE);
        } else if (position <= 1) {
            float scale = Math.max(MIN_SCALE, 1 - Math.abs(position));
            page.setScaleY(scale);

        } else {
            page.setScaleY(MIN_SCALE);
        }
    }
}