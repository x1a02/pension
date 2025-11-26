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
    wx.showLoading({ title: 'Loading...' })

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
      title: 'Registration feature coming soon',
      icon: 'none'
    })
  }
})
