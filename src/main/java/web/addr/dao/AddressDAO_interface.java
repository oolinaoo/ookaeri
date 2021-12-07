package web.addr.dao;

import java.util.List;

import web.addr.entity.AddressVO;

public interface AddressDAO_interface {
	public String insert(AddressVO addressVO);
    public void update(AddressVO addressVO);
    public Integer delete(Integer addrNo);
    public AddressVO findByPrimaryKey(Integer addrNo);
    public List<AddressVO> getAll();

}
