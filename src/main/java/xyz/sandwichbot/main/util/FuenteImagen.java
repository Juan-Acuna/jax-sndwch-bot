package xyz.sandwichbot.main.util;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Random;

public class FuenteImagen {
	private String name;
	private String url;
	private String queryUrl;
	private boolean nsfw;
	private boolean censored;
	private boolean gif;
	private boolean video;
	private int maxTags = 50;
	private String[] customTags;
	private String imgPattern;
	private String selectionPattern;
	private boolean api;
	private String defaultTag;
	private int maxPage = 0;
	private String pageName = null;
	
	/*  */
	public static final FuenteImagen Google = new FuenteImagen("Google","https://google.com","",false,true,true,true,50,null,"","",false,null);
	public static final FuenteImagen RandomCat = new FuenteImagen("RandomCat","http://random.cat","http://aws.random.cat/meow",false,"file",null,true);
	public static final FuenteImagen RealBooru = new FuenteImagen("RealBooru","https://realbooru.com","https://realbooru.com/index.php?page=post&s=list&tags=",true,false,true,true,5,null,"https://realbooru.com//images/[0-9]{1,5}/(.{5,50})\\.[a-zA-Z0-9]{2,5}","https://realbooru.com/index.php\\?page=post\\&s=view\\&id=[0-9]{5,7}",false,"male+female",200,"pid");
	public static final FuenteImagen DanBooru = new FuenteImagen("DanBooru","https://danbooru.donmai.us","https://danbooru.donmai.us/posts?tags=",true,false,true,true,2,null,"https://[a-zA-Z]{3,8}.donmai.us/[a-zA-Z]{2,8}/[0-9a-zA-Z%_/]{20,200}.[a-zA-Z0-9]{3,5}","/posts/[0-9]{4,8}",false,"pussy",60,"page");//4340762
	public static final FuenteImagen _3DBooru = new FuenteImagen("3DBooru","http://behoimi.org","http://behoimi.org/post?commit=Search&tags=",false,true,false,false,10,null,"","",false,null);
	public static final FuenteImagen GelBooru = new FuenteImagen("GelBooru","https://gelbooru.com","https://gelbooru.com/index.php?page=post&s=list&tags=",true,false,false,true,10,null,"https://img3.gelbooru.com//images/[0-9a-zA-Z]{1,4}/[0-9a-zA-Z]{1,4}/[0-9a-zA-Z_%]{25,45}.[a-zA-Z0-9]{2,5}", "https://gelbooru.com/index.php\\?page=post&s=view&id=[0-9]{2,10}",false,"pussy",700,"pid");
	public static final FuenteImagen Konachan = new FuenteImagen("Konachan","https://konachan.com","https://konachan.com/post?tags=",true,true,false,false,10,null, "https://konachan.com/image/[0-9a-zA-Z]{15,35}/Konachan.com%20-[0-9a-zA-Z%_]{5,200}.[a-zA-Z0-9]{2,5}","href=\"/post/show/[0-9]{1,10}",false,"pussy",700,"page");
	public static final FuenteImagen KonachanNet = new FuenteImagen("KonachanNet","https://konachan.net","https://konachan.net/post?tags=",false,true,false,false,10,null,"","",false,null);
	public static final FuenteImagen LBooru = new FuenteImagen("LBooru","https://lolibooru.moe","https://lolibooru.moe/post?tags=",false,true,true,true,10,null,"","",false,null);
	public static final FuenteImagen R34 = new FuenteImagen("R34","https://rule34.xxx","https://rule34.xxx/index.php?page=post&s=list&tags=",true,false,true,true,10,null,"","",false,null);
	public static final FuenteImagen SafeBooru = new FuenteImagen("SafeBooru","https://safebooru.org","https://safebooru.org/index.php?page=post&s=list&tags=",false,true,true,true,10,null,"","",false,null);//https://e-shuushuu.net/search/results/?tags=26 OTRO SAFE OTAKU
	public static final FuenteImagen XBooru = new FuenteImagen("XBooru","https://xbooru.com","https://xbooru.com/index.php?page=post&s=list&tags=",true,false,true,true,10,null,"https://img.xbooru.com//images/[0-9a-zA-Z]{1,5}/[0-9a-zA-Z_%]{5,40}.[a-zA-Z0-9]{2,5}","index.php\\?page=post&s=view&id=[0-9]{4,8}",false,"pussy",3000,"pid");
	public static final FuenteImagen Yandere = new FuenteImagen("Yandere","https://yande.re","https://yande.re/post?tags=",true,false,false,false,10,null,"https://files.yande.re/sample/[0-9a-zA-Z]{15,40}/yande.re[0-9a-zA-Z%_]{20,250}.[a-zA-Z0-9]{2,5}","https://yande.re/post/show/[0-9]{4,10}",false,"pussy",990,"page");
	
