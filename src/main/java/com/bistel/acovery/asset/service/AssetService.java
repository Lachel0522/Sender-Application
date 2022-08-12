package com.bistel.acovery.asset.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.SqlSession;

import com.bistel.acovery.asset.mapper.AssetMapper;
import com.bistel.acovery.asset.model.Asset;
import com.bistel.acovery.asset.model.AssetParameter;
import com.bistel.acovery.asset.session.AssetSqlSessionManager;

public class AssetService {

	public static Asset getAsset(String assetId) throws Exception {
		Asset asset = null;
		try (SqlSession sqlSession = AssetSqlSessionManager.getSqlSession()) {
			AssetMapper assetMapper = sqlSession.getMapper(AssetMapper.class);

			String assetName = assetMapper.selectAssetName(assetId);
			if (StringUtils.isBlank(assetName))
				throw new Exception("not found asset name with assetId. assetId = '" + assetId + "'");
			List<AssetParameter> assetParameterList = assetMapper.selectAssetParameter(assetId);
			if (assetParameterList.isEmpty())
				throw new Exception("not found parameter is modeled to asset with assetId. assetId = '" + assetId + "'");
			Map<String, String> parameterIdMap = assetParameterListToIdMap(assetParameterList);

			asset = new Asset(assetId, assetName, parameterIdMap);

		} catch (Exception e) {
			throw e;
		}
		return asset;
	}

	private static Map<String, String> assetParameterListToIdMap(List<AssetParameter> list) {
		return list.stream().collect(Collectors.toMap(AssetParameter::getId, AssetParameter::getName));
	}
}
