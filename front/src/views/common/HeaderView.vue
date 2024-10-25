<template>
  <div>
    <nav class="navbar navbar-default navbar-fixed-top" role="navigation">
      <div>
        <div class="navbar-header">
          <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#example-navbar-collapse">
            <span class="sr-only">切换导航</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span> <span class="icon-bar"></span>
          </button>
        </div>

        <div class="collapse navbar-collapse" id="example-navbar-collapse">
          <ul class="nav navbar-nav">
            <li class="active">
              <el-button style="margin-top: 5px; margin-left: 10px" @click="handlerToLink('')" type="primary">主页</el-button>
            </li>
          </ul>

          <ul id="login-show" :style="{display: role?'flex':'none'}" style="justify-content: end; padding-right: 10px" class="nav navbar-nav navbar-right">
            <li class="user-show" :style="{display: role === 'user'?'block':'none'}" style="display: none">
              <el-button style="margin-top: 5px; margin-right: 10px" @click="toCart" type="primary">购物车</el-button>
            </li>
            <li class="user-show" :style="{display: role === 'user'?'block':'none'}">
              <el-button style="margin-top: 5px; margin-right: 10px" @click="toOrder" type="primary">我的订单</el-button>
            </li>
            <li class="admin-show" :style="{display: role === 'admin'?'block':'none'}">
              <el-button style="margin-top: 5px; margin-right: 10px" @click="handlerToLink('admin')" type="primary">管理中心</el-button>
            </li>
            <li>
              <el-button style="margin-top: 5px; margin-right: 10px" @click="loginOut()" type="danger">退出</el-button>
            </li>
          </ul>

          <ul id="no-login-show" :style="{display: role?'none':'flex'}" style="justify-content: end; padding-right: 10px" class="nav navbar-nav navbar-right">
            <li>
              <el-button style="margin-top: 5px; margin-right: 10px" @click="() => {showLogin = true}" type="primary">登录</el-button>
            </li>
            <li>&nbsp;&nbsp;&nbsp;</li>
            <li>
              <el-button style="margin-top: 5px; margin-right: 10px" @click="() => {showRegister = true}" type="warning">注册用户</el-button>
            </li>
          </ul>
        </div>
      </div>
    </nav>
    <login-view  v-if="showLogin" @close="() => {showLogin = false}"/>
    <user-register v-if="showRegister" @close="() => {showRegister = false}"/>

    <el-dialog
        title="购物车"
        :visible.sync="showCart"
        width="1200px"
        :before-close="() => {showCart = false}">
      <div>
        <cart-shopping/>
      </div>
    </el-dialog>

    <el-dialog
        title="我的订单"
        :visible.sync="showOrder"
        width="1200px"
        :before-close="() => {showOrder = false}">
      <div>
        <user-order-manager/>
      </div>
    </el-dialog>
  </div>
</template>

<script>
  import Auth from "../../api/Auth";
  import UserRegister from "../user/component/UserRegister";
  import LoginView from "./LoginView";
  import CartShopping from "@/views/user/CartShopping";
  import UserOrderManager from "@/views/user/UserOrderManager";

  export default {
     name: "HeaderView",
    components: {UserOrderManager, CartShopping, LoginView, UserRegister},
    data() {
      return {
        type: null,
        role: null,
        showLogin: false,
        showRegister: false,
        auth: {},
        showCart: false,
        showOrder: false,
      }
    },
    methods: {
      toCart() {
        this.showCart = true;
      },
      toOrder() {
        this.showOrder = true;
      },
      handlerToLink(link) {
        window.location.href = "/#/" + link;
      },
      current() {
        Auth.current().then(res => {
          let data = res.data.data;
          if (!data) {
            this.role = null;
            if (this.$route.query.showLogin === 'true') {
              setTimeout(() => {
                this.showLogin = true;
              }, 300);
            }
            return;
          }
          this.role = data.role;
          this.auth = data;
        })
      },
      loginOut() {
        Auth.loginOut().then(() => {
          this.$message.success("退出成功");
          setTimeout(() => {
            window.location.href = "/#/";
            window.location.reload();
          }, 300);
        })
      }
    },
    created() {
      this.current();
    },
  }
</script>

<style>
  .navbar-default {
    background: rgba(0, 0, 0, 0.3) !important;
    border: unset;
  }
</style>
