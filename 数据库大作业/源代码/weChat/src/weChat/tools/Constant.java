package weChat.tools;

/**
 * 
 */
public interface Constant {
	
	//user,admin
	public static final String PARAM_SESSION_USER_ID = "session_user_id";
	
	public static final String PARAM_SESSION_USER_OBJECT = "session_user_object";
	
	public static final String PARAM_PWD1 = "pwd1";
	
	public static final String PARAM_PWD2 = "pwd2";
	
	public static final String PARAM_RETURN = "action_result";
	
	public static final String PARAM_RETURN_SUCCESS = "success";
	
	public static final String PARAM_RETURN_FAIL = "fail";
	
	public static final String PARAM_PAGE = "p";

	//user
	public static final String PARAM_ALBUM_NAME = "albumname";
	
	public static final String PARAM_ALBUM_PARENT = "parentname";
	
	public static final String PARAM_ALBUM_IMAGE_URL = "imageurl";
	
	public static final String PARAM_ALBUM_IMAGE_LOCALE_URL = "image_locale_url";
	
	public static final String PARAM_ALBUM_ID = "albumid";
	
	public static final String PARAM_ALBUM_PRIVATE = "private";
	
	public static final String SESSION_UPLOADED_RESOURCE_LIST = "uploaded_resource_list";
	
	public static final String PARAM_RESOURCE_TIMESTAMP = "resource_upload_timestamp";
	
	public static final String PARAM_RESOURCE_REMARK = "resource_remark";
	
	public static final String PARAM_KEYWORD = "query_key_word";
		
	// 设置最大上传的文件大小
	public static final int PARAM_ALBUM_IMAGE_MAX_FILE_SIZE = 500 * 1024;
	// 设置内存中存储文件的最大值
	public static final int PARAM_ALBUM_IMAGE_MAX_MEM_FILE = 500 * 1024;
	
	// 设置最大上传的文件大小
	public static final int PARAM_RESOURCE_MAX_FILE_SIZE = 5120000;
	// 设置内存中存储文件的最大值
	public static final int PARAM_RESOURCE_MAX_MEM_FILE = 5120000;
		
	//admin	
	public static final String PARAM_USER_NAME = "username";
	
	public static final String PARAM_PWD = "pwd";
	
	public static final String PARAM_WIEW_KEY = "wiewkey";
	
	public static final String PARAM_USER_ID = "userid";
	
	public static final String PARAM_USER_LOCKED = "locked";
	
	public static final String PARAM_RESOURCE_ID = "resourceid";
	
	public static final String PARAM_RESOURCE_NAME = "name";
	
	public static final String PARAM_RESOURCE_OWNER_NAME = "username";
	
	public static final String BACKUP_FILE_TYPE = ".sql";
	
	public static final String PARAM_BACKUP_FILE_NAME = "filename";
	
	//user,fore
	public static final String ALBUM_ALL = "albumall";
	
	public static final String ALBUM_VIDEO = "video";
	
	public static final String ALBUM_IMAGE = "image";
	
	public static final String ALBUM_AUDIO = "audio";
	
	public static final String ALBUM_OTHER = "other";
	
	public static final String ALBUM_VIDEO_DEFAULT_IMAGE = "img/default_video.png";
	
	public static final String ALBUM_IMAGE_DEFAULT_IMAGE = "img/default_image.png";
	
	public static final String ALBUM_AUDIO_DEFAULT_IMAGE = "img/default_audio.png";
		
	//fore
	
	public static final String INSTALL_DATABASE_NAME = "databasename";
	
	public static final String INSTALL_URL = "url";
	
	public static final String INSTALL_DRIVER_NAME = "dirvername";
	
	public static final String INSTALL_USER_NAME = "username";
	
	public static final String INSTALL_PASSWORD = "password";
	
	public static final String INSTALL_MODEL = "install";
		
	public static final String INSTALL_EXCPTION = "install_exception";
	
	//当用户进入查询页面，不带任何关键字，设置此参数，系统会自动屏蔽掉列表
	public static final String SEARCH_CONDITION = "condition";
	
	//音频播放时，每一首歌曲都有一个插图
	public static final String IMAGE_FOR_AUDIO_PLAY = "image_for_audio_play";
	
	public static final String IMAGE_ID = "imageid";
	
}
