<template>
    <a-layout>
        <the-student-score-sider></the-student-score-sider>
        <a-layout-content
                :style="{ background: '#fff', padding: '24px', margin: 0, minHeight: '280px' }"
        >
            <div align="right">
                <a-form layout="inline" :model="param">
                    <a-form-item>
                        <a-button type="Default" @click="showApplyAppealModal">
                            <PlusOutlined />  申请
                        </a-button><a-button type="Default" >
                        <FormOutlined />  修改
                    </a-button><a-button type="Default">
                        <CloseOutlined />  删除
                    </a-button>
                    </a-form-item>
                </a-form>
            </div>
            <div align="center">

                <a-card title="申请记录"  style="width: 1100px" :body-style='{padding:1}' :headStyle="{ height: 9,  background: '#0785fd', color:'#ffffff'}">
                    <div align="center">
                        <a-table
                                :columns="columns"
                                :row-key="record => record.id"
                                :data-source="course_scores"
                                :loading="loading"
                                :pagination="false"
                        >
                            <template v-slot:action="{ text, record }">
                                <a-space size="small">
                                    <a-checkbox v-model:checked="checked"></a-checkbox>
                                </a-space>

                            </template>
                        </a-table>
                    </div>
                </a-card>

            </div>

        </a-layout-content>
    </a-layout>

    <a-modal
            title="复核申请"
            v-model:visible="modalVisible"
            :confirm-loading="modalLoading"
            @ok="handleApplyModalOk"
            cancelText="取消"
            okText="确认"
    >
        <a-form :model="appeal" :label-col="{ span: 5 }" :wrapper-col="{ span: 18 }">
            <a-form-item label="学号">
                <a-input v-model:value="appeal.studentId" :disabled="true"/>
            </a-form-item>
            <a-form-item label="课程代码">
                <a-input v-model:value="appeal.courseUid" />
            </a-form-item>
            <a-form-item label="班级编号">
                <a-input v-model:value="appeal.classIndex"/>
            </a-form-item>
        </a-form>
    </a-modal>
</template>


<script lang="ts">
    import { defineComponent, onMounted, ref, computed } from 'vue';
    import TheStudentScoreSider from '@/components/the-student-score-sider.vue';
    import axios from 'axios';
    import { message } from 'ant-design-vue';
    import {Tool} from "@/util/tool";
    import store from "@/store";


    export default defineComponent({
        name: 'StudentScoreAppeal',
        components: {
            TheStudentScoreSider,
        },
        setup() {

            const user = computed(() => store.state.user);


            const param = ref();
            param.value = {};

            const course_scores = ref();

            const pagination = ref({
                current: 1,
                pageSize: 8,
                total: 0
            });
            const loading = ref(false);

            const columns = [
                {
                    title: '选择',
                    key: 'action',
                    slots: { customRender: 'action' }
                },
                {
                    title: '课程代码',
                    key: 'courseUid',
                    dataIndex: 'appeal.courseUid'
                },
                {
                    title: '课程名称',
                    dataIndex: 'course.courseName'
                },
                {
                    title: '班级编号',
                    dataIndex: 'appeal.classIndex'
                },
                {
                    title: '原始成绩',
                    dataIndex: 'appeal.originalScore'
                },
                {
                    title: '状态',
                    dataIndex: 'appeal.status'
                },
                {
                    title: '复核成绩',
                    dataIndex: 'appeal.alteredScore'
                },
            ];

            /**
             * 数据查询
             **/
            const handleQuery = (params: any) => {
                loading.value = true;
                course_scores.value = [];
                axios.get("/appeal/student_appeals/"+user.value.userId).then((response) => {
                    loading.value = false;
                    const data = response.data;
                    if (data.success) {
                        course_scores.value = data.content;

                    } else {
                        message.error(data.message);
                    }
                });
            };


            // -------- 表单 ---------
            const appeal = ref();
            appeal.value = {};
            appeal.value.studentId = user.value.userId;
            const modalVisible = ref(false);
            const modalLoading = ref(false);
            const handleApplyModalOk = () => {
                modalLoading.value = true;

                axios.post("/appeal/student_appeal", appeal.value).then((response) => {
                    modalLoading.value = false;
                    const data = response.data; // data = commonResp
                    if (data.success) {
                        modalVisible.value = false;
                        // 重新加载列表
                        handleQuery({
                            page: pagination.value.current,
                            size: pagination.value.pageSize,
                        });
                        message.info(data.message);
                    } else{
                        message.error(data.message);
                    }
                });
            };


            /**
             * 编辑
             */
            const showApplyAppealModal = () => {
                modalVisible.value = true;

            };

            onMounted(() => {
                handleQuery({
                    page: 1,
                    size: pagination.value.pageSize,
                });
            });

            return {
                param,
                pagination,
                columns,
                loading,
                handleQuery,
                course_scores,
                user,
                modalVisible,
                modalLoading,
                appeal,
                showApplyAppealModal,
                handleApplyModalOk,
            }
        }
    });
</script>

