package com.tjr.utils;

import jodd.io.FileUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.ResourceUtils;

import java.io.*;
import java.net.URL;

public class JavaPreJalopy {


    public static String formatJava(String path,String content){
        if(StringUtils.isEmpty(content)){
            return null;
        }
        StringBuffer sb = new StringBuffer();
        BufferedReader reader = null;
        String filePath = path+"/abcdefg.java";
        File file = new File(filePath);
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            Jalopy jalopy = new Jalopy();
            InputStream is = new ByteArrayInputStream(content.getBytes("UTF-8"));
            jalopy.setInput(is, filePath);
            jalopy.setOutput(file);
            jalopy.format();
            if (jalopy.getState() == Jalopy.State.OK) {
                reader = new BufferedReader(new FileReader(file));
                String tempString = null;
                while ((tempString = reader.readLine()) != null) {
                    sb.append(tempString).append("\n");
                }
                reader.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
            file.delete();
        }
        return sb.toString();
    }


    public static void main(String[] args) throws Exception {
        String content = "   package com.tjr.utils;\n" +
                "\n" +
                "   import net.sf.ehcache.Cache;\n" +
                "import net.sf.ehcache.CacheManager;\n" +
                "import net.sf.ehcache.Element;\n" +
                "import org.springframework.beans.factory.annotation.Autowired;\n" +
                "import org.springframework.context.annotation.Lazy;\n" +
                "import org.springframework.stereotype.Component;\n" +
                "\n" +
                "/**\n" +
                " *    EhCache 缓存工具类\n" +
                " * 使用String格式的value\n" +
                " *\n" +
                " * {@link com.spz.demo.singleboot.config.EhcacheConfig}\n" +
                " * {@link CacheConst}\n" +
                " * @author zp\n" +
                " */\n" +
                "@Component\n" +
                "public class EhcacheUtil {\n" +
                "\n" +
                "    @Autowired\n" +
                "       @Lazy\n" +
                "    private CacheManager cacheManager;\n" +
                "\n" +
                "    // 默认的缓存存在时间（秒）\n" +
                "    private static final int DEFAULT_LIVE_SECOND = 20 * 60;\n" +
                "\n" +
                "    /**\n" +
                "     * 添加缓存\n" +
                "     * @param key\n" +
                "     * @param value\n" +
                "     * @param timeToLiveSeconds 缓存生存时间（秒）\n" +
                "     */\n" +
                "            public void set(String key,String value,int timeToLiveSeconds){\n" +
                "        Cache cache = cacheManager.getCache(CacheConst.PROJECT.getName());\n" +
                "        Element element = new Element(\n" +
                "                key, value,\n" +
                "                0,// timeToIdleSeconds=0\n" +
                "                timeToLiveSeconds);\n" +
                "        cache.put(element);\n" +
                "    }\n" +
                "\n" +
                "    /**\n" +
                "     * 添加缓存\n" +
                "     * 使用默认生存时间 {@link DEFAULT_LIVE_SECOND}\n" +
                "     * @param key\n" +
                "     * @param value\n" +
                "     */\n" +
                "    public void set(String key,String value){\n" +
                "        Cache cache = cacheManager.getCache(CacheConst.PROJECT.getName());\n" +
                "        Element element = new Element(\n" +
                "                key, value,\n" +
                "                0,// timeToIdleSeconds\n" +
                "                DEFAULT_LIVE_SECOND);\n" +
                "                                      cache.put(element);\n" +
                "    }\n" +
                "\n" +
                "    /**\n" +
                "     * 添加缓存\n" +
                "     * @param key\n" +
                "     * @param value\n" +
                "                           * @param timeToIdleSeconds 对象空闲时间，指对象在多长时间没有被访问就会失效。\n" +
                "     *                          只对eternal为false的有效。传入0，表示一直可以访问。以秒为单位。\n" +
                "     * @param timeToLiveSeconds 缓存生存时间（秒）\n" +
                "     *                          只对eternal为false的有效\n" +
                "     */\n" +
                "    public void set(String key,String value,int timeToIdleSeconds, int timeToLiveSeconds){\n" +
                "        Cache cache = cacheManager.getCache(CacheConst.PROJECT.getName());\n" +
                "        Element element = new Element(\n" +
                "                key, value,\n" +
                "                timeToIdleSeconds,\n" +
                "                timeToLiveSeconds);\n" +
                "        cache.put(element);\n" +
                "    }\n" +
                "\n" +
                "    /**\n" +
                "     * 获取缓存\n" +
                "     * @param key\n" +
                "     * @return\n" +
                "     */\n" +
                "    public String get(String key){\n" +
                "        Cache cache = cacheManager.getCache(CacheConst.PROJECT.getName());\n" +
                "        Element element = cache.get(key);\n" +
                "        if(element == null){\n" +
                "            return null;\n" +
                "        }\n" +
                "        return (String) element.getObjectValue();\n" +
                "    }\n" +
                "\n" +
                "}\n";
        File file = new File("D:\\java\\uploadFile");
        if (!file.exists()) {
            file.mkdirs();
        }


        try {

            String result = formatJava(file.getAbsolutePath(),content);
            System.out.println(result);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

}
