const { request } = require('../../../utils/request')

Page({
  data: {
    activityList: []
  },

  onLoad() {
    this.loadActivityList()
  },

  onShow() {
    this.loadActivityList()
  },

  loadActivityList() {
    wx.showLoading({ title: '加载中...' })

    request({
      url: '/activity/upcoming',
      method: 'GET'
    }).then(list => {
      wx.hideLoading()
      this.setData({
        activityList: list
      })
    }).catch(() => {
      wx.hideLoading()
    })
  },

  viewDetail(e) {
    const id = e.currentTarget.dataset.id
    wx.navigateTo({
      url: '/pages/activity/detail/detail?id=' + id
    })
  }
})
