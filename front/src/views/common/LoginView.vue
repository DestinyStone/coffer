<template>
  <div id="login" class="modal">
    <div class="modal-dialog">
      <div class="modal-content">
        <div class="modal-body">
          <button @click="handlerClose" class="close" data-dismiss="modal">
            <span>&times;</span>
          </button>
        </div>
        <div class="modal-title">
          <h1 class="text-center">登录</h1>
        </div>

        <!-- 登录表单 -->
        <div class="modal-body">
          <form class="form-group" action="#" onsubmit=" return false" method="post">
            <div class="form-group has-feedback" id="login-account-div">
              <label for="login-account">用户名</label>
              <div class="input-group">
                <span class="input-group-addon"><span class="glyphicon glyphicon-user"></span></span>
                <input id="login-account" name="account" class="form-control" placeholder="请输入用户名" maxlength="20" type="text">
              </div>
            </div>

            <div class="form-group has-feedback" id="login-password-div">
              <label for="login-password">密码</label>
              <div class="input-group">
                <span class="input-group-addon"><span class="glyphicon glyphicon-lock"></span></span>
                <input id="login-password" name="password" class="form-control" placeholder="请输入密码" maxlength="20" type="password">
              </div>
            </div>

            <div class="btn-group pull-left" data-toggle="buttons" id="status-div">
              <label class="btn btn-default active">
                <input name="status" value="user" type="radio" checked>用户(默认)
              </label>
              <label class="btn btn-default">
                <input name="status" value="admin" type="radio"> 管理员
              </label>
            </div>

            <div class="text-right">
              <button class="btn btn-primary" type="submit" @click="login">登录</button>
              <button class="btn btn-danger" @click="handlerClose" data-dismiss="modal" style="margin-left: 10px;">取消</button>
            </div>
            <br />
          </form>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
  import $ from "jquery";
  import Auth from "../../api/Auth";

  export default {
    name: "LoginView",
    methods: {
      // 成功函数
      success(obj) {
        obj.removeClass('has-error').addClass('has-success');
        obj.find('.tips').hide();
        obj.find('.glyphicon-ok').show();
        obj.find('.glyphicon-remove').hide();
      },
      // 失败函数
      fail(obj, msg) {
        obj.removeClass('has-success').addClass('has-error');
        obj.find('.glyphicon-remove').show();
        obj.find('.glyphicon-ok').hide();
        obj.find('.tips').text(msg).show();
      },
      login() {
        let data = {};
        data.account = $("#login-account").val();
        data.password = $("#login-password").val();
        data.status = $("input[name='status']:checked").val();

        if (!data.account) {
          this.fail($("#login-account-div"));
          return;
        }

        if (!data.password) {
          this.fail($("#login-password-div"));
          return;
        }

        Auth.login(data).then(() => {
          this.$message.success("登录成功");
          setTimeout(() => {
            window.location.reload();
          }, 300)
        })
      },
      handlerClose() {
        $('#login').css("display", "none");
        this.$emit("close");
      },
      init() {
        $('#login').css("display", "block");
      }
    },
    mounted() {
      this.init();
    }
  }
</script>

<style scoped>

</style>