	public String getQuery(int page, String[] tags, boolean gif, boolean video, boolean random) throws Exception {
		String t = (this.defaultTag==null?"+":"+"+this.defaultTag);
		if(tags!=null) {
			t = "";
			int cont = 0;
			for(String s : tags) {
				if(cont>=this.maxTags) {
					break;
				}
				if(s.trim().replace(" ","").length()<=0) {
					continue;
				}
				t += "+" + URLEncoder.encode(s, StandardCharsets.UTF_8.toString());
				cont++;
			}
		}
		String tipo = "";
		if(random) {
			Random r = new Random(System.currentTimeMillis());
			int i = r.nextInt(18)+1;
			if(i==2 || i==5 || i==8 || i==11 || i==14 || i==17 && this.video) {
				tipo="video+";
			}else if(i==3 || i==6 || i==9 || i==12 || i==15 || i==18 && this.gif) {
				tipo="gif+";
			}
		}else {
			if(video && this.video) {
				tipo = "video+";
				gif=false;
			}
			if(gif && this.gif) {
				tipo="gif+";
			}
		}
		return queryUrl + tipo + t.substring(1) + ((this.pageName!=null&&this.maxPage>0)?"&" + this.pageName + "=" +page:"");
	}
	public String getQuery(String[] tags, boolean gif, boolean video, boolean random) throws Exception {
		String t = (this.defaultTag==null?"+":"+"+this.defaultTag);
		if(tags!=null) {
			t = "";
			int cont = 0;
			for(String s : tags) {
				if(cont>=this.maxTags) {
					break;
				}
				if(s.trim().replace(" ","").length()<=0) {
					continue;
				}
				t += "+" + URLEncoder.encode(s, StandardCharsets.UTF_8.toString());
				cont++;
			}
		}
		String tipo = "";
		if(random) {
			Random r = new Random(System.currentTimeMillis());
			int i = r.nextInt(18)+1;
			if(i==2 || i==5 || i==8 || i==11 || i==14 || i==17 && this.video) {
				tipo="video+";
			}else if(i==3 || i==6 || i==9 || i==12 || i==15 || i==18 && this.gif) {
				tipo="gif+";
			}
		}else {
			if(video && this.video) {
				tipo = "video+";
				gif=false;
			}
			if(gif && this.gif) {
				tipo="gif+";
			}
		}
		return queryUrl + tipo + t.substring(1);
	}
	
	/* GETTERS SETTERS */
	public boolean isCensored() {
		return censored;
	}
	public void setCensored(boolean censored) {
		this.censored = censored;
	}
	public boolean isGif() {
		return gif;
	}
	public void setGif(boolean gif) {
		this.gif = gif;
	}
	public boolean isVideo() {
		return video;
	}
	public void setVideo(boolean video) {
		this.video = video;
	}
	public int getMaxTags() {
		return maxTags;
	}
	public void setMaxTags(int maxTags) {
		this.maxTags = maxTags;
	}
	public String[] getCustomTags() {
		return customTags;
	}
	public void setCustomTags(String[] customTags) {
		this.customTags = customTags;
	}
	public String getName() {
		return name;
	}
	public String getUrl() {
		return url;
	}
	public String getQueryUrl() {
		return queryUrl;
	}
	public boolean isNsfw() {
		return nsfw;
	}
	public String getImgPattern() {
		return imgPattern;
	}
	public String getSelectionPattern() {
		return selectionPattern;
	}
	public boolean isApi() {
		return api;
	}
	
