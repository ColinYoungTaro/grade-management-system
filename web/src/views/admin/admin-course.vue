<template>
    <a-layout>
        <the-manage-course-sider></the-manage-course-sider>
        <a-layout-content
                :style="{ background: '#fff', padding: '24px', margin: 0, minHeight: '280px' }"
        >
            <p>
                <a-form layout="inline" :model="param">
                    <a-form-item>
                        <a-input v-model:value="param.courseUid" placeholder="课程代码">
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
                    :data-source="courses"
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
                                @confirm="handleDelete(record.courseUid)"
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
            title="课程表单"
            v-model:visible="modalVisible"
            :confirm-loading="modalLoading"
            @ok="handleModalOk"
    >
        <a-form :model="course" :label-col="{ span: 5 }" :wrapper-col="{ span: 18 }">
            <a-form-item label="课程代码">
                <a-input v-model:value="course.courseUid" :disabled="!!course.rowId"/>
            </a-form-item>
            <a-form-item label="课程名称">
                <a-input v-model:value="course.courseName" />
            </a-form-item>
            <a-form-item label="学分">
                <a-input v-model:value="course.credit" />
            </a-form-item>
            <a-form-item label="学时">
                <a-input v-model:value="course.schoolHour" />
            </a-form-item>
            <a-form-item label="课程类型">
                <a-input v-model:value="course.courseType" />
            </a-form-item>
            <a-form-item label="开设学年">
                <a-input v-model:value="course.year" />
            </a-form-item>
            <a-form-item label="开设学期">
                <a-input v-model:value="course.term" />
            </a-form-item>
        </a-form>
    </a-modal>
</template>


<script lang="ts">
    import { defineComponent, onMounted, ref, computed } from 'vue';
    import { message } from 'ant-design-vue';
    import {Tool} from "@/util/tool";
    import TheManageCourseSider from '@/components/the-manage-course-sider.vue';
    import store from "@/store";
    import axios from 'axios';

    export default defineComponent({
        name: 'AdminCourse',
        components: {
            TheManageCourseSider,
        },
        setup() {
            const param = ref();
            param.value = {};

            const courses = ref();

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
                    key: 'credit',
                    dataIndex: 'credit'
                },
                {
                    title: '学时',
                    dataIndex: 'schoolHour'
                },
                {
                    title: '课程类型',
                    dataIndex: 'stringCourseType'
                },
                {
                    title: '开设学年',
                    dataIndex: 'year'
                },
                {
                    title: '开设学期',
                    dataIndex: 'stringTerm'
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
                courses.value = [];
                axios.get("/course/list", {
                    params :{
                        page: params.page,
                        size: params.size,
                        courseUid: param.value.courseUid
                    }
                }).then((response) => {
                    loading.value = false;
                    const data = response.data;
                    if (data.success) {
                        courses.value = data.content.list;

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
            const course = ref();
            const modalVisible = ref(false);
            const modalLoading = ref(false);
            const handleModalOk = () => {
                modalLoading.value = true;

                axios.post("/course/save", course.value).then((response) => {
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
                course.value = Tool.copy(record);
            };

            /**
             * 新增
             */
            const add = () => {
                modalVisible.value = true;
                course.value = {};
            };

            /**
             * 删除
             */
            const handleDelete = (courseUid: number) => {
                axios.delete("/course/delete/" + courseUid).then((response) => {
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
                courses,
                pagination,
                columns,
                loading,
                handleTableChange,
                handleQuery,

                edit,
                add,

                course,
                modalVisible,
                modalLoading,
                handleModalOk,
                handleDelete
            }
        }
    });

</script>