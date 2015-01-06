package com.mayi.julyup.adapter;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.daimajia.numberprogressbar.NumberProgressBar;
import com.mayi.julyup.R;
import com.mayi.julyup.activity.BaseActivity;
import com.mayi.julyup.been.FilesBeen;
import com.mayi.julyup.configs.Configs;
import com.mayi.julyup.util.FileUtils;
import com.mayi.julyup.util.LogUtil;

@EBean
public class Download_List_Adapter extends BaseAdapter {
	@RootContext
	public Context context;

	LayoutInflater inflater;
	public FilesBeen filesbeen = new FilesBeen();

	@AfterInject
	void init() {
		inflater = LayoutInflater.from(context);
	}

	public void SetData(FilesBeen filesbeen) {
		this.filesbeen = filesbeen;
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		return filesbeen.getResult().size();
	}

	@Override
	public Object getItem(int arg0) {
		return filesbeen.getResult().get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(final int arg0, View curentview, ViewGroup arg2) {
		Hold hold = null;
		if (curentview == null) {
			hold = new Hold();
			curentview = inflater.inflate(R.layout.my_download_list_item, null);
			hold.file_name = (TextView) curentview.findViewById(R.id.file_name);
			hold.last_modify_time = (TextView) curentview.findViewById(R.id.last_modify_time);
			hold.file_download = (Button) curentview.findViewById(R.id.file_download);
			hold.file_pause = (Button) curentview.findViewById(R.id.file_pause);
			hold.file_open = (Button) curentview.findViewById(R.id.file_open);
			hold.number_progress = (NumberProgressBar) curentview.findViewById(R.id.number_progress_bar);
			
			curentview.setTag(hold);
		} else {
			hold = (Hold) curentview.getTag();
		}
		hold.file_name.setText(((FilesBeen.Data) getItem(arg0)).getFILE_NAME());
		hold.last_modify_time.setText(((FilesBeen.Data) getItem(arg0))
				.getLAST_MODIFY_TIME());
		hold.file_download.setOnClickListener(new DownloadListener(hold, arg0));
		hold.file_pause.setOnClickListener(new PauseListener(hold, arg0));
		hold.file_open.setOnClickListener(new OpenListener(hold, arg0));
		hold.is_have_file=FileUtils.Is_Have_Files(Configs.Download_Dir, ((FilesBeen.Data) getItem(arg0)).getFILE_NAME(), true);
		//如果已经存在文件
		if(hold.is_have_file){
			hold.number_progress.setVisibility(View.GONE);
			//显示打开按钮
			hold.file_download.setVisibility(View.INVISIBLE);
			hold.file_pause.setVisibility(View.INVISIBLE);
			hold.file_open.setVisibility(View.VISIBLE);
			
		}
		return curentview;
	}

	class Hold {
		TextView file_name, last_modify_time;
		Button file_download, file_pause, file_open;
		boolean is_have_file = false;
		NumberProgressBar number_progress;
		MyAsyncTask myAsyncTask;
	}
	
	//打开文件
	class OpenListener implements OnClickListener {
		public Hold hold;
		public int position;

		public OpenListener(Hold hold, int arg0) {
			this.hold = hold;
			this.position = arg0;
		}

		@Override
		public void onClick(View v) {
			File file = new File(Configs.Download_Dir+ ((FilesBeen.Data) getItem(this.position)).getFILE_NAME().trim());
			FileUtils.openFile(file, context);
		}
	}
	
	//暂停下载
	class PauseListener implements OnClickListener {
		public Hold hold;
		public int position;

		public PauseListener(Hold hold, int arg0) {
			this.hold = hold;
			this.position = arg0;
		}

		@Override
		public void onClick(View v) {
			this.hold.file_download.setVisibility(View.VISIBLE);
			this.hold.file_pause.setVisibility(View.INVISIBLE);

			if (this.hold.myAsyncTask!=null) {
				this.hold.myAsyncTask.DOWNLOAD_STATUE_PAUSE=true;
			}
		}
	}
	
	//下载文件
	class DownloadListener implements OnClickListener {
		public Hold hold;
		public int position;

		public DownloadListener(Hold hold, int arg0) {
			this.hold = hold;
			this.position = arg0;
		}

		@Override
		public void onClick(View v) {
			this.hold.file_download.setVisibility(View.INVISIBLE);
			this.hold.file_pause.setVisibility(View.VISIBLE);
			this.hold.number_progress.setVisibility(View.VISIBLE);
			
			//路径
			String thePath =((FilesBeen.Data) getItem(this.position)).getPATH();
				thePath=thePath.substring(7).trim();
				thePath=thePath.replace("\\", "/").replace("//", "/").trim();
				thePath="http://"+thePath.trim();
				LogUtil.e("thePath  :", thePath);
				
				if(hold.myAsyncTask==null){
					hold.myAsyncTask=new MyAsyncTask(this.hold,thePath,"JSESSIONID="+BaseActivity.JSESSIONID);
					hold.myAsyncTask.execute();
				}else{
					this.hold.myAsyncTask.DOWNLOAD_STATUE_PAUSE=false;
				}
		}
	}
	
	
	class MyAsyncTask extends AsyncTask<Void, Integer, String> {
		public boolean DOWNLOAD_STATUE_FINISHED=false;//是否完成
		public boolean DOWNLOAD_STATUE_PAUSE=false;
		
		private int count=0;//文件总的大小
		private int current=0;//当前下载了多少
		private Hold hold;
		private String file_url,session;
		public MyAsyncTask(Hold hold,String url,String session){
			this.hold=hold;
			this.file_url=url;
			this.session=session;
		}
		
		@Override
		protected String doInBackground(Void... params) {
			try {
				URL getUrl = new URL(file_url);
//				URLConnection conn = getUrl.openConnection();
				HttpURLConnection conn = (HttpURLConnection) getUrl.openConnection();
				conn.setRequestProperty("Cookie", session);
				conn.setRequestProperty("Accept-Encoding", "identity"); 
				count = conn.getContentLength();
				Log.e("mayi", "count  :"+count);
				DataInputStream input = new DataInputStream(conn.getInputStream());
				DataOutputStream output = new DataOutputStream(new FileOutputStream(Configs.Download_Dir+hold.file_name.getText().toString().trim()));
				byte[] buffer = new byte[1024*8];
				int len = -1;
				while (!DOWNLOAD_STATUE_FINISHED) {
					while (!DOWNLOAD_STATUE_PAUSE && (len = input.read(buffer)) > 0) {
						current += len;
						output.write(buffer, 0, len);
						publishProgress(current);
					}
				}
				output.flush();
				output.close();
				input.close();

			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

			return "";
		}

		@Override
		protected void onPostExecute(String result) {
			Log.i("mayi", result + "");
			super.onPostExecute(result);
			hold.file_download.setVisibility(View.INVISIBLE);
			hold.file_pause.setVisibility(View.INVISIBLE);
			hold.file_open.setVisibility(View.VISIBLE);
			this.hold.number_progress.setVisibility(View.GONE);
		}

		@Override
		protected void onProgressUpdate(Integer... values) {
			
			hold.number_progress.setMax(count);
			hold.number_progress.setProgress(values[0]);
			
			if (values[0] == count) {
				this.DOWNLOAD_STATUE_FINISHED = true;
			}
			super.onProgressUpdate(values);
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			
		}

	}

}
