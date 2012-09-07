package opens.components.cache.utils;

import opens.components.cache.FileCache;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.widget.ImageView;

/**
 * Class to display bitmaps of the UI thread
 * @author Leonardo Rossetto <leonardoxh@gmail.com>
 * @since 07/09/2012
 */
public final class BitmapLoader extends AsyncTask<String, Void, Bitmap> {
	
	/** The view to display the bitmap */
	private ImageView view;
	
	/**
	 * Construct 
	 * @param image the view to display the bitmap
	 */
	public BitmapLoader(ImageView image) {
		this.view = image;
	}
	
	@Override
	protected Bitmap doInBackground(String... params) {
		return new FileCache(this.view.getContext()).get(params[0]);
	}
	
	@Override
	public void onPostExecute(Bitmap result) {
		this.view.setImageBitmap(result);
	}
	
}
