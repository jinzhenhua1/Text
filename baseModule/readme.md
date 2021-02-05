1、如果用到InstallUtils ，则需要在 manifest 中添加：
~~~
        <provider
            android:name="androidx.core.content.FileProvider"
            android:authorities="com.jzh.basemodule.fileProvider"
            android:exported="false"
            android:grantUriPermissions="true">
            <meta-data
                android:name="android.support.FILE_PROVIDER_PATHS"
                android:resource="@xml/file_paths" />
        </provider>
~~~
并在对应的xml 文件夹中添加文件 ，内容如下：
~~~
        <?xml version="1.0" encoding="utf-8"?>
        <paths>
            <external-path path="Android/data/***your_application_id***/" name="files_root" />
            <external-path path="." name="external_storage_root" />
        </paths>
~~~
其中 external-path 表示外置存储的根目录，path 则是具体的路径，这里是要把你存放安装包的路径分享出来，所以写存放安装包的路径，一般都下载到外置目录中的file目录下
external-files-path：表示外置存储目录的关联目录的files文件夹，如：/sdcard/Android/data/packagename/files/  
external-cache-path：表示外置存储目录的关联目录的cache文件夹，如：/sdcard/Android/data/packagename/cache/  
cache-path：表示内置安装目录的cache文件夹，如：/data/user/0/packagename/cache/
files-path：表示内置安装目录的files文件夹，如：/data/user/0/packagename/files/
