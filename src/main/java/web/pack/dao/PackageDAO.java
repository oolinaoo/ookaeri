package web.pack.dao;

import java.util.List;

import web.pack.entity.PackageVO;

public interface PackageDAO {
	// 此介面定義對資料庫的相關存取抽象方法
	public void add(PackageVO packagevo);
	public void update(PackageVO packagevo);
	public void delete(int pack_no);
	public PackageVO findByPK(int pack_no);
	public List<PackageVO> getAll();
}
