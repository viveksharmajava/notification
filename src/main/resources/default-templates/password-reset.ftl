<!DOCTYPE html>
<html>
<head><meta charset="UTF-8"/><title>Password reset</title></head>
<body style="font-family: Arial, sans-serif; color: #222;">
  <h2>Reset your password</h2>
  <p>Hi<#if customerName??> ${customerName}<#elseif userLogin?? && userLogin.userLoginId??> ${userLogin.userLoginId}</#if>,</p>
  <p>Click the link below to reset your password.</p>
  <#if resetLink??>
  <p><a href="${resetLink}">${resetLink}</a></p>
  </#if>
  <p style="margin-top: 24px;">— PlayPro</p>
</body>
</html>
