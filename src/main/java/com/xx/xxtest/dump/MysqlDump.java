package com.xx.xxtest.dump;

/**
 * @author xuxiang
 */
public class MysqlDump {
    public static void dump(SourceParam sourceParam, String fileName) {
        StringBuffer sb = new StringBuffer();
        sb.append("mysqldump")
                .append(" -h").append(sourceParam.getHost())
                .append(" -P").append(sourceParam.getPort())
                .append(" -u").append(sourceParam.getUser())
                .append(" -p").append(sourceParam.getPassword())
                .append(" --database ").append(sourceParam.getDatabase())
                .append(" | gzip > ").append(fileName).append(".gz");
//            String[] cmd = {"/bin/sh", "-c", sb.toString()};
//            String [] cmd={"cmd","/C","copy C:\\Users\\xuxiang\\Desktop\\test.txt C:\\Users\\xuxiang\\Desktop\\test1.txt"};
//            Process process = Runtime.getRuntime().exec(cmd);
//            System.out.println("start...");
//            process.waitFor();
//            System.out.println("exitValue=" + process.exitValue());
        System.out.println(sb.toString());
    }

    public static void main(String[] args) {
        SourceParam sourceParam = new SourceParam();
        sourceParam.setHost("cd-cdb-pm5effd2.sql.tencentcdb.com");
        sourceParam.setPort(63105);
        sourceParam.setUser("gaobang");
        sourceParam.setPassword("");
        sourceParam.setDatabase("gaobang");
        dump(sourceParam, "aa");
    }
}
