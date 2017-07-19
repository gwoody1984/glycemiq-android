/*
 * Copyright (C) 2016 Saurabh Rane
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.eveningoutpost.dexdrip.Glycemiq.FoodApi.android;

import com.eveningoutpost.dexdrip.Glycemiq.FoodApi.model.Food;

/** 
 * Callback listener interface for delivering parsed response
 *
 * @author Saurabh Rane
 * @version 2.0
 */
public interface ResponseListener {
	
	/** 
	 * Called when a food response is received.
	 *
	 * @param food			the food item from the response
	 */
	public void onFoodResponse(Food food);
}