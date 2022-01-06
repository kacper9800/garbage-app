package com.garbage.schedule;

import androidx.annotation.DrawableRes;
import androidx.annotation.StringRes;

import com.garbage.R;

public enum GarbageType {
    BIO(R.string.common_bio, R.drawable.bio),
    PLASTIC(R.string.common_plastic, R.drawable.plastic),
    PAPER(R.string.common_paper, R.drawable.paper),
    GLASS(R.string.common_glass, R.drawable.glass),
    MIX(R.string.common_mix, R.drawable.mix);

    @StringRes
    private final int name;
    @DrawableRes
    private final int resId;

    GarbageType(@StringRes int name, @DrawableRes int resId) {
        this.name = name;
        this.resId = resId;
    }

    public int getName() {
        return name;
    }

    public int getResId() {
        return resId;
    }
}
