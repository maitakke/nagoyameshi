package com.example.nagoyameshi.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.nagoyameshi.entity.RegularHoliday;
import com.example.nagoyameshi.entity.RegularHolidayRestaurant;
import com.example.nagoyameshi.entity.Restaurant;

public interface RegularHolidayRestaurantRepository extends JpaRepository<RegularHolidayRestaurant, Integer>{
	// 指定した店舗の定休日のid（RegularHolidayエンティティのid）をリスト形式で取得するメソッド
	@Query("SELECT rhr.regularHoliday.id FROM RegularHolidayRestaurant rhr WHERE rhr.restaurant = :restaurant")
	public List<Integer> findRegularHolidayIdsByRestaurant(Restaurant restaurant);
	
	// 指定した店舗と定休日が紐づいたRegularHolidayRestaurantエンティティを取得するメソッド
	   public Optional<RegularHolidayRestaurant> findByRegularHolidayAndRestaurant(RegularHoliday regularHoliday, Restaurant restaurant);
	
	// 指定した店舗に紐づくRegularHolidayRestaurantエンティティをリスト形式で取得するメソッド
	public List<RegularHolidayRestaurant> findByRestaurant(Restaurant restaurant);

}
