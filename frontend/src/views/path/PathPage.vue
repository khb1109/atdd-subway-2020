<template>
    <div class="d-flex flex-column justify-center height-100vh-65px">
        <div class="d-flex justify-center relative">
            <v-card class="card-border" max-height="600" max-width="600" width="500">
                <v-card-title class="font-weight-bold justify-center relative">
                    경로 검색
                </v-card-title>
                <v-card-text class="relative mt-2 px-0 line-list-container d-flex flex-column">
                    <div class="px-4 pb-6">
                        <div class="d-flex width-100">
                            <v-select
                                    :items="allStationsView"
                                    class="pr-4 path-station-select"
                                    color="grey darken-1"
                                    dense
                                    item-color="amber darken-3"
                                    label="출발역"
                                    outlined
                                    v-model="path.source"
                            ></v-select>
                            <v-icon class="relative bottom-15">mdi-arrow-right-bold</v-icon>
                            <v-select
                                    :items="allStationsView"
                                    class="pl-4 path-station-select"
                                    color="grey darken-1"
                                    dense
                                    item-color="amber darken-3"
                                    label="도착역"
                                    outlined
                                    v-model="path.target"
                            ></v-select>
                        </div>
                        <div class="d-flex mb-4">
                            <v-btn @click="onSearchResult" class="width-100" color="amber" depressed>검색</v-btn>
                        </div>
                        <v-divider v-if="pathResult"/>
                        <div class="d-flex justify-center mt-4" v-if="pathResult">
                            <v-card flat width="400">
                                <v-tabs background-color="transparent" color="amber" grow v-model="tab">
                                    <v-tab @click="onChangePathType(PATH_TYPE.DISTANCE)">최단 거리</v-tab>
                                    <v-tab @click="onChangePathType(PATH_TYPE.DURATION)">최소 시간</v-tab>
                                    <v-tab @click="onChangePathType(PATH_TYPE.ARRIVAL_TIME)">빠른 도착</v-tab>
                                </v-tabs>
                                <v-tabs-items v-model="tab">
                                    <v-tab-item>
                                        <v-simple-table>
                                            <template v-slot:default>
                                                <thead>
                                                <tr>
                                                    <th>소요시간</th>
                                                    <th>거리</th>
                                                    <th>요금</th>
                                                </tr>
                                                </thead>
                                                <tbody>
                                                <tr>
                                                    <td>{{ pathResult.duration }}분</td>
                                                    <td>{{ pathResult.distance }}km</td>
                                                    <td>{{ pathResult.woowaSubwayFare }}원</td>
                                                </tr>
                                                </tbody>
                                            </template>
                                        </v-simple-table>
                                    </v-tab-item>
                                    <v-tab-item>
                                        <v-simple-table>
                                            <template v-slot:default>
                                                <thead>
                                                <tr>
                                                    <th>소요시간</th>
                                                    <th>거리</th>
                                                    <th>요금</th>
                                                </tr>
                                                </thead>
                                                <tbody>
                                                <tr>
                                                    <td>{{ pathResult.duration }}분</td>
                                                    <td>{{ pathResult.distance }}km</td>
                                                    <td>{{ pathResult.woowaSubwayFare }}원</td>
                                                </tr>
                                                </tbody>
                                            </template>
                                        </v-simple-table>
                                    </v-tab-item>
                                    <v-tab-item>
                                        <Dialog :close="close">
                                            <template slot="trigger">
                                                <v-btn class="mt-4" depressed>
                                                    {{ getCurrentTime }} 출발
                                                    <v-icon>mdi-menu-down</v-icon>
                                                </v-btn>
                                            </template>
                                            <template slot="title">
                                                <div class="width-100 text-center mt-6">출발 시각 설정</div>
                                            </template>
                                            <template slot="text">
                                                <div class="px-2">
                                                    <p>출발 시간을 기준으로 가장 빠른 경로를 안내해드립니다.</p>
                                                    <v-form @submit.prevent ref="edgeForm" v-model="valid">
                                                        <v-row>
                                                            <v-col cols="4">
                                                                <v-select
                                                                        :items="departureTimeSelectView.dayTime"
                                                                        :rules="rules.departureTime.dayTime"
                                                                        color="grey darken-1"
                                                                        dense
                                                                        item-color="amber darken-3"
                                                                        outlined
                                                                        v-model="departureTimeView.dayTime"
                                                                        width="400"
                                                                ></v-select>
                                                            </v-col>
                                                            <v-col cols="4">
                                                                <v-select
                                                                        :items="departureTimeSelectView.hour"
                                                                        :rules="rules.departureTime.hour"
                                                                        color="grey darken-1"
                                                                        dense
                                                                        item-color="amber darken-3"
                                                                        label="시"
                                                                        outlined
                                                                        v-model="departureTimeView.hour"
                                                                        width="400"
                                                                ></v-select>
                                                            </v-col>
                                                            <v-col cols="4">
                                                                <v-select
                                                                        :items="departureTimeSelectView.minute"
                                                                        :rules="rules.departureTime.minute"
                                                                        color="grey darken-1"
                                                                        dense
                                                                        item-color="amber darken-3"
                                                                        label="분"
                                                                        outlined
                                                                        v-model="departureTimeView.minute"
                                                                        width="400"
                                                                ></v-select>
                                                            </v-col>
                                                        </v-row>
                                                    </v-form>
                                                </div>
                                            </template>
                                            <template slot="action">
                                                <v-btn :disabled="!valid" @click.prevent="onUpdateSearchResult"
                                                       color="amber">확인
                                                </v-btn>
                                            </template>
                                        </Dialog>
                                        <v-simple-table>
                                            <template v-slot:default>
                                                <thead>
                                                <tr>
                                                    <th>소요시간</th>
                                                    <th>거리</th>
                                                    <th>요금</th>
                                                </tr>
                                                </thead>
                                                <tbody>
                                                <tr>
                                                    <td>{{ pathResult.duration }}분</td>
                                                    <td>{{ pathResult.distance }}km</td>
                                                    <td>{{ pathResult.woowaSubwayFare }}원</td>
                                                </tr>
                                                </tbody>
                                            </template>
                                        </v-simple-table>
                                    </v-tab-item>
                                </v-tabs-items>
                            </v-card>
                        </div>
                        <v-divider/>
                        <div class="d-flex justify-center mt-4" v-if="pathResult">
                            <v-card flat width="400">
                                <template v-for="(station, index) in pathResult.stations">
                  <span :key="station.id">
                    <v-chip :color="index === 0 || index === pathResult.stations.length - 1 ? 'amber' : ''" :key="index"
                            class="ma-2">
                      <v-avatar left v-if="index === 0 || index === pathResult.stations.length - 1">
                        <v-icon>mdi-subway</v-icon>
                      </v-avatar>
                      {{ station.name }}
                    </v-chip>
                    <v-icon v-if="index < pathResult.stations.length - 1">mdi-arrow-right-bold</v-icon>
                  </span>
                                </template>
                            </v-card>
                            <AddFavoriteButton :path="path"/>
                        </div>
                    </div>
                </v-card-text>
            </v-card>
        </div>
    </div>
