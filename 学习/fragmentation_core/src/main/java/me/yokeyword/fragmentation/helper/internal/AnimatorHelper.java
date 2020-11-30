public class AnimatorHelper {
    private Animation nonAnim. nonAminFixed;
    public Animation enterAnim, exitANim, popEnterAnim, popExitAnim;

    private Context context;;
    private FragmentAnimator fragmentAnimator;

    public AnimatorHelper(Context context, FragmentAnimator fragmentAnimator) {
        this.context = context;
        notifyChanged(fragmentAnimator);
    }

    public void notifyChanged(FragmentAnimator fragmentAnimator) {
        this.fragmentAnimator = fragmentAnimator;
        initEnterAnim();
        initExitAnim();
        initPopEnterAnim();
        initPopExitAnim();
    }

    public Animation getNonAnim() {
        if(nonAnim == null) {
            nonAnim = AnimationUtils.loadAnimation(context, R.anim.no_anim);
        }
        return noneAnim;
    }

    public Animation getNonAnimFixed() {
        if(noneAnimFixed == null) {
            noneAnimFixed = new Animation() {

            };
        }
        return noneAnimFixed;
    }

    @Nullable
    public Animation compatChildFragmentExitAnim(Fragment fragment) {
        if((fragment.getTag() != null && fragment.getTag().startsWith("android:switcher:") && fragment.getUserVisibleHint())
            || (fragment.getParentFragment() != null && fragment.getParentFragment().isRemoving() && !fragment.isHidden())) {
                Animation animation = new Animation(){

                };
                animation.setDuration(exitAnim.getDuration());
                return animation;
        }
        return null;
    }

    private Animation initEnterAnim() {
        if(fragmentAnimator.getEnter() == 0) {
            enterAnim = AnimationUtils.loadAnimation(context, R.anim.no_anim);
        } else {
            enterAnim = AnimationUtils.loadAnimation(context, fragmentAnimator.getEnter());
        }
        return enterAnim;
    }

    private Animation initExitAnim() {
        if(fragmentAnimator.getExit() == 0) {
            exitAnim = AnimationUtils.loadAnimation(context, R.anim.no_anim);
        } else {
            exitAnim = AnimationUtils.loadAnimation(context, fragmentAnimator.getExit());
        }
        return exitAnim;
    }

    private Animation initPopEnterAnim() {
        if(fragmentAnimator.getPopEnter() == 0) {
            popEnterAnim = AnimationUtils.loadAnimation(context, R.anim.no_anim);
        } else {
            popEnterAnim = AnimationUtils.loadAnimation(context, fragmentAnimator.getPopEnter());
        }
        return popEnterAnim;
    }

    private Animation initPopExitAnim() {
        if(fragmentAnimator.getPopExit() == 0){
            popExitAnim = AnimationUtils.loadAnimation(context, R.anim.no_anim);
        } else {
            popExitAnim = AnmationUtils.loadAnimation(context, fragmentAnimator.getPopExit());
        }
        return popExitAnim;
    }
}
