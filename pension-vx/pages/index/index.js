const { request } = require('../../utils/request')

Page({
  data: {
    userInfo: null,
    greeting: '早上好',
    currentDate: '',
    currentTime: '',
    elderlyCount: 0,
    serviceCount: 0,
    activityCount: 0,
    recentActivities: [],
    todayTasks: []
  },

  onLoad() {
    this.checkLogin()
    this.updateDateTime()
    // 每分钟更新一次时间
    this.timeInterval = setInterval(() => {
      this.updateDateTime()
    }, 60000)
  },

  onUnload() {
    if (this.timeInterval) {
      clearInterval(this.timeInterval)
    }
  },

  onShow() {
    this.loadData()
  },

  updateDateTime() {
    const now = new Date()
    const hour = now.getHours()
    const month = now.getMonth() + 1
    const day = now.getDate()
    const weekDays = ['日', '一', '二', '三', '四', '五', '六']
    const weekDay = weekDays[now.getDay()]

    // 根据时间设置问候语
    let greeting = '早上好'
    if (hour >= 6 && hour < 9) {
      greeting = '早上好'
    } else if (hour >= 9 && hour < 12) {
      greeting = '上午好'
    } else if (hour >= 12 && hour < 14) {
      greeting = '中午好'
    } else if (hour >= 14 && hour < 18) {
      greeting = '下午好'
    } else if (hour >= 18 && hour < 22) {
      greeting = '晚上好'
    } else {
      greeting = '夜深了'
    }

    this.setData({
      greeting: greeting,
      currentDate: `${month}月${day}日 星期${weekDay}`,
      currentTime: this.formatTime(now)
    })
  },

  formatTime(date) {
    const hour = date.getHours()
    const minute = date.getMinutes()
    return `${hour.toString().padStart(2, '0')}:${minute.toString().padStart(2, '0')}`
  },

  checkLogin() {
    const userInfo = wx.getStorageSync('userInfo')
    if (!userInfo) {
      wx.redirectTo({
        url: '/pages/login/login'
      })
      return
    }
    this.setData({
      userInfo: userInfo
    })
  },

  loadData() {
    this.loadElderlyCount()
    this.loadServiceCount()
    this.loadUpcomingActivities()
    this.loadMockTasks()
  },

  loadElderlyCount() {
    request({
      url: '/elderly/list',
      method: 'GET'
    }).then(list => {
      this.setData({
        elderlyCount: list.length || 0
      })
    }).catch(() => {
      // 使用模拟数据
      this.setData({
        elderlyCount: 12
      })
    })
  },

  loadServiceCount() {
    request({
      url: '/service/list',
      method: 'GET'
    }).then(list => {
      this.setData({
        serviceCount: list.length || 0
      })
    }).catch(() => {
      // 使用模拟数据
      this.setData({
        serviceCount: 8
      })
    })
  },

  loadUpcomingActivities() {
    request({
      url: '/activity/upcoming',
      method: 'GET'
    }).then(list => {
      const activities = list.slice(0, 3)
      this.setData({
        recentActivities: activities.length > 0 ? activities : this.getMockActivities(),
        activityCount: list.length || 0
      })
    }).catch(() => {
      // 使用模拟数据
      this.setData({
        recentActivities: this.getMockActivities(),
        activityCount: 5
      })
    })
  },

  loadMockTasks() {
    // 模拟今日关怀任务
    const tasks = [
      { id: 1, type: 'health', name: '张大爷血压测量', time: '09:30', status: 'pending' },
      { id: 2, type: 'visit', name: '李奶奶上门慰问', time: '14:00', status: 'pending' },
      { id: 3, type: 'activity', name: '太极拳活动组织', time: '16:00', status: 'pending' }
    ]
    this.setData({
      todayTasks: tasks
    })
  },

  getMockActivities() {
    return [
      { id: 1, activityName: '健康讲座：秋季养生', location: '社区活动中心', activityDate: '2025-11-28 14:00' },
      { id: 2, activityName: '书法兴趣小组', location: '文化活动室', activityDate: '2025-11-29 09:30' },
      { id: 3, activityName: '集体生日会', location: '多功能厅', activityDate: '2025-11-30 15:00' }
    ]
  },

  navigateToElderly() {
    wx.switchTab({
      url: '/pages/elderly/list/list'
    })
  },

  navigateToService() {
    wx.switchTab({
      url: '/pages/service/list/list'
    })
  },

  navigateToActivity() {
    wx.switchTab({
      url: '/pages/activity/list/list'
    })
  },

  navigateToHealth() {
    wx.navigateTo({
      url: '/pages/health/list/list'
    })
  },

  viewActivityDetail(e) {
    const id = e.currentTarget.dataset.id
    wx.navigateTo({
      url: '/pages/activity/detail/detail?id=' + id
    })
  },

  handleTaskClick(e) {
    const task = e.currentTarget.dataset.task
    wx.showToast({
      title: '任务功能开发中',
      icon: 'none',
      duration: 2000
    })
  }
})
