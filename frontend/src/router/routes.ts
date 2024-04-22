import CustomerView from '../views/CustomerView.vue';

export default [
	{
		path: '/',
		name: 'home',
		component: CustomerView,
		meta: {
			requiresAuth: true,
			title: "Customer",
		},
	},
];