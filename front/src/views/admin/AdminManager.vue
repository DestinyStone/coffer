<template>
  <div>
    <div style="height: 80px;">
      <header-view/>
    </div>
    <div class="">
      <div class="row">
        <div class="col-md-2" id="menu-left">
          <div class="panel panel-default">
            <div class="panel-body">
              <ul id="item-urls" class="nav nav-pills nav-stacked">
                <li :class="{'active' : active === 'manager-item'}"><a style="cursor: pointer" @click="() => {active = 'manager-item'}">库存管理</a></li>
                <li :class="{'active' : active === 'manager-order'}"><a style="cursor: pointer" @click="() => {active = 'manager-order'}">订单管理</a></li>
              </ul>
            </div>
          </div>
        </div>
        <div class="col-md-10">
          <el-card style="width: calc(100% - 10px);">
            <item-manager v-if="active === 'manager-item'"/>
            <order-manager v-if="active === 'manager-order'"/>
          </el-card>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
  import HeaderView from "../common/HeaderView";
  import ItemManager from "./ItemManager";
  import OrderManager from "./OrderManager";
  export default {
    name: "AdminManager",
    components: {OrderManager, ItemManager, HeaderView},
    data() {
      return {
        active: "manager-item",
      }
    }
  }
</script>

<style>
#menu-left {
  min-width: 185px;
}
body {
  overflow: hidden;
  background: -webkit-gradient(linear, left top, right top, from(#beaaeb), color-stop(0.25, #9bb5f1), color-stop(0.5, #bca7ed), color-stop(0.75, #90adef), to(#c2a1e0));
}
.nav-pills > li.active > a, .nav-pills > li.active > a:hover, .nav-pills > li.active > a:focus {
  background: -webkit-gradient(linear, left top, right top, from(#beaaeb), color-stop(0.25, #9bb5f1), color-stop(0.5, #bca7ed), color-stop(0.75, #90adef), to(#c2a1e0));
}
.panel-default {
  border: unset;
}
.panel{
  background: rgba(0, 0, 0, 0.3);
}
</style>
