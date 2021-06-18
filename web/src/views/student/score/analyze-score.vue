<template>
    <a-layout>
        <the-student-score-sider></the-student-score-sider>
        <a-layout-content
                :style="{ background: '#fff', padding: '3px', margin: 0, minHeight: '280px' }"
        >
            <div align="center">

                <a-card title="学业成绩"  style="width: 1200px" :body-style='{padding:1}' :headStyle="{ height: 9,  background: '#0785fd', color:'#ffffff'}">
                    <div align="center">
                        <a-card style="width: 1100px" :bordered=false :body-style='{padding:0}' :headStyle="{ height: 6, 'text-align': 'left' }"  >
                            <div style="background-color: #ffffff; ">
                                <a-row :gutter="0">
                 <pre>

                 </pre>
                                    <a-col :span="10">
                                        <a-card  title="学年平均绩点"  :bordered="false">
                                            <a-col :span="24">
                                                <div id="average_score" style="width: 100%;height:300px;"></div>
                                            </a-col>
                                        </a-card>
                                    </a-col>
                                    <pre>

    </pre>
                                    <a-col :span="10">
                                        <a-card  title="学年排名统计"  :bordered="false">
                                            <a-col :span="24">
                                                <div id="rank_score" style="width: 100%;height:300px;"></div>
                                            </a-col>
                                        </a-card>
                                    </a-col>
                                </a-row>
                            </div>
                        </a-card>
                    </div>

                    <div align="center">
                        <a-card title="学籍信息" style="width: 1100px" :bordered=false  :body-style='{padding:0}' :headStyle="{height: 6, 'text-align': 'left' }" >
                            <div style="background-color: #ffffff; ">
                                <a-row :gutter="0">
                 <pre>

                 </pre>
                                    <a-col :span="10">
                                        <a-card title="学年总学分" :bordered="false">
                                            <a-col :span="24">
                                                <div id="total_credit" style="width: 100%;height:300px;"></div>
                                            </a-col>
                                        </a-card>
                                    </a-col>
                                    <pre>

    </pre>
                                    <a-col :span="10">
                                        <a-card title="挂科情况统计" :bordered="false">
                                            <a-col :span="24">
                                                <div id="failed_course" style="width: 100%;height:300px;"></div>
                                            </a-col>
                                        </a-card>
                                    </a-col>
                                </a-row>
                            </div>
                        </a-card>
                    </div>
                    <a-button type="primary" @click="handleConfirm">
                        确定
                    </a-button>
                </a-card>
            </div>
        </a-layout-content>
    </a-layout>
</template>


<script lang="ts">
    import moment, { Moment } from 'moment';
    import { defineComponent, onMounted, ref, computed } from 'vue';
    import TheStudentScoreSider from '@/components/the-student-score-sider.vue';
    import axios from 'axios';
    import { message } from 'ant-design-vue';
    import {Tool} from "@/util/tool";
    import store from "@/store";

    declare let echarts: any;


    export default defineComponent({
        name: 'AnalyzeScore',
        components: {
            TheStudentScoreSider,
        },
        setup() {
            const dateFormat = 'YYYY/MM/DD';
            const user = computed(() => store.state.user);

            const student = ref();
            student.value = {};

            /**
             * 数据查询
             **/
            const handleQuery = (params: any) => {
                student.value = {};
                axios.get("/student/information", {
                    params :{
                        userId: user.value.userId,
                    }
                }).then((response) => {
                    const data = response.data;
                    if (data.success) {
                        student.value = data.content;
                    } else {
                        message.error(data.message);
                    }
                });
            };

            const handleConfirm = (params: any) => {
                axios.post("/student/information/save", student.value).then((response) => {
                    const data = response.data; // data = commonResp
                    if (data.success) {
                        message.info("提交成功");
                        // 重新加载列表
                        handleQuery({
                            userId: user.value.userId,
                        });
                    } else{
                        message.error(data.message);
                    }
                });
            };


            const scoreEcharts = (list: any) => {
                // 基于准备好的dom，初始化echarts实例
                const averageScoreChart = echarts.init(document.getElementById('average_score'));

                const xAxis = [];
                const averageScore = [];
                for (let i = 0; i < list.length; i++) {
                    const record = list[i];
                    xAxis.push(record.date);
                    averageScore.push(record.averageScore);
                }
                // 指定图表的配置项和数据
                const averageScoreOption = {
                    tooltip: {
                        trigger: 'axis'
                    },
                    legend: {
                        data:['平均绩点']
                    },
                    toolbox: {
                        feature: {
                            saveAsImage: {}
                        }
                    },
                    xAxis: {
                        type: 'category',
                        boundaryGap: false,
                        data: xAxis
                    },
                    yAxis: {
                        type: 'value'
                    },
                    series: [{
                        name: '平均绩点',
                        type: 'bar',
                        data: averageScore
                    }]
                };



                // 使用刚指定的配置项和数据显示图表。
                averageScoreChart.setOption(averageScoreOption);
            };

            const getScoreInformation = () => {
                axios.get('/student/score/information',{
                    params :{
                        userId: user.value.userId,
                    }
                }).then((response) => {
                    const data = response.data;
                    if (data.success) {
                        const averageScoreList = data.content;

                        scoreEcharts(averageScoreList)
                    }
                });
            };

            onMounted(() => {
                handleQuery({
                    userId: user.value.userId,
                });
                getScoreInformation();
            });

            return {
                handleQuery,
                student,
                user,
                dateFormat,
                handleConfirm,
            }
        }
    });
</script>
