/*
 * Designed and developed by 2020 skydoves (Jaewoong Eum)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.skydoves.sandwich.datasource.adapters.internal

import com.skydoves.sandwich.datasource.DataSource
import com.skydoves.sandwich.datasource.ResponseDataSource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * @author skydoves (Jaewoong Eum)
 *
 * DataSourceCallDelegate is a delegate [Call] proxy for handling and transforming normal generic type [T]
 * as [DataSource] that wrapping [T] data from the network responses.
 */
internal class DataSourceCallDelegate<T>(proxy: Call<T>) : CallDelegate<T, DataSource<T>>(proxy) {

  override fun enqueueImpl(callback: Callback<DataSource<T>>) {
    val responseDataSource = ResponseDataSource<T>()
      .combine(proxy, null)
    callback.onResponse(this@DataSourceCallDelegate, Response.success(responseDataSource))
  }

  override fun executeImpl(): Response<DataSource<T>> {
    val responseDataSource = ResponseDataSource<T>()
      .combine(proxy, null)
    return Response.success(responseDataSource)
  }

  override fun cloneImpl() = DataSourceCallDelegate(proxy.clone())
}
