public class DefaultHorizontalAnimator extends FragmentAnimator implements parcelable{
    
    public DefaultHorizontalAnimator() {
        enter = R.anim.h_fragment_enter;
        exit = R.anim.h_fragment_exti;
        popEnter = R.anim.h_fragment_pop_enter;
        popExit = R.anim.h_fragment_pop_exit;
    }

    protected DefaultHorizontalAnimator(Parcel in) {
        super(in);
    }

    @Override
    public void writeParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
    }

    @Override
    public int describeContents(){
        return 0;
    }

    public static final Creator<DefaultHorizontalAnimator> CREATOR = new Creator<DefaultHorizontalAnimator>() {
        @Override
        public DefaultHorizontalAnimator createFromParcel(Parcel in) {
            return new DefaulthorizontalAnimator(in);
        }

        @Override
        public DefaultHorizontalAnimator[] newArray(int size) {
            return new DefaulthorizontalAniator[size];
        }
    }

}
