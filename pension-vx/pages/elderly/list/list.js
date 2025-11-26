const { request } = require('../../../utils/request')

Page({
  data: {
    elderlyList: [],
    searchQuery: ''
  },

  onLoad() {
    this.loadElderlyList()
  },

  onShow() {
    this.loadElderlyList()
  },

  loadElderlyList() {
    wx.showLoading({ title: '加载中...' })

    request({
      url: '/elderly/list',
      method: 'GET'
    }).then(list => {
      wx.hideLoading()
      this.setData({
        elderlyList: list
      })
    }).catch(() => {
      wx.hideLoading()
    })
  },

  onSearchInput(e) {
    this.setData({
      searchQuery: e.detail.value
    })
  },

  handleSearch() {
    if (!this.data.searchQuery) {
      this.loadElderlyList()
      return
    }

    wx.showLoading({ title: '搜索中...' })

    request({
      url: '/elderly/search?name=' + this.data.searchQuery,
      method: 'GET'
    }).then(list => {
      wx.hideLoading()
      this.setData({
        elderlyList: list
      })
    }).catch(() => {
      wx.hideLoading()
    })
  },

  viewDetail(e) {
    const id = e.currentTarget.dataset.id
    wx.navigateTo({
      url: '/pages/elderly/detail/detail?id=' + id
    })
  }
})
