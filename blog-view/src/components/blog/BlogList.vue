<template>
	<div>
		<!--content-->
		<BlogItem :blogList="blogList" v-viewer/>
		<!--分页-->
<!--		<Pagination :getBlogList="getBlogList" :totalPage="totalPage"/>-->
  <InfoMore></InfoMore>
	</div>
</template>

<script>
	import BlogItem from "./BlogItem";
	import Pagination from "./Pagination";
  	import InfoMore from "@/components/blog/infoMore";
	export default {
		name: "BlogList",
		components: {InfoMore, Pagination, BlogItem},
		props: {
			getBlogList: {
				type: Function,
				required: true
			},
			blogList: {
				type: Array,
				required: true
			},
			totalPage: {
				type: Number,
				required: true
			}
		}
	}
</script>

<style scoped>

</style>