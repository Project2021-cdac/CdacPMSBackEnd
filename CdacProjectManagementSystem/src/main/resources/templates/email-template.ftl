<!DOCTYPE html>
<html lang="en">
s<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
	<style>
		h2{
	    	text-align: center;
		}
		
		img{
		    display: block;
		    margin:0 auto;
		}
		
		#welcome{	
		    background:#03a9f4;
			padding:10px 5px; 
			font-family: cursive;
			font-size: x-large;
			font-weight: bold;
			border:1px solid #2196f3;
			border-radius: 35px;
		}
		
		#user{
		    text-align: center;
		    margin:0 auto;
		    padding:20px;
		    color:black;
		    border-radius:50px;
		    border: 1px solid #303f9f;
		    background: rgb(34,166,195);
		    background: radial-gradient(circle, rgba(34,166,195,1) 0%, rgba(67,79,176,1) 78%);
		}
		
		#cpms{
		    width: 200px;
		    margin:10px auto;
		    text-align: center;
		    border-radius: 20px;
		    border:2px solid #4340ff;
		    background: #49aac4;
		}
		
		#link-address{
		    text-decoration: none;
		    color:white;
		    font-weight: bolder;
		}
		
		#content-area{
	    	max-width:500px;
		    margin:0px auto;
		    padding:10px;
		    background-color: light-grey;
		}
		
		#wishes{
		    margin:0 auto;
		    text-align: center;
		    background-color
		}
	
		#link-address{
		    text-decoration: none;
		    color:white;
		    font-weight: bolder;
		}
		
		#about{
		    width:450px;
		    margin:20px auto;
		    font-weight: bold;
		    text-justify: newspaper;
		}
	</style>
</head>
<body>
	<div id="content-area">
		<!-- <img src="cid:cdac-acts-logo.jpeg" alt="Cdac Acts Logo"/>-->
		<h2>CDAC Project Management System</h2>
		<p>Welcome <span id="welcome">${firstname} ${lastname}</span> to CPMS, </p>
	    <p id="msg">Here are your login signature to CPMS</p> 
	    <div id="user">
	        <p><span>User Name: ${username}</span></p>
	        <p><span>Signature: Last 4 digits of PRN</span></p>
	    </div>
	    <div id="about">
            <ul>
                <li>Project Management System is a one stop shop to manage your entire project at one place.</li>
                <li>It allows you to register and manage your project life cycle.</li>
            </ul>    
            <div id="wishes">Best of luck</div>
        </div>
	    <div id="cpms">
	        <p><a id="link-address" href="https://www.google.com" target="_blank">Take me to CPMS</a></p>
	    </div>
    </div>
</body>
</html>
