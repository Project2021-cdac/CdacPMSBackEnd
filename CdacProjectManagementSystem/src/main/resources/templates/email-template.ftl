<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>CPMS Registration Email</title>
<style>
	h2{
    	text-align: center;
	}
	
	.center-aligner{
	    display: block;
	    margin-left: auto;
	    margin-right: auto;
	}
	
	#welcome{	
	    background:#03a9f4;
	    padding-left:10px;
	    padding-right:10px;
	    padding-top:5px;
	    padding-bottom: 5px;
	    font-family: cursive;
	    font-size: x-large;
	    font-weight: bold;
	    border:1px solid #2196f3;
	    border-radius: 35px;
	}
	
	#user{
	    border-radius:50px;
	    border: 1px solid #303f9f;
	    width:500px;
	    padding:20px;
	    text-align: center;
	    background: rgb(34,166,195);
	    background: radial-gradient(circle, rgba(34,166,195,1) 0%, rgba(67,79,176,1) 78%);
	}
	
	.cred-text{
	    color:white;
	    font-weight: bolder;
	}
	
	#cpms{
	    background: #49aac4;
	    width: 200px;
	    padding:5px;
	    margin:10px;
	    border:2px solid #4340ff;
	    border-radius: 20px;
	    text-align: center;
	}
	
	#link-address{
	    text-decoration: none;
	    color:white;
	    font-weight: bolder;
	}
	
	#content-area{
    	max-width:500px;
    	margin:auto;
	}
	
	.redirect-button{
	    position: absolute;
	    top: 45%;
	    left: 51%;
	    transform: translate(-50%, -50%);
	}
</style>
</head>
<body>
	<div id="content-area">
		<img class="center-aligner" src="cid:cdac-acts-logo.jpeg" alt="Cdac Acts Logo"/>
		<h2>CDAC Project Managment System</h2>
		<p>Welcome <span id="welcome">${firstname} ${lastname}</span> to CPMS, </p>
	    <p id="msg">Here are your login signature to CPMS</p> 
	    <div id="user" class="center-aligner">
	        <p><span class="cred-text">User Name: ${username}</span></p>
	        <p><span class="cred-text">Signature: ${password}</span></p>
	    </div>
	    <div id="cpms" class="redirect-button">
	        <p><a id="link-address" href="https://www.google.com" target="_blank">Take me to CPMS</a></p>
	    </div>
    </div>
</body>
</html>
