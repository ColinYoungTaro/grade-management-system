<template>
    <a-layout>
        <the-admin-class-sider></the-admin-class-sider>
        <a-layout-content
                :style="{ background: '#fff', padding: '24px', margin: 0, minHeight: '280px' }"
        > <div align="center">

            <a-card title="审核申请"  style="width: 1100px" :body-style='{padding:1}' :headStyle="{ height: 9,  background: '#0785fd', color:'#ffffff'}">
                <div align="center">
            <a-table
                    :columns="columns"
                    :rowKey="(record, index)=>{return index}"
                    :data-source="appealList"
                    :loading="loading"
                    :pagination="false"
            >
                <template v-slot:action="{ text, record }">
                    <a-space size="small">
                        <a-button type="primary" @click="checkApply(record)" :style="!!record.appeal.status? {display:'none'} : {}">
                            待审核
                        </a-button>
                    </a-space>
                    <a-space size="small">
                    <a-button type="default" :style="!record.appeal.status? {display:'none'} : {}">
                        已处理
                    </a-button>
                    </a-space>
                </template>
            </a-table>
                </div>
            </a-card>
        </div>
        </a-layout-content>
    </a-layout>

    <a-modal
            title="审核申请"
            v-model:visible="modalVisible"
            :confirm-loading="modalLoading"
            @ok="handleApplyModalOk"
            cancelText="取消"
            okText="确认"
    >
        <a-form :model="appealReq" :label-col="{ span: 5 }" :wrapper-col="{ span: 18 }">
            <a-form-item label="申请编号">
                <a-input v-model:value="appealReq.rowId" :disabled="true"/>
            </a-form-item>
            <a-form-item label="是否同意">
                <a-cascader v-model:value="appealReq.agree" :options="options" placeholder="不同意" />
            </a-form-item>
            <a-form-item label="分数" v-show="appealReq.agree==1">
                <a-input v-model:value="appealReq.score"/>
            </a-form-item>
        </a-form>
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
        name: 'TeacherAdminApply',
        components: {
            TheAdminClassSider,
        },
        setup() {
            const user = computed(() => store.state.user);

            const appealList = ref();

            const loading = ref(false);

            const appeal = ref();
            appeal.value = {};
            const modalVisible = ref(false);
            const modalLoading = ref(false);

            const appealReq = ref();
            appealReq.value={};

            const options = [
                {
                    value: 1,
                    label: '同意',
                },
                {
                    value: 0,
                    label: '不同意',
                },
            ];


            const columns = [
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
                    title: '学生学号',
                    dataIndex: 'studentId'
                },
                {
                    title: '学生姓名',
                    dataIndex: 'studentName'
                },
                {
                    title: '原始成绩',
                    dataIndex: 'appeal.originalScore'
                },
                {
                    title: '复核成绩',
                    dataIndex: 'appeal.alteredScore'
                },
                {
                    title: '状态',
                    dataIndex: 'appeal.status'
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
            const handleQuery = () => {
                loading.value = true;
                appealList.value = [];
                axios.get("/appeal/teacher_appeals/"+user.value.userId).then((response) => {
                    loading.value = false;
                    const data = response.data;
                    if (data.success) {
                        appealList.value = data.content;
                    } else {
                        message.error(data.message);
                    }
                });
            };

            /**
             * 编辑
             */
            const checkApply = (record: any) => {
                modalVisible.value = true;
                appeal.value = Tool.copy(record);
                appealReq.value.rowId = appeal.value.appeal.rowId;
                appealReq.value.score = appeal.value.appeal.originalScore;
                appealReq.value.agree = 0;

            };

            const handleApplyModalOk = () => {
                modalLoading.value = true;
                appealReq.value.agree = (appealReq.value.agree==1) ?true:false;
                axios.post("/appeal/appeal_handle/teacher", appealReq.value).then((response) => {
                    modalLoading.value = false;
                    const data = response.data; // data = commonResp
                    if (data.success) {
                        modalVisible.value = false;
                        // 重新加载列表
                        handleQuery();
                        message.info(data.message);
                    } else{
                        message.error(data.message);
                    }
                });
            };

            onMounted(() => {
                handleQuery();
            });

            return {
                appealList,
                columns,
                loading,
                handleQuery,
                modalVisible,
                modalLoading,
                checkApply,
                appeal,
                appealReq,
                options,
                handleApplyModalOk,
            }
        }
    });
</script>

