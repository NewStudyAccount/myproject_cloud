import type { App } from 'vue'
import Pagination from './Pagination/index.vue'
import RightToolbar from './RightToolbar/index.vue'

export default {
  install(app: App) {
    app.component('Pagination', Pagination)
    app.component('RightToolbar', RightToolbar)
  }
}
