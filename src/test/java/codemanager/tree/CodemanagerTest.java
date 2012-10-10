package codemanager.tree;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cei.codemanager.Codes;
import cei.codemanager.Trees;
import cei.domains.SystemCode;

import code.config.TestCodemanagerConfiguration;
import db.config.TestDBConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestCodemanagerConfiguration.class, TestDBConfiguration.class})
public class CodemanagerTest {

	@Autowired
	Trees trees;
	
	@Autowired
	Codes codes;
	
	@Test
	public void test() throws Throwable {
		List<SystemCode> tree = trees.getTree("SAMPLE", "MENU", 1);

		for(SystemCode branch : tree) {
			System.out.println(branch);
		}
		
	}

//	@Test
	public void test2() throws Throwable {
		List<SystemCode> codes = this.codes.getCodes("SAMPLE", "MENU");
		
		for(SystemCode code : codes) {
			System.out.println(code);
		}
	}
	
//	@Test
	public void test3() {
		System.out.println(codes.getCode("SAMPLE", "MENU"));
	}
}
