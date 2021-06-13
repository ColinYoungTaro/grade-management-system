import { createStore } from 'vuex'

declare let SessionStorage: any;
const USER = "USER";

const store = createStore({
  state: {
    user: SessionStorage.get(USER) || {},
    managerRoleId: 1,
    teacherRoleId: 2,
    studentRoleId: 3,
    currentYear: 21,
    currentTerm: 3,
  },
  mutations: {
    setUser (state, user) {
      state.user = user;
      SessionStorage.set(USER, user);
    }
  },
  actions: {
  },
  modules: {
  }
});

export default store;
