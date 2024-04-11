package com.yaru.TimeBank.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yaru.TimeBank.common.R;
import com.yaru.TimeBank.entity.*;
import com.yaru.TimeBank.service.*;
import com.yaru.TimeBank.utils.GethUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.web3j.crypto.CipherException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/Order")
public class AOrderController {

    @Autowired
    private AccountService accountService;
    @Autowired
    private ElderService elderService;
    @Autowired
    private VolunteerService volunteerService;
    @Autowired
    private ActivityService activityService;
    @Autowired
    private AOrderService orderService;
    @Autowired
    private RequirementService requirementService;
    private HttpSession session;
    private GethUtils gethUtils = new GethUtils();

    public AOrderController() throws CipherException, IOException {
    }

    /**
     * 查询老人账户信息
     * @param request
     * @return
     */
    @GetMapping("/getElderAccount")
    public R<Account> getElderAccount(HttpServletRequest request,
    String sessionvalueE){
        sessionvalueE="2";
        //根据页面页面session查询数据库
        LambdaQueryWrapper<Elder> queryWrapperElder = new LambdaQueryWrapper<>();
        queryWrapperElder.eq(Elder::getId,sessionvalueE);
        Elder eld = elderService.getOne(queryWrapperElder);
        String eldIdentity = eld.getIdentityNumber();
        //根据页面提交的用户名username查询数据库
        LambdaQueryWrapper<Account> queryWrapperAccount = new LambdaQueryWrapper<>();
        queryWrapperAccount.eq(Account::getIdentityNumber,eldIdentity);
        Account acc = accountService.getOne(queryWrapperAccount);

        if(acc == null){
            return R.error("未找到账户");
        }
        String password = acc.getAccountPassword();
        String address = acc.getAccountAddress();
        if(!gethUtils.UnlockAccount(address,password)){
            return R.error("账户解锁失败");
        }

//        request.getSession().setAttribute("employee",emp.getId());
        return R.success(acc);
    }

    /**
     * 查询志愿者账户信息
     * @param request
     * @return
     */
    @GetMapping("/getVolunteerAccount")
    public R<Account> getVolunteerAccount(HttpServletRequest request,
    String sessionvalueV){
        sessionvalueV="2";
        //根据页面页面session查询数据库
        LambdaQueryWrapper<Volunteer> queryWrapperVolunteer = new LambdaQueryWrapper<>();
        queryWrapperVolunteer.eq(Volunteer::getId,sessionvalueV);
        Volunteer vol = volunteerService.getOne(queryWrapperVolunteer);
        String volIdentity = vol.getIdentityNumber();
        //根据页面提交的用户名username查询数据库
        LambdaQueryWrapper<Account> queryWrapperAccount = new LambdaQueryWrapper<>();
        queryWrapperAccount.eq(Account::getIdentityNumber,volIdentity);
        Account acc = accountService.getOne(queryWrapperAccount);

        if(acc == null){
            return R.error("未找到账户");
        }
        String password = acc.getAccountPassword();
        String address = acc.getAccountAddress();
        if(!gethUtils.UnlockAccount(address,password)){
            return R.error("账户解锁失败");
        }

//        request.getSession().setAttribute("employee",emp.getId());
        return R.success(acc);
    }


//    /**
//     * 创建账户
//     * @param request
//     * @return
//     */
//    @PostMapping("/createVolunteerAccount")
//    public R<Account> createElderAccount(HttpServletRequest request,@RequestBody Account account){
//        //根据session取出用户身份证信息
//        session=request.getSession();
//        String sessionvalue = (String) session.getAttribute("volunteer");
//        //根据页面页面session查询数据库
//        LambdaQueryWrapper<Volunteer> queryWrapperVolunteer = new LambdaQueryWrapper<>();
//        queryWrapperVolunteer.eq(Volunteer::getId,sessionvalue);
//        Volunteer vol = volunteerService.getOne(queryWrapperVolunteer);
//        if(vol==null){
//            return R.error("请创建账户");
//        }
//
//
//        account.setAccountStatus("冻结");
//        elderService.save(elder);
//
//        return R.success("老年需求者注册成功");
//    }
    /**
     * 志愿者活动被接收
     * @param request
     * @return
     */
    @PostMapping("/createElderOrder")
    public R<String> createElderOrder(HttpServletRequest request, @RequestBody Activity activity,
    String sessionvalueE) throws Exception {
        sessionvalueE="2";
        //获取志愿者身份证信息
        LambdaQueryWrapper<Activity> queryWrapperActivity = new LambdaQueryWrapper<>();
        queryWrapperActivity.eq(Activity::getId,activity.getId());
        Activity act = activityService.getOne(queryWrapperActivity);
        LambdaQueryWrapper<Volunteer> queryWrapperVolunteer = new LambdaQueryWrapper<>();
        queryWrapperVolunteer.eq(Volunteer::getId,act.getVolunteerId());
        Volunteer vol = volunteerService.getOne(queryWrapperVolunteer);
        String volIdentity = vol.getIdentityNumber();

        LambdaQueryWrapper<Elder> queryWrapperElder = new LambdaQueryWrapper<>();
        queryWrapperElder.eq(Elder::getId,sessionvalueE);
        Elder eld = elderService.getOne(queryWrapperElder);
        String eldIdentity = eld.getIdentityNumber();
        System.out.println(eldIdentity);
        AOrder order = new AOrder();
        order.setId(2L);
        order.setSenderAddress(eldIdentity);
        order.setReceiverAddress(volIdentity);
        order.setOrderTime(LocalDateTime.now());
        order.setState(0);
        order.setFinishTime(2);
        orderService.save(order);

        LambdaQueryWrapper<Account> queryWrapperAccount1 = new LambdaQueryWrapper<>();
        queryWrapperAccount1.eq(Account::getIdentityNumber,volIdentity);
        Account acc1 = accountService.getOne(queryWrapperAccount1);
        String Volunteeracc = acc1.getAccountNumber();

        LambdaQueryWrapper<Account> queryWrapperAccount2 = new LambdaQueryWrapper<>();
        queryWrapperAccount2.eq(Account::getIdentityNumber,eldIdentity);
        Account acc2 = accountService.getOne(queryWrapperAccount2);
        String Elderacc = acc2.getAccountNumber();

        gethUtils.addcharacter(Elderacc,Volunteeracc);
        gethUtils.deploycontract();
        gethUtils.newcontract("0xfc2d8584f1f042f041545cd928b3ad267782e12c");
//        request.getSession().setAttribute("employee",emp.getId());
        return R.success("成功啦");
    }

