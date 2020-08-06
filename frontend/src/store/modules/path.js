import { SET_PATH } from '@/store/shared/mutationTypes'
import { SEARCH_PATH } from '@/store/shared/actionTypes'
import PathService from '@/api/modules/path'

const state = {
  pathResult: null
}

const getters = {
  pathResult(state) {
    return state.pathResult
  }
}

const mutations = {
  [SET_PATH](state, pathResult) {
    state.pathResult = pathResult
  }
}

const actions = {
  async [SEARCH_PATH]({ commit }, items) {
    console.log("test");
    const param = new URLSearchParams(items).toString();
    return PathService.get(param).then(({ data }) => {
      console.log(data)
      commit('setPath', data)
    })
  }
}

export default {
  state,
  getters,
  actions,
  mutations
}