<template>
  <div>
    <el-row :gutter="10">

      <!--单条添加-->
      <el-col :span="2">
        <el-button type="primary" size="small" icon="el-icon-plus" @click="addDialogVisible=true">单条添加</el-button>
      </el-col>

      <!--批量添加-->
      <el-col :span="3.5">
        <el-upload
            class="upload-demo"
            ref="upload"
            action="#"
            :http-request="importFile"
            :before-upload="beforeAvatarUpload"
            :auto-upload="false"
            :limit="1"
            accept=".xls,.xlsx,.csv"
            :on-exceed="handleExceed">

          <el-button slot="trigger" type="primary" size="small">批量添加</el-button>

          <el-button
              :loading="dataForms.loading"
              style="margin-left: 10px"
              type="success"
              @click="fileSubmit"
              size="small"
              v-loading.fullscreen.lock="fullscreenLoading"
              element-loading-text="正在执行..."
              element-loading-spinner="el-icon-loading"
              element-loading-background="rgba(0, 0, 0, 0.8)">确定添加
          </el-button>
        </el-upload>
      </el-col>

      <!--模板下载-->
      <el-col :span="2">
        <el-button type="primary" size="small" @click="downloadTemplate" :disabled="isDownloading">下载模板</el-button>
      </el-col>
    </el-row>

    <el-table :data="sentenceList">
      <el-table-column label="序号" type="index" width="50"></el-table-column>
      <el-table-column label="类型" width="120" prop="type" :formatter="formatType"></el-table-column>
      <el-table-column label="内容" width="550" prop="content"></el-table-column>
      <el-table-column label="来源" width="420" prop="source"></el-table-column>

      <el-table-column label="操作">
        <template v-slot="scope">
          <!--编辑-->
          <el-button type="primary" icon="el-icon-edit" size="mini" @click="showEditDialog(scope.row)">编辑</el-button>

          <!--删除-->
          <el-popconfirm title="确定删除吗？" icon="el-icon-delete" iconColor="red" @onConfirm="deleteSentenceById(scope.row.id)">
            <el-button size="mini" type="danger" icon="el-icon-delete" slot="reference">删除</el-button>
          </el-popconfirm>

        </template>
      </el-table-column>
    </el-table>

    <!--分页-->
    <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange"
                   :current-page="queryInfo.pageNum"
                   :page-sizes="[10, 20, 30, 50]" :page-size="queryInfo.pageSize" :total="total"
                   layout="total, sizes, prev, pager, next, jumper" background>
    </el-pagination>

    <!--添加标签对话框-->
    <el-dialog title="添加标签" width="50%" :visible.sync="addDialogVisible" :close-on-click-modal="false"
               @close="addDialogClosed">
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
    <el-dialog title="编辑标签" width="50%" :visible.sync="editDialogVisible" :close-on-click-modal="false"
               @close="editDialogClosed">
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
import {getData, getType, editSentence, addSingleSentence, addExcelSentence, deleteSentenceById} from '@/api/sentence'
import * as XLSX from "xlsx";