    /**
     * 志愿者活动被接收
     * @param request
     * @return
     */
    @PostMapping("/createVolunteerOrder")
    public R<String> createVolunteerOrder(HttpServletRequest request, @RequestBody Requirement requirement,
    String sessionvalueV) throws Exception {
        sessionvalueV="2";
        //获取老人身份证信息
        LambdaQueryWrapper<Requirement> queryWrapperRequirement = new LambdaQueryWrapper<>();
        queryWrapperRequirement.eq(Requirement::getId,requirement.getId());
        Requirement req = requirementService.getOne(queryWrapperRequirement);
        LambdaQueryWrapper<Elder> queryWrapperElder = new LambdaQueryWrapper<>();
        queryWrapperElder.eq(Elder::getId,req.getElderId());
        Elder eld = elderService.getOne(queryWrapperElder);
        String eldIdentity = eld.getIdentityNumber();
        //根据session取出志愿者身份证信息
        LambdaQueryWrapper<Volunteer> queryWrapperVolunteer = new LambdaQueryWrapper<>();
        queryWrapperVolunteer.eq(Volunteer::getId,sessionvalueV);
        Volunteer vol = volunteerService.getOne(queryWrapperVolunteer);
        String volIdentity = vol.getIdentityNumber();

        AOrder order = new AOrder();
        order.setId(2L);
        order.setSenderAddress(eldIdentity);
        order.setReceiverAddress(volIdentity);
        order.setOrderTime(LocalDateTime.now());
        order.setState(0);
        order.setFinishTime(2);
        orderService.save(order);

        LambdaQueryWrapper<Account> queryWrapperAccount1 = new LambdaQueryWrapper<>();
        queryWrapperAccount1.eq(Account::getIdentityNumber,eldIdentity);
        Account acc1 = accountService.getOne(queryWrapperAccount1);
        String Elderacc = acc1.getAccountNumber();

        LambdaQueryWrapper<Account> queryWrapperAccount2 = new LambdaQueryWrapper<>();
        queryWrapperAccount2.eq(Account::getIdentityNumber,volIdentity);
        Account acc2 = accountService.getOne(queryWrapperAccount2);
        String Volunteeracc = acc2.getAccountNumber();

        gethUtils.addcharacter(Elderacc,Volunteeracc);
        gethUtils.deploycontract();
        gethUtils.newcontract("0xfc2d8584f1f042f041545cd928b3ad267782e12c");
//        request.getSession().setAttribute("employee",emp.getId());
        return R.success("成功啦");
    }

    /**
     * 查询需求订单（老人）
     * @param page
     * @param pageSize
     * @return
     */
    @GetMapping("/getRequired")
    public R<Page> getRequired(HttpServletRequest request,int page,int pageSize,
    String sessionvalueE) throws Exception {
        sessionvalueE="2";
        LambdaQueryWrapper<Elder> queryWrapperElder = new LambdaQueryWrapper<>();
        queryWrapperElder.eq(Elder::getId,sessionvalueE);
        Elder eld = elderService.getOne(queryWrapperElder);
        String eldIdentity = eld.getIdentityNumber();
        //构造分页构造器
        Page pageInfo = new Page(page,pageSize);

        //构造条件构造器
        LambdaQueryWrapper<AOrder> queryWrapper = new LambdaQueryWrapper();
        //添加过滤条件
        queryWrapper.eq(AOrder::getSenderAddress,eldIdentity);
        //添加排序条件
        queryWrapper.orderByDesc(AOrder::getOrderTime);

        //执行查询
        orderService.page(pageInfo,queryWrapper);

        return R.success(pageInfo);
    }

