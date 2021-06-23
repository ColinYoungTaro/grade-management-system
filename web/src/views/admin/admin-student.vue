<template>
  <a-layout>
    <the-user-sider></the-user-sider>
    <a-layout-content
            :style="{ background: '#fff', padding: '24px', margin: 0, minHeight: '280px' }"
    >
      <p>
        <a-form layout="inline" :model="param">
          <a-form-item>
            <a-input v-model:value="param.userId" placeholder="学号">
            </a-input>
          </a-form-item>
          <a-form-item>
            <a-button type="primary" @click="handleQuery({page: 1, size: pagination.pageSize})">
              查询
            </a-button>
          </a-form-item>
          <a-form-item>
            <a-button type="primary" @click="add()">
              新增
            </a-button>
          </a-form-item>
        </a-form>
      </p>
      <a-table
              :columns="columns"
              :row-key="record => record.id"
              :data-source="students"
              :pagination="pagination"
              :loading="loading"
              @change="handleTableChange"
      >
        <template v-slot:action="{ text, record }">
          <a-space size="small">
            <a-button type="primary" @click="edit(record)">
              编辑
            </a-button>
            <a-popconfirm
                    title="是否确认删除?"
                    ok-text="是"
                    cancel-text="否"
                    @confirm="handleDelete(record.userId)"
            >
            <a-button type="danger">
              删除
            </a-button>
            </a-popconfirm>
          </a-space>
        </template>
      </a-table>
    </a-layout-content>
  </a-layout>
  <a-modal
          title="学生表单"
          v-model:visible="modalVisible"
          :confirm-loading="modalLoading"
          @ok="handleModalOk"
  >
    <a-form :model="student" :label-col="{ span: 5 }" :wrapper-col="{ span: 18 }">
      <a-form-item label="学号">
        <a-input v-model:value="student.userId" :disabled="!!student.rowId"/>
      </a-form-item>
      <a-form-item label="名字">
        <a-input v-model:value="student.userName" />
      </a-form-item>
      <a-form-item label="密码" v-show="!student.rowId">
        <a-input v-model:value="student.passwordEncode" type="password"/>
      </a-form-item>
      <a-form-item label="性别">
          <a-cascader v-model:value="student.strGender" :options="options" placeholder="性别" />
      </a-form-item>
      <a-form-item label="年级">
        <a-input v-model:value="student.grade" />
      </a-form-item>
      <a-form-item label="学院">
        <a-input v-model:value="student.departmentId" />
      </a-form-item>
      <a-form-item label="专业">
        <a-input v-model:value="student.majorId" />
      </a-form-item>
      <a-form-item label="学籍状态">
        <a-input v-model:value="student.status" />
      </a-form-item>
    </a-form>
  </a-modal>
</template>

<script lang="ts">
  import { defineComponent, onMounted, ref } from 'vue';
  import TheUserSider from '@/components/the-user-sider.vue';
  import axios from 'axios';
  import { message } from 'ant-design-vue';
  import {Tool} from "@/util/tool";

  declare let hexMd5: any;
  declare let KEY: any;

  export default defineComponent({
    name: 'AdminStudent',
    components: {
      TheUserSider,
    },
    setup() {
      const param = ref();
      param.value = {};

      const students = ref();

      const pagination = ref({
        current: 1,
        pageSize: 8,
        total: 0
      });
      const loading = ref(false);

      const columns = [
        {
          title: '学号',
          key: 'userId',
          dataIndex: 'userId'
        },
        {
          title: '名字',
          dataIndex: 'userName'
        },
        {
          title: '密码',
          key: 'passwordEncode',
          dataIndex: 'passwordEncode'
        },
        {
          title: '性别',
          dataIndex: 'strGender'
        },
        {
          title: '年级',
          dataIndex: 'grade'
        },
        {
          title: '学院',
          dataIndex: 'departmentId'
        },
        {
          title: '专业',
          dataIndex: 'majorId'
        },
        {
          title: '学籍状态',
          dataIndex: 'status'
        },
        {
          title: '操作',
          key: 'action',
          slots: { customRender: 'action' }
        }
      ];

        const options = [
            {
                value: "男",
                label: '男',
            },
            {
                value: '女',
                label: '女',
            },
        ];

      /**
       * 数据查询
       **/
      const handleQuery = (params: any) => {
        loading.value = true;
        students.value = [];
        axios.get("/schooluser/student/list", {
          params :{
            page: params.page,
            size: params.size,
            userId: param.value.userId
          }
        }).then((response) => {
          loading.value = false;
          const data = response.data;
          if (data.success) {
            students.value = data.content.list;

            // 重置分页按钮
            pagination.value.current = params.page;
            pagination.value.total = data.content.total;
          } else {
            message.error(data.message);
          }
        });
      };

      /**
       * 表格点击页码时触发
       */
      const handleTableChange = (pagination: any) => {
        console.log("分页参数：" + pagination);
        handleQuery({
          page: pagination.current,
          size: pagination.pageSize
        });
      };

      // -------- 表单 ---------
      const student = ref();
      const modalVisible = ref(false);
      const modalLoading = ref(false);
      const handleModalOk = () => {
        modalLoading.value = true;

        student.value.passwordEncode = hexMd5(student.value.passwordEncode + KEY);

        student.value.gender = student.value.strGender == "男";

        axios.post("/schooluser/student/save", student.value).then((response) => {
          modalLoading.value = false;
          const data = response.data; // data = commonResp
          if (data.success) {
            modalVisible.value = false;

            // 重新加载列表
            handleQuery({
              page: pagination.value.current,
              size: pagination.value.pageSize,
            });
          } else{
            message.error(data.message);
          }
        });
      };

      /**
       * 编辑
       */
      const edit = (record: any) => {
        modalVisible.value = true;
        student.value = Tool.copy(record);
        //student.value = record;
      };

      /**
       * 新增
       */
      const add = () => {
        modalVisible.value = true;
        student.value = {};
      };

      /**
       * 删除
       */
      const handleDelete = (userId: number) => {
        axios.delete("/schooluser/delete/" + userId).then((response) => {
          const data = response.data; // data = commonResp
          if (data.success) {
            // 重新加载列表
            handleQuery({
              page: pagination.value.current,
              size: pagination.value.pageSize,
            });
          }
        });
      };

      onMounted(() => {
        handleQuery({
          page: 1,
          size: pagination.value.pageSize,
        });
      });

      return {
        param,
        students,
        pagination,
        columns,
        loading,
        handleTableChange,
        handleQuery,

        edit,
        add,

        student,
        modalVisible,
        modalLoading,
        handleModalOk,
        handleDelete,
        options,
      }
    }
  });
</script>