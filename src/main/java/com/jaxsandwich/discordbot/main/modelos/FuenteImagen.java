package com.jaxsandwich.discordbot.main.modelos;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.jaxsandwich.discordbot.conexion.CommandManager;
import com.jaxsandwich.discordbot.conexion.anotaciones.*;

@PrivateFieldsAllowed
public class FuenteImagen {
	@ExcludeField
	private static Map<String, FuenteImagen> cont = Collections.synchronizedMap(new HashMap<String, FuenteImagen>());
	
	public static void compute(FuenteImagen fuente) {
		cont.put(fuente.name, fuente);
	}
	public static FuenteImagen find(String nombre) {
		return cont.get(nombre);
	}
	public static ArrayList<FuenteImagen> getAsList() {
		ArrayList<FuenteImagen> l = new ArrayList<FuenteImagen>(cont.values());
		return l;
	}
	public static int getCount() {
		return cont.size();
	}
	public static void load(List<FuenteImagen> l) {
		for(FuenteImagen f : l) {
			FuenteImagen.cont.put(f.name,f);
		}
	}
	@PrimaryKey
	protected String name;
	protected String url;
	protected String queryUrl;
	protected boolean nsfw;
	protected boolean censored;
	protected boolean gif;
	protected boolean video;
	protected int maxTags = 50;
	@ArrayToStringSeparator("+")
	protected String[] customTags;
	protected String imgPattern;
	protected String selectionPattern;
	protected boolean api;
	protected String defaultTag;
	protected int maxPage = 0;
	protected String pageName = null;
	
	/*
	@ExcludeField
	public static final FuenteImagen Google = new FuenteImagen("Google","https://google.com","",false,true,true,true,50,null,"","",false,null);
	@ExcludeField
	public static final FuenteImagen RandomCat = new FuenteImagen("RandomCat","http://random.cat","http://aws.random.cat/meow",false,"file",null,true);
	@ExcludeField
	public static final FuenteImagen RealBooru = new FuenteImagen("RealBooru","https://realbooru.com","https://realbooru.com/index.php?page=post&s=list&tags=",true,false,true,true,5,null,"https://realbooru.com//images/[0-9]{1,5}/(.{5,50})\\.[a-zA-Z0-9]{2,5}","https://realbooru.com/index.php\\?page=post\\&s=view\\&id=[0-9]{5,7}",false,"male+female",200,"pid");
	@ExcludeField
	public static final FuenteImagen DanBooru = new FuenteImagen("DanBooru","https://danbooru.donmai.us","https://danbooru.donmai.us/posts?tags=",true,false,true,true,2,null,"https://[a-zA-Z]{3,8}.donmai.us/[a-zA-Z]{2,8}/[0-9a-zA-Z%_/]{20,200}.[a-zA-Z0-9]{3,5}","/posts/[0-9]{4,8}",false,"male+female",60,"page");//4340762
	@ExcludeField
	public static final FuenteImagen _3DBooru = new FuenteImagen("3DBooru","http://behoimi.org","http://behoimi.org/post?commit=Search&tags=",false,true,false,false,10,null,"","",false,null);
	@ExcludeField
	public static final FuenteImagen GelBooru = new FuenteImagen("GelBooru","https://gelbooru.com","https://gelbooru.com/index.php?page=post&s=list&tags=",true,false,false,true,10,null,"https://img3.gelbooru.com//images/[0-9a-zA-Z]{1,4}/[0-9a-zA-Z]{1,4}/[0-9a-zA-Z_%]{25,45}.[a-zA-Z0-9]{2,5}", "https://gelbooru.com/index.php\\?page=post&s=view&id=[0-9]{2,10}",false,"male+female",700,"pid");
	@ExcludeField
	public static final FuenteImagen Konachan = new FuenteImagen("Konachan","https://konachan.com","https://konachan.com/post?tags=",true,true,false,false,10,null, "https://konachan.com/image/[0-9a-zA-Z]{15,35}/Konachan.com%20-[0-9a-zA-Z%_]{5,200}.[a-zA-Z0-9]{2,5}","href=\"/post/show/[0-9]{1,10}",false,"male+female",700,"page");
	@ExcludeField
	public static final FuenteImagen KonachanNet = new FuenteImagen("KonachanNet","https://konachan.net","https://konachan.net/post?tags=",false,true,false,false,10,null,"","",false,null);
	@ExcludeField
	public static final FuenteImagen LBooru = new FuenteImagen("LBooru","https://lolibooru.moe","https://lolibooru.moe/post?tags=",false,true,true,true,10,null,"","",false,null);
	@ExcludeField
	public static final FuenteImagen R34 = new FuenteImagen("R34","https://rule34.xxx","https://rule34.xxx/index.php?page=post&s=list&tags=",true,false,true,true,10,null,"https://us.rule34.xxx//images/[0-9a-zA-Z]{2,6}/[0-9a-zA-Z]{25,40}.[a-zA-Z0-9]{3,5}","href=\"index.php\\?page=post&s=view&id=[0-9]{5,9}",false,null);
	@ExcludeField
	public static final FuenteImagen SafeBooru = new FuenteImagen("SafeBooru","https://safebooru.org","https://safebooru.org/index.php?page=post&s=list&tags=",false,true,true,true,10,null,"","",false,null);//https://e-shuushuu.net/search/results/?tags=26 OTRO SAFE OTAKU
	@ExcludeField
	public static final FuenteImagen XBooru = new FuenteImagen("XBooru","https://xbooru.com","https://xbooru.com/index.php?page=post&s=list&tags=",true,false,true,true,10,null,"https://img.xbooru.com//images/[0-9a-zA-Z]{1,5}/[0-9a-zA-Z_%]{5,40}.[a-zA-Z0-9]{2,5}","index.php\\?page=post&s=view&id=[0-9]{4,8}",false,"male+female",3000,"pid");
	@ExcludeField
	public static final FuenteImagen Yandere = new FuenteImagen("Yandere","https://yande.re","https://yande.re/post?tags=",true,false,false,false,10,null,"https://files.yande.re/sample/[0-9a-zA-Z]{15,40}/yande.re[0-9a-zA-Z%_]{20,250}.[a-zA-Z0-9]{2,5}","https://yande.re/post/show/[0-9]{4,10}",false,"male+female",990,"page");
	*/
	@ExcludeField
	public static final String Google = "Google";
	@ExcludeField
	public static final String RandomCat = "RandomCat";
	@ExcludeField
	public static final String RealBooru = "RealBooru";
	@ExcludeField
	public static final String DanBooru = "DanBooru";//4340762
	@ExcludeField
	public static final String _3DBooru = "3DBooru";
	@ExcludeField
	public static final String GelBooru = "GelBooru";
	@ExcludeField
	public static final String Konachan = "Konachan";
	@ExcludeField
	public static final String KonachanNet = "KonachanNet";
	@ExcludeField
	public static final String LBooru = "LBooru";
	@ExcludeField
	public static final String R34 = "R34";
	@ExcludeField
	public static final String SafeBooru = "SafeBooru";//https://e-shuushuu.net/search/results/?tags=26 OTRO SAFE OTAKU
	@ExcludeField
	public static final String XBooru = "XBooru";
	@ExcludeField
	public static final String Yandere = "Yandere";
	
