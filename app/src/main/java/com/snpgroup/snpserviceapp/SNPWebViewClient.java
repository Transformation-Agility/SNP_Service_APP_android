package com.snpgroup.snpserviceapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.MailTo;
import android.net.Uri;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import java.lang.ref.WeakReference;

public class SNPWebViewClient extends WebViewClient {
    private final WeakReference<Activity> mActivityRef;

    public SNPWebViewClient(Activity activity) {
        mActivityRef = new WeakReference<Activity>(activity);
    }

    @Override
    // specifically handle mailto-links and teams chat
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        final Activity activity = mActivityRef.get();
        if (url.startsWith("mailto:")) {
            if (activity != null) {
                MailTo mt = MailTo.parse(url);
                Intent i = newEmailIntent(activity, mt.getTo(), mt.getSubject(), mt.getBody(), mt.getCc());
                activity.startActivity(i);
                view.reload();
                return true;
            }
        } else if (url.startsWith("sip:")) {
            PackageManager packageManager = activity.getPackageManager();

            Intent sendIntent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse("msteams://teams.microsoft.com/l/chat/0/0?users=" + url.substring(4)));
            if (sendIntent.resolveActivity(packageManager) == null) {
                sendIntent.setData(Uri.parse("https://teams.microsoft.com/l/chat/0/0?users=" + url.substring(4)));
            }
            activity.startActivity(sendIntent);
            return true;
        } else {
            view.loadUrl(url);
        }
        return true;
    }

    private Intent newEmailIntent(Context context, String address, String subject, String body, String cc) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_EMAIL, new String[] { address });
        intent.putExtra(Intent.EXTRA_TEXT, body);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_CC, cc);
        intent.setType("message/rfc822");
        return intent;
    }
}