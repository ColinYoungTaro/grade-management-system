<template>
    <a-layout>
        <the-select-course-sider></the-select-course-sider>
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
                        <a-button type="primary" @click="handleQuery">
                            查询
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
                        <a-button type="primary" @click="select(record)" :style="!!record.selectCourseStatus? {display:'none'} : {}">
                            选课
                        </a-button>
                    </a-space>
                    <a-space size="small">
                    <a-button type="danger"  @click="unselect(record)"  :style="!record.selectCourseStatus? {display:'none'} : {} ">
                        退选
                    </a-button>
                    </a-space>
                </template>
            </a-table>
        </a-layout-content>
    </a-layout>
</template>


<script lang="ts">
    import { defineComponent, onMounted, ref, computed  } from 'vue';
    import TheSelectCourseSider from '@/components/the-select-course-sider.vue';
    import { TableState, TableStateFilters } from 'ant-design-vue/es/table/interface';
    import axios from 'axios';
    import { message } from 'ant-design-vue';
    import {Tool} from "@/util/tool";
    import store from "@/store";

    export default defineComponent({
        name: 'SelectALLCourse',
        components: {
            TheSelectCourseSider,
        },
        setup() {
            const user = computed(() => store.state.user);
            const current_year = computed(() => store.state.currentYear);
            const current_term = computed(() => store.state.currentTerm);

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
                    title: '课程类型',
                    dataIndex: 'stringCourseType'
                },
                {
                    title: '开课学院',
                    dataIndex: 'departmentName'
                },
                {
                    title: '状态',
                    dataIndex: 'stringStatus',
                    filters: [
                        { text: '未选', value: '未选' },
                        { text: '已选', value: '已选' },
                    ],
                },
                {
                    title: '教师',
                    dataIndex: 'teacherName'
                },
                {
                    title: '班级',
                    dataIndex: 'classIndex'
                },
                {
                    title: '学期',
                    dataIndex: 'courseTerm'
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
                axios.get("/student/get/all/course", {
                    params :{
                        userId: user.value.userId,
                        courseUid: param.value.courseUid
                    }
                }).then((response) => {
                    loading.value = false;
                    const data = response.data;
                    if (data.success) {
                        courses.value = data.content;
                        pagination.value.current = params.page;
                    } else {
                        message.error(data.message);
                    }
                });
            };

            const selectCourse = ref();

            /**
             * 选课
             **/
            const select = (record: any) => {

                selectCourse.value = Tool.copy(record);
                selectCourse.value.studentId = user.value.userId;

                axios.post("/student/select/course", selectCourse.value).then((response) => {
                    const data = response.data; // data = commonResp
                    if (data.success) {
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

            /**
             * 退课
             **/
            const unselect = (record: any) => {
                axios.delete("/student/unselect/course/" + user.value.userId +"/"+ record.courseUid +"/"+ record.classIndex).then((response) => {
                    const data = response.data; // data = commonResp
                    if (data.success) {
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
                select,
                selectCourse,
                unselect,

            }
        }
    });
</script>