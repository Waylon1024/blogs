<template>
  <div>
    <div class="button-container">
      <button href="javascript:;" @click.prevent="goToMore" class="color-btn">查看更多</button>
    </div>
  </div>
</template>

<script>
export default {
  methods: {
    goToMore() {
      this.$router.push({name: 'blogAll'})
    }
  }
}
</script>

<style scoped>

</style>