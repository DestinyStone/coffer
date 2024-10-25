<template>
  <div>
    <div style="display: flex; justify-content: end">
      <el-button style="margin-right: 10px" @click="openSave" type="primary">新增商品</el-button>
    </div>
    <div>
      <el-table
          :data="data"
          height="650"
          border
          style="width: 100%; margin-top: 30px;">
        <el-table-column
            prop="itemId"
            align="center"
            label="ID">
        </el-table-column>
        <el-table-column
            prop="name"
            align="center"
            label="名称">
        </el-table-column>
        <el-table-column
            prop="type"
            align="center"
            label="类型">
        </el-table-column>
        <el-table-column
            prop="stock"
            align="center"
            label="库存量">
        </el-table-column>
        <el-table-column
            prop="price"
            align="center"
            label="单价">
        </el-table-column>
        <el-table-column
            prop="description"
            align="center"
            label="描述">
        </el-table-column>
        <el-table-column
            prop="imageUrl"
            align="center"
            label="图片">
          <template slot-scope="{row}">
            <el-image :src="row.imageUrl" style="height: 40px"/>
          </template>
        </el-table-column>
        <el-table-column
            align="center"
            width="200"
            label="操作">
          <template slot-scope="{row}">
            <div style="display: flex; justify-content: space-around">
              <el-button @click="openUpdate(row)" type="text">编辑</el-button>
              <el-button @click="openDelete(row.itemId)" type="text">删除</el-button>
            </div>
          </template>
        </el-table-column>
      </el-table>
    </div>

    <el-dialog
        :title="currentId ? '编辑' : '新增'"
        :visible.sync="showSubmit"
        width="600px"
        :before-close="() => {showSubmit = false}">
      <div>
        <el-form :model="form" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
          <el-form-item label="名称" prop="name">
            <el-input v-model="form.name"></el-input>
          </el-form-item>
          <el-form-item label="类型" prop="type">
            <el-select style="width: 100%" v-model="form.type">
              <el-option label="‌‌浓缩咖啡" value="‌‌浓缩咖啡"></el-option>
              <el-option label="‌‌美式咖啡" value="‌‌美式咖啡"></el-option>
              <el-option label="‌‌拿铁" value="‌‌拿铁"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="库存量" prop="stock">
            <el-input v-model="form.stock" type="number"></el-input>
          </el-form-item>
          <el-form-item label="单价" prop="price">
            <el-input v-model="form.price" type="number"></el-input>
          </el-form-item>
          <el-form-item label="描述" prop="description">
            <el-input v-model="form.description" :rows="4" type="textarea"></el-input>
          </el-form-item>
          <el-form-item label="图片" prop="imageUrl">
            <el-upload
                class="avatar-uploader"
                action="/api/item/upload"
                :show-file-list="false"
                :on-success="handlerUpload">
              <img v-if="form.imageUrl" :src="form.imageUrl" class="avatar">
              <i v-else class="el-icon-plus avatar-uploader-icon"></i>
            </el-upload>
          </el-form-item>
        </el-form>
        <el-button @click="handlerSubmit" type="primary" style="width: 100%">{{currentId ? '编辑' : '新增'}}</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
  import Item from "../../api/Item";

  export default {
    name: "ItemManager",
    data() {
      return {
        data: [],
        showSubmit: false,
        currentId: null,
        form: {},
        rules: {
          name: [{ required: true, message: '请输入名称', trigger: 'blur' },],
          type: [{ required: true, message: '请输入类型', trigger: 'blur' },],
          stock: [{ required: true, message: '请输入库存量', trigger: 'blur' },],
          price: [{ required: true, message: '请输入单价', trigger: 'blur' },],
          description: [{ required: true, message: '请输入描述', trigger: 'blur' },],
        },
      }
    },
    methods: {
      handlerUpload(res) {
        let data = res.data;
        this.$set(this.form, 'imageUrl', data);
      },
      handlerSubmit() {
        this.$refs['ruleForm'].validate((valid) => {
          if (!valid) {
            return;
          }
          if (!this.currentId) {
            Item.save(this.form).then(() => {
              this.$message.success("新增成功");
              this.onLoad();
              this.showSubmit = false;
            })
            return;
          }
          Item.update(this.currentId, this.form).then(() => {
            this.$message.success("更新成功");
            this.onLoad();
            this.showSubmit = false;
          })
        });
      },
      openDelete(id) {
        this.$confirm('确定删除数据吗？', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          return Item.delete(id);
        }).then(() => {
          this.$message({type: 'success', message: '删除成功!'});
          this.onLoad();
        }).catch(() => {
        });
      },
      openUpdate(data) {
        this.currentId = data.itemId;
        this.form = JSON.parse(JSON.stringify(data));
        this.showSubmit = true;
      },
      openSave() {
        this.currentId = null;
        this.form = {};
        this.showSubmit = true;
      },
      onLoad() {
        Item.list().then(res => {
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
.avatar-uploader .el-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}
.avatar-uploader .el-upload:hover {
  border-color: #409EFF;
}
.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 178px;
  height: 178px;
  line-height: 178px;
  text-align: center;
}
.avatar {
  width: 178px;
  height: 178px;
  display: block;
}
</style>
