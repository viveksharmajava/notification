<!DOCTYPE html>
<html>
<head><meta charset="UTF-8"/><title>Order shipped</title></head>
<body style="font-family: Arial, sans-serif; color: #222;">
  <h2>Your order is on the way</h2>
  <p>Order <strong>${orderId!''}</strong> has been shipped.</p>
  <#if trackingNumber??>
  <p>Tracking number: <strong>${trackingNumber}</strong></p>
  </#if>
  <#if carrier??>
  <p>Carrier: ${carrier}</p>
  </#if>
  <p style="margin-top: 24px;">— PlayPro</p>
</body>
</html>
