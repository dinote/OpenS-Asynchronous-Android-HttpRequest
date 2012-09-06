package opens.components.cache.serializers;

import opens.components.cache.FileCache;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.widget.ImageView;

public final class BitmapLoader extends AsyncTask<String, Void, Bitmap> {
	
	private ImageView view;
	
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
