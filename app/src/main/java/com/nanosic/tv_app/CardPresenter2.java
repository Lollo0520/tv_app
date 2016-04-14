package com.nanosic.tv_app;


import android.graphics.drawable.Drawable;
import android.support.v17.leanback.widget.ImageCardView;
import android.support.v17.leanback.widget.Presenter;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ImageView;

/*
 * A CardPresenter is used to generate Views and bind Objects to them on demand.
 * It contains an Image CardView
 */
public class CardPresenter2 extends Presenter {
    private static final String TAG = "CardPresenter";

    private static final int CARD_WIDTH = 400;
    private static final int CARD_HEIGHT = 400;
    private static int sSelectedBackgroundColor;
    private static int sDefaultBackgroundColor;
    private Drawable mDefaultCardImage;

    private static void updateCardBackgroundColor(ImageCardView view, boolean selected) {
        int color = selected ? sSelectedBackgroundColor : sDefaultBackgroundColor;
        // Both background colors should be set because the view's background is temporarily visible
        // during animations.
        view.setBackgroundColor(color);
        view.findViewById(R.id.info_field).setBackgroundColor(color);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent) {
        Log.d(TAG, "onCreateViewHolder");

        sDefaultBackgroundColor = parent.getResources().getColor(R.color.default_background);
        sSelectedBackgroundColor = parent.getResources().getColor(R.color.selected_background);
        mDefaultCardImage = parent.getResources().getDrawable(R.drawable.movie);

        ImageCardView cardView = new ImageCardView(parent.getContext()) {
            @Override
            public void setSelected(boolean selected) {
//                updateCardBackgroundColor(this, selected);
                super.setSelected(selected);
            }
        };

        cardView.setFocusable(true);
        cardView.setFocusableInTouchMode(true);
        cardView.setInfoAreaBackgroundColor(parent.getResources().getColor(R.color.card_info_bg));
        cardView.setBackgroundColor(parent.getResources().getColor(R.color.default_background));
//        updateCardBackgroundColor(cardView, false);
        return new ViewHolder(cardView);
    }

    @Override
    public void onBindViewHolder(Presenter.ViewHolder viewHolder, Object item) {
        Device device = (Device) item;
        ImageCardView cardView = (ImageCardView) viewHolder.view;

        Log.d(TAG, "onBindViewHolder");
        if (device.getImage() != 0) {
            cardView.setTitleText(device.getTitle());
//            cardView.setContentText(movie.getStudio());
            cardView.setMainImageDimensions(CARD_WIDTH, CARD_HEIGHT);

            cardView.getMainImageView().setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            cardView.getMainImageView().setImageDrawable(viewHolder.view.getContext().getDrawable(device.getImage()));
           /* Glide.with(viewHolder.view.getContext())
                    .load(movie.getCardImageUrl())
                    .centerCrop()
                    .error(mDefaultCardImage)
                    .into(cardView.getMainImageView());*/
        }
    }

    @Override
    public void onUnbindViewHolder(Presenter.ViewHolder viewHolder) {
        Log.d(TAG, "onUnbindViewHolder");
        ImageCardView cardView = (ImageCardView) viewHolder.view;
        // Remove references to images so that the garbage collector can free up memory
        cardView.setBadgeImage(null);
        cardView.setMainImage(null);
    }
}

