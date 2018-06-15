/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tonybkru.questbot;

import org.apache.http.HttpHost;
import tonybkru.common.util.QueriesEngine;
import tonybkru.questbot.bot.document.spi.DocumentMarshaller;
import tonybkru.questbot.bot.document.spi.JsonDocumentMarshallerImpl;
import tonybkru.questbot.bot.schema.Action;
import tonybkru.questbot.bot.state.QuestStateHolder;
import tonybkru.questbot.model.ClsDocType;
import tonybkru.questbot.model.repository.ClassifierRepository;
import tonybkru.questbot.model.repository.ClassifierRepositoryImpl;

import java.io.*;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author timofeevan
 */
public class AppEnv {

    public static String PROXY_HOST1 = "177.66.119.21";
    public static Integer PROXY_PORT1 = 3128;

    private Map environments = new HashMap();

    private static AppEnv CONTEXT;

    private DocumentMarshaller marshaller = new JsonDocumentMarshallerImpl(Action.class, ClsDocType.ACTION);

    private ClassifierRepository classifierRepository = new ClassifierRepositoryImpl();

    private QuestStateHolder questStateHolder;

    private AppEnv() {

    }

    private void initManagers() {
        questStateHolder =  new QuestStateHolder();
    }

    private Properties initProperties(String propFileName) {
        Properties properties = null;
//        File fProp = null;
//        String propDir = null;
//        if (propFileName == null) {
//            propFileName = "conf.properties";
//            propDir = "conf";
//
//            fProp = new File(propDir + File.separator + propFileName);
//        } else {
//            fProp = new File(propFileName);
//        }
//
//        if (!fProp.exists()) {
//            Logger.getLogger(AppEnv.class.getName()).log(Level.SEVERE, "not exists: " + fProp.getAbsolutePath());
//            fProp = new File(".." + "/" + propDir + "/" + propFileName);
//            if (!fProp.exists()) {
//                Logger.getLogger(AppEnv.class.getName()).log(Level.SEVERE, "not exists: " + fProp.getAbsolutePath());
//                fProp = new File("...." + "/" + propDir + "/" + propFileName);
//                if (!fProp.exists()) {
//                    Logger.getLogger(AppEnv.class.getName()).log(Level.SEVERE, "not exists: " + fProp.getAbsolutePath());
//                    fProp = new File(propFileName);
//                    Logger.getLogger(AppEnv.class.getName()).log(Level.SEVERE, "default: " + fProp.getAbsolutePath());
//                }
//            }
//        }
//        if (fProp.exists()) {
//            try (InputStream fis = new FileInputStream(fProp);) {
//                properties = new Properties();
//                properties.load(new InputStreamReader(fis, Charset.forName("UTF-8")));
//                for (Map.Entry<Object, Object> entry : properties.entrySet()) {
//                    Logger.getLogger(AppEnv.class.getName()).log(Level.SEVERE, entry.getKey() + " = " + entry.getValue());
//                }
//                environments.putAll(properties);
//                Bot.TOKEN = properties.getProperty("TOKEN");
//                Bot.NAME = properties.getProperty("USERNAME");
//            } catch (FileNotFoundException ex) {
//                Logger.getLogger(AppEnv.class.getName()).log(Level.SEVERE, null, ex);
//            } catch (IOException ex) {
//                Logger.getLogger(AppEnv.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }
        return properties;
    }

    public ClassifierRepository getClassifierRepository() {
        return classifierRepository;
    }

    public DocumentMarshaller getMarschaller() {
        return marshaller;
    }


    public void init(String propFileName) {
        initProperties(propFileName);
        initManagers();
    }

    public void init() {
        initProperties(null);

        //String db = "db";
        //String dbUrl = "jdbc:h2:" + getRootPath() + File.separator + db + File.separator + "QUEB;AUTO_SERVER=TRUE"; //<property name=\"javax.persistence.jdbc.url\" value=\
        //Map hm = new HashMap();
        //hm.put("javax.persistence.jdbc.url", dbUrl);

        //QueriesEngine dao = QueriesEngine.instance("QUE", hm);

        //((ClassifierRepositoryImpl) getClassifierRepository()).setDAO(dao);

        //initManagers();
    }

    public static AppEnv getContext(String propFileName) { //https://habrahabr.ru/post/129494/
        if (CONTEXT == null) {
            CONTEXT = new AppEnv();
            CONTEXT.init(propFileName);
        }
        return CONTEXT;
    }

    public static AppEnv getContext() { //https://habrahabr.ru/post/129494/
        if (CONTEXT == null) {
            CONTEXT = new AppEnv();
            CONTEXT.init();
        }
        return CONTEXT;
    }

    public HttpHost getProxy() {
        //if (environments.get(PROXY_HOST) != null && environments.get(PROXY_PORT) != null
        //        && environments.get(PROXY_USE) != null && "true".equalsIgnoreCase((String) environments.get(PROXY_USE))) {
            try {
                HttpHost proxy = new HttpHost(PROXY_HOST1, PROXY_PORT1);
                return proxy;
            } catch (NumberFormatException ex) {
                Logger.getLogger(AppEnv.class.getName()).log(Level.SEVERE, null, ex);
            }
        //}
        return null;
    }

    public QuestStateHolder getQuestStateHolder() {
        return questStateHolder;
    }
}
