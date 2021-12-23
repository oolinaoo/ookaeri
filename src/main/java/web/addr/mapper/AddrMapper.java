package web.addr.mapper;

import java.util.List;

import web.addr.entity.AddressVO;

public interface AddrMapper {
	List<AddressVO> listAll();
	Integer insert(AddressVO addressVO);
	//Integer update(AddressVO addressVO);
}
