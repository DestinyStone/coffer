import VueRouter from 'vue-router'  //将createRouter、createWebHistory引入vue

const routes = [
  {
    path: '/',  //配置默认路由
    name: 'home', //路由名
    component: () => import("@/views/user/HomeView.vue"), //引入该路由使用的组件
  },
  {
    path: '/detail',
    name: 'detail',
    component: () => import("@/views/user/ItemDetail.vue"),
  },
  {
    path: '/shopping',
    name: 'shopping',
    component: () => import("@/views/user/CartShopping.vue"),
  },
  {
    path: '/admin',
    name: 'admin',
    component: () => import("@/views/admin/AdminManager.vue"),
  },
];
const router = new VueRouter({ //设置为history模式
  routes
});

export default router
