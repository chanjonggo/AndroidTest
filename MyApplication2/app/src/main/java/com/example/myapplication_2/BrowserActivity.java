package com.example.myapplication_2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.net.URISyntaxException;

public class BrowserActivity extends Activity {
    private final String TAG = "ITouch_WebActivity";
    private WebView webView = null;
    private boolean backPressed = false;

    private static final int REQUEST_VOICE = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!checkRestart(savedInstanceState)) {
            //overridePendingTransition(R.anim.anim_right_in, R.anim.anim_stay);

            initLayout();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && this.webView.canGoBack()) {
            this.webView.goBack();
            return true;
        }

        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
            alertDialog.setTitle("프로그램을 종료 하시겠습니까?");
            alertDialog.setMessage("기술 노트 \n https://wikidocs.net/22308으로 접속...");
            alertDialog.setIcon(R.drawable.ic_launcher_background);
            alertDialog.setPositiveButton("아니오", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                }
            });

            alertDialog.setNeutralButton("리뷰/별점가기", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse("market://details?id=com.hitouch.wikibook"));
                    startActivity(intent);
                }
            });

            alertDialog.setNegativeButton("예", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    android.os.Process.killProcess(android.os.Process.myPid());
                }
            });

            alertDialog.show();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onBackPressed() {
        if (this.webView.canGoBack()) {
            this.webView.goBack();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private void initLayout() {
        setContentView(R.layout.activity_main);
        setTitle(getString(R.string.title_web_info));

        this.webView = (WebView) findViewById(com.example.myapplication_2.R.id.webInfo);
        this.webView.setWebViewClient(new WebViewClient());
        WebSettings set = this.webView.getSettings();
        set.setJavaScriptEnabled(true);

        this.webView.getSettings().setDomStorageEnabled(true);

        String url = String.format(HttpURL.WEBURL);
        this.webView.loadUrl(url);
    }

    private boolean checkRestart(Bundle savedInstanceState) {
        if (null != savedInstanceState) {
            return true;
        }

        return false;
    }


    //내포 클래스
    class WebClient extends WebViewClient {

        @Override
        public void onReceivedSslError(WebView view, final SslErrorHandler handler, SslError error) {
            super.onReceivedSslError(view, handler, error);

            final AlertDialog.Builder builder = new AlertDialog.Builder(BrowserActivity.this);
            String message = getString(R.string.msg_cert_error);

            switch (error.getPrimaryError()) {
                case SslError.SSL_UNTRUSTED:
                    message = getString(R.string.msg_cert_trust_error);
                    break; // 이 사이트의 보안 인증서는 신뢰할 수 있습니다
                case SslError.SSL_EXPIRED:
                    message = getString(R.string.msg_cert_expired_error);
                    break; // 보안 인증서가 만료되었습니다.
                case SslError.SSL_IDMISMATCH:
                    message = getString(R.string.msg_cert_mismatched_error);
                    break; // 보안 인증서가 ID 일치하지 않습니다.
                case SslError.SSL_NOTYETVALID:
                    message = getString(R.string.msg_cert_not_yet_error);
                    break;  // 보안 인증서가 아직 유효하지 않습니다.
                default:
                    builder.setMessage("확인되지 않은 SSL 에러" + error.toString());
                    break;
            }

            handler.cancel();
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (url.startsWith("intent:") || url.startsWith("market:") || url.startsWith("ispmobile:")) {
                Intent intent;
                try {
                    intent = Intent.parseUri(url, Intent.URI_INTENT_SCHEME);
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                    return false;
                }

                if (getPackageManager().resolveActivity(intent, 0) == null) {
                    String pkgName = intent.getPackage();
                    if (null != pkgName) {
                        intent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://search?q=pname:" + pkgName));
                        intent.addCategory(Intent.CATEGORY_BROWSABLE);
                        startActivity(intent);
                        return true;
                    }
                    return false;
                }

                try {
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                view.loadUrl(url);
                return true;
            }

            return true;
        }
    }
};