	public int getMaxPage() {
		return maxPage;
	}
	public void setMaxPage(int maxPage) {
		this.maxPage = maxPage;
	}
	public String getPageName() {
		return pageName;
	}
	public void setPageName(String pageName) {
		this.pageName = pageName;
	}
	/*  CONSTRUCTORES Y OTROS  */
	public static FuenteImagen Custom(String name, String url, String queryUrl, boolean nsfw) {
		return new FuenteImagen(name + "(custom)",url,queryUrl,nsfw);
	}
	public static FuenteImagen Custom(String name, String url, String queryUrl, boolean nsfw, boolean censored, boolean gif,
			boolean video, int maxTags, String[] customTags, String imgPattern, String selectionPattern,  boolean api, int maxPage, String pageName) {
		return new FuenteImagen(name + "(custom)", url, queryUrl, nsfw, censored, gif, video, maxTags, customTags, imgPattern, selectionPattern, api, null,maxPage, pageName);
	}
	public static FuenteImagen Custom(String name, String url, String queryUrl, boolean gif,
			boolean video, String imgPattern, String selectionPattern,  boolean api) {
		return new FuenteImagen(name + "(custom)", url, queryUrl, false, false, gif, video, 50, null, imgPattern, selectionPattern, api,null,1,null);
	}
	private FuenteImagen(String name, String url, String queryUrl, boolean nsfw, boolean censored, boolean gif,
	boolean video, int maxTags, String[] customTags, String imgPattern, String selectionPattern,  boolean api,
	String defTag, int maxPage, String pageName) {
		this.name = name;
		this.url = url;
		this.queryUrl = queryUrl;
		this.nsfw = nsfw;
		this.censored = censored;
		this.gif = gif;
		this.video = video;
		this.maxTags = maxTags;
		this.customTags = customTags;
		this.imgPattern=imgPattern;
		this.selectionPattern=selectionPattern;
		this.api=api;
		this.defaultTag=defTag;
		this.maxPage=maxPage;
		this.pageName=pageName;
	}
	private FuenteImagen(String name, String url, String queryUrl, boolean nsfw, boolean censored, boolean gif,
			boolean video, int maxTags, String[] customTags, String imgPattern, String selectionPattern,  boolean api,
			String defTag) {
				this.name = name;
				this.url = url;
				this.queryUrl = queryUrl;
				this.nsfw = nsfw;
				this.censored = censored;
				this.gif = gif;
				this.video = video;
				this.maxTags = maxTags;
				this.customTags = customTags;
				this.imgPattern=imgPattern;
				this.selectionPattern=selectionPattern;
				this.api=api;
				this.defaultTag=defTag;
			}
	private FuenteImagen(String name, String url, String queryUrl, boolean nsfw, int maxTags, String[] customTags, String imgPattern, String selectionPattern,  boolean api) {
		this.name = name;
		this.url = url;
		this.queryUrl = queryUrl;
		this.nsfw = nsfw;
		this.censored = false;
		this.gif = false;
		this.video = false;
		this.maxTags = maxTags;
		this.customTags = customTags;
		this.imgPattern=imgPattern;
		this.selectionPattern=selectionPattern;
		this.api=api;
	}
	private FuenteImagen(String name, String url, String queryUrl, boolean nsfw, boolean censored, String imgPattern, String selectionPattern,  boolean api) {
		this.name = name;
		this.url = url;
		this.queryUrl = queryUrl;
		this.nsfw = nsfw;
		this.censored = censored;
		this.gif = false;
		this.video = false;
		this.imgPattern=imgPattern;
		this.selectionPattern=selectionPattern;
		this.api=api;
	}
	private FuenteImagen(String name, String url, String queryUrl, boolean nsfw, String imgPattern, String selectionPattern,  boolean api) {
		this.name = name;
		this.url = url;
		this.queryUrl = queryUrl;
		this.nsfw = nsfw;
		this.censored = false;
		this.gif = false;
		this.video = false;
		this.imgPattern=imgPattern;
		this.selectionPattern=selectionPattern;
		this.api=api;
	}
	private FuenteImagen(String name, String url, String queryUrl, boolean nsfw) {
		this.name=name;
		this.url=url;
		this.nsfw=nsfw;
	}
}
