<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <title>photoTest</title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" type="text/css" href="datetimepicker/jquery.datetimepicker.css"/>
    <link rel="stylesheet" type="text/css" href="css/register.css"/>
</head>

<body>
    <div class="register_head">
        <div class="container">
            <div class="title">
                <h1>sign up now!</h1>
            </div>
        </div>
    </div>

    <div class="register_content">
        <div class="container">
            <form method="post" action="photo.do" name="register_form" enctype="multipart/form-data">
                <div class="row_other">
                    <label for="photo" class="sex">照片:</label>
                    <span class="error">輸入錯誤</span>
                    <div class="clear"></div>
                    <div class="profilePic_preview">
                        <div class="mem_uploadPic"></div>
                    </div>
                    <div class="other_block">
                        <div class="memUploadPic_btn_block">
                            <input type="file" name="photo" id="memUploadPic_file" style="margin-top: 5px;">
                        </div>
                    </div>
                </div>
                <div class="submit">
                    <input type="hidden" name="action" value="photo">
                    <input type="submit" value="sign up">
                </div>
            </form>
        </div>
    </div>
    <script src="datetimepicker/jquery.js"></script>
    <script src="datetimepicker/jquery.datetimepicker.full.js"></script>
    <script src="js/register.js"></script>
</body>

</html>