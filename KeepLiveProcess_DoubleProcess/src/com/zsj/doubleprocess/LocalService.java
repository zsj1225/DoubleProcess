package com.zsj.doubleprocess;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;

/**
 * 第一个服务。本地服务
 * 
 * @author zsj
 *
 */
public class LocalService extends Service {

	private MyBind bind;
	private MyServiceConnection conn;

	@Override
	public void onCreate() {
		super.onCreate();
		if (bind == null) {
			bind = new MyBind();
		}
		if (conn == null) {
			conn = new MyServiceConnection();
		}
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// 绑定另外进程的服务RemoteService,并设置BIND_IMPORTANT为重要绑定
		bindService(new Intent(LocalService.this, RemoteService.class), conn,
				Context.BIND_IMPORTANT);

		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public IBinder onBind(Intent intent) {
		return bind;
	}

	public class MyBind extends RemoteConn.Stub {
		@Override
		public String getProcessName() throws RemoteException {
			return "LocalService";
		}
	}

	public class MyServiceConnection implements ServiceConnection {

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			// 远程服务连接成功回调
		}

		@Override
		public void onServiceDisconnected(ComponentName name) {
			// 被绑定的服务断开连接的时候回调
			// 启动被绑定的服务。
			startService(new Intent(LocalService.this, RemoteService.class));
			bindService(new Intent(LocalService.this, RemoteService.class),
					conn, Context.BIND_IMPORTANT);
		}
	}

}
