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
package com.qlangtech.tis.runtime.module.screen;

import com.alibaba.citrus.turbine.Context;
import com.qlangtech.tis.manage.spring.aop.Func;

/* *
 * @author 百岁（baisui@qlangtech.com）
 * @date 2019年1月17日
 */
public class Jarlist extends BasicScreen {

    /**
     */
    private static final long serialVersionUID = 1L;

    @Func("core_config_resource_manage")
    public void execute(Context context) throws Exception {
    // this.enableChangeDomain(context);
    // 
    // AppDomainInfo domain = getAppDomain();
    // AppPackageCriteria criteria = new AppPackageCriteria();
    // criteria.createCriteria().andAppIdEqualTo(domain.getAppid());
    // 
    // criteria.setOrderByClause("pid desc");
    // 
    // List<AppPackage> packageList = this.getAppPackageDAO().selectByExample(
    // criteria, 1, 30);
    // 
    // ConfirmLinkBuilder confirmLinkBuilder = new ConfirmLinkBuilder(this
    // .getUriService(), this.getFormService());
    // 
    // List<AppPackageAdapter> adapterList = new ArrayList<AppPackageAdapter>();
    // for (AppPackage pack : packageList) {
    // adapterList.add(new AppPackageAdapter(pack, confirmLinkBuilder));
    // }
    // 
    // context.put("packageList", adapterList);
    }
}