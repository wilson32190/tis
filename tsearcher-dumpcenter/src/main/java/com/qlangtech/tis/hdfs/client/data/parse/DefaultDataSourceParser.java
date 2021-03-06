/* 
 * The MIT License
 *
 * Copyright (c) 2018-2022, qinglangtech Ltd
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.qlangtech.tis.hdfs.client.data.parse;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.qlangtech.tis.exception.DataSourceParseException;

/*
 * @description  根据表达式进行分库分表的解析
 * @since  2011-8-3 下午01:33:10
 * @version  1.0
 *
 * @author 百岁（baisui@qlangtech.com）
 * @date 2019年1月17日
 */
public class DefaultDataSourceParser implements DataSourceParser {

    protected static DecimalFormat format = null;

    private static final String DEFAULT_SPLIT_LEVEL1 = ";";

    private static final String DEFAULT_SPLIT_LEVEL2 = ":";

    private static final String DEFAULT_SPLIT_LEVEL3 = ",";

    private static final String DEFAULT_SPLIT_LEVEL4 = "-";

    protected String defaultSubTableString = "_0000";

    /**
     * @param defaultSubTableString
     * @uml.property  name="defaultSubTableString"
     */
    public void setDefaultSubTableString(String defaultSubTableString) {
        this.defaultSubTableString = defaultSubTableString;
    }

    public void init() {
        format = new DecimalFormat(defaultSubTableString);
    }

    @Override
    public Map<String, List<String>> parseDescription(String desc) throws DataSourceParseException {
        if (format == null) {
            throw new DataSourceParseException("请在调用该方法之前调用init()方法对format进行初始化操作");
        }
        String base = this.preprocess(desc);
        String[] parseDesc = base.split(DEFAULT_SPLIT_LEVEL1);
        Map<String, List<String>> resultMap = new HashMap<String, List<String>>();
        for (int i = 0; i < parseDesc.length; i++) {
            String[] dsts = parseDesc[i].split(":");
            if (dsts.length == 2) {
                String ds = dsts[0];
                String ts = dsts[1];
                List<String> tablesSuffix = null;
                if (resultMap.containsKey(ds)) {
                    tablesSuffix = resultMap.get(ds);
                } else {
                    tablesSuffix = new ArrayList<String>();
                    resultMap.put(ds, tablesSuffix);
                }
                this.parseTablesSuffix(tablesSuffix, ts);
            } else if (dsts.length == 1) {
                resultMap.put(dsts[0], null);
            } else {
                throw new DataSourceParseException("数据源表达式不符合规范");
            }
        }
        return resultMap;
    }

    private void parseTablesSuffix(List<String> tablesSuffix, String tables) throws DataSourceParseException {
        String[] suffixs = tables.split(DEFAULT_SPLIT_LEVEL3);
        if (suffixs.length > 1) {
            for (int i = 0; i < suffixs.length; i++) {
                int index = suffixs[i].indexOf(DEFAULT_SPLIT_LEVEL4);
                if (index != -1) {
                    String start = suffixs[i].substring(0, index);
                    String end = suffixs[i].substring(index + 1);
                    parseZone(start, end, tablesSuffix);
                } else {
                    tablesSuffix.add(format.format(Integer.valueOf(suffixs[i])));
                }
            }
        } else if (suffixs.length == 1) {
            int index = suffixs[0].indexOf(DEFAULT_SPLIT_LEVEL4);
            if (index != -1) {
                String start = suffixs[0].substring(0, index);
                String end = suffixs[0].substring(index + 1);
                parseZone(start, end, tablesSuffix);
            } else {
                tablesSuffix.add(format.format(Integer.valueOf(suffixs[0])));
            }
        }
    }

    protected void parseZone(String begin, String end, List<String> store) throws DataSourceParseException {
        try {
            Integer head = Integer.valueOf(begin);
            Integer tail = Integer.parseInt(end);
            Integer tmp = null;
            if (head > tail) {
                tmp = tail;
                tail = head;
                head = tmp;
            }
            for (int i = head; i <= tail; i++) {
                store.add(format.format(i));
            }
        } catch (NumberFormatException nfe) {
            throw new DataSourceParseException("解析数据源表达式解析数字区间出现错误，输入值为begin:" + begin + ",end:" + end, nfe);
        }
    }

    protected String preprocess(String raw) {
        return raw.replaceAll(" ", "");
    }

    public static void main(String[] args) {
        DefaultDataSourceParser parser = new DefaultDataSourceParser();
        parser.init();
        String raw = "ds1:2,3,5-7, 9 ;ds2: 1,4,11,20, 21-25;ds4:30-32";
        String raw1 = "ds1;ds2";
        String raw2 = "ds2:7-5,23,2;ds3:1-34";
        long begin = System.nanoTime();
        try {
            System.out.println(parser.parseDescription(raw));
        } catch (DataSourceParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        // int index = "2-10".indexOf(DEFAULT_SPLIT_LEVEL4);
        // String start = "2-10".substring(0, index);
        // String end = "2-10".substring(index + 1);
        // System.out.println("start " + start);
        // System.out.println("end " + end);
        System.out.println("total spend time: " + (System.nanoTime() - begin) / 1000000.0 + "ms");
    }
}
