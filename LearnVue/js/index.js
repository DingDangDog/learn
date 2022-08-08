var app = new Vue({
	el: "#app",
	data: {
		message: "Hello Vue"
	},
	methods: {
		m_over: function () {
			this.message = "mouseover"
		},
		m_out: function () {
			this.message = "mouseout"
		}
	}
});

var app1 = new Vue({
	el: "#app1",
	data: {
		isactive: 0,
		message: "隐藏"
	},
	methods: {
		hide: function () {
			this.isactive = 1;
			this.message = "显示"
		},
		show: function () {
			this.isactive = 0;
			this.message = "隐藏"
		}
	}
});

var app2 = new Vue({
	el: "#app2",
	data: {
		select: -1,
		sorts: ["箱包", "饰品", "数码", "鞋靴"]
	},
	methods: {
		mouse_over(index) {
			// alert("MouseOver");
			this.select = index;
		},
		mouse_out() {
			this.select = -1;
		}
	}
});