	/*public static void iniciar() throws Exception {
		System.out.println("  0%");
		CommandManager.insert(Google, false);
		CommandManager.insert(RandomCat, false);
		CommandManager.insert(RealBooru, false);
		System.out.println(" 25%");
		CommandManager.insert(_3DBooru, false);
		CommandManager.insert(GelBooru, false);
		CommandManager.insert(Konachan, false);
		System.out.println(" 50%");
		CommandManager.insert(KonachanNet, false);
		CommandManager.insert(LBooru, false);
		CommandManager.insert(R34, false);
		System.out.println(" 75%");
		CommandManager.insert(SafeBooru, false);
		CommandManager.insert(XBooru, false);
		CommandManager.insert(Yandere, false);
		System.out.println("100%");
	}*/
	public void show() {
		System.out.println("\n*********************");
		System.out.println(">"+this.name);
		System.out.println(">"+this.url);
		System.out.println(">"+this.queryUrl);
		System.out.println(">"+this.nsfw);
		System.out.println(">"+this.censored);
		System.out.println(">"+this.gif);
		System.out.println(">"+this.video);
		System.out.println(">"+this.maxTags);
		System.out.println(">"+this.customTags);
		System.out.println(">"+this.imgPattern);
		System.out.println(">"+this.selectionPattern);
		System.out.println(">"+this.api);
		System.out.println(">"+this.defaultTag);
		System.out.println(">"+this.maxPage);
		System.out.println(">"+this.pageName);
		System.out.println("*********************\n");
	}
	
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
	public FuenteImagen() {}
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
