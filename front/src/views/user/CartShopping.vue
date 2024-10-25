<template>
  <div style="height: 100%">
    <div class="row">
      <div class="col-md-12">
        <div class="panel panel-warning" style="background: #FFFFFF;">
          <div class="panel-body" id="item" style="overflow: auto;">
            <table id="table" class="table table-striped" style="text-align: center;  min-width: 800px">
              <tbody>
              <tr>
                <td>商品ID</td>
                <td>商品图片</td>
                <td>商品名称</td>
                <td>类型</td>
                <td>数量</td>
                <td>单价</td>
                <td>小计</td>
                <td>删除</td>
              </tr>
              <tr v-for="item in data" :key="item.id">
                <td>{{item.itemId}}</td>
                <td><img style="width: 30px; height: 30px;" :src="item.imageUrl"></td>
                <td>{{item.name}}</td>
                <td>{{item.type}}</td>
                <td><input @input="() => {handlerChange(item)}" v-model="item.number" type="number" style="width: 50px"></td>
                <td>{{item.price}}</td>
                <td>{{item.price * item.number}}</td>
                <td>
                  <button class="btn btn-info" type="submit" @click="handlerDelete(item.itemId)">
                    <span class="glyphicon glyphicon-remove"></span>
                  </button>
                </td>
              </tr>
              </tbody>
            </table>
          </div>
          <div class="panel-footer " style="height: 50px">
            <div class="pull-right">
              <font size="4"><strong>总价：<span id="countPrice">{{allTotal}}</span>&nbsp;&nbsp;&nbsp;&nbsp;</strong></font>
              <el-button style="margin-right: 10px; position:relative; top: -5px" @click="handlerGenerate" type="success">结&nbsp;算</el-button>
            </div>
          </div>
        </div>
      </div>
    </div>

    <order-generate v-if="showSubmit" @close="() => {showSubmit = false; this.onLoad()}"/>
  </div>
</template>

<script>
  import Cart from "../../api/Cart";
  import OrderGenerate from "./component/OrderGenerate";
  export default {
    name: "CartShopping",
    components: {OrderGenerate},
    data() {
      return {
        data: [],
        showSubmit: false,
      }
    },
    computed: {
      allTotal() {
        let total = 0;
        for(let item of this.data) {
          total += item.price * item.number;
        }
        return total;
      }
    },
    methods: {
      handlerGenerate() {
        if (this.data.length <= 0) {
          this.$message.warning("购物车信息不存在");
          return;
        }
        this.showSubmit = true;
      },
      handlerChange(data) {
        Cart.update(data.itemId, data);
      },
      handlerDelete(id) {
        Cart.delete(id).then(() => {
          this.$message.success("删除成功");
          this.onLoad();
        })
      },
      onLoad() {
        Cart.list().then(res => {
          let data = res.data.data;
          this.data = data;
        })
      }
    },
    created() {
      this.onLoad();
    }
  }
</script>

<style scoped>

</style>
