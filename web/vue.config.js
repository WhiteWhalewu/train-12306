const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
    transpileDependencies: true,
    devServer: {
        proxy: {
            '/api': {        //表示会拦截以api为开头的请求
                target: 'http://localhost:8000/api',    //表示需要请求的目标url
                changeOrigin: true,    // 是否开启跨域
                pathRewrite: {        //重写路径
                    '^/api': ''       //把/api开头的变为空
                }
            }
        }
    }


})
