/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 * @author Fernando Cejas (the android10 coder)
 */
package org.android10.viewgroupperformance.component;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import org.android10.gintonic.annotation.DebugTrace;

/**
 *
 */
public class MyLinearLayout extends LinearLayout {

  public MyLinearLayout(Context context) {
    super(context);show(1,"234");
  }

  public MyLinearLayout(Context context, AttributeSet attrs) {
    super(context, attrs);show(1,"234");
  }

  public MyLinearLayout(Context context, AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
      show(1,"234");
  }

@DebugTrace
    private int show(int a,String b){
        for (int i = 0; i <= 100; i++) {
            System.out.println("hello 123");
        }
    return 123;
    }

  @DebugTrace
  @Override
  protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    sleep(10);
  }

  @Override
  protected void onLayout(boolean changed, int l, int t, int r, int b) {
    super.onLayout(changed, l, t, r, b);
  }

  @Override
  protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);
  }

  /**
   * Method for sleeping. Testing purpose. DON'T DO SOMETHING LIKE THIS!!!
   *
   * @param millis Amount of millis to sleep.
   */
  private void sleep(long millis) {
    try {
      Thread.sleep(millis);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}
