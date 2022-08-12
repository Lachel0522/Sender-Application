package com.bistel.acovery.asset.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.bistel.acovery.asset.model.AssetParameter;

public interface AssetMapper {

	public List<AssetParameter> selectAssetParameter(@Param("assetId") String assetId);

	public String selectAssetName(@Param("assetId") String assetId);

}
