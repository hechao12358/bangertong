package com.wode.bangertong.common.utils;

import java.util.Random;

public class CheckCodeGenerator
{

  /**
   * 检验码的最大长度
   */
  private static final int MAX_CHECK_CODE_LENGTH = 6;

  /**
   * 用于产生检验码的最大数值
   */
  private static final int MAX_NUMBER = 1000000;

  private static final Random random = new Random();
  
  /**
   * 生成6位的验证码
   * 
   * @return
   */
  public static final String generateCheckCode()
  { 
    String checkCode = String.valueOf((int) (random.nextFloat() * MAX_NUMBER));
    /**
     * 如果产生的验证码长度小于6，那么前面补0到6位，如果长于6位，那么直接截取前六位
     */
    if (checkCode.length() < MAX_CHECK_CODE_LENGTH)
    {
      StringBuilder zeroString = new StringBuilder(MAX_CHECK_CODE_LENGTH - checkCode.length());
      for (int i = 0; i < MAX_CHECK_CODE_LENGTH - checkCode.length(); i++)
      {
        // 为小于六位的字符串前面补0
        zeroString.append("0");
      }
      checkCode = zeroString.toString() + checkCode;
    }
    else if (checkCode.length() > MAX_CHECK_CODE_LENGTH)
    {
      checkCode = checkCode.substring(0, MAX_CHECK_CODE_LENGTH);
    }

    return checkCode;
  }

  /**
   * 生成4位的验证码
   * 
   * @return
   */
  public static final String generateVerifyCode()
  {
    String vc = String.format("%04d", ((int) (random.nextFloat() * 9999)));
    return vc;
  }
//
//  public static void main(String[] args) {
//    System.out.println(generateCheckCode());
//  }

}