    /**
     * 查询活动订单(志愿者)
     * @param request
     * @return
     */
    @GetMapping("/getActivity")
    public R<Page> getActivity(HttpServletRequest request,int page,int pageSize,
    String sessionvalueV) throws Exception {
        sessionvalueV="2";
        //根据session取出志愿者身份证信息
        LambdaQueryWrapper<Volunteer> queryWrapperVolunteer = new LambdaQueryWrapper<>();
        queryWrapperVolunteer.eq(Volunteer::getId,sessionvalueV);
        Volunteer vol = volunteerService.getOne(queryWrapperVolunteer);
        String volIdentity = vol.getIdentityNumber();
        //构造分页构造器
        Page pageInfo = new Page(page,pageSize);

        //构造条件构造器
        LambdaQueryWrapper<AOrder> queryWrapper = new LambdaQueryWrapper();
        //添加过滤条件
        queryWrapper.like(true, AOrder::getReceiverAddress,volIdentity);
        //添加排序条件
        queryWrapper.orderByDesc(AOrder::getOrderTime);

        //执行查询
        orderService.page(pageInfo,queryWrapper);

        return R.success(pageInfo);
    }

    /**
     * 查询订单(管理员)
     * @param request
     * @return
     */
    @GetMapping("/getOrder")
    public R<Page> getOrder(HttpServletRequest request,int page,int pageSize) throws Exception {
        //构造分页构造器
        Page pageInfo = new Page(page,pageSize);

        //构造条件构造器
        LambdaQueryWrapper<AOrder> queryWrapper = new LambdaQueryWrapper();
        //添加排序条件
        queryWrapper.orderByDesc(AOrder::getOrderTime);

        //执行查询
        orderService.page(pageInfo,queryWrapper);

        return R.success(pageInfo);
    }

    /**
     * 接收方签名
     * @param request
     * @return
     */
    @PostMapping("/Rsign")
    public R<String> Rsign(HttpServletRequest request, @RequestBody AOrder order) throws Exception {
        String Re = order.getReceiverAddress();
        LambdaQueryWrapper<Account> queryWrapperAccount = new LambdaQueryWrapper<>();
        queryWrapperAccount.eq(Account::getIdentityNumber,Re);
        Account acc = accountService.getOne(queryWrapperAccount);

        gethUtils.signcontract(acc.getAccountAddress(),acc.getAccountPassword());

        order.setState(order.getState()+1);
        System.out.println(order.getState());
        orderService.updateById(order);
        return R.success("成功签名");
    }

    /**
     * 发送方签名
     * @param request
     * @return
     */
    @PostMapping("/Ssign")
    public R<String> Ssign(HttpServletRequest request, @RequestBody AOrder order) throws Exception {
        String Se = order.getSenderAddress();
        LambdaQueryWrapper<Account> queryWrapperAccount = new LambdaQueryWrapper<>();
        queryWrapperAccount.eq(Account::getIdentityNumber,Se);
        Account acc = accountService.getOne(queryWrapperAccount);

        gethUtils.signcontract(acc.getAccountAddress(),acc.getAccountPassword());
        order.setState(order.getState()+1);
        order.setFinishTime(2);
        orderService.updateById(order);
        return R.success("成功签名");
    }

    /**
     * 管理员管理订单
     * @param request
     * @return
     */
    @PostMapping("/Asign")
    public R<String> Asign(HttpServletRequest request, @RequestBody AOrder order) throws Exception {

        List<String> character = gethUtils.getC();
        System.out.println(character);
        if(gethUtils.getcontract()){
            gethUtils.deletecharacter(character.get(0),character.get(1),character.get(2));
            order.setOMoney((order.getFinishTime()*60/10));
            orderService.updateById(order);
            LambdaQueryWrapper<Account> queryWrapperAccount1 = new LambdaQueryWrapper<>();
            queryWrapperAccount1.eq(Account::getIdentityNumber,order.getSenderAddress());
            Account acc1 = accountService.getOne(queryWrapperAccount1);
            acc1.setMoney(acc1.getMoney()-order.getFinishTime()*60/10);
            accountService.updateById(acc1);
            LambdaQueryWrapper<Account> queryWrapperAccount2 = new LambdaQueryWrapper<>();
            queryWrapperAccount2.eq(Account::getIdentityNumber,order.getReceiverAddress());
            Account acc2 = accountService.getOne(queryWrapperAccount2);
            acc2.setMoney(acc2.getMoney()+order.getFinishTime()*60/10);
            accountService.updateById(acc2);
            return R.success("成功");
        }
        else{
            return R.error("无法提交");
        }
    }

}
