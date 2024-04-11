package com.yaru.TimeBank.utils;

import com.yaru.TimeBank.entity.TimeBank;
import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.response.EthGetBalance;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;
import org.web3j.tuples.generated.Tuple3;
import org.web3j.tx.ClientTransactionManager;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.StaticGasProvider;
import org.web3j.utils.Convert;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class GethUtils {
    private final Web3j web3j = Web3j.build(new HttpService("http://localhost:8545"));// Web3j实例，用于与以太坊节点通信// 替换为您的RPC节点URL
    private final Credentials credentials;// 交易管理器，处理交易相关操作
    private final TimeBank contract;// TimeBank智能合约实例，用于与合约交互
    private final String contractAddress = "0xfc2d8584f1f042f041545cd928b3ad267782e12c";// 智能合约地址，替换为您的智能合约地址
    String privateKey = "0x9521878a143730d9fdf1d88b2fcdba57e063e0a2";
    public GethUtils() throws CipherException, IOException {
        // 使用私钥创建交易管理器
        credentials = WalletUtils.loadCredentials("001","D:\\Geth\\data1\\keystore\\UTC--2024-03-17T09-10-43.493583000Z--9521878a143730d9fdf1d88b2fcdba57e063e0a2");
        // Gas提供器，设置固定的Gas价格和Gas限制
        ContractGasProvider gasProvider = new StaticGasProvider(BigInteger.valueOf(200000000000L), BigInteger.valueOf(3963434L));
        // 加载TimeBank智能合约
        this.contract = TimeBank.load(contractAddress, web3j, credentials, gasProvider);
    }

    /////////基本geth方法
    //解锁账户
    public boolean UnlockAccount(String keystorePath,String password){
        try {
            Credentials credentials = WalletUtils.loadCredentials(password, keystorePath);
            // 成功解锁账户后，credentials对象包含了私钥，可以用于签名交易等操作
        } catch (IOException | CipherException e) {
            System.out.println("解锁账户失败: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
        return true;
    }

    //查询账户
    public String searchAccount(String address) throws ExecutionException, InterruptedException, IOException {
//        String privateKey = "0x9521878a143730d9fdf1d88b2fcdba57e063e0a2";
//        RemoteCall<BigInteger> getBalanceCall = client.getBalance(address);
//        BigInteger balance = getBalanceCall.sendAsync().get();
////        System.out.println("账户余额：" + balance);
        // 调用eth_getBalance以获取余额
        EthGetBalance ethGetBalance = web3j.ethGetBalance(address, DefaultBlockParameterName.LATEST).send();

        // 将得到的余额从wei转换为ether
        String balance = String.valueOf(Convert.fromWei(ethGetBalance.getBalance().toString(), Convert.Unit.ETHER));
        return balance;
    }

    //创建账户
    public String CreateAccount(String password) throws InvalidAlgorithmParameterException, CipherException, IOException, NoSuchAlgorithmException, NoSuchProviderException {
        // 钱包存放路径
        String walletFilePath = "D:\\Geth\\data1\\keystore";
        //生成钱包，对应目录下会创建对应的私钥文件。
        String walletFileName = WalletUtils.generateNewWalletFile(password, new File(walletFilePath), false);
        // 加载指定位置的钱包
        Credentials credentials = WalletUtils.loadCredentials(password, walletFilePath + "/" + walletFileName);
        String address = credentials.getAddress();
        String address1 = address.substring(2);
        //获取文件名
        String path = "D:\\Geth\\data1\\keystore"; // 路径
        String realPath = null;
        File f = new File(path);//获取路径
        File fa[] = f.listFiles();//用数组接收
        for (int i = 0; i < fa.length; i++) {//循环遍历
            File fs = fa[i];//获取数组中的第i个
            if (fs.getName().substring(37)==address1) {
                realPath = "D:\\Geth\\data1\\keystore\\"+fs.getName();
            }
        }
//        System.out.println("address:" + address);
//        System.out.println("PrivateKey:" + credentials.getEcKeyPair().getPrivateKey());
//        System.out.println("PublicKey:" + credentials.getEcKeyPair().getPublicKey());
        return realPath;
    }

    /////////合约方法
    public void addcharacter(String address1,String address2) throws Exception {
        RemoteFunctionCall<TransactionReceipt> Remote1 = contract.addCharacter(address1,"1");
        TransactionReceipt addaddress1 = Remote1.sendAsync().get();
        String transactionHash1 = addaddress1.getTransactionHash();
        System.out .println(transactionHash1);
        RemoteFunctionCall<TransactionReceipt> Remote2 = contract.addCharacter(address2,"2");
        TransactionReceipt addaddress2 = Remote2.sendAsync().get();
        String transactionHash2 = addaddress2.getTransactionHash();
        System.out.println(transactionHash2);
    }
    //初始化交易合约
    public void deploycontract() throws ExecutionException, InterruptedException {
        RemoteFunctionCall<TransactionReceipt> deployC = contract.deployEvi();
        TransactionReceipt deployT = deployC.sendAsync().get();
    }
    //管理员创建合约，加入合约唯一标识
    public void newcontract(String onlypassword) throws Exception {
        RemoteFunctionCall<TransactionReceipt> newC =  contract.newEvi(onlypassword);
        TransactionReceipt newT = newC.sendAsync().get();
        List<String> qwer = contract.getAllCharater().send();
        System.out.println(qwer);
    }
    //交易双方签名 签名方法
    public void signcontract(String address,String password) throws CipherException, IOException, ExecutionException, InterruptedException {
        ContractGasProvider gasProvider = new StaticGasProvider(BigInteger.valueOf(200000000000L), BigInteger.valueOf(3963434L));
//        TransactionManager transactionManager1 = new ClientTransactionManager(web3j, address);

        Credentials credentials = WalletUtils.loadCredentials(password, address);
        TimeBank contract1 = TimeBank.load(contractAddress, web3j, credentials, gasProvider);;
        RemoteFunctionCall<TransactionReceipt> signR =  contract1.sign();
        TransactionReceipt signT = signR.sendAsync().get();
    }
    //判断三方签名是否成功并判断唯一标识是否正确，进行交易
    public boolean getcontract() throws Exception {
        RemoteFunctionCall<Tuple3<String, List<String>, List<String>>>re = contract.getEvi();
        CompletableFuture<Tuple3<String, List<String>, List<String>>> result = re.sendAsync();
        Tuple3<String, List<String>, List<String>> res =  result.get();
        List<String> secondelement = res.getValue2();
        List<String> thirdelement = res.getValue3();

        Collections.sort(secondelement);
        Collections.sort(thirdelement);
        System.out.println(Objects.equals(secondelement.get(0), thirdelement.get(0)) );
        if(Objects.equals(secondelement.get(0), thirdelement.get(0)) && Objects.equals(secondelement.get(1), thirdelement.get(1)) && Objects.equals(secondelement.get(2), thirdelement.get(2))){
            return true;
        }
        else {
            return false;
        }
    }
    public List<String> getC() throws Exception {
        RemoteFunctionCall<Tuple3<String, List<String>, List<String>>>re = contract.getEvi();
        CompletableFuture<Tuple3<String, List<String>, List<String>>> result = re.sendAsync();
        Tuple3<String, List<String>, List<String>> res =  result.get();
        List<String> secondelement = res.getValue2();
        return secondelement;
    }
    //删除合约人员信息
    public void deletecharacter(String address1,String address2,String address3) throws ExecutionException, InterruptedException {
        RemoteFunctionCall<TransactionReceipt> deleteR1 = contract.removeCharacter(address1);
        TransactionReceipt deleteT1 = deleteR1.sendAsync().get();
        RemoteFunctionCall<TransactionReceipt> deleteR2 = contract.removeCharacter(address2);
        TransactionReceipt deleteT2 = deleteR2.sendAsync().get();
        RemoteFunctionCall<TransactionReceipt> deleteR3 = contract.removeCharacter(address3);
        TransactionReceipt deleteT3 = deleteR3.sendAsync().get();
    }

}
