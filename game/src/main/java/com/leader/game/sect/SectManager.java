package com.leader.game.sect;

import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;

import com.leader.core.db.CommonDao;
import com.leader.core.server.model.ShutdownListener;
import com.leader.core.util.TextKit;
import com.leader.game.player.model.Player;
import com.leader.game.player.model.RandomName;
import com.leader.game.protobuf.protocol.RoleProtocol.RoleInfo;
import com.leader.game.protobuf.protocol.SectProtocol.ResCreatSectMessage;
import com.leader.game.protobuf.protocol.SectProtocol.SectInfo;
import com.leader.game.role.RoleManager;
import com.leader.game.role.model.Role;
import com.leader.game.sect.dao.SectDao;
import com.leader.game.sect.model.Sect;
import com.leader.game.server.model.AttributeKeys;
import com.leader.game.util.WordFilter;

import io.netty.channel.Channel;
import io.netty.util.internal.StringUtil;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/** 门派管理 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SectManager implements ShutdownListener {
	private static class SigletonHolder {
		static final SectManager INSTANCE = new SectManager();
	}

	public static SectManager getInstance() {
		return SigletonHolder.INSTANCE;
	}

	/** 角色名表达式 */
	private static final String regEx = "^[\u4e00-\u9fa5_a-zA-Z0-9]+$";
	private static final Pattern pattern = Pattern.compile(regEx);
	/** 所有门派名 */
	private Set<String> sectNames = Collections.synchronizedSet(new HashSet<String>());
	/** 门派随机名 */
	private RandomName scetRandom;
	@Autowired
	SectDao dao;
	@Autowired
	CommonDao commonDao;

	public void init() {
		List<String> strings = dao.loadName();
		if (strings != null && !strings.isEmpty()) {
			sectNames.addAll(strings);
		}
		scetRandom = new RandomName();
	}

	/** 创建门派 */
	public Sect creatSect(Channel channel, String name, ResCreatSectMessage.Builder response) {
		try {
			Player player = channel.attr(AttributeKeys.PLAYER).get();
			if (player == null) {
				response.setCode(-1);// 未建立角色
				return null;
			}

			if (!StringUtil.isNullOrEmpty(name) && nameIsAvailable(name)) {
				response.setCode(1);// 门派名称非法或已被占用
				return null;
			}
			sectNames.add(name);
			Sect sect = new Sect();
			sect.setPlayerId(player.getId());
			sect.setName(name);

			List<Role> roles = RoleManager.getInstance().allotRole(player.getNickname(), player.getSex());
			if (roles == null || roles.size() == 0) {
				response.setCode(2);// 初始化门派失败
				return null;
			}
			sect.setRoles(roles);

			commonDao.store(sect);
			return sect;
		} catch (Exception e) {
			response.setCode(1);// 门派名称非法或已被占用
			log.error(e.getMessage(), e);
		}
		return null;
	}

	/** 寻找门派 */
	public Sect findSect(long id) {
		return dao.findByPlayerID(id);
	}

	/** 随机一个名字 */
	public String randomName() {
		for (int i = 0; i < 20; i++) {
			String name = scetRandom.getName();
			if (!sectNames.contains(name)) {
				return name;
			}
		}
		return TextKit.replace(UUID.randomUUID().toString(), "-", "");
	}

	/**
	 * 昵称是否合法
	 * 
	 * @param name
	 * @return 1不合法 2昵称重复 3门派名重复
	 */
	public boolean nameIsAvailable(String name) {
		if (WordFilter.getInstance().hashBadWords(name)) {
			return true;
		}
		Matcher m = pattern.matcher(name);
		if (!m.matches()) {
			return true;
		}
		if (sectNames.contains(name)) {
			return true;
		}
		return false;
	}

	/** 将门派装箱为protobuf */
	public SectInfo.Builder packSect(Sect sect) {
		SectInfo.Builder builder = SectInfo.newBuilder();
		builder.setId(sect.getId());
		builder.setPlayerId(sect.getPlayerId());
		builder.setName(sect.getName());
		builder.setLevel(sect.getLevel());
		builder.setExp(sect.getExp());
		builder.setPrestige(sect.getPrestige());
		builder.setGold(sect.getGold());
		builder.setDiamond(sect.getDiamond());

		List<RoleInfo.Builder> roles = builder.getRolesBuilderList();
		Iterator<Role> iterator = sect.getRoles().iterator();
		while (iterator.hasNext()) {
			Role role = iterator.next();
			roles.add(RoleManager.getInstance().packRole(role));
		}
		return builder;
	}

	@Override
	public void shutdown() {
		log.debug("门派管理器已关闭!");
	}

}
