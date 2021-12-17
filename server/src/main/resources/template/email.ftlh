<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Spring Boot Email using FreeMarker</title>
</head>
<body>
<div style="margin-top: 10px">Greetings</div>
<div> <b> There is the bonus text: </b></div>
<p> ${recipient.bonusText} </p>

<div>The vendorInfo registration form got filled with the following credentials:  <br>
    Company name : ${recipient.vendorInfo.companyName} <br>
    Contact name : ${recipient.vendorInfo.contactName} <br>
    Address : ${recipient.vendorInfo.address} <br>
    Iban : ${recipient.vendorInfo.iban} <br>
    Phone : ${recipient.vendorInfo.phone} <br>
    Website : ${recipient.vendorInfo.website} <br>
    Notes : ${recipient.vendorInfo.notes} <br>
    Date Created : ${recipient.vendorInfo.dateCreated} <br>
    If everything is fine You can verify <b> <i> here </i></b> (supposed to be link) <br>
</div>
<br>
<p>
    Best regards, ${recipient.user.email}
</p>
<br/>
<div> Have a nice day..!</div>
</body>
</html>