</template>

<script>
    import validator from '@/utils/validator'
    import {mapActions, mapGetters, mapMutations} from 'vuex'
    import {SHOW_SNACKBAR} from '@/store/shared/mutationTypes'
    import {PATH_TYPE, SNACKBAR_MESSAGES} from '@/utils/constants'
    import {FETCH_STATIONS, SEARCH_PATH} from '@/store/shared/actionTypes'
    import AddFavoriteButton from '@/views/path/components/AddFavoriteButton'
    import dialog from '@/mixins/dialog'
    import Dialog from '@/components/dialogs/Dialog'

    export default {
        name: 'PathPage',
        components: {Dialog, AddFavoriteButton},
        mixins: [dialog],
        computed: {
            ...mapGetters(['stations', 'pathResult']),
            getCurrentTime() {
                const {hour, minute} = this.departureTimeView
                return `${hour > 12 ? '오후' : '오전'} ${hour < 10 ? `0${hour}` : hour}:${minute < 10 ? `0${minute}` : minute}`
            }
        },
        async created() {
            await this.initAllStationsView()
            this.initDepartureTimeView()
        },
        methods: {
            ...mapMutations([SHOW_SNACKBAR]),
            ...mapActions([SEARCH_PATH, FETCH_STATIONS]),
            async onSearchResult() {
                try {
                } catch (e) {
                    this.showSnackbar(SNACKBAR_MESSAGES.COMMON.FAIL)
                    console.error(e)
                }
            },
            initDepartureTimeView() {
                this.departureTimeSelectView.hour = Array.from(Array(24)).map((_, i) => this.getTimeSelectView(i))
                this.departureTimeSelectView.minute = Array.from(Array(60)).map((_, i) => this.getTimeSelectView(i))
                const today = new Date()
                const hour = today.getHours()
                const dayTime = hour > 12 ? 'pm' : 'am'
                const minute = today.getMinutes()
                this.departureTimeView = {dayTime, minute, hour}
            },
            getTimeSelectView(i) {
                return {
                    text: i < 10 ? `0${i}` : i,
                    value: i
                }
            },
            async onUpdateSearchResult() {
                try {
                    await this.onSearchResult()
                    this.closeDialog()
                    this.showSnackbar(SNACKBAR_MESSAGES.PATH.ARRIVAL_TIME.SUCCESS)
                } catch (e) {
                    this.showSnackbar(SNACKBAR_MESSAGES.PATH.ARRIVAL_TIME.FAIL)
                }
            },
            async initAllStationsView() {
                try {
                    await this.fetchStations()
                    if (this.stations.length <= 0) {
                        return
                    }
                    this.allStationsView = this.stations.map((station) => {
                        return {
                            text: station.name,
                            value: station.id
                        }
                    })
                } catch (e) {
                    this.showSnackbar(SNACKBAR_MESSAGES.COMMON.FAIL)
                }
            },
            onChangePathType(type) {
                this.path.type = type
                this.onSearchResult()
            }
        },
        data() {
            return {
                path: {
                    source: '',
                    target: '',
                    type: PATH_TYPE.DISTANCE
                },
                allStationsView: [],
                rules: {...validator},
                PATH_TYPE: {...PATH_TYPE},
                tab: null,
                valid: false,
                time: '',
                departureTimeView: {
                    dayTime: '',
                    hour: '',
                    minute: ''
                },
                departureTimeSelectView: {
                    dayTime: [
                        {
                            text: '오전',
                            value: 'am'
                        },
                        {
                            text: '오후',
                            value: 'pm'
                        }
                    ]
                }
            }
        }
    }
</script>
<style scoped>
    .path-station-select {
        width: 200px;
    }
</style>
