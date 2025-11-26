const { request } = require('../../../utils/request')

Page({
  data: {
    activity: null
  },

  onLoad(options) {
    const id = options.id
    if (id) {
      this.loadActivityDetail(id)
    }
  },

  loadActivityDetail(id) {
    wx.showLoading({ title: '加载中...' })

    request({
      url: '/activity/get/' + id,
      method: 'GET'
    }).then(activity => {
      wx.hideLoading()
      this.setData({
        activity: activity
      })
    }).catch(() => {
      wx.hideLoading()
    })
  },

  handleRegister() {
    wx.showToast({
      title: '报名功能开发中',
      icon: 'none'
    })
  }
})
