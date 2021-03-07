package com.reptile.show.project.app;

import com.jess.arms.base.BaseAppConstants;

public interface AppConstants extends BaseAppConstants {
    String ACTIVITY_FRAGMENT_REPLACE = "ActivityFragmentReplace";

    String INTENT_KEY_HOME2URL = "home_url_intent_key";
    String INTENT_KEY_HOME2MOVE_ISDIR = "home_move_isdir_intent_key";
    String INTENT_KEY_HOME2MOVE_ID = "home_move_id_intent_key";
    interface HomeAdapterViewType{
        int TYPE_DIR = 1;
        int TYPE_URL = 2;
    }
}
