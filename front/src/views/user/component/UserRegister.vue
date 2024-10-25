<template>
  <div id="submit" class="modal">
    <div class="modal-dialog">
      <div class="modal-content">

        <div class="modal-body"><button @click="handlerClose" class="close" data-dismiss="modal"><span>&times;</span></button></div>
        <div class="modal-title"><h1 class="text-center" id="operatorTitle">用户注册</h1></div>

        <!-- 注册表单 -->
        <div class="modal-body">
          <form action="#" onsubmit="return false;" method="post">
            <div class="form-group has-feedback" id="user-submit-account-div">
              <label for="user-submit-account">用户名</label>
              <div class="input-group">
                <span class="input-group-addon"><span class="glyphicon glyphicon-user"></span></span>
                <input id="user-submit-account" name="account" class="form-control" placeholder="请输入用户名" maxlength="20" type="text">
              </div>
              <span style="color: red; display: none;" class="tips"></span> <span style="display: none;" class=" glyphicon glyphicon-remove form-control-feedback"></span>
              <span style="display: none;" class="glyphicon glyphicon-ok form-control-feedback"></span>
            </div>

            <div class="form-group has-feedback" id="user-submit-password-div">
              <label for="user-submit-password">密码</label>
              <div class="input-group"><span class="input-group-addon"><span class="glyphicon glyphicon-lock"></span></span>
                <input id="user-submit-password" name="password" class="form-control" placeholder="请输入密码，明文存储，请勿输入常用密码" maxlength="20" type="password">
              </div>
              <span style="color: red; display: none;" class="tips"></span> <span style="display: none;" class="glyphicon glyphicon-remove form-control-feedback"></span>
              <span style="display: none;" class="glyphicon glyphicon-ok form-control-feedback"></span>
            </div>

            <div class="form-group has-feedback" id="user-submit-tel-div">
              <label for="user-submit-tel">手机号码</label>
              <div class="input-group">
                <span class="input-group-addon"><span class="glyphicon glyphicon-phone"></span></span>
                <input id="user-submit-tel" name="tel" class="form-control" placeholder="请输入手机号码" maxlength="11" type="text">
              </div>
              <span style="color: red; display: none;" class="tips"></span> <span style="display: none;" class="glyphicon glyphicon-remove form-control-feedback"></span>
              <span style="display: none;" class="glyphicon glyphicon-ok form-control-feedback"></span>
            </div>

            <div class="form-group has-feedback" id="user-submit-email-div">
              <label for="user-submit-email">邮箱</label>
              <div class="input-group">
                <span class="input-group-addon"><span class="glyphicon glyphicon-envelope"></span></span>
                <input id="user-submit-email" name="email" class="form-control" type="email" placeholder="例如:123@123.com">
              </div>
              <span style="color: red; display: none;" class="tips"></span> <span style="display: none;" class="glyphicon glyphicon-remove form-control-feedback"></span>
              <span style="display: none;" class="glyphicon glyphicon-ok form-control-feedback"></span>
            </div>

            <div class="text-right">
              <button class="btn btn-primary" type="button" @click="submitHandler">提&nbsp;&nbsp;交</button>
              <button class="btn btn btn-warning orm-control" style="margin-left: 10px;" type="reset">重&nbsp;&nbsp;置</button>
              <button class="btn btn-danger" type="button" @click.stop="handlerClose" style="margin-left: 10px;">取&nbsp;&nbsp;消</button>
            </div>

          </form>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
  import $ from "jquery"
  import User from "../../../api/User";
  export default {
    name: "UserRegister",
    data() {
      return {
        checkRegisterAccount: false,
        checkRegisterPassword: false,
        checkRegisterTel: false,
        checkRegisterEmail: false,
      }
    },
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

      submitHandler() {
        if (this.checkRegisterAccount) {
          this.success($("#user-submit-account-div"));
        } else {
          this.fail($("#user-submit-account-div"));
        }

        if (this.checkRegisterPassword) {
          this.success($("#user-submit-password-div"));
        } else {
          this.fail($("#user-submit-password-div"));
        }

        if (this.checkRegisterTel) {
          this.success($("#user-submit-tel-div"));
        } else {
          this.fail($("#user-submit-tel-div"));
        }

        if (this.checkRegisterEmail) {
          this.success($("#user-submit-email-div"));
        } else {
          this.fail($("#user-submit-email-div"));
        }

        if (this.checkRegisterAccount && this.checkRegisterPassword && this.checkRegisterTel && this.checkRegisterEmail) {
          let user = {};
          user.userId = this.id;
          user.account = $("#user-submit-account").val();
          user.password = $("#user-submit-password").val();
          user.tel = $("#user-submit-tel").val();
          user.email = $("#user-submit-email").val();


          User.save(user).then(() => {
            this.$message.success("注册成功");
            this.handlerClose();
          })
        }
      },
      init() {
        $('#submit').css("display", "block");

        // 用户名
        let regAccount = /^[a-zA-Z_][a-zA-Z0-9_]{4,19}$/;
        // 用户名匹配
        $(document).ready(() => {
          $('#user-submit-account-div').find('#user-submit-account').change(() =>  {
            let value = $("#user-submit-account").val();
            if (regAccount.test(value)) {
              this.success($("#user-submit-account-div"));
              this.checkRegisterAccount = true;
            } else if (value.length < 5) {
              this.fail($("#user-submit-account-div"), '用户名太短，不能少于5个字符');
              this.checkRegisterAccount = false;
            } else {
              this.fail($("#user-submit-account-div"), '用户名只能为英文数字和下划线,且不能以数字开头')
              this.checkRegisterAccount = false;
            }
          });
        });

        // 密码
        let regPasswordAlpha = /[a-zA-Z]/;
        let regPasswordNum = /[0-9]/;
        let registerPassword;

        // 密码匹配
        // 匹配字母、数字、特殊字符至少两种的函数
        function atLeastTwo(password) {
          let b = regPasswordAlpha.test(password) ? 1 : 0;
          let c = regPasswordNum.test(password) ? 1 : 0;
          return b + c;
        }

        // 注册密码验证
        $(document).ready(() => {
          // 检验长短以及是否包含多种特殊字符
          $('#user-submit-password-div').find('#user-submit-password').change(() =>  {
            registerPassword =$("#user-submit-password").val();
            if (registerPassword.length < 8) {
              this.fail($("#user-submit-password-div"), '密码太短，不能少于8个字符');
              this.checkRegisterPassword = false;
            } else {
              if (atLeastTwo(registerPassword) < 2) {
                this.fail($("#user-submit-password-div"), '密码中至少包含字母、数字、特殊字符的两种');
                this.checkRegisterPassword = false;
              } else {
                this.success($("#user-submit-password-div"));
                this.checkRegisterPassword = true;
              }
            }
          });

        });

        // 手机号码
        let regTel = /^[0-9]{11}$/;
        $(document).ready(() => {
          $('#user-submit-tel-div').find("#user-submit-tel").change(() =>  {
            let value = $("#user-submit-tel").val();
            if (regTel.test(value)) {
              this.success($("#user-submit-tel-div"));
              this.checkRegisterTel = true;
            } else {
              this.fail($("#user-submit-tel-div"), '手机号码只能为11位数字');
              this.checkRegisterTel = false;
            }
          });
        });

        // 邮箱
        let regEmail = /^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/;
        $(document).ready(() =>  {
          $('#user-submit-email-div').find("#user-submit-email").change(() =>  {
            let value = $("#user-submit-email").val();
            if (regEmail.test(value)) {
              this.success($("#user-submit-email-div"));
              this.checkRegisterEmail = true;
            } else {
              this.fail($("#user-submit-email-div"), '邮箱格式错误,正确格式如:123312@qq.com');
              this.checkRegisterEmail = false;
            }
          });

        });
      },
      handlerClose() {
        $('#submit').css("display", "none");
        this.$emit("close");
      }
    },
    mounted() {
      this.init();
    }
  }
</script>

<style scoped>

</style>