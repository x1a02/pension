const { request } = require('../../../utils/request')

Page({
  data: {
    serviceList: [],
    categories: []
  },

  onLoad() {
    this.loadServiceList()
  },

  onShow() {
    this.loadServiceList()
  },

  loadServiceList() {
    wx.showLoading({ title: '加载中...' })

    request({
      url: '/service/list',
      method: 'GET'
    }).then(list => {
      wx.hideLoading()
      this.setData({
        serviceList: list
      })
    }).catch(() => {
      wx.hideLoading()
    })
  },

  viewDetail(e) {
    const id = e.currentTarget.dataset.id
    wx.navigateTo({
      url: '/pages/service/detail/detail?id=' + id
    })
  }
})