export default {
  name: "SentenceList",
  components: {},
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
        id: '',
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
      // 批量添加
      dataForms: {
        barCode: "",
        loading: false,
      },
      fullscreenLoading: false, // 加载中
      loadingText: "正在上传...",
      isDownloading: false, // 模板是否正在下载
    }
  },
  created() {
    this.getData();
    this.getType();
  },
  methods: {
    // 获取美文类型列表
    getType() {
      getType().then(res => {
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

    formatType(row) {
      if (row.type === 1) {
        return '诗词';
      } else if (row.type === 5) {
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

    // 根据id删除对应的美文
    deleteSentenceById(id) {
      deleteSentenceById(id).then(res => {
        this.msgSuccess(res.msg)
        this.getData()
      })
    },

    fileSubmit() {
      //没有选取文件弹出提醒
      if (this.$refs.upload.uploadFiles.length < 1) {
        this.$message.warning("请先选择一个文件");
      } else {
        //否则提交
        this.$refs.upload.submit();
      }
    },

    //上传文件前的钩子，file是文件
    beforeAvatarUpload(file) {
      //拿到文件大小是否小于10
      const isLt10M = file.size / 1024 / 1024 < 10;
      //不小于10提醒过大
      if (!isLt10M) {
        this.$message.error("上传文件大小不能超过 10MB!");
      } else {
        //否则加载效果出现
        this.fullscreenLoading = true;
      }
      return isLt10M;
    },

    //限制选择文件数量一个
    handleExceed(files, fileList) {
      this.$message.warning(`当前限制选择 1 个文件，请先删除下方文件,重新选择`);
    },

    //http-request:覆盖默认的上传行为
    importFile(files) {
      var sentenceList = [];
      //loading图显示正在解析
      this.loadingText = "正在解析";
      //
      var fileReader = new FileReader();
      //加载图显示
      this.dataForms.loading = true;
      // 以二进制方式打开文件
      fileReader.readAsBinaryString(files.file);
      fileReader.onload = (ev) => {
        try {
          var data = ev.target.result,
              workbook = XLSX.read(data, {
                type: "binary",
              }); // 以二进制流方式读取得到整份excel表格对象
        } catch (e) {
          this.$message.error("文件类型不正确");
          this.dataForms.loading = false;
          this.$refs.upload.clearFiles();
          this.fullscreenLoading = false;
          return;
        }
        // 表格的表格范围，可用于判断表头是否数量是否正确
        var fromTo = "";
        // 遍历每张表读取
        for (var sheet in workbook.Sheets) {
          if (workbook.Sheets.hasOwnProperty(sheet)) {
            // 获取当前表格的范围
            fromTo = workbook.Sheets[sheet]["!ref"];
            // 将当前表格转换为 JSON 格式的数组
            var sheetData = XLSX.utils.sheet_to_json(workbook.Sheets[sheet]);
            // 检查列数是否正确
            var expectedColumnNames = ["type", "content", "source"];
            // 获取sheetData数组中的第一行数据
            var columnNames = Object.keys(sheetData[0]);
            if (columnNames.length !== expectedColumnNames.length || !expectedColumnNames.every(name => columnNames.includes(name))) {
              this.$message.error("Excel文档列名或列数不正确，请检查");
              this.$refs.upload.clearFiles();
              this.dataForms.loading = false;
              this.fullscreenLoading = false;
              return;
            }
            // 将每行数据转换为符合后端实体类的对象
            sheetData.forEach((row, index) => {
              var sentence = {
                type: row.type,
                content: row.content,
                source: row.source,
              };
              // 将创建的对象添加到sentenceList数组中
              sentenceList.push(sentence);
            });
            break; // 如果只取第一张表，就取消注释这行
          }
        }
        this.loadingText = "正在上传...";

        //请求后台
        addExcelSentence(sentenceList)
            .then(res => {
              if (res && res.code === 200) {
                this.loadingText = "";
                this.$message({
                  message: "上传成功",
                  type: "success",
                  duration: 1500,
                });
                this.dataForms.loading = false; // 加载状态结束
                this.$refs.upload.clearFiles(); // 清除上传组件中选择的文件
                this.fullscreenLoading = false; // 加载状态结束
                this.getData(); // 刷新列表数据
              } else {
                this.$message.error(res.msg);
                this.$refs.upload.clearFiles();
                this.dataForms.loading = false;
                this.fullscreenLoading = false;
                this.getData(); // 刷新列表数据
              }
            })
            .catch((error) => {
              this.$message.error(error.toString());
              this.$refs.upload.clearFiles();
              this.dataForms.loading = false;
              this.fullscreenLoading = false;
            })
      }
    },

    // 下载模板
    downloadTemplate() {
      if (this.isDownloading) {
        return; // 如果正在下载，则不执行下载逻辑
      }
      // 从localStorage中获取上次下载的时间戳
      const lastDownloadTime = localStorage.getItem('lastDownloadTime');
      const currentTime = Date.now();
      if (lastDownloadTime && currentTime - lastDownloadTime < 60000) {
        // 如果在一分钟内已经下载过，则不执行下载逻辑
        return;
      }
      this.isDownloading = true;
      // 执行下载逻辑
      window.location.href = 'http://qianniu.waylon1024.cn/blog_downloadTemplate/sentence.xlsx';
      // 将当前时间戳保存到localStorage
      localStorage.setItem('lastDownloadTime', currentTime);
      // 一分钟后重置下载状态
      setTimeout(() => {
        this.isDownloading = false;
      }, 60000);
    },

  },
  computed: {
    typeLabel() {
      return (type) => {
        switch (type) {
          case 1:
            return '诗词';
          case 2:
            return 'XX';
          case 3:
            return 'XX';
          case 4:
            return 'XX';
          case 5:
            return '自创';
          default:
            return '';
        }
      }
    },
  },
  mounted() {
    // 在页面加载完成后，检查上次下载的时间戳
    const lastDownloadTime = localStorage.getItem('lastDownloadTime');
    const currentTime = Date.now();
    if (lastDownloadTime && currentTime - lastDownloadTime < 60000) {
      // 如果在一分钟内已经下载过，则禁用下载按钮
      this.isDownloading = true;
    }
  },

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
