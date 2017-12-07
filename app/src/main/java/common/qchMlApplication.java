package common;
import android.app.*;
import com.pgyersdk.crash.*;

public class qchMlApplication extends Application
{

    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();

        PgyCrashManager.register(this);
    }
}
