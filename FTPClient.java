package ftp.kehu;

import com.google.appinventor.components.annotations.*;
import com.google.appinventor.components.common.ComponentCategory;
import com.google.appinventor.components.runtime.*;
import com.google.appinventor.components.runtime.util.*;
import com.google.appinventor.components.runtime.errors.YailRuntimeError;

@DesignerComponent(version = FTPClient.VERSION,
    description = "made in fan hao jie",
    category = ComponentCategory.EXTENSION,
    nonVisible = true,
    iconName = "images/extension.png")

@SimpleObject(external = true)
public class FTPClient extends AndroidNonvisibleComponent 
{
    public static final int VERSION = 1;
    private static final String LOG_TAG = "FTPClient";

    public FTPClient(ComponentContainer container) {super(container.$form());}
}
