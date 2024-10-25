<template>
  <div style="height: 100%;">
    <header-view/>
    <div style="margin-top: 80px">
      <div class="row">
        <div class="col-md-2" id="menu-left">
          <div class="panel panel-default">
            <div class="panel-body">
              <ul id="item-urls" class="nav nav-pills nav-stacked">
                <li :class="{'active' : active === 'all'}"><a style="cursor: pointer" @click="onSearch('all')">全部</a></li>
                <li :class="{'active' : active === '‌‌浓缩咖啡'}"><a style="cursor: pointer" @click="onSearch('‌‌浓缩咖啡')">‌‌浓缩咖啡</a></li>
                <li :class="{'active' : active === '‌‌美式咖啡'}"><a style="cursor: pointer" @click="onSearch('‌‌美式咖啡')">‌‌美式咖啡</a></li>
                <li :class="{'active' : active === '‌‌拿铁'}"><a style="cursor: pointer" @click="onSearch('‌‌拿铁')">‌‌拿铁</a></li>
              </ul>
            </div>
          </div>
        </div>
        <div class="col-md-10">
          <div style="display: flex; width: calc(100% - 10px); flex-flow: wrap; justify-content: center;">
            <div v-for="item of top3Data" :key="item.id" style="width: 20%; min-width: 300px; padding: 10px; box-sizing: border-box;">
              <el-card @click.native="toDetail(item)" style="cursor: pointer">
                <div style="display: flex; justify-content: center;">
                  <el-image :src="item.imageUrl"></el-image>
                </div>
                <div style="font-size: 20px; text-align: center; margin-top: 30px;">
                  名称： {{item.name}}
                </div>
                <div style="font-size: 20px; text-align: center;">
                  类型： {{item.type}}
                </div>
              </el-card>
            </div>
          </div>
        </div>
      </div>
    </div>

    <el-dialog
        title="添加购物车"
        :visible.sync="showDetail"
        width="1200px"
        :before-close="() => {showDetail = false}">
      <div>
        <item-detail v-if="showDetail" :id="selectRow.itemId" @success="() => {showDetail = false}"/>
      </div>
    </el-dialog>

  </div>
</template>

<script>
  import HeaderView from "../common/HeaderView";
  import Item from "../../api/Item";
  import ItemDetail from "@/views/user/ItemDetail";
  export default {
    name: "HomeView",
    components: {ItemDetail, HeaderView},
    data() {
      return {
        top3Data: [],
        active: 'all',
        selectRow: {},
        showDetail: false,
      }
    },
    methods: {
      toDetail(item) {
        this.selectRow = JSON.parse(JSON.stringify(item));
        this.showDetail = true;
      },
      onSearch(status) {
        this.active = status;
        this.onLoad();
      },
      onLoad() {
        let active = this.active === 'all' ? null : this.active;

        Item.list({type: active}).then(res => {
          this.top3Data = res.data.data;
        })
      }
    },
    created() {
      this.onLoad();
    }
  }
</script>

<style>
.el-card__body:hover {
  border: 1px solid #409EFF;
}
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
  @media screen and (max-width:740px){
    #bottom {
      display: none;
    }
  }
  /*main区域居中*/
  .main {
    text-align: center;
    background-color: #fff;
    border-radius: 20px;
    height: 400px;
    margin: 60px auto;
    position: absolute;
    top: 0;
    left: 0;
    right: 0;
    bottom: 0;
  }

  .items {
    text-align: center;
    margin-left: auto;
    margin-right: auto;
  }

  .items img {
    width: 150px;
  }

  /*去除轮播两边阴影*/
  #cl #cr {
    background-image: none;
  }

  .item img {
    width: 100%;
    height: 400px;
    overflow: hidden;
  }
  #home-bg {
    position:relative;
    width: 100%;
    height: 460px;
    background-size: 100% 460px;
    background-repeat: no-repeat;
  }
</style>
