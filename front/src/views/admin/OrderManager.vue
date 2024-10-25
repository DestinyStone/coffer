<template>
  <div>
    <el-table
        :data="data"
        height="650"
        border
        style="width: 100%; margin-top: 30px;">
      <el-table-column
          prop="orderId"
          align="center"
          label="订单编号">
      </el-table-column>
      <el-table-column
          prop="account"
          align="center"
          label="账户">
      </el-table-column>
      <el-table-column
          prop="orderTotal"
          align="center"
          label="总计">
      </el-table-column>
      <el-table-column
          prop="address"
          align="center"
          label="收货地址">
      </el-table-column>
      <el-table-column
          prop="status"
          align="center"
          label="状态">
      </el-table-column>
      <el-table-column
          prop="createdTime"
          align="center"
          label="下单时间">
      </el-table-column>
      <el-table-column
          align="center"
          width="200"
          label="操作">
        <template slot-scope="{row}">
          <div style="display: flex; justify-content: space-around">
            <el-button v-if="row.status === '处理中'" @click="openUpdate(row.orderId)" type="text">处理订单</el-button>
            <el-button @click="openDetail(row.orderId)" type="text">查看详情</el-button>
          </div>
        </template>
      </el-table-column>
    </el-table>

    <el-dialog
        title="订单详情"
        :visible.sync="showDetail"
        width="800px"
        :before-close="() => {showDetail = false}">
      <el-table
          :data="detailData"
          border
          style="width: 100%; margin-top: 30px;">
        <el-table-column
            prop="imageUrl"
            align="center"
            label="商品图片">
          <template slot-scope="{row}">
            <el-image :src="row.imageUrl" style="height: 40px"/>
          </template>
        </el-table-column>
        <el-table-column
            prop="name"
            align="center"
            label="商品名称">
        </el-table-column>
        <el-table-column
            prop="type"
            align="center"
            label="类型">
        </el-table-column>
        <el-table-column
            prop="number"
            align="center"
            label="数量">
        </el-table-column>
        <el-table-column
            prop="price"
            align="center"
            label="单价">
        </el-table-column>
      </el-table>
    </el-dialog>

  </div>
</template>

<script>
  import Order from "../../api/Order";

  export default {
    name: "OrderManager",
    data() {
      return {
        data: [],
        currentId: null,
        showDetail: false,
        detailData: [],
      }
    },
    methods: {
      openUpdate(id) {
        Order.status(id).then(() => {
          this.$message.success("处理成功");
          this.onLoad();
        })
      },
      openDetail(id) {
        Order.detail(id).then(res => {
          this.detailData = res.data.data;
          this.showDetail = true;
        })
      },
      onLoad() {
        Order.list().then(res => {
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
