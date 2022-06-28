package rs.ac.bg.etf.pp1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

import java_cup.runtime.Symbol;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

import rs.ac.bg.etf.pp1.ast.Program;
import rs.ac.bg.etf.pp1.util.Log4JUtils;
import rs.etf.pp1.mj.runtime.Code;
import rs.etf.pp1.symboltable.Tab;

public class Compiler {

	static {
		DOMConfigurator.configure(Log4JUtils.instance().findLoggerConfigFile());
		Log4JUtils.instance().prepareLogFile(Logger.getRootLogger());
	}

	public static void main(String[] args) throws Exception {

		Logger log = Logger.getLogger(Compiler.class);

		if (args.length < 2) {
			log.error("Neispravni argumenti. Ispravno koriscenje: Compiler <source-file> <obj-file>");
			return;
		}

		File sourceCode = new File(args[0]);
		if (!sourceCode.exists()) {
			log.error("fajl sa izvornim kodom [" + sourceCode.getAbsolutePath() + "] ne postoji.");
			return;
		}

		log.info("Kompajliranje fajla: " + sourceCode.getAbsolutePath());

		Reader br = null;
		try {
			log.info("===================================");
			log.info("Leksicka analiza");

			br = new BufferedReader(new FileReader(sourceCode));
			Yylex lexer = new Yylex(br);

			log.info("===================================");
			log.info("Sintaksna analiza");
			MJParser p = new MJParser(lexer);
			Symbol s = p.parse(); // pocetak parsiranja

			if (!p.errorDetected) {
				log.info("Parsiranje uspesno zavrseno.");

				Program prog = (Program) (s.value);

				// ispis sintaksnog stabla
				log.info(prog.toString(""));

				log.info("===================================");
				log.info("Semanticka analiza:");
				ExtendedTab.extendedInit();

				SemanticAnalyzer semanticAnalyzer = new SemanticAnalyzer();
				prog.traverseBottomUp(semanticAnalyzer);

				if (!semanticAnalyzer.errorDetected) {
					log.info("Semanticka analiza uspesno zavrsena.");
					log.info("===================================");
					log.info("Tabela simbola:");
					tsdump();
					log.info("===================================");
					File objFile = new File(args[1]);
					if (objFile.exists())
						objFile.delete();
					log.info("Generisanje koda: " + objFile.getAbsolutePath());

					CodeGenerator codeGen = new CodeGenerator();
					codeGen.nVars = semanticAnalyzer.nVars;
					codeGen.vftStart = codeGen.nVars;
					codeGen.classConstructors = semanticAnalyzer.classConstructors;
					codeGen.classTree = semanticAnalyzer.classTree;
					prog.traverseBottomUp(codeGen);
					Code.dataSize = codeGen.nVars;
					Code.mainPc = codeGen.mainPc;
					Code.write(new FileOutputStream(objFile));
					log.info("Kompajliranje uspesno zavrseno.");
				} else {
					log.error("Semanticka analiza neuspesno zavrsena.");
				}
			} else {
				log.error("Parsiranje neuspesno zavrseno.");
			}
		} finally {
			if (br != null)
				try {
					br.close();
				} catch (IOException e1) {
					log.error(e1.getMessage(), e1);
				}
		}

	}

	public static void tsdump() {
		Tab.dump(new ExtendedDumpSymbolTableVisitor());
	}
}
