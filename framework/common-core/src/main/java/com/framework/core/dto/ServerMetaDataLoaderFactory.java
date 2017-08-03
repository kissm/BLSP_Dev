//package dto;
//
//public class ServerMetaDataLoaderFactory{
//
//    private static MetaDataLoader loader;
//
//    public static IMetaDataLoader getLoader()    {
//        if (loader != null)        {
//            return loader;
//
//        }
//
//        ServerConfig config = ServerConfig.getInstance();
//        String entityCacheFile = config.getEntityCacheFile();
//        String metaDataPath = config.getMetaDataPath();
//
//        loader = new MetaDataLoader(metaDataPath, entityCacheFile);
//        loader.setCacheEnable(
//                ServerConfig.getInstance().isMetaCacheEnabled());
//        return loader;
//    }
//
//    static {
//        Runtime.getRuntime().addShutdownHook(new Thread() {
//            public void run(){
//                super.run();
//                ServerMetaDataLoaderFactory.getLoader().saveCache();
//            }
//        });
//    }
//
//}
