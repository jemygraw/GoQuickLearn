package net.duokr.goquicklearn.activity;

import net.duokr.goquicklearn.config.LearnContent;

import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.widget.Toast;
import net.duokr.goquicklearn.R;

public class ContentViewGestureListener implements OnGestureListener {
    private int FLING_MIN_DISTANCE = 150;
    private int FLING_MIN_VELOCITY = 15;
    private ContentViewActivity hostActivity;

    public ContentViewGestureListener(ContentViewActivity hostActivity) {
        this.hostActivity = hostActivity;
    }

    @Override
    public boolean onDown(MotionEvent event) {
        return false;
    }

    @Override
    public boolean onFling(MotionEvent event1, MotionEvent event2,
                           float velocityX, float velocityY) {

        if (event1.getX() - event2.getX() > FLING_MIN_DISTANCE
                && Math.abs(velocityX) > FLING_MIN_VELOCITY) {
            // Fling left
            LearnContent nextChapter = this.hostActivity.getNextChapter();
            if (nextChapter != null) {
                this.hostActivity.displayCurrentChapter(nextChapter);
            }else{
                Toast.makeText(this.hostActivity,R.string.no_next_chapter_tip,Toast.LENGTH_SHORT).show();
            }

        } else if (event2.getX() - event1.getX() > FLING_MIN_DISTANCE
                && Math.abs(velocityX) > FLING_MIN_VELOCITY) {
            // Fling right
            LearnContent previousChapter = this.hostActivity
                    .getPreviousChapter();
            if (previousChapter != null) {
                this.hostActivity.displayCurrentChapter(previousChapter);
            }else{
                Toast.makeText(this.hostActivity,R.string.no_previous_chapter_tip,Toast.LENGTH_SHORT).show();
            }
        }

        return false;
    }

    @Override
    public void onLongPress(MotionEvent event) {

    }

    @Override
    public boolean onScroll(MotionEvent event1, MotionEvent event2, float v1,
                            float v2) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent event) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent event) {
        return false;
    }

}
