Page({
  data: {
    userInfo: null,
    menuItems: [
      {
        id: 'message',
        title: '我的消息',
        icon: 'message',
        badge: 3
      },
      {
        id: 'settings',
        title: '系统设置',
        icon: 'settings',
        badge: 0
      },
      {
        id: 'help',
        title: '使用帮助',
        icon: 'help',
        badge: 0
      },
      {
        id: 'about',
        title: '关于我们',
        icon: 'about',
        badge: 0
      }
    ]
  },

  onLoad() {
    const userInfo = wx.getStorageSync('userInfo')
    this.setData({
      userInfo: userInfo
    })
  },

  onShow() {
    const userInfo = wx.getStorageSync('userInfo')
    this.setData({
      userInfo: userInfo
    })
  },

  handleMenuItem(e) {
    const id = e.currentTarget.dataset.id
    wx.showToast({
      title: '功能开发中',
      icon: 'none'
    })
  },

  handleLogout() {
    wx.showModal({
      title: '确认退出',
      content: '确定要退出登录吗？',
      confirmText: '确定',
      cancelText: '取消',
      confirmColor: '#FF7F50',
      success: (res) => {
        if (res.confirm) {
          wx.clearStorageSync()
          wx.reLaunch({
            url: '/pages/login/login'
          })
        }
      }
    })
  }
})
