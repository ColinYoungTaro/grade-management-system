<template>
  <a-layout>
    <the-user-sider></the-user-sider>
    <a-layout-content
            :style="{ background: '#fff', padding: '24px', margin: 0, minHeight: '280px' }"
    >
      <a-table
              :columns="columns"
              :row-key="record => record.id"
              :data-source="student"
              :pagination="pagination"
              :loading="loading"
              @change="handleTableChange"
      >
        <template v-slot:action="{ text, record }">
          <a-space size="small">
            <a-button type="primary">
              编辑
            </a-button>
            <a-button type="danger">
              删除
            </a-button>
          </a-space>
        </template>
      </a-table>
    </a-layout-content>
  </a-layout>
</template>

<script lang="ts">
  import { defineComponent, onMounted, ref } from 'vue';
  import TheUserSider from '@/components/the-user-sider.vue';
  import axios from 'axios';

  export default defineComponent({
    name: 'AdminManager',
    components: {
      TheUserSider,
    },
    setup() {
      const student = ref();

      const pagination = ref({
        current: 1,
        pageSize: 3,
        total: 0
      });
      const loading = ref(false);

      const columns = [
        {
          title: '用户名',
          dataIndex: 'userId'
        },
        {
          title: '专业',
          key: 'majorId',
          dataIndex: 'majorId'
        },
        {
          title: '年级',
          dataIndex: 'grade'
        },
        {
          title: '学籍状态',
          dataIndex: 'status'
        },
        {
          title: '行政班级',
          dataIndex: 'adminClassId'
        },
        {
          title: '入学年份',
          dataIndex: 'enterYear'
        },
        {
          title: '操作',
          key: 'action',
          slots: { customRender: 'action' }
        }
      ];

      /**
       * 数据查询
       **/
      const handleQuery = (params: any) => {
        loading.value = true;
        axios.get("/student/list", {
          params :{
            page: params.page,
            size: params.size
          }
        }).then((response) => {
          loading.value = false;
          const data = response.data;
          student.value = data.content.list;

          // 重置分页按钮
          pagination.value.current = params.page;
          pagination.value.total = data.content.total;
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

      onMounted(() => {
        handleQuery({
          page: 1,
          size: pagination.value.pageSize,
        });
      });

      return {
        student,
        pagination,
        columns,
        loading,
        handleTableChange
      }
    }
  });
</script>