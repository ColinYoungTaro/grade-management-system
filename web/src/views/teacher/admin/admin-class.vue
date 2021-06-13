<template>
    <a-layout>
        <the-admin-class-sider></the-admin-class-sider>
        <a-layout-content
                :style="{ background: '#fff', padding: '24px', margin: 0, minHeight: '280px' }"
        >
            <a-table
                    :columns="columns"
                    :rowKey="(record, index)=>{return index}"
                    :data-source="courseClasses"
                    :pagination="pagination"
                    :loading="loading"
                    @change="handleTableChange"
            >
                <template v-slot:action="{ text, record }">
                    <a-space size="small">
                        <a-button type="primary" @click="commitScore(record)">
                            登记分数
                        </a-button>
                    </a-space>
                </template>
            </a-table>
        </a-layout-content>
    </a-layout>
    <a-modal
            title="登记分数"
            v-model:visible="modalVisible"
            :confirm-loading="modalLoading"
            @ok="handleModalOk"
    >
        <a-table
                :columns="columnsScore"
                :rowKey="record => record.userId"
                :data-source="courseClassStudents"
                :pagination="false"
        >
            <template v-slot:action="{ text, record }">
                <a-space size="small">
                    <a-input v-model:value="record.score" placeholder="分数"  :disabled="record.scoredStatus">
                    </a-input>
                </a-space>
            </template>
        </a-table>
    </a-modal>
</template>

<script lang="ts">
    import { defineComponent, onMounted, ref, computed } from 'vue';
    import TheAdminClassSider from '@/components/the-admin-class-sider.vue';
    import axios from 'axios';
    import { message } from 'ant-design-vue';
    import {Tool} from "@/util/tool";
    import store from "@/store";

    export default defineComponent({
        name: 'AdminClass',
        components: {
            TheAdminClassSider,
        },
        setup() {
            const user = computed(() => store.state.user);

            const courseClasses = ref();

            const pagination = ref({
                current: 1,
                pageSize: 8,
                total: 0
            });
            const loading = ref(false);

            const columns = [
                {
                    title: '课程代码',
                    key: 'courseUid',
                    dataIndex: 'courseUid'
                },
                {
                    title: '课程名称',
                    dataIndex: 'courseName'
                },
                {
                    title: '学分',
                    dataIndex: 'credit'
                },
                {
                    title: '学时',
                    dataIndex: 'schoolHour'
                },
                {
                    title: '班级编号',
                    dataIndex: 'classIndex'
                },
                {
                    title: '选课人数',
                    dataIndex: 'studentCount'
                },
                {
                    title: '操作',
                    key: 'action',
                    slots: { customRender: 'action' }
                }
            ];

            const columnsScore = [
                {
                    title: '学号',
                    key: 'studentId',
                    dataIndex: 'studentId'
                },
                {
                    title: '名字',
                    dataIndex: 'userName'
                },
                {
                    title: '分数',
                    key: 'action',
                    slots: { customRender: 'action' }
                }
            ];

            /**
             * 数据查询
             **/
            const handleQuery = (params: any) => {
                loading.value = true;
                courseClasses.value = [];
                axios.get("/teacher/class/list", {
                    params :{
                        page: params.page,
                        size: params.size,
                        teacherId: user.value.userId
                    }
                }).then((response) => {
                    loading.value = false;
                    const data = response.data;
                    if (data.success) {
                        courseClasses.value = data.content.list;

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
            const courseClass = ref();
            // -------- 表单 ---------
            const modalVisible = ref(false);
            const modalLoading = ref(false);
            const handleModalOk = () => {
                modalLoading.value = true;

                axios.post("/teacher/commit/score", courseClassStudents.value).then((response) => {
                    modalLoading.value = false;
                    const data = response.data; // data = commonResp
                    if (data.success) {
                        modalVisible.value = false;
                        message.success(data.message);
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

            const courseClassStudents = ref();
            /**
             * 登记分数
             */
            const commitScore = (record: any) => {

                modalVisible.value = true;
                courseClassStudents.value = [];
                courseClass.value = Tool.copy(record);
                axios.get("/teacher/courseClass/student/list", {
                    params :{
                        teacherId: user.value.userId,
                        courseUid: courseClass.value.courseUid,
                        classIndex: courseClass.value.classIndex
                    }

                }).then((response) => {
                    const data = response.data; // data = commonResp
                    if (data.success) {
                        courseClassStudents.value = data.content;
                    } else{
                        message.error(data.message);
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
                courseClasses,
                pagination,
                columns,
                loading,
                handleTableChange,
                handleQuery,

                courseClass,
                modalVisible,
                modalLoading,
                handleModalOk,
                commitScore,
                columnsScore,
                courseClassStudents,
            }
        }
    });
</script>