<template>
  <div>
    <!--添加-->
    <el-row :gutter="10">
      <el-col :span="6">
        <el-button type="primary" size="small" icon="el-icon-plus" @click="addDialogVisible=true">单条添加美文</el-button>
      </el-col>
    </el-row>

    <el-table :data="sentenceList">
      <el-table-column label="序号" type="index" width="50"></el-table-column>
      <el-table-column label="类型" width="120" prop="type" :formatter="formatType"></el-table-column>
      <el-table-column label="内容" width="550" prop="content"></el-table-column>
      <el-table-column label="来源" width="420" prop="source"></el-table-column>

      <el-table-column label="操作">
        <template v-slot="scope">
          <el-button type="primary" icon="el-icon-edit" size="mini" @click="showEditDialog(scope.row)">编辑</el-button>
          <el-popconfirm title="确定删除吗？" icon="el-icon-delete" iconColor="red" @onConfirm="deleteTagById(scope.row.id)">
            <el-button size="mini" type="danger" icon="el-icon-delete" slot="reference">删除</el-button>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>

    <!--分页-->
    <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="queryInfo.pageNum"
                   :page-sizes="[10, 20, 30, 50]" :page-size="queryInfo.pageSize" :total="total"
                   layout="total, sizes, prev, pager, next, jumper" background>
    </el-pagination>

    <!--添加标签对话框-->
    <el-dialog title="添加标签" width="50%" :visible.sync="addDialogVisible" :close-on-click-modal="false" @close="addDialogClosed">
      <!--内容主体-->
      <el-form :model="addForm" :rules="formRules" ref="addFormRef" label-width="80px">

        <el-form-item label="美文类型" prop="type">
          <el-select v-model="addForm.type" placeholder="请选择类型" :clearable="true" style="width: 100%">
            <el-option v-for="type in typeList" :key="type" :label="typeLabel(type)" :value="type"></el-option>
          </el-select>
        </el-form-item>

        <el-form-item label="美文内容" prop="content">
          <el-input v-model="addForm.content"></el-input>
        </el-form-item>

        <el-form-item label="美文来源" prop="source">
          <el-input v-model="addForm.source"></el-input>
        </el-form-item>

      </el-form>
      <!--底部-->
      <span slot="footer">
				<el-button @click="addDialogVisible=false">取 消</el-button>
				<el-button type="primary" @click="addSingleSentence">确 定</el-button>
			</span>
    </el-dialog>

    <!--编辑标签对话框-->
    <el-dialog title="编辑标签" width="50%" :visible.sync="editDialogVisible" :close-on-click-modal="false" @close="editDialogClosed">
      <!--内容主体-->

      <el-form :model="editForm" :rules="formRules" ref="editFormRef" label-width="80px">

        <el-form-item label="美文类型" prop="type">
          <el-select v-model="editForm.type" placeholder="请选择类型" :clearable="true" style="width: 100%">
            <el-option v-for="type in typeList" :key="type" :label="typeLabel(type)" :value="type"></el-option>
          </el-select>
        </el-form-item>

        <el-form-item label="美文内容" prop="content">
          <el-input v-model="editForm.content"></el-input>
        </el-form-item>

        <el-form-item label="美文来源" prop="source">
          <el-input v-model="editForm.source"></el-input>
        </el-form-item>

      </el-form>
      <!--底部-->
      <span slot="footer">
				<el-button @click="editDialogVisible=false">取 消</el-button>
				<el-button type="primary" @click="editSentence">确 定</el-button>
			</span>
    </el-dialog>

  </div>
</template>

<script>
import Breadcrumb from "@/components/Breadcrumb";
import {getData,getType,editSentence,addSingleSentence} from '@/api/sentence'

export default {
  name: "SentenceList",
  components: {
    Breadcrumb
  },
  data() {
    return {
      queryInfo: {
        pageNum: 1,
        pageSize: 10
      },
      // 美文
      sentenceList: [],
      // 美文类型
      typeList: [],
      total: 0,
      addDialogVisible: false,
      editDialogVisible: false,
      addForm: {
        type: '',
        content: '',
        source: '',
      },
      editForm: {},
      formRules: {
        name: [
            {required: true, message: '请输入美文内容', trigger: 'blur'},
        ]
      },
    }
  },
  created() {
    this.getData();
    this.getType();
  },
  methods: {
    // 获取美文类型列表
    getType(){
      getType().then(res=>{
        this.typeList = res.data
      })
    },
    // 获取美文数据
    getData() {
      getData(this.queryInfo).then(res => {
        this.sentenceList = res.data.list
        this.total = res.data.total
      })
    },
    formatType(row){
      if (row.type === 1){
        return '诗词';
      }else if (row.type === 5){
        return '自创';
      }
      // 如果type的值不为1，可以根据需要设置其他情况的显示内容或返回原始值
      return row.type;
    },
    //监听 pageSize 改变事件
    handleSizeChange(newSize) {
      this.queryInfo.pageSize = newSize
      this.getData()
    },
    //监听页码改变事件
    handleCurrentChange(newPage) {
      this.queryInfo.pageNum = newPage
      this.getData()
    },
    // 关闭添加弹窗窗口时，恢复内容为空
    addDialogClosed() {
      this.addForm.type = ''
      this.$refs.addFormRef.resetFields()
    },
    editDialogClosed() {
      this.editForm = {}
      this.$refs.editFormRef.resetFields()
    },
    addSingleSentence() {
      this.$refs.addFormRef.validate(valid => {
        if (valid) {
          addSingleSentence(this.addForm).then(res => {
            this.msgSuccess(res.msg)
            this.addDialogVisible = false
            this.getData()
          })
        }
      })
    },
    editSentence() {
      this.$refs.editFormRef.validate(valid => {
        if (valid) {
          editSentence(this.editForm).then(res => {
            this.msgSuccess(res.msg)
            this.editDialogVisible = false
            this.getData()
          })
        }
      })
    },
    showEditDialog(row) {
      this.editForm = {...row}
      this.editDialogVisible = true
    },
    deleteTagById(id) {
      deleteTagById(id).then(res => {
        this.msgSuccess(res.msg)
        this.getData()
      })
    }
  },
  computed:{
    typeLabel(){
      return(type)=>{
        switch (type){
          case 1: return '诗词';
          case 2: return 'XX';
          case 3: return 'XX';
          case 4: return 'XX';
          case 5: return '自创';
          default: return '';
        }
      }
    },
  }
}
</script>

<style scoped>
.el-button + span {
  margin-left: 10px;
}
 .el-dropdown {
   vertical-align: top;
 }
.el-dropdown + .el-dropdown {
  margin-left: 15px;
}
.el-icon-arrow-down {
  font-size: 12px;
}
</style>
