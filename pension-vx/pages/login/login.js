const { request } = require('../../utils/request')

Page({
  data: {
    username: '',
    password: ''
  },

  onLoad() {
    const userInfo = wx.getStorageSync('userInfo')
    if (userInfo) {
      wx.switchTab({
        url: '/pages/index/index'
      })
    }
  },

  onUsernameInput(e) {
    this.setData({
      username: e.detail.value
    })
  },

  onPasswordInput(e) {
    this.setData({
      password: e.detail.value
    })
  },

  handleLogin() {
    if (!this.data.username) {
      wx.showToast({
        title: '请输入账号',
        icon: 'none'
      })
      return
    }

    if (!this.data.password) {
      wx.showToast({
        title: '请输入密码',
        icon: 'none'
      })
      return
    }

    wx.showLoading({
      title: '登录中...'
    })

    // 模拟登录：admin/admin123 直接成功
    if (this.data.username === 'admin' && this.data.password === 'admin123') {
      wx.hideLoading()
      const mockUser = {
        id: 1,
        username: 'admin',
        realName: '管理员',
        role: 'admin'
      }
      wx.setStorageSync('userInfo', mockUser)
      wx.showToast({
        title: '登录成功',
        icon: 'success'
      })
      setTimeout(() => {
        wx.switchTab({
          url: '/pages/index/index'
        })
      }, 1500)
      return
    }

    // 尝试真实登录
    request({
      url: '/user/login',
      method: 'POST',
      data: {
        username: this.data.username,
        password: this.data.password
      }
    }).then(user => {
      wx.hideLoading()
      wx.setStorageSync('userInfo', user)
      wx.showToast({
        title: '登录成功',
        icon: 'success'
      })
      setTimeout(() => {
        wx.switchTab({
          url: '/pages/index/index'
        })
      }, 1500)
    }).catch(() => {
      wx.hideLoading()
      wx.showToast({
        title: '账号或密码错误',
        icon: 'none'
      })
    })
  }
})
