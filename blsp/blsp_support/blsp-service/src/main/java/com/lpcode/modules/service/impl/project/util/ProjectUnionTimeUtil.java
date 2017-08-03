package com.lpcode.modules.service.impl.project.util;

import com.lpcode.modules.blsp.entity.PrjTask;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author Pengs
 * @package com.lpcode.modules.service.impl.project.util
 * @fileName ProjectUnionTimeUtil
 * @date 16/3/22.
 */
public class ProjectUnionTimeUtil {

    /**
     * 比较取出集合中的所有事项日期时间段（事项开始时间到事项结束时间）的并集
     *
     * @param prjTaskList
     * @return
     */
    public static Map matchPrjTaskList(List<PrjTask> prjTaskList) {
        Map<Long, PrjTask> prjTaskMap = new HashMap<>();
        Map<Long, PrjTask> resultMap = new HashMap<>();
        for (PrjTask prjTask : prjTaskList) {
            prjTaskMap.put(prjTask.getId(), prjTask);
        }
        boolean flag = true;
        for (Long key : prjTaskMap.keySet()) {
            PrjTask prjTask = prjTaskMap.get(key);
            for (Long key1 : prjTaskMap.keySet()) {
                PrjTask prjTask1 = prjTaskMap.get(key1);
                if (crossTaskTime(prjTask, prjTask1)) {
                    prjTask1 = mactchNewUnionTime(prjTask1, prjTask);
                    resultMap.put(prjTask1.getId(), prjTask1);
                    flag = false;
                    break ;
                }

            }
            if(flag){
                resultMap.put(prjTask.getId(), prjTask);
            }
        }
        return resultMap;
    }

    /**
     * 取两个有交集时间段的并集（最大开始和结束区间）
     *
     * @param prjTask
     * @param prjTask1
     * @return
     */
    private static PrjTask mactchNewUnionTime(PrjTask prjTask, PrjTask prjTask1) {
        Date startDate = prjTask.getTaskStartTime();
        Date endDate = prjTask.getTaskRealEndtime();
        Date startDate1 = prjTask1.getTaskStartTime();
        Date endDate1 = prjTask1.getTaskRealEndtime();
        if (startDate.after(startDate1)) {
            prjTask.setTaskStartTime(startDate1);
        }
        if (endDate.before(endDate1)) {
            prjTask.setTaskRealEndtime(endDate1);
        }
        return prjTask;
    }

    /**
     * 判断两个事项中是否有时间交差
     *
     * @param prjTask
     * @param prjTask1
     * @return
     */
    private static boolean crossTaskTime(PrjTask prjTask, PrjTask prjTask1) {
        Date startDate = prjTask.getTaskStartTime();
        Date endDate = prjTask.getTaskRealEndtime();
        Date startDate1 = prjTask1.getTaskStartTime();
        Date endDate1 = prjTask1.getTaskRealEndtime();
        if (endDate.after(startDate1) && startDate.before(endDate1)) {
            return true;
        } else {
            return false;
        }
    }

    private Date createDate(String str) throws ParseException {
        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
        return sf.parse(str);
    }

    private void test() throws ParseException {
        PrjTask prjTask1 = new PrjTask();
        prjTask1.setId(1L);
        prjTask1.setTaskStartTime(createDate("20160101"));
        prjTask1.setTaskRealEndtime(createDate("20160116"));
        PrjTask prjTask2 = new PrjTask();
        prjTask2.setId(2L);
        prjTask2.setTaskStartTime(createDate("20160102"));
        prjTask2.setTaskRealEndtime(createDate("20160109"));
        PrjTask prjTask3 = new PrjTask();
        prjTask3.setId(3L);
        prjTask3.setTaskStartTime(createDate("20160115"));
        prjTask3.setTaskRealEndtime(createDate("20160312"));
        PrjTask prjTask4 = new PrjTask();
        prjTask4.setId(4L);
        prjTask4.setTaskStartTime(createDate("20160214"));
        prjTask4.setTaskRealEndtime(createDate("20160217"));
        PrjTask prjTask5 = new PrjTask();
        prjTask5.setId(5L);
        prjTask5.setTaskStartTime(createDate("20161202"));
        prjTask5.setTaskRealEndtime(createDate("20161212"));
        PrjTask prjTask6 = new PrjTask();
        prjTask6.setId(6L);
        prjTask6.setTaskStartTime(createDate("20161202"));
        prjTask6.setTaskRealEndtime(createDate("20161212"));

        List<PrjTask> al = new ArrayList<>();
        al.add(prjTask1);
        al.add(prjTask2);
        al.add(prjTask3);
        al.add(prjTask5);
        al.add(prjTask6);
        al.add(prjTask4);
        Map<Long, PrjTask> resultMap = matchPrjTaskList(al);
        System.out.print(resultMap.size());
    }

    public static void main(String[] args) throws Exception {
        ProjectUnionTimeUtil util = new ProjectUnionTimeUtil();
        util.test();

    }
}
