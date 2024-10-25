<template>
  <div id="generate" class="modal">
    <div class="modal-dialog">
      <div class="modal-content">

        <div class="modal-body"><button @click="handlerClose" class="close" data-dismiss="modal"><span>&times;</span></button></div>
        <div class="modal-title"><h1 class="text-center" id="operatorTitle">结算</h1></div>

        <!-- 注册表单 -->
        <div class="modal-body">
          <form action="#" onsubmit="return false" method="post">
            <div class="form-group has-feedback" id="submit-address-div">
              <label for="submit-address">收获地址</label>
              <div class="input-group">
                <span class="input-group-addon"><span class="glyphicon glyphicon-user"></span></span>
                <input id="submit-address" name="address" class="form-control" maxlength="20" type="text">
              </div>
              <span style="color: red; display: none;" class="tips"></span> <span style="display: none;" class=" glyphicon glyphicon-remove form-control-feedback"></span>
              <span style="display: none;" class="glyphicon glyphicon-ok form-control-feedback"></span>
            </div>

            <div class="text-right">
              <button class="btn btn-primary" @click="handlerSubmit" type="submit">确认结算</button>
              <button class="btn btn-danger" @click="handlerClose" style="margin-left: 10px;">取&nbsp;&nbsp;消</button>
            </div>
          </form>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
  import $ from "jquery";
  import Order from "../../../api/Order";

  export default {
    name: "OrderGenerate",
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

      handlerSubmit() {
        let data = {};
        data.address = $("#submit-address").val();
        if (!data.address) {
          this.fail($("#submit-address-div"));
          return;
        }

        Order.userSave(data).then(() => {
          this.$message.success("结算成功");
          this.handlerClose();
        })

      },
      handlerClose() {
        $('#generate').css("display", "none");
        this.$emit("close");
      },
      init() {
        $('#generate').css("display", "block");
      }
    },
    mounted() {
      this.init();
    }
  }
</script>

<style scoped>

</style>