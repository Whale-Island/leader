package com.leader.game.util;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WordFilter {

	private static final Logger log = LoggerFactory.getLogger(WordFilter.class);
	private volatile List<String> arrt = new ArrayList<String>();// 文字
	private volatile Node rootNode = new Node('R');

	private WordFilter() {
		initWords();
	}

	private static class SigletonHolder {
		static final WordFilter INSTANCE = new WordFilter();
	}

	public static WordFilter getInstance() {
		return SigletonHolder.INSTANCE;
	}

	/**
	 * 过滤敏感词
	 *
	 * @param content
	 * @return
	 */
	public String badWordstFilterStr(String content) {
		char[] chars = content.toCharArray();
		Node node = rootNode;
		StringBuffer buffer = new StringBuffer();
		List<String> badList = new ArrayList<String>();
		int a = 0;
		while (a < chars.length) {
			node = findNode(node, chars[a]);
			if (node == null) {
				node = rootNode;
				a = a - badList.size();
				if (badList.size() > 0) {
					badList.clear();
				}
				buffer.append(chars[a]);
			} else if (node.flag == 1) {
				badList.add(String.valueOf(chars[a]));
				for (int i = 0; i < badList.size(); i++) {
					buffer.append("*");
				}
				node = rootNode;
				badList.clear();
			} else {
				badList.add(String.valueOf(chars[a]));
				if (a == chars.length - 1) {
					for (int i = 0; i < badList.size(); i++) {
						buffer.append(badList.get(i));
					}
				}
			}
			a++;
		}
		return buffer.toString();
	}

	private void initWords() {
		try {
			log.info("initWords() - initWords========================"); //$NON-NLS-1$
			rootNode = new Node('R');
			FileInputStream configis = null;
			String screenWords = null;
			try {
				String configFile = "../serverConfig/screen_word.properties";
				File configfile = new File(configFile);
				Properties configprop = new Properties();
				configis = new FileInputStream(configfile);
				configprop.load(configis);
				screenWords = configprop.getProperty("words");
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			} finally {
				try {
					if (configis != null) {
						configis.close();
					}
				} catch (Exception e) {
					log.error(e.getMessage(), e);
				}
			}
			if (screenWords == null) {
				return;
			}
			String tempstr[] = screenWords.split(",");
			for (int i = 0; i < tempstr.length; i++) {
				String ss = tempstr[i];
				arrt.add(ss.replace("\"", "").trim());
			}
			createTree();
		} catch (Exception e) {
			log.error("敏感词初始化失败", e); //$NON-NLS-1$
		}
	}

	private void createTree() {
		if (log.isDebugEnabled()) {
			log.debug("createTree() - createTree========================"); //$NON-NLS-1$
		}
		for (String str : arrt) {
			char[] chars = str.toCharArray();
			if (chars.length > 0) {
				insertNode(rootNode, chars, 0);
			}
		}
	}

	private void insertNode(Node node, char[] cs, int index) {
		Node n = findNode(node, cs[index]);
		if (n == null) {
			n = new Node(cs[index]);
			node.nodes.put(String.valueOf(cs[index]), n);
		}

		if (index == (cs.length - 1)) {
			n.flag = 1;
		}

		index++;
		if (index < cs.length) {
			insertNode(n, cs, index);
		}
	}

	/**
	 * 名字是否有不允许字符
	 *
	 * @param content
	 * @return true有坏字 false 没有
	 */
	public boolean hashNoLimitedWords(String content) {
		char[] chars = content.toCharArray();
		for (char c : chars) {
			// c是汉字
			if ((c >= 0x4e00) && (c <= 0x9fbb)) {
				continue;
			}
			// c是数字或英文
			if (Character.isLetterOrDigit(c)) {
				continue;
			}
			return true;
		}
		return false;
	}

	/**
	 * 是否有坏字
	 *
	 * @param content
	 * @return true有坏字 false 没有
	 */
	public boolean hashBadWords(String content) {
		char[] chars = content.toCharArray();
		Node node = rootNode;
		StringBuffer buffer = new StringBuffer();
		List<String> word = new ArrayList<String>();
		int a = 0;
		while (a < chars.length) {
			node = findNode(node, chars[a]);
			if (node == null) {
				node = rootNode;
				a = a - word.size();
				buffer.append(chars[a]);
				word.clear();
			} else if (node.flag == 1) {
				node = null;
				return true;
			} else {
				word.add(String.valueOf(chars[a]));
			}
			a++;
		}
		chars = null;
		word.clear();
		word = null;
		return false;
	}

	private Node findNode(Node node, char c) {
		return node.nodes.get(String.valueOf(c));
	}

	private static class Node {

		@SuppressWarnings("unused")
		public char c;
		public int flag;
		public HashMap<String, Node> nodes = new HashMap<String, Node>();

		public Node(char c) {
			this.c = c;
			this.flag = 0;
		}

		@SuppressWarnings("unused")
		public Node(char c, int flag) {
			this.c = c;
			this.flag = flag;
		}
	}
}
