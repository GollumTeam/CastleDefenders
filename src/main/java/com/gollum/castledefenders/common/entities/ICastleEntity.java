package com.gollum.castledefenders.common.entities;

public interface ICastleEntity {
	
	/**
	 * @return Vitesse du mod
	 */
	double getMoveSpeed ();
	/**
	 * @return Point de vie du mod
	 */
	double getMaxHealt ();
	/**
	 * @return Point de vie du mod
	 */
	double getAttackStrength();
	/**
	 * @return Zone de detection du mod
	 */
	double getFollowRange();
	/**
	 * @return Vitesse de tir du mod
	 */
	double getTimeRange();
	
}
