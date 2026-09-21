<!DOCTYPE html>
<html>
<head><meta charset="UTF-8"/><title>Order confirmation</title></head>
<body style="font-family: Arial, sans-serif; color: #222;">
  <h2>Thanks for your order<#if customerName??>, ${customerName}</#if>!</h2>
  <p>Your order <strong>${orderId!''}</strong> has been confirmed.</p>
  <#if orderTotal??>
  <p>Order total: <strong>${orderTotal}</strong><#if currency??> ${currency}</#if></p>
  </#if>
  <#if items?? && (items?size > 0)>
  <table cellpadding="8" cellspacing="0" border="1" style="border-collapse: collapse;">
    <thead>
      <tr><th align="left">Item</th><th>Qty</th><th align="right">Price</th></tr>
    </thead>
    <tbody>
      <#list items as item>
      <tr>
        <td>${item.name!''}</td>
        <td align="center">${item.qty!1}</td>
        <td align="right">${item.price!''}</td>
      </tr>
      </#list>
    </tbody>
  </table>
  </#if>
  <p style="margin-top: 24px;">— PlayPro</p>
</body>
</html>
