import { createRouter, createWebHistory } from 'vue-router'
import MailListView from '@/views/mails/MailListView.vue'
import MailDetailView from '@/views/mails/MailDetailView.vue'
import LandingPage from '@/views/LandingPage/LandingPageView.vue'
import SignUp from '@/views/LandingPage/SignUp.vue'
// import NavBar from '@/components/NavBar.vue'

const routes = [
  {
    path: '/',
    name: 'land-linepage-view',
    component: LandingPage
  },
  {
    path: '/signup',
    name: 'sign-up-view',
    component: SignUp
  },
  {
    path: '/list',
    name: 'mail-list-view',
    component: MailListView
  },
  {
    path: '/mail',
    name: 'mail-detail-view',
    component: MailDetailView
  },
  {
    path: '/home',
    name: 'landing-page',
    component: LandingPage
  }
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router