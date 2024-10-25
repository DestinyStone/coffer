<template>
  <div style="height: 100%">
    <div class="row">
      <!-- 左侧菜单栏 -->
      <div class="col-md-6" id="item-image">
        <img :src="data.imageUrl" style="width: 100%; max-height: 600px">
      </div>
      <!-- 右侧主体 -->
      <div class="col-md-6">
        <div class="panel panel-default" style="background: -webkit-gradient(linear, left top, right top, from(#beaaeb), color-stop(0.25, #9bb5f1), color-stop(0.5, #bca7ed), color-stop(0.75, #90adef), to(#c2a1e0));">
          <div class="panel-heading">
            <font size="3"><span class="glyphicon glyphicon glyphicon-leaf"></span> 商品详情 </font>
          </div>
          <div class="panel-body">
            <font size="8"><strong id="item-name">{{data.name}}</strong></font>&nbsp;&nbsp;&nbsp;&nbsp;<br />
            <font size="5"><strong id="item-type">{{'商品类型：' + data.type}}</strong></font>&nbsp;&nbsp;&nbsp;&nbsp;<br />
            <font size="5"><strong id="item-price">{{'价格：' + data.price}}</strong></font><br /><br /><br />
            <font size="4" id="item-description">{{data.description}}</font> <br /> <br />

            <center id="trigger-login" :style="{display: role?'none':'block'}">
              <font size="5" style="width: 50%"><strong>如需购买，请先登录</strong></font><br />
              <button @click="handlerToLogin" type="submit" class="btn btn-info navbar-btn" style="width: 50%">
                <span class="glyphicon glyphicon-log-in"></span>&nbsp;登&nbsp;&nbsp;录
              </button>
            </center>

            <center id="admin-login" :style="{display: role==='admin'?'block':'none'}" style="display: none;">
              <font size="5" style="width: 50%"><strong>管理员不支持商品购买</strong></font><br />
            </center>

            <form id="trigger-shopping" onsubmit="return false;" :style="{display: role==='user'?'block':'none'}" action="#" method="post">
              <div class="form-group has-feedback">
                <div id="item-stock-hide" style="display: none"></div>
                <label for="item-number"><font size="4" id="item-stock">{{`购买数量(库存量：${data.stock})`}}</font></label>
                <div class="input-group">
                  <span class="input-group-btn">
                    <button class="btn btn-default" @click="decreaseItemNum()" type="button">-</button>
                  </span>
                  <input id="item-number" name="number" type="text" class="form-control" v-model="stock" style="text-align: center;">
                  <span class="input-group-btn">
                    <button class="btn btn-default" @click="increaseItemNum()" type="button">+</button>
                  </span>
                </div>
                <br />
                <button type="submit" @click="joinCartShopping" class="btn btn-danger" style="width: 100%">加入购物车</button>
              </div>
            </form>

          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
  import Item from "../../api/Item";
  import Auth from "../../api/Auth";
  import Cart from "../../api/Cart";
  export default {
    name: "ItemDetail",
    props: {
      id: null
    },
    data() {
      return {
        data: {},
        role: null,
        stock: 1,
      }
    },
    watch: {
      stock(newValue, oldValue) {
        let regNum = /^[0-9]+$/;
        if (!regNum.test(newValue)) {
          this.stock = oldValue;
        }
      }
    },
    methods: {
      joinCartShopping() {
        let data = {};
        data.itemId = this.id;
        data.number = this.stock;

        Cart.save(data).then(() => {
          this.$message.success("加入购物车成功");
          this.$emit("success");
        })
      },
      onLoad() {
        Item.getById(this.id).then(res => {
          this.data = res.data.data;
        })
      },
      current() {
        Auth.current().then(res => {
          let data = res.data.data;
          if (!data) {
            this.role = null;
            return;
          }
          this.role = data.role;
        })
      },
      handlerToLogin() {
        if (this.role) {
          return;
        }
        let query = this.$route.query;
        let path = `/detail?id=${query.id}&showLogin=true`
        this.$router.push({path: path}, () => {});
        window.location.reload()
      },
      decreaseItemNum() {
        if (this.stock <= 1) {
          return;
        }
        this.stock--;
      },
      increaseItemNum() {
        if (this.stock >= this.data.stock) {
          return;
        }
        this.stock++;
      }
    },
    created() {
      this.onLoad();
      this.current();
    }
  }
</script>

<style scoped>

</style>
