<template>
  <div id="submit" class="modal">
    <div class="modal-dialog">
      <div class="modal-content">

        <div class="modal-body"><button @click="handlerClose" class="close" data-dismiss="modal"><span>&times;</span></button></div>
        <div class="modal-title"><h1 class="text-center" id="operatorTitle">订单详情</h1></div>
        <table id="table-detail" class="table table-striped" style="text-align: center">
          <tbody>
          <tr>
            <td>商品图片</td>
            <td>商品名称</td>
            <td>类型</td>
            <td>数量</td>
            <td>单价</td>
            <td>小计</td>
          </tr>
          <tr v-for="item in data" :key="item.id">
            <td><img style="width: 30px; height: 30px;" :src="item.imageUrl"></td>
            <td>{{item.name}}</td>
            <td>{{item.type}}</td>
            <td>{{item.number}}</td>
            <td>{{item.price}}</td>
            <td>{{item.number * item.price}}</td>
          </tr>
          </tbody>
        </table>
        <center style="padding-bottom: 50px;">
          <font size="4"><strong id="total-wrapper">总价：{{total}}&nbsp;&nbsp;&nbsp;&nbsp;</strong></font><br/><br/>
          <button class="btn btn-primary" @click="handlerClose" data-dismiss="modal">关&nbsp;&nbsp;闭</button>
        </center>

      </div>
    </div>
  </div>
</template>

<script>
  import $ from "jquery"
  import Order from "../../../api/Order";
  export default {
    name: "OrderDetail",
    props: {
      id: {
        type: Number,
      }
    },
    data() {
      return {
        data: [],
        total: 0,
      }
    },
    methods: {
      init() {
        $('#submit').css("display", "block");

        if (this.id) {
          Order.detail(this.id).then(res => {
            this.data = res.data.data;
            for(let item of this.data) {
              this.total += item.number * item.price;
            }
          })
        }
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