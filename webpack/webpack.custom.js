const path = require('path');
const webpack = require('webpack');
const { merge } = require('webpack-merge');
const BrowserSyncPlugin = require('browser-sync-webpack-plugin');
const { BundleAnalyzerPlugin } = require('webpack-bundle-analyzer');
const WebpackNotifierPlugin = require('webpack-notifier');
const CopyWebpackPlugin = require('copy-webpack-plugin');

const environment = require('./environment');
const proxyConfig = require('./proxy.conf');

module.exports = async (config, options, targetOptions) => {
  // PLUGINS
  if (config.mode === 'development') {
    config.plugins.push(
      new WebpackNotifierPlugin({
        title: 'Parking Garage Application',
      }),
    );
  }

  // configuring proxy for back end service
  const tls = config.devServer?.server?.type === 'https';
  if (config.devServer) {
    config.devServer.proxy = proxyConfig({ tls });
  }

  if (targetOptions.target === 'serve' || config.watch) {
    config.plugins.push(
      new BrowserSyncPlugin(
        {
          host: 'localhost',
          port: 9000,
          https: tls,
          proxy: {
            target: `http${tls ? 's' : ''}://localhost:${targetOptions.target === 'serve' ? '4200' : '8080'}`,
            ws: true,
            proxyOptions: {
              changeOrigin: false, //pass the Host header to the backend unchanged  https://github.com/Browsersync/browser-sync/issues/430
            },
            proxyReq: [
              function (proxyReq) {
                // URI that will be retrieved by the ForwardedHeaderFilter on the server side
                proxyReq.setHeader('X-Forwarded-Host', 'localhost:9000');
                proxyReq.setHeader('X-Forwarded-Proto', 'https');
              },
            ],
          },
          socket: {
            clients: {
              heartbeatTimeout: 60000,
            },
          },
        },
        {
          reload: targetOptions.target === 'build', // enabled for build --watch
        },
      ),
    );
  }

  if (config.mode === 'production') {
    config.plugins.push(
      new BundleAnalyzerPlugin({
        analyzerMode: 'static',
        openAnalyzer: false,
        // Webpack statistics in temporary folder
        reportFilename: '../../../stats.html',
      }),
    );
  }

  config.plugins.push(
    new webpack.DefinePlugin({
      __VERSION__: JSON.stringify(environment.__VERSION__),
      __DEBUG_INFO_ENABLED__: environment.__DEBUG_INFO_ENABLED__ || config.mode === 'development',
      SERVER_API_URL: JSON.stringify(environment.SERVER_API_URL),
    }),
  );

  config = merge(
    config,
  );

  return config;
};